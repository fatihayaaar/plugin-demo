##  Plugin Demo



## Introduction



## Installation

```shell
docker-compose up -d
```

## Test

#### Build Template

```shell
curl -X POST http://localhost:8080/api/plugin-template/build \
-H "Content-Type: application/json" \
-d '{
    "pluginTypeNames": [
        "USER_PLUGIN"
    ]
}'
```



#### Build Project

```java
public class User implements UserPluginAdapter {

    @Override
    public boolean confirm(String username, String password) {
        return Objects.equals(username, "fayar") && Objects.equals(password, "fayar");
    }

}
```



#### Upload File

```shell
curl -X POST http://localhost:8080/api/plugin/upload \
-F "file=@/dosyanin/yolu/dosyaadi"
```



#### Load File

```shell
curl -X POST http://localhost:8080/api/plugin/load \
-H "Content-Type: text/plain" \
--data "plugin20240907235922183"
```



#### Plugin Test

```shell
curl -X POST http://localhost:8080/api/plugin/user/plugin20240907235922183/verify \
-H "Content-Type: application/json" \
-d '{
    "username": "fayar",
    "password": "fayar"
}'
```

