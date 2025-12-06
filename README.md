# Set JAVA_HOME in maven
mvnw.cmd
set JAVA_HOME=%PROGRAMFILES%\Java\jdk-21.0.1

# Run app
./mvnw.cmd spring-boot:run -DskipTests

# Build artifact
.\mvnw.cmd clean package -DskipTests

# Show dependencies
.\mvnw.cmd dependency:tree

# Endpoints
http://localhost:8080/hello
http://localhost:8080/hello?name=miel
http://localhost:8080/ping

# Docker
# https://docs.docker.com/engine/reference/commandline/image_tag/
docker image build --no-cache --file Dockerfile --tag miel1980/helloworld-api:1.0.0 .
docker container run --rm --privileged -p 8080:8080 miel1980/helloworld-api:1.0.0



# What is the difference between a Filter and an Interceptor
https://stackoverflow.com/questions/35856454/difference-between-interceptor-and-filter-in-spring-mvc


# Run hashicorp http-echo
docker container run --rm --privileged -p 5678:5678 -text="hello world" hashicorp/http-echo:latest


https://stackoverflow.com/questions/56139706/speeding-up-apt-get-update-to-speed-up-docker-image-builds



# Host API at Google Cloud (Google App Engine)
https://console.cloud.google.com/  
https://c.gle/APy2Ad0qrW21ggiamyXnj7x1Ftxd4fP4QsnEsDL1PjeU5KrTaVlTIbSX1bjE648aovyE5fZMfTtWtInMERKXWkIrBTt4X7EeNH1Uj0DMaE9iVFbzfRhjM2a-nQMe_8kYHwz_K3m_wZDUPJ_cb_MGkOJTBRZPBF8GZnDakvju2tb_Hl1Tf9XgntkfVABzqYPE9Nr0JMgEI7v95A_9_fSxfw9aX0Vo4JLKxNSmbI5VVCL7eZS7KXirGGwDoL1YHmhmj826XbHrip2CA9vgV2vOX5H7g3KWbl6Om3yTrR9rNCZfnFVFQ9qw

# Cloud Build / History
https://console.cloud.google.com/cloud-build/builds?referrer=search&project=cosmic-ascent-480221-u3  

https://docs.cloud.google.com/sdk/docs/install-sdk  
https://cloud.google.com/sdk/docs/quickstarts  
https://docs.cloud.google.com/build/docs/deploying-builds/deploy-appengine  

gcloud init  
gcloud auth list  
gcloud config list  
gcloud help  
gcloud help compute instances create  


# Deploy using gcloud command
.\mvnw.cmd clean package  
gcloud auth login  
gcloud config set project mdj-helloworld-api  
gcloud app deploy app.yaml --project=mdj-helloworld-api  

# Verify deployment
gcloud app logs tail -s default  
gcloud app browse  

# URL
https://mdj-helloworld-api.ew.r.appspot.com  