package datasource;

import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class DataSource1 implements DataSourceApi {

	private String apikey = "26e9478c36574d2b893160935172809";

	@Override
	public HashMap<String, String> getData() {
		String cities[] = { "London", "Munich", "Zurich", "Berlin", "Paris" };
		HashMap<String, String> data = new HashMap<String, String>();
		for (int i = 0; i < 5; i++) {
			String url1 = "http://api.apixu.com/v1/current.json?key=" + apikey + "&q=" + cities[i];
			URL url = null;
			try {
				url = new URL(url1);
			} catch (MalformedURLException e) {

				e.printStackTrace();
				return null;
			}
			HttpURLConnection httpUrl = null;
			try {
				httpUrl = (HttpURLConnection) url.openConnection();
			} catch (IOException e) {

				e.printStackTrace();
				return null;
			}
			InputStream in = null;
			try {
				in = httpUrl.getInputStream();
			} catch (IOException e) {

				e.printStackTrace();
				return null;
			}
			String encoding = httpUrl.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			String body = null;
			try {
				body = IOUtils.toString(in, encoding);
			} catch (IOException e) {

				e.printStackTrace();
				return null;
			}
			JSONObject jsonObj = null;
			try {
				jsonObj = new JSONObject(body);
			} catch (JSONException e) {

				e.printStackTrace();
				return null;
			}
			JSONObject curr = null;
			try {
				curr = jsonObj.getJSONObject("current");
			} catch (JSONException e) {

				e.printStackTrace();
				return null;
			}
			try {
				Double tmp = curr.getDouble("temp_c");
				data.put(cities[i], tmp.toString());
			} catch (JSONException e) {

				e.printStackTrace();
				return null;
			}
		}
		return data;

	}

}
