# Fleet Management Deployment Execution Steps

## Step 1: Build and Test Locally with Docker Compose

Start the application and PostgreSQL containers:

```bash
docker compose up --build -d
```

Verify containers are running:

```bash
docker ps
```

Check application logs:

```bash
docker logs fleet-management
```

Verify application health:

```bash
curl http://localhost:8082/actuator/health
```

Expected Response:

```json
{"status":"UP"}
```

---

## Step 2: Stop Docker Compose

To run everything in Kubernetes instead of Docker Compose:

```bash
docker compose down
```

---

## Step 3: Deploy to Kubernetes (Minikube)

Give execute permission to the deployment script:

```bash
chmod +x deploy.sh
```

Run deployment:

```bash
./deploy.sh
```

This script performs:

* Docker image build
* Load image into Minikube
* Deploy PostgreSQL
* Create PostgreSQL Service
* Deploy Fleet Management application
* Create Fleet Management Service
* Display application URL

---

## Step 4: Verify Deployment

Check deployments:

```bash
kubectl get deployments
```

Check pods:

```bash
kubectl get pods
```

Check services:

```bash
kubectl get svc
```

View application logs:

```bash
kubectl logs deployment/fleet-management
```

Access application:

```bash
minikube service fleet-management-service --url
```

Health endpoint:

```bash
http://<MINIKUBE_URL>/actuator/health
```

Prometheus endpoint:

```bash
http://<MINIKUBE_URL>/actuator/prometheus
```

---

## Step 5: Cleanup Kubernetes Resources

Give execute permission to cleanup script:

```bash
chmod +x cleanup.sh
```

Run cleanup:

```bash
./cleanup.sh
```

This removes:

* Fleet Management Deployment
* Fleet Management Service
* PostgreSQL Deployment
* PostgreSQL Service

Verify cleanup:

```bash
kubectl get all
```

Flow diagram 
```text
Spring Boot Application
          │
          ▼
Docker Build
          │
          ▼
Docker Image
          │
          ▼
Minikube Image Load
          │
          ▼
Fleet Management Deployment
          │
          ▼
Fleet Management Service
          │
          ▼
PostgreSQL Service
          │
          ▼
PostgreSQL Deployment
          │
          ▼
fleetdb Database
          │
          ▼
Application Access via Minikube Service URL
```
