package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import java.net.URL;

public class Api {
    final static String key = "RGAPI-c6d7f70c-1fa1-485c-93ba-e6e4ecc15434";
    private static HttpURLConnection connection;
    static BufferedReader reader;
    static String line;
    static StringBuffer responseContent = new StringBuffer();
    public static String fetchDataFromApiAsString(String link) {
        try {
            URL url = new URL(link);
            //System.out.println(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            System.out.println(status);
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();

            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseContent.toString();
    }
    public static String getSummonderIdByUserName(String id){
        String link = "https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + id+"?api_key="+ key;
        try {
            URL url = new URL(link);
            //System.out.println(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            System.out.println(status);
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();

            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] userData = responseContent.toString().split(",");
        for (int i = 0; i < userData.length; i++) {
            System.out.println(userData[i]);
        }
        String puuid =userData[2].split(":")[1];
        puuid = puuid.substring(1, puuid.length()-1);
        System.out.println("printing puuid parsed: " + puuid);
        return puuid;

    }
    public static String getMatchesBySummonerId(String id){
        String puuid = getSummonderIdByUserName(id);
        String link = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/"+ puuid +"/ids?start=0&count=20&" + "api_key="+ key;
        return fetchDataFromApiAsString(link);
    }
}