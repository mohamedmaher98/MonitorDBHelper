package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLUtil {

	public String urlToText(String urlFromClient) throws IOException {
		URL url = new URL(urlFromClient);

		HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
		openConnection.setConnectTimeout(5000);
		openConnection.setReadTimeout(5000);
		int rCode = openConnection.getResponseCode();
		String rMessageString = openConnection.getResponseMessage();
		if (openConnection.getResponseCode() != 200) {

			throw new IOException("the message is: " + rMessageString + "the response code is: " + rCode);
		}
		try (InputStream inputStream = openConnection.getInputStream();
				InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(streamReader)) {
			StringBuilder textBuilder = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				textBuilder.append(line);
			}
			if (textBuilder.isEmpty())
				throw new IOException("there are no data from the server");
			return textBuilder.toString();
		}

	}

	
}
