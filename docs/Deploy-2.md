Here's a step-by-step guide to deploy a Spring Boot API to Google App Engine:

## Prerequisites

1. **Google Cloud Account** with billing enabled
2. **Java 11 or 17** installed locally
3. **Maven or Gradle** for building
4. **Google Cloud SDK** installed

## Step 1: Initialize Google Cloud SDK

```bash
# Install Google Cloud SDK (if not installed)
# Follow: https://cloud.google.com/sdk/docs/install

# Login to Google Cloud
gcloud auth login

# Set your project
gcloud config set project YOUR_PROJECT_ID
```

## Step 2: Prepare Your Spring Boot Application

### Maven Project Structure:
```
your-project/
├── src/
├── pom.xml
└── app.yaml          # App Engine configuration
```

### Gradle Project Structure:
```
your-project/
├── src/
├── build.gradle
└── app.yaml
```

## Step 3: Create `app.yaml` Configuration

Create `app.yaml` in your project root:

```yaml
runtime: java11  # or java17
instance_class: F2  # F1 is default (lower cost)

# For Java 17 or newer runtime:
# runtime: java17

# For automatic scaling (default):
automatic_scaling:
  min_instances: 1
  max_instances: 10
  target_cpu_utilization: 0.65
  target_throughput_utilization: 0.65
  min_pending_latency: 30ms
  max_pending_latency: automatic

# For basic scaling (more control):
# basic_scaling:
#   max_instances: 5
#   idle_timeout: 10m

# Environment variables
env_variables:
  SPRING_PROFILES_ACTIVE: "prod"

# Health checks (important for APIs)
health_check:
  enable_health_check: True
  check_interval_sec: 30
  timeout_sec: 10
  unhealthy_threshold: 5
  healthy_threshold: 2

# Resources
resources:
  cpu: 1
  memory_gb: 2
  disk_size_gb: 10
```

## Step 4: Configure Application Port

Update your `application.properties` or `application.yml`:

```properties
# For App Engine
server.port=${PORT:8080}

# Production settings
spring.jpa.hibernate.ddl-auto=validate
spring.datasource.url=jdbc:postgresql:///your-db?cloudSqlInstance=INSTANCE_CONNECTION_NAME&socketFactory=com.google.cloud.sql.postgres.SocketFactory
```

## Step 5: Add App Engine Plugin

### For Maven (`pom.xml`):
```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.google.cloud.tools</groupId>
            <artifactId>appengine-maven-plugin</artifactId>
            <version>2.4.4</version>
        </plugin>
        
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <executable>true</executable>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### For Gradle (`build.gradle`):
```gradle
plugins {
    id 'org.springframework.boot' version '2.7.0'
    id 'com.google.cloud.tools.appengine' version '2.4.4'
}

appengine {
    deploy {
        projectId = 'YOUR_PROJECT_ID'
        version = '1'
    }
}
```

## Step 6: Test Locally

```bash
# Test with local App Engine dev server
mvn appengine:run
# or
gcloud app deploy --version=dev
```

## Step 7: Deploy to App Engine

### Option A: Using Maven/Gradle
```bash
# Maven
mvn clean package appengine:deploy

# Gradle
gradle appengineDeploy
```

### Option B: Using gcloud command
```bash
# Build JAR
mvn clean package

# Deploy
gcloud app deploy app.yaml --project=YOUR_PROJECT_ID

# Deploy with specific version
gcloud app deploy --version=v2 --promote
```

## Step 8: Verify Deployment

```bash
# Check deployment status
gcloud app describe

# View logs
gcloud app logs tail -s default

# Open in browser
gcloud app browse

# List versions
gcloud app versions list
```

## Step 9: Database Configuration (Optional)

For Cloud SQL with PostgreSQL:

1. **Enable Cloud SQL API**
2. **Create database instance**
3. **Update `app.yaml`**:

```yaml
env_variables:
  SPRING_DATASOURCE_URL: jdbc:postgresql:///your-db?cloudSqlInstance=PROJECT_ID:REGION:INSTANCE_NAME&socketFactory=com.google.cloud.sql.postgres.SocketFactory
  SPRING_DATASOURCE_USERNAME: your-user
  SPRING_DATASOURCE_PASSWORD: your-password
```

4. **Add dependency**:
```xml
<dependency>
    <groupId>com.google.cloud.sql</groupId>
    <artifactId>postgres-socket-factory</artifactId>
    <version>1.11.0</version>
</dependency>
```

## Common Issues & Solutions

1. **Port Conflict**: Ensure `server.port=${PORT:8080}` is set
2. **Memory Issues**: Increase `memory_gb` in `app.yaml`
3. **Cold Starts**: Use `min_instances: 1` or warming requests
4. **Database Connection**: Whitelist App Engine IPs in Cloud SQL

## Cost Optimization Tips

```yaml
# Use lower instance class for dev
instance_class: F1

# Set min instances to 0 for lower traffic
automatic_scaling:
  min_instances: 0
  max_instances: 3
```

## Additional Commands

```bash
# Stop a version
gcloud app versions stop v1

# Delete old versions
gcloud app versions delete v1 v2

# Set traffic splitting
gcloud app services set-traffic --splits=v2=.5,v1=.5
```

Your API should now be running at `https://PROJECT_ID.uc.r.appspot.com`