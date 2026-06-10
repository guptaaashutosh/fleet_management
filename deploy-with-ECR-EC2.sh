#!/bin/bash

set -e

echo "=================================="
echo "Starting Fleet Management Deploy Using ECR and EC2"
echo "=================================="

# Configuration
export AWS_REGION="us-east-1"
export ECR_REPOSITORY="fleet-management"

# Get AWS Account ID dynamically
export AWS_ACCOUNT_ID=$(aws sts get-caller-identity \
  --query Account \
  --output text)

echo "AWS_ACCOUNT_ID=$AWS_ACCOUNT_ID"
echo $AWS_ACCOUNT_ID

echo "AWS Account ID: $AWS_ACCOUNT_ID"

# Login to ECR
echo "Logging in to ECR..."

aws ecr get-login-password --region $AWS_REGION | \
docker login \
  --username AWS \
  --password-stdin \
  $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com

# Pull latest image
echo "Pulling latest image..."

docker pull \
$AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$ECR_REPOSITORY:latest

# Stop existing containers
echo "Stopping old containers..."

docker compose down || true

# Remove dangling images (optional)
docker image prune -f

# Start containers
echo "Starting containers..."

docker compose up -d

# Show running containers
echo "Running containers:"
docker ps

echo "=================================="
echo "Deployment Completed Successfully"
echo "=================================="