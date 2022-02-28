# weather_app

## How it works

A “Worldwide Windsurfer’s Weather Service” application.
Expose a REST API, where the argument is a day (yyyy-mm-dd date format) and return value is one of following locations:

Jastarnia (Poland)
Bridgetown (Barbados)
Fortaleza (Brazil)
Pissouri (Cyprus)
Le Morne (Mauritius)

depending on which place offers better windsurfing conditions on that day in the 16 forecast day range. 

The best location selection criteria are:
   If the wind speed is not within <5; 18> (m/s) and the temperature is not in the range <5; 35> (°C), the location is not suitable for windsurfing. However, if they are in these ranges, then the best location is determined by the highest value calculated from the following formula:
   v * 3 + temp
   where v is the wind speed in m/s on a given day, and temp is an average forecasted temperature for a given day in Celsius, respectively

## How to run the app

For Maven execute the command from root the directory
```
./mvnw package -Dmaven.test.skip && java -jar target/weather_app-0.0.1-SNAPSHOT.jar
```

For Docker execute the command from root the directory to create docker image
```
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=kacper/weather_app-docker
```

and to run the docker container execute the command
```
docker run -it -p8080:8080 kacper/weather_app-docker:latest
```

