##  Plugin Demo

This project is a Java plugin application developed using Spring and Java Class Loader. Various design patterns have been implemented in the application, which also supports dynamic inclusion of formats at runtime. It allows adding new features and formats by including JAR files at runtime.

![Adsız-2024-08-25-1346](https://github.com/user-attachments/assets/e83559b3-841a-43a8-945f-c02535335b08)

## Introduction

- The application allows you to customize and develop it according to your needs by adding a plugin interface under the “Adapter” package.

- Plugins are loaded and become available at runtime.

- The project uses Class Loader.

- Design patterns have been utilized in the project.

- As this is a demo project, no database is used.

## Installation

#### With Docker

- Use this command to start the server in the project directory.

```shell
docker-compose up -d
```

## Test

#### Build Template

The following HTTP request allows you to download a template project of the “USER_PLUGIN” type. This plugin is designed to return a boolean value based on the username and password, and it can be extensively customized.

```shell
curl -X POST http://localhost:8080/api/plugin-template/build \
-H "Content-Type: application/json" \
-d '{
    "pluginTypeNames": [
        "USER_PLUGIN"
    ]
}'
```

In this example, the request results in downloading a template named **plugin20240907235922183.zip**

#### Build Project

You can design the **boolean confirm(String username, String password)** method in any way you like. In this example, we have implemented the method to return true when both the username and password values are equal to “fayar”.

```java
public class User implements UserPluginAdapter {

    @Override
    public boolean confirm(String username, String password) {
        return Objects.equals(username, "fayar") && Objects.equals(password, "fayar");
    }

}
```

After building the project, we generate a JAR file. Then, we compress the JAR file along with any used libraries into a ZIP file named **plugin20240907235922183.zip**

#### Upload File

We upload the compressed file to the server using this HTTP request.

```shell
curl -X POST http://localhost:8080/api/plugin/upload \
-F "file=@/file/to/path/plugin20240907235922183.zip"
```

#### Load File

We load the uploaded plugin into the project at runtime using this HTTP request.

```shell
curl -X POST http://localhost:8080/api/plugin/load \
-H "Content-Type: text/plain" \
--data "plugin20240907235922183"
```

#### Plugin Test

We can test the uploaded plugin of type “USER_PLUGIN” using this HTTP request.

```shell
curl -X POST http://localhost:8080/api/plugin/user/plugin20240907235922183/verify \
-H "Content-Type: application/json" \
-d '{
    "username": "fayar",
    "password": "fayar"
}'
```
