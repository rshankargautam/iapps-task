## Requirement

- Java 8
- Docker Desktop
- IntelliJ
- Postman

## To run the app

- Build the project
```sh
mvn clean package -DskipLiquibase=true -DskipTests=true
```
- Build docker container
```sh
cd iapps-task
```
```sh
docker-compose up
```
```sh
mvn clean package
```
## Now to test the API
- POST API: "localhost:8080/epaper/uploadxml"
    - in form-data add key "xmlFile" change type to file and attach the file.
    - xml will be processed.

- GET API: "localhost:8080/epaper/getallepaper".
    - parameters for this API:
        - search: if need to data for specific newspaper name.
        - sortBy: need to sort by any specific column(By default: id)
        - order: true for Ascending | false for Descending order.
        - pageNo: starts from 0
        - pageSize: no of record need to get in result. 
