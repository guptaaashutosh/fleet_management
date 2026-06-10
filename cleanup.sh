#!/bin/bash

kubectl delete -f k8s/fleet-management-service.yaml --ignore-not-found
kubectl delete -f k8s/fleet-management-deployment.yaml --ignore-not-found

kubectl delete -f k8s/postgres-service.yaml --ignore-not-found
kubectl delete -f k8s/postgres-deployment.yaml --ignore-not-found