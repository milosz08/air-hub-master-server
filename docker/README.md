# AirHubMaster Server

Spring Boot Java server created for the AirHub Master mobile application with the Rest API specifications. Provide basic
functionalities: signin, signup, password reset via email token and other specific application requirements. All
application data are stored in MySQL relational database.

[GitHub repository](https://github.com/milosz08/air-hub-master-server)

## Build image

```bash
docker build -t milosz08/air-hub-master-server .
```

## Create container

* Using command:

```bash
docker run -d \
  --name ahms-app \
  -p 8080:8080 \
  -e AHMS_DB_HOST=<database host> \
  -e AHMS_DB_NAME=<database name> \
  -e AHMS_DB_USERNAME=<database username> \
  -e AHMS_DB_PASSWORD=<database password> \
  -e AHMS_SERVER_XMS=1024m \
  -e AHMS_SERVER_XMX=1024m \
  -e AHMS_MAIL_PROTOCOL=<mail protocol, smtps> \
  -e AHMS_MAIL_HOST=<SMTP server host> \
  -e AHMS_MAIL_PORT=<SMTP server port, 587> \
  -e AHMS_MAIL_USERNAME=<SMTP server username, empty> \
  -e AHMS_MAIL_PASSWORD=<SMTP server password, empty> \
  -e AHMS_MAIL_SEND_ADDRESS=<SMTP sender address> \
  -e AHMS_MAIL_REPLY_ADDRESS=<SMTP reply address> \
  -e AHMS_MAIL_SSL_ENABLED=<SMTP enable SSL, false> \
  -e AHMS_MAIL_AUTH_ENABLED=<SMTP enable Auth, false> \
  milosz08/air-hub-master-server:latest
```

* Using `docker-compose.yml` file:

```yaml
services:
  ahms-app:
    container_name: ahms-app
    image: milosz08/air-hub-master-server:latest
    ports:
      - '8080:8080'
    environment:
      TAI_DB_HOST: <database host>
      TAI_DB_NAME: <database name>
      TAI_DB_USERNAME: <database username>
      TAI_DB_PASSWORD: <database password>
      TAI_SERVER_XMS: 1024m
      TAI_SERVER_XMX: 1024m
      TAI_MAIL_PROTOCOL: <mail protocol, smtps>
      TAI_MAIL_HOST: <SMTP server host>
      TAI_MAIL_PORT: <SMTP server port, 587>
      TAI_MAIL_USERNAME: <SMTP server username, empty>
      TAI_MAIL_PASSWORD: <SMTP server password, empty>
      AHMS_MAIL_SEND_ADDRESS: <SMTP sender address> \
      AHMS_MAIL_REPLY_ADDRESS: <SMTP reply address> \
      TAI_MAIL_SSL_ENABLED: <SMTP enable SSL, false>
      TAI_MAIL_AUTH_ENABLED: <SMTP enable Auth, false>
    networks:
      - ahms-network

  # other containers...

networks:
  tai-network:
    driver: bridge
```

## Author

Created by Miłosz Gilga. If you have any questions about this application, send
message: [miloszgilga@gmail.com](mailto:miloszgilga@gmail.com).

## License

This project is licensed under the Apache 2.0 License.
