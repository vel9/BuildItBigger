package com.vel9studios.levani.builditbigger.jokecloud;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestEndpointDirect {

    @Test
    public void test() {

         String response = getJSON();
         assert response != null;
    }

    // the following test accesses the server directly
    private String getJSON(){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String jsonData = null;

        try {

            URL url = new URL("http://localhost:8080/_ah/api/myApi/v1/joke");

            // Create the request and open connection to movie db
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            jsonData = buffer.toString();

        } catch (IOException e) {

            return null;

        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    //Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return jsonData;
    }

}