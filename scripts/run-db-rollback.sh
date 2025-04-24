#!/bin/bash
BATCH_ID=$1
# Perform rollback using $BATCH_ID
if [[ -z "$BATCH_ID" ]]; then
  echo "❌ No batch_id provided. Aborting rollback."
  exit 1
fi
ROLLBACK_DONE=$(echo "$OUTPUT" | jq -r '.CommandInvocations[0].CommandPlugins[0].Output' | grep -oP '(?<=rollback_done=)\w+')
