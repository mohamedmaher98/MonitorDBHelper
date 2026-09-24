package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;
import java.sql.Connection;

public class HttpURLUtil {

	public String urlToText(String urlFromClient) throws IOException {
		URL url = new URL(urlFromClient);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		if (connection.getResponseCode() != 200) {
			System.out.println("not success");
			throw new IOException("bad status");
		}

		try(
		InputStream stream = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(stream, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				)
		{
		StringBuilder string = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			string.append(line);
		}
		return string.toString();
		}
		
	}

}
