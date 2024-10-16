
curl -X POST -H "Content-Type: application/json" \
-d '{
  "ec2_vm_name": "philvm",
  "snow_request": "snow1234",
  "ec2_instance_type": "instance type 1",
  "ec2_instance_count": "122"
}' \
http://localhost:8080/snow/ticketresponse


curl -X POST -H "Content-Type: application/json" \
-d '{
  "ci_name": "philvm",
  "snow_request": "12345",
  "memory_size": "1024",
  "cpu_size": "2"
}' \
http://ec2-13-61-5-208.eu-north-1.compute.amazonaws.com:8080/snow/ticketresponse

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: skupper-site
data:
  name: demolab-site
  console: "true"
  console-user: "admin"
  console-password: "edaeda"
  flow-collector: "true"
```

skupper init --site-name aws-site --ingress-host ec2-13-61-5-208.eu-north-1.compute.amazonaws.com

skupper token create linkaws.token

skupper expose deployment eda-snow-receiver --port 8080

skupper service create eda-snow-receiver 8080 --host-ip 0.0.0.0