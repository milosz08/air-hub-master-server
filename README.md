# AirHub Master API

![](https://img.shields.io/badge/Made%20in-Java%20%2017-1abc9c.svg)
&nbsp;&nbsp;
![](https://img.shields.io/badge/Made%20with-Spring%20Boot%203.0.6-green.svg)
&nbsp;&nbsp;
![](https://img.shields.io/badge/Build%20with-Gradle%207.6.1-brown.svg)
&nbsp;&nbsp;
<br>
> More info about this project you will find [on my personal website](https://miloszgilga.pl/project/air-hub-master-api).

Spring Boot Java server created for the [AirHub Master](https://github.com/Lettulouz/AirHubMaster) mobile application with 
the Rest API specifications. Provide basic functionalities: signin, signup, password reset via email token and other 
specific application requirements. All application data are stored in MySQL relational database. More info about this 
project you will find [here](https://github.com/Lettulouz/AirHubMaster).

This application required newest stable [JMPS library](https://github.com/Milosz08/jmpsl) (required modules: communication,
core, security). It may happen that the version of JMPSL you are currently using is not yet available in the remote repository.
In this case, you will have to build the library yourself in your local maven repository (see below).

## Table of content
* [Clone script](#clone-script)
* [Prepare project](#prepare-project)
* [Run from IDE](#run-from-ide)
* [Run from JAR](#run-from-jar)
* [Create WAR](#create-war)
* [Available endpoints](#available-endpoints)
* [Change Xmx and Xms parameters (JVM Heap Size)](#change-xmx-and-xms-parameters)
* [Internationalization (i18n)](#internationalization-i18n)
* [Tech stack](#tech-stack)
* [Author](#author)
* [Project status](#project-status)
* [License](#license)

<a name="clone-script"></a>
## Clone script
To install the program on your computer use the command (or use the built-in GIT system in your IDE environment):
```
$ git clone https://github.com/Milosz08/air-hub-master-api air-hub-master-server
```
<a name="prepare-project"></a>
## Prepare project
Before run the project you must download and build the latest version of the JMPS library. To do this, follow the steps below:
* Clone the latest version of JMPS library from my GitHub:
```
$ git clone -b [latest branch, ex. v1.0.2_05] https://github.com/Milosz08/jmpsl jmps-library
```
* Go to root of project and type:
```
$ ./gradlew packageAllToLocal
```
This command build all library modules in `~/.m2/repositories/pl/miloszgilga/jmpsl` directory.
* Now, after you clone AirHub Master API project via command in `Clone script` section, make sure than all repository
paths are typed correctly (in `/gradle/libs.versions.toml` file):
```toml
[versions]
# ...
jmpsl = '[version from branch, ex. 1.0.2_05]'
# ...

[libraries]
# ...
jmpsl-core          = { module = 'pl.miloszgilga:jmpsl-core',           version.ref = 'jmpsl' }
jmpsl-security      = { module = 'pl.miloszgilga:jmpsl-security',       version.ref = 'jmpsl' }
jmpsl-communication = { module = 'pl.miloszgilga:jmpsl-communication',  version.ref = 'jmpsl' }
# ...
```
* Create `.env` file and put necessary values (from `.env.sample` file) (you must be in ROOT of project context):
```
$ grep -v '^#' .env.sample | cp .env
$ nano .env
```
* Congrats, now you can build the project via:
```
./gradlew build
```
If it fails, make sure the paths specified are correct and that the `.jar` JMPSL files exist in the local maven repository.

<a name="run-from-ide"></a>
## Run from IDE
* To run application via gradle wrapper in IDE, type:
```
$ ./gradlew bootRunDev    # for development version
$ ./gradlew bootRunProd   # for production version
```

<a name="run-from-jar"></a>
## Run from JAR
* First, you must build to executable `.jar` file via:
```
$ ./gradlew bootJar
```
All files (`.env`, `.jar` and run/kill bash scripts) you will find in `/build/jar` directory.
* Too run the application, execute `jar-run.sh` script:
```
$ cd build/jar
$ ./jar-run.sh
```
Application will run in the background with assigned PID. To check, if application run correctly, type:
```
$ ps
```
to show all process.
* Too stop the application, execute `jar-kill.sh` script:
```
$ ./jar-kill.sh
```

<a name="create-war"></a>
## Create WAR
To create `.war` file, type:
```
$ ./gradlew war
```
Generated `.war` file should be located in `/build/war` directory. You can directly place this file type in your Tomcat 
or Jetty web container runtime environment.

<a name="available-endpoints"></a>
## Available endpoints
For detailed endpoint data, go to http://127.0.0.1:8081/swagger-ui/index.html (available only in the spring "dev" profile).

<a name="change-xmx-and-xms-parameters"></a>
## Change Xmx and Xms parameters (JVM Heap Size)
In `jar-run.sh` bash script file, change this lines of code:
```bash
#!/bin/bash

START_JAVA_HEAP_SIZE="256m"     # -Xms parameter, min. 128MB
MAX_JAVA_HEAP_SIZE="512m"       # -Xmx parameter
```

<a name="internationalization-i18n"></a>
## Internationalization (i18n)
* To add a new language, create a new resource file in the `classpath:` directory via command:
```
$ cd run-scripts
$ ./add-lang.sh --lang=[i18n tag]
```
where [i18n tag] could be ex. `fr` or `en-us` etc. This script will create language files in `classpath:i18n-api`,
`classpath:i18n-jpa` and `classpath:i18n-mail` based based on a template from a file `messages_en.properties`.
* After generated files, fill with propriet communicates and declare new lang tag in `application.yml` file:
```yml
jmpsl:
    core:
        locale:
            available-locales: pl,en,[i18n tag]
            default-locale: pl
```

<a name="tech-stack"></a>
## Tech stack
* Java 17
* Spring Boot
* JMPSL (modules: communication, core, security)
* MySQL database (for production), H2 database (for development)
* Freemarker for mail templates
* Swagger OpenAPI documentation

<a name="author"></a>
## Author
Created by Miłosz Gilga. If you have any questions about this application, send message: [personal@miloszgilga.pl](mailto:personal@miloszgilga.pl).

<a name="project-status"></a>
## Project status
Project is still in development.

<a name="license"></a>
## License
This application is on Apache 2.0 License.

