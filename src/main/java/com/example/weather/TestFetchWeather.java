package com.example.weather;

import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestFetchWeather {
    @Test
    public void testFetchWeather(){
        try{
            double latitude = 40.7128;
            double longitude = -74.0060;

            JSONObject weatherData = WeatherFetch.fetchWeather(latitude,longitude);

            assertNotNull(weatherData);
            assertEquals(weatherData.getDouble("lat"), latitude, 0.1);
            assertEquals(weatherData.getDouble("lon"), longitude, 0.1);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testConvertCityStateToLatLon(){
        try{
            String city = "London";
            String state = "KY";

            double[] latLon = WeatherFetch.convertCityStateToLatLon(city, state);
            assertNotNull(latLon);
            assertEquals(latLon[0], 37.1289771, 0.1);
            assertEquals(latLon[1], -84.0832646, 0.1);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testFetchWeatherByCityState(){
        try{
            String city = "London";
            String state = "KY";

            JSONObject weatherData = WeatherFetch.fetchWeatherByCityState(city, state);
            assertNotNull(weatherData);
            assertEquals(weatherData.getDouble("lat"),37.1289771 , 0.1);
            assertEquals(weatherData.getDouble("lon"),-84.0832646, 0.1);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


}
