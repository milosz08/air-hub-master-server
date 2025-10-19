# AirHubMaster Server

[[Docker image](https://hub.docker.com/r/milosz08/air-hub-master-server)] |
[[About project](https://miloszgilga.pl/project/air-hub-master-server)]

Spring Boot Java server created for the [AirHubMaster](https://github.com/Lettulouz/AirHubMaster) mobile application
with the Rest API specifications. Provide basic functionalities: signin, signup, password reset via email token and
other specific application requirements. All application data are stored in MySQL relational database. More info about
this project you will find [here](https://github.com/Lettulouz/AirHubMaster).

## Table of content

* [Clone and install](#clone-and-install)
* [Prepare develop environment](#prepare-develop-environment)
* [Create executable JAR file (bare-metal)](#create-executable-jar-file-bare-metal)
* [Internationalization (i18n)](#internationalization-i18n)
* [Tech stack](#tech-stack)
* [Author](#author)
* [License](#license)

## Clone and install

1. To install the program on your computer, use the command below:

```bash
$ git clone https://github.com/milosz08/air-hub-master-server
```

2. Create docker containers for **ahms-mysql-db**, **ahms-phpmyadmin**, **ahms-mailhog** and **ahms-app** via:

```bash
$ docker-compose up -d
```

This command should create 4 docker containers:

| Application     | Port                                                         | Description                       |
|-----------------|--------------------------------------------------------------|-----------------------------------|
| ahms-mysql-db   | [8530](http://localhost:8530)                                | MySQL database                    |
| ahms-phpmyadmin | [8531](http://localhost:8531)                                | MySQL database client             |
| ahms-mailhog    | [8532](http://localhost:8532), [8533](http://localhost:8533) | Mailhog server (fake mail server) |
| ahms-app        | [8534](http://localhost:8534)                                | Application (REST API)            |

> [!TIP]
> If you have already MySQL database client, you can omit creating `ahms-phpmyadmin` container. To omit, create only
> MySQL db container via: `$ docker compose up -d ahms-mysql-db ahms-mailhog ahms-app`.

## Prepare develop environment

1. Clone and install via `git clone` command (see *Clone and install* section).
2. Create `.env` file based `example.env` in root directory with following content:

```properties
# ports
AHMS_MYSQL_PORT=8530
AHMS_PHPMYADMIN_PORT=8531
AHMS_MAILHOG_SERVER_PORT=8532
AHMS_MAILHOG_UI_PORT=8533
AHMS_APP_PORT=8534
# other
AHMS_MYSQL_PASSWORD=<MySQL database password>
AHMS_APP_JWT_SECRET=<JWT secret token>
```

3. Go to root directory and run MySQL database and fake mail server via:

```bash
$ docker-compose up -d ahms-mysql-db ahms-phpmyadmin ahms-mailhog
```

> [!TIP]
> If you have already MySQL database client, you can omit creating `ahms-phpmyadmin` container. To omit, create only
> MySQL db container via: `$ docker compose up -d ahms-mysql-db ahms-mailhog`.

4. Compile and run app via (for UNIX):

```bash
$ ./gradlew clean build
$ ./gradlew bootRun --args='--spring.profiles.active=dev'
```

or for Windows:

```bash
.\gradlew.cmd clean build
.\gradlew.cmd bootRun --args='--spring.profiles.active=dev'
```

5. Check application state via endpoint: [/actuator/health](http://localhost:8534/actuator/health).
   If response show this:

```json
{
  "status": "UP"
}
```

application listening incoming requests.

## Create executable JAR file (bare-metal)

1. To create executable JAR file for, you must type (for UNIX):

```bash
$ ./gradlew clean bootJar
```

or for Windows:

```bash
.\gradlew.cmd clean bootJar
```

Output JAR file will be located inside `.bin` directory. With this file you can run app in bare-metal environment
without virtualization via:

```bash
$ java \
  -Xms1024m \
  -Xmx1024m \
  -Dspring.profiles.active=dev \
  -Dserver.port=8080 \
  -jar air-hub-master-server.jar
```

> [!NOTE]
> If you can run app with `prod` Spring Boot profile, you must pass all environment variables defined for `ahms-app` in
> `docker-compose.yml` file.

## Internationalization (i18n)

* To add a new language, copy all `i18n-*` message sources with new prefix (message sources are located in `resources`
  directory)

* After generated files, fill with propriet communicates and declare new lang tag in `application.yml` file:

```yml
application:
  i18n:
    available-languages:
      - en
      - pl
      - [ i18n tag ]
    default-langauge: pl
```

* To force language in HTTP requests, be sure that `Accept-Language` header was added in every requests, ex.:

```
Accept-Language: fr
```

Without this header, server will return responses in the language you have set as default in the
`application.i18n.default-langauge` parameter. Language value in `Accept-Language` header also affects the generated
language of the email content.

## Tech stack

* Java 17,
* Spring Boot 3,
* Spring Data JPA, MySQL database, Liquibase,
* Freemarker for mail templates,
* Docker containers.

## Author

Created by Miłosz Gilga. If you have any questions about this application, send
message: [miloszgilga@gmail.com](mailto:miloszgilga@gmail.com).

## License

This application is on Apache 2.0 License.
