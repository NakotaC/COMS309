# websocket_unit3_1

### What is this example

Simple Implementation of websocket.
For this example, the user has three options to connect to the public chat room
1. Open the index.html file in `Client_JS` folder
2. Run the adroid app in `WebSocketAndorid` folder
3. Connect to `ws://localhost:8080/chat/{username}` in Postman

### Important Notes

- To run the server on your local machine to test the public chat room, you can
	- Run the server in IntelliJ normally, OR
	- Execute the command 'java -jar WebSocketServer.jar' in the terminal to run the included .jar server (for frontend students)

- Use `10.0.2.2` instead of `localhost` IF the server program is running on the same host as the Android Enmulator

Read the Websockets.pdf to understand the inner workings of the frontend and backend for this public chat room (only for concepts, ignore examples as they may be out-dated).

### Dependencies and Configurations

#### Backend

- pom.xml:
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```

#### Frontend

- AndroidManifest.xml
    - add `<uses-permission android:name="android.permission.INTERNET" />` before `<application>`
    - add `android:usesCleartextTraffic="true"` inside `<application>`

- build.gradle (Module :app)
    - add `implementation 'com.android.volley:volley:1.2.1'` inside `dependencies{}`, then sync gradle.

### Version Tested
|Android Studio            | Android SDK | Gradle | Gradle Plugin | Gradle JDK | Emulator |
|--------------------------|-------------|--------|---------------|------------|----------|
|Giraffe 2022.3.1 Patch 2  |     33      | 8.0.0  |    8.1.2      |    17      | Pixel 3a |


|IntelliJ  | Project SDK | Springboot | Maven |
|----------|-------------|------------|-------|
|2021.2.4  |     11      | 3.1.4      | 3.6.3 |

