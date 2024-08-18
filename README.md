### Deployment on Render by Creating a Docker Image for Your Spring Boot Application

### Prerequisites

- A Render account [https://render.com]
- Docker installed on your machine
- A GitHub repository containing your Spring Boot application

### Creating a Docker Image for Your Spring Boot Application

### Step 1: Build the JAR File

1. **Navigate to your Spring Boot project directory**:

   Open a terminal and go to the root directory of your Spring Boot project.

2. **Build the JAR file**:

   Depending on your build tool, run one of the following commands:

   - **If you are using Maven**:

     ```bash
     mvn clean package
     ```

     This command compiles your code, runs tests, and packages the application into a JAR file. The resulting JAR file will be located in the `target` directory.

   - **If you are using Gradle**:

     ```bash
     ./gradlew build
     ```

     This command does the same as the Maven command, placing the JAR file in the `build/libs` directory.

   Ensure that the JAR file is named appropriately (e.g., `employeeCRUD-0.0.1-SNAPSHOT.jar`). You can verify this by checking the `target` or `build/libs` directory.

### Step 2: Create a Dockerfile

1. **In the root directory of your Spring Boot project**, create a file named `Dockerfile` (no file extension). Add the following content to it:

   ```dockerfile
   # Use an official OpenJDK runtime as a parent image
   FROM openjdk:17-jdk-slim

   # Set the working directory in the container
   WORKDIR /app

   # Copy the jar file into the container at /app
   COPY target/your-app-name.jar app.jar

   # Expose the port the app runs on
   EXPOSE 8080

   # Run the jar file
   ENTRYPOINT ["java", "-jar", "app.jar"]
   ```

   Replace `your-app-name.jar` with the name of your JAR file (e.g., `employeeCRUD-0.0.1-SNAPSHOT.jar`).

### Step 3: Build the Docker Image

1. Open a terminal and navigate to the root directory of your project where the `Dockerfile` is located.

2. Run the following command to build the Docker image:

   ```bash
   docker build -t your-app-name .
   ```

   Replace `your-app-name` with a name for your Docker image (e.g., `employee-management-system`).

### Step 4: Test the Docker Image Locally

1. After building the image, you can run it locally to ensure everything works:

   ```bash
   docker run -p 8080:8080 your-app-name
   ```

   This command maps port 8080 on your host machine to port 8080 in the Docker container.

2. Open a web browser and navigate to `http://localhost:8080` to test if the application is running as expected.

### Step 5: (Optional) Tag and Push to Docker Hub

1. If you want to push your Docker image to Docker Hub, first log in to your Docker Hub account:

   ```bash
   docker login
   ```

2. Tag your Docker image:

   ```bash
   docker tag your-app-name your-dockerhub-username/your-app-name:latest
   ```

3. Push the image to Docker Hub:

   ```bash
   docker push your-dockerhub-username/your-app-name:latest
   ```

   Replace `your-dockerhub-username` with your Docker Hub username.

### Step 6: Push to GitHub

Before pushing your code to GitHub, make sure to update your `.gitignore` file. Remove the following lines from `.gitignore`:

```plaintext
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/
```

This ensures that the target directory, which contains the JAR file, is included in your GitHub repository.

After updating the `.gitignore` file, add, commit, and push your changes to GitHub.

### Deploy on Render

#### Create a New Web Service:

1. Log in to your Render account.
2. Navigate to the Dashboard.
3. Click on `New` and select `Web Service`.
4. Connect your GitHub repository if you haven't already, and select the repository containing your Spring Boot project.

#### Configure Deployment:

1. Name your service (e.g., `employee-management-system`).
2. In the Environment section, select `Docker`.
3. For the Dockerfile Path, enter the path to your Dockerfile (e.g., `/Dockerfile` if it’s in the root directory).
4. Set the Publish Directory to `/app`.

#### Build and Deploy:

1. Click on `Create Web Service`.
2. Render will build your Docker image and deploy the application. The process might take a few minutes.

#### Access Your Application:

1. Once the deployment is complete, Render will provide you with a public URL where your application is hosted.
