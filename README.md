# Elasticsearch File Upload Service

A Spring Boot application for uploading and storing files in **Elasticsearch**, supporting multiple file types like TXT, JSON, and XML. This project demonstrates robust backend development practices with **DevOps-friendly design principles** for maintainable and reliable deployments.

---

## Table of Contents

- [Project Overview](#project-overview)  
- [Features](#features)  
- [Architecture](#architecture)  
- [DevOps Highlights](#devops-highlights)  
- [Preparing the VM](#preparing-the-vm)  
- [Getting Started](#getting-started)  
- [API Endpoints](#api-endpoints)  
- [Testing](#testing)  
- [Testing with Bruno](#testing-with-bruno)  
- [Future Improvements](#future-improvements)  
- [Docs and References](#docs-and-references)

---

## Project Overview

This service allows users to upload files through a REST API. Each file is processed to detect its type, extract content, and automatically determine the Elasticsearch index based on file naming conventions. Built with Spring Boot, it leverages ElasticsearchOperations for seamless storage and retrieval.

---

## Features

- Upload **TXT, JSON, XML** files via REST API  
- Automatic **file type detection**  
- **Dynamic index creation** based on file naming  
- **Detailed logging** of file processing  
- Handles Elasticsearch authentication and connectivity errors  
- Fully **unit-tested** service and controller layers  

---

## Architecture

1. **Controller Layer**: Handles file uploads and responses.  
2. **Service Layer**: Processes files, detects types, extracts content, and indexes them in Elasticsearch.  
3. **Elasticsearch Configuration**: Secure connection using SSL and Basic Authentication.  
4. **Model Layer**: Represents file data in Elasticsearch with custom date formatting.  

---

## DevOps Highlights

- **Configurable**: Elasticsearch host and credentials via `application.properties`  
- **Logging & Monitoring**: SLF4J logging provides visibility into runtime operations  
- **Unit Testing**: JUnit 5 and Mockito ensure reliable behavior before deployment  
- **Scalable Design**: File processing and dynamic indexing make it ready for production environments  

---

## Preparing the VM

To set up VM and Elasticsearch securely with public HTTPS access:

### 1. VM Network Configuration
- Use **Bridged Network** for the VM.  
- Ensure the VM gets an IP accessible from your host machine.  
- Use this IP to connect your Spring Boot application to Elasticsearch.

### 2. Install Elasticsearch
- Download and install Elasticsearch as a Service on the VM.  
- Enable **security** in `elasticsearch.yml`:  
```yaml
xpack.security.enabled: true
xpack.security.enrollment.enabled: true
xpack.security.http.ssl:
  enabled: true
  keystore.path: certs/http.p12
xpack.security.transport.ssl:
  enabled: true
  verification_mode: certificate
  keystore.path: certs/transport.p12
  truststore.path: certs/transport.p12
cluster.initial_master_nodes: ["vm"]
```
- Allow HTTP API connections from anywhere in `elasticsearch.yml`:
```yaml
http.host: 0.0.0.0
```
- Start Elasticsearch:  
```bash
sudo systemctl start elasticsearch
sudo systemctl enable elasticsearch
```

### 3. Import Elasticsearch CA Certificate for HTTPS
To allow Java application to trust the Elasticsearch HTTPS certificate:

1. Check JAVA_HOME:  
```cmd
echo %JAVA_HOME%
```
Example:  
```
C:\Program Files\Java\jdk-17
```
2. Copy the CA certificate to th host where Java app is running:
Path in Elasticsearch VM : /etc/elasticsearch/certs/http_ca.crt
3. Import the CA certificate:  
```cmd
keytool -importcert -alias elastic-ca -file "path\to\http_ca.crt" -keystore "C:\Program Files\Java\jdk-17\lib\security\cacerts" -storepass changeit
```

4. Verify the import:  
```cmd
keytool -list -keystore "C:\Program Files\Java\jdk-17\lib\security\cacerts" -storepass changeit | findstr elastic-ca
```

> After this, Spring Boot application can securely connect to Elasticsearch over HTTPS.  

---

## Getting Started

1. Clone the repository:  
```bash
git clone <repository-url>
cd elasticsearch-file-service
```

2. Configure Elasticsearch credentials in `application.properties`:  
```properties
elasticsearch.host=<vm-ip>:9200
elasticsearch.username=elastic
elasticsearch.password=<password>
```

3. Run the application:  
```bash
mvn spring-boot:run
```

4. Access the API at `http://localhost:8084/api/files/upload`.  

---

## API Endpoints

**POST /api/files/upload**  
- Upload a file (multipart/form-data)  
- Supported file types: TXT, JSON, XML  
- Returns a JSON response with status and index name  

**Response Example**:  
```json
{
  "message": "File uploaded successfully to Index: ",
  "index": "default"
}
```

---

## Testing

- **Unit Tests**: Service and controller tested with JUnit 5 & Mockito  
- **Mocked ElasticsearchOperations** verifies indexing behavior  
- Run tests with Maven:  
```bash
mvn test
```

---

## Testing with Bruno

Bruno can be used to test both the **file upload API** and **Elasticsearch instance** securely over HTTPS.  

### 1. Configure SSL/TLS Certificate Verification
1. Open **Bruno Preferences → General**.  
2. Check **SSL/TLS Certificate Verification**.  
3. Check **Use Custom CA Certificate**.  
4. Select the **`http_ca.crt`** file.  

> This ensures Bruno trusts Elasticsearch HTTPS certificate.

### 2. Example Bruno Requests

#### **Upload a File**
- URL: `http://localhost:8084/api/files/upload`  
- Method: `POST`  
- Body: `multipart/form-data` with the file to upload.  
- Example file: `58-example-sample.json`

#### **Get Elasticsearch Indexes**
- URL: `https://<vm-ip>:9200/_cat/indices?v`  
- Method: `GET`  
- Authentication: Basic Auth (`elastic` / `<password>`)

#### **Search Content in an Index**
- URL: `https://<vm-ip>:9200/58/_search?q=*`  
- Method: `GET`  
- Authentication: Basic Auth (`elastic` / `<password>`)

---

## Future Improvements

- Docker containerization for easier deployment  
- Integration with CI/CD pipelines  

---

## Docs and References

All additional documentation, including:

- Screenshots of the application and testing process
- Bruno requests collection
- Log files from the application

are available in the `docs/` directory of this repository.

---