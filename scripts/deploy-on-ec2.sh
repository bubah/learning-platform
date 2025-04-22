#!/bin/bash

set -e  # Exit on error

echo "🔑 Fetching environment variables from SSM..."

# === 1. Set Environment Variables ===
ENV_PATH="/lp/dev/"
ENV_FILE="/etc/profile.d/springboot_env.sh"

echo "Fetching SSM parameters from path: ${ENV_PATH}"

# Ensure the jq tool is available
if ! command -v jq &> /dev/null; then
    echo "❌ jq not found. Installing jq before running this script."
    yum install -y jq  # Amazon Linux
    apt-get install -y jq  # Ubuntu
fi

PARAMS=$(aws ssm get-parameters-by-path --path "$ENV_PATH" --recursive --with-decryption --region us-east-1)

# Clear and recreate the environment file
echo "# Auto-generated env vars for Spring Boot app" > "$ENV_FILE"

# Parse and write env vars
echo "$PARAMS" | jq -r '.Parameters[] | "\(.Name)=\(.Value)"' | while IFS='=' read -r full_name value; do
    var_name=$(basename "$full_name")
    echo "export $var_name=\"$value\"" >> "$ENV_FILE"
done

chmod +x "$ENV_FILE"  # Ensure it can be sourced

echo "📦 Downloading JAR from S3..."
aws s3 cp s3://lp-mediaconvstack-artifact-repo-dev-805358685077/learning-platform/dev/{{COMMIT_HASH}}/learning-platform.jar /opt/myapp/app.jar

echo "🚀 Setting up systemd service..."

# === 2. Create systemd service ===
cat > /etc/systemd/system/springboot-app.service <<EOF
[Unit]
Description=Spring Boot Application
After=network.target

[Service]
User=root
EnvironmentFile=-$ENV_FILE
ExecStart=/usr/bin/java -jar /opt/myapp/app.jar
Restart=always
RestartSec=5
StandardOutput=journal
StandardError=journal
SyslogIdentifier=springboot-app

[Install]
WantedBy=multi-user.target
EOF

# === 3. Reload, Enable, Start ===
systemctl daemon-reexec
systemctl daemon-reload
systemctl enable springboot-app
systemctl restart springboot-app

# === 4. Status Check ===
if systemctl is-active --quiet springboot-app; then
    echo "✅ Spring Boot app is running"
else
    echo "❌ Spring Boot app failed to start"
    journalctl -u springboot-app --no-pager -n 20
fi