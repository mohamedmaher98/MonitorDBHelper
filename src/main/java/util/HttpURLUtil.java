package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLUtil
{

    public String urlToText(String urlFromClient) throws IOException
    {
        
        URL url = new URL(urlFromClient);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        int responseCode = connection.getResponseCode();
        if (responseCode >= 500 && responseCode <= 599)
        {
            throw new TemporaryApiException("temporary "+responseCode);
        }
        if (responseCode >= 400 && responseCode <= 499)
        {
            throw new IOException("Permanent "+responseCode);
        }

        try (
                InputStream stream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(stream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);)
        {
            StringBuilder string = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                string.append(line);
            }
            return string.toString();
        }

    }

}
