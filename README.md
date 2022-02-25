# weather_app

Build a “Worldwide Windsurfer’s Weather Service” application meeting the requirements listed below.

1.Use the Weatherbit Forecast API(https://www.weatherbit.io/api/weather-forecast-16-day) as the weather data source.

2. Expose a REST API, where the argument is a day (yyyy-mm-dd date format) and return value is one of following locations:

Jastarnia (Poland)
Bridgetown (Barbados)
Fortaleza (Brazil)
Pissouri (Cyprus)
Le Morne (Mauritius)

depending on which place offers better windsurfing conditions on that day in the 16 forecast day range. Apart from returning the name of the location, the response should also include weather conditions (at least average temperature - Celcius, wind speed - m/s) for the location on that day.


3. The list of windsurfing locations (including geographical coordinates) should be embedded in the application in a way that allows for extensions at a later stage. Note: There is no API requirement for creating or editing the locations.

4. The best location selection criteria are:
If the wind speed is not within <5; 18> (m/s) and the temperature is not in the range <5; 35> (°C), the location is not suitable for windsurfing. However, if they are in these ranges, then the best location is determined by the highest value calculated from the following formula:
v * 3 + temp
where v is the wind speed in m/s on a given day, and temp is an average forecasted temperature for a given day in Celsius, respectively - you can obtain these parameters from the “data” key in Weatherbit API’s response.
If none of the locations meets the above criteria, the application does not return any.

5. The application is written in Java 8 or higher and uses Spring Boot. Frontend is not required.

6. It has a gradle or maven building mechanism and a README file describing at least the procedure for building and running the application.

7. Exceptional scenarios or requirements not specified above should be handled at your discretion. 
