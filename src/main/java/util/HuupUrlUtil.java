package util;

import java.io.IOException;
import java.io.NotActiveException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HuupUrlUtil {

	public String returnUrlText(String urlFromClient) throws IOException {
		URL url = new URL(urlFromClient);
		HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
		urlConnection.setReadTimeout(5000);
		urlConnection.setConnectTimeout(5000);
		if (urlConnection.getResponseCode()!=200) {
			thorw new Exception(urlConnection.getResponseMessage());
		}
	}
}
