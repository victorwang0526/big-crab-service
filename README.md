## REST API Service

jersey + spring boot + mysql + swagger + mybatis

NEED: gradle + maven + JDK

#### mysql

```
create database carrot;

grant all privileges on *.* to carrot@localhost Identified by "carrot";
```

### start service

```
gradle bootRun
```

可访问：
http://localhost:8090/carrot

### war

```
gradle war
```

### code style
IDEA - File - Import Settings... codestyle.jar

### swagger ui
use swagger-ui-2.0.24.dist.zip

