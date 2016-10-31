package com.evansitzes.game.http;


import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by evan on 10/27/16.
 */
public class HttpCall implements Net.HttpResponseListener {
    private URL nameUrl;
    private URL equipmentUrl;
    private URLConnection conn = null;

    public HttpCall() {
        try {
            nameUrl = new URL("http://localhost:8080/player");
            equipmentUrl = new URL("http://localhost:8080/player/1/equipment");
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getPlayerName() {
        // USING JAVA IO AND NET CLASS
        String returnValue = null;
        try {
            conn = nameUrl.openConnection();
            conn.setDoOutput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                returnValue = inputLine;
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return returnValue;
    }

    public void getPlayerEquipment() {
        // USING JAVA IO AND NET CLASS
        Json json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);

        try {
            conn = equipmentUrl.openConnection();
            conn.setDoOutput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String text = json.toJson(inputLine);
                Equipment data = json.fromJson(Equipment.class, text);
                System.out.println(data);
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

//        return returnValue;
    }

    public class Equipment {
        public Array<Data> entity;
    }

    public class Data {
        private int id;
        private int playerId;
        private String itemName;
        private boolean equipped;
    }


    @Override
    public void handleHttpResponse(final Net.HttpResponse httpResponse) {
        final int statusCode = httpResponse.getStatus().getStatusCode();
        System.out.println(statusCode + " " + httpResponse.getResultAsString());
    }

    @Override
    public void failed(final Throwable t) {
        System.out.println(t.getMessage());
    }

    @Override
    public void cancelled() {

    }
}
