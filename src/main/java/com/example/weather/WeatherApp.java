package com.example.weather;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.weather.WeatherFetch.fetchWeatherByCityState;

public class WeatherApp extends Application {
    @Override
    public void start(Stage stage) {
        TextField cityField = new TextField();
        cityField.setPromptText("Enter city");
        TextField stateField = new TextField();
        stateField.setPromptText("Enter state");
        Button fetchButton = new Button("Fetch Weather");

        Label temperatureLabel = new Label();
        temperatureLabel.setText("Temp: ");
        Label weatherLabel = new Label();
        weatherLabel.setText("Current Weather: ");
        Label weatherDescriptionLabel = new Label();
        weatherDescriptionLabel.setText("Description: ");
        Label feelsLikeLabel = new Label();
        feelsLikeLabel.setText("Feels Like: ");
        Label timeZoneLabel = new Label();
        timeZoneLabel.setText("This Location's Timezone is: ");
        Label sunriseLabel = new Label();
        sunriseLabel.setText("Sunrise: ");
        Label sunsetLabel = new Label();
        sunsetLabel.setText("Sunset: ");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(cityField, 0, 0);
        gridPane.add(stateField, 1, 0);
        gridPane.add(fetchButton, 0, 1, 2, 1);
        gridPane.add(temperatureLabel, 1,1, 2, 1);
        gridPane.add(weatherLabel,0,2,2,1);
        gridPane.add(weatherDescriptionLabel,0,3,2,1);
        gridPane.add(feelsLikeLabel, 1, 2, 2,1);
        gridPane.add(timeZoneLabel,1,3,2,1);
        gridPane.add(sunriseLabel,1,4,2,1);
        gridPane.add(sunsetLabel,1,5,2,1);

        Scene scene = new Scene(gridPane,600,300);
        stage.setScene(scene);
        stage.setTitle("Weather App");
        stage.show();

        fetchButton.setOnAction(event -> {
            String city = cityField.getText();
            String state = stateField.getText();
            try {
                JSONObject weatherData = fetchWeatherByCityState(city, state);

                double temperature = weatherData
                        .getJSONObject("current")
                        .getDouble("temp");
                temperatureLabel.setText("Temp: " + temperature + "°F");

                String weather = weatherData
                        .getJSONObject("current")
                        .getJSONArray("weather")
                        .getJSONObject(0)
                        .getString("main");
                weatherLabel.setText("Current Weather: " + weather);

                String weatherDescription = weatherData
                        .getJSONObject("current")
                        .getJSONArray("weather")
                        .getJSONObject(0)
                        .getString("description");
                weatherDescriptionLabel.setText("Description: " + weatherDescription);

                double feelsLike = weatherData
                        .getJSONObject("current")
                        .getDouble("feels_like");
                feelsLikeLabel.setText("Feels Like: " + feelsLike );

                String timeZone = weatherData
                        .getString("timezone");
                timeZoneLabel.setText("this Location's Timezone is: " + timeZone);

                Long sunrise = weatherData
                        .getJSONObject("current")
                        .getLong("sunrise");
                sunriseLabel.setText("Sunrise: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(sunrise * 1000)));

                Long sunset = weatherData
                        .getJSONObject("current")
                        .getLong("sunset");
                sunsetLabel.setText("Sunset: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(sunset * 1000)));



            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });

    }

    public static void main(String[] args) {
        launch();
    }
}