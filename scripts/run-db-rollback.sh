#!/bin/bash

LOG_FILE="/var/log/springboot-rollback.log"
exec 1>>"$LOG_FILE" 2>&1

set -euo pipefail
trap 'echo "❌ Rollback script failed on line $LINENO"' ERR

# === 1. Load Environment Variables ===
ENV_FILE="/etc/profile.d/springboot_env.sh"
if [[ -f "$ENV_FILE" ]]; then
  source "$ENV_FILE"
else
  echo "❌ Environment file not found at $ENV_FILE"
  exit 1
fi

# === 2. Set DB Vars ===
DB_HOST="$LEARNING_PLATFORM_DB_HOST"
DB_USER="$LEARNING_PLATFORM_DB_USER"
DB_NAME="$LEARNING_PLATFORM_DB_NAME"
DB_PASSWORD="$LEARNING_PLATFORM_DB_PASSWORD"
export PGPASSWORD="$DB_PASSWORD"

# === 3. Get Batch ID ===
BATCH_ID="{{BATCH_ID}}"
if [[ -z "$BATCH_ID" ]]; then
  echo "❌ No batch_id provided. Aborting rollback."
  exit 1
fi

echo "🔄 Starting rollback for batch_id: $BATCH_ID"

# === 4. Fetch Migrations to Rollback ===
ROLLBACK_VERSIONS=$(psql -h "$DB_HOST" -U "$DB_USER" -d "$DB_NAME" -t -c "
  SELECT migration_version
  FROM schema_rollback_log
  WHERE batch_id = $BATCH_ID
  ORDER BY applied_at DESC
" | xargs)

if [[ -z "$ROLLBACK_VERSIONS" ]]; then
  echo "⚠️ No migrations found to rollback for batch_id $BATCH_ID"
  exit 0
fi

# === 5. Rollback Each Version ===
FLYWAY_DIR="/tmp/flyway/sql"  # Update with your actual Flyway SQL directory path

for version in $ROLLBACK_VERSIONS; do
  echo "⏪ Rolling back migration: $version"

  # Find the undo script for the version
  matching_files=("$FLYWAY_DIR"/U"${version}"__*.sql)

  if [[ ${#matching_files[@]} -eq 0 ]]; then
    echo "❌ No undo script found for version $version"
    exit 1
  elif [[ ${#matching_files[@]} -gt 1 ]]; then
    echo "❌ Multiple undo scripts found for version $version. Manual intervention required."
    printf '%s\n' "${matching_files[@]}"
    exit 1
  fi

  UNDO_SCRIPT="${matching_files[0]}"
  echo "📄 Executing undo script: $(basename "$UNDO_SCRIPT")"
  psql -h "$DB_HOST" -U "$DB_USER" -d "$DB_NAME" -f "$UNDO_SCRIPT"

  # === 6. Delete Flyway schema history entry ===
  echo "🗑️ Deleting Flyway schema history entry for version: $version"
  psql -h "$DB_HOST" -U "$DB_USER" -d "$DB_NAME" -c "
    DELETE FROM flyway_schema_history WHERE version = '$version';
  "
done

# === 7. Confirm Rollback Success ===
echo "✅ Rollback for batch $BATCH_ID completed successfully."

# === 8. Output Flag for GitHub Actions ===
echo "rollback_done=true"
echo "rolled_back_batch_id=$BATCH_ID"
