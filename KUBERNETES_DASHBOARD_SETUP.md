## Kubernetes Dashboard Setup (Minikube)

If Minikube is already installed and running, Kubernetes Dashboard can be enabled using the built-in Minikube addon.

### Verify Minikube Status

```bash
minikube status
```

Expected output:

```text
host: Running
kubelet: Running
apiserver: Running
```

### Enable Kubernetes Dashboard

```bash
minikube addons enable dashboard
```

Verify Dashboard pods:

```bash
kubectl get pods -n kubernetes-dashboard
```

### Launch Dashboard

```bash
minikube dashboard
```

This command automatically opens the Kubernetes Dashboard in the browser.

### Stop Dashboard

If the dashboard was started using:

```bash
minikube dashboard
```

stop it by pressing:

```text
Ctrl + C
```

in the terminal where it is running.

### Disable Dashboard Addon

```bash
minikube addons disable dashboard
```

### Re-enable Dashboard

```bash
minikube addons enable dashboard
```

### Verify Dashboard Status

```bash
minikube addons list | grep dashboard
```

or

```bash
kubectl get pods -n kubernetes-dashboard
```

### View Cluster Resources in Dashboard

After logging in, you can monitor:

* Nodes
* Deployments
* Pods
* Services
* ConfigMaps
* Secrets
* Events
* Logs

This provides a graphical interface for managing and troubleshooting Kubernetes resources running in the local Minikube cluster.
