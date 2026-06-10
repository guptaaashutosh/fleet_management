#!/bin/bash

set -e

echo "====================================="
echo "Building Docker Image"
echo "====================================="

docker build -t fleet-management:latest .

echo "====================================="
echo "Loading Image into Minikube"
echo "====================================="

minikube image load fleet-management:latest

echo "====================================="
echo "Deploying PostgreSQL"
echo "====================================="

kubectl apply -f k8s/postgres-deployment.yaml
kubectl apply -f k8s/postgres-service.yaml

echo "====================================="
echo "Waiting for PostgreSQL"
echo "====================================="

kubectl rollout status deployment/postgres

echo "====================================="
echo "Deploying Fleet Management"
echo "====================================="

kubectl apply -f k8s/fleet-management-deployment.yaml
kubectl apply -f k8s/fleet-management-service.yaml

echo "====================================="
echo "Waiting for Fleet Management"
echo "====================================="

kubectl rollout status deployment/fleet-management

echo "====================================="
echo "Resources"
echo "====================================="

kubectl get deployments
kubectl get pods -o wide
kubectl get svc

echo "====================================="
echo "Application URL"
echo "====================================="

minikube service fleet-management-service --url

echo "====================================="
echo "Deployment Completed Successfully"
echo "====================================="