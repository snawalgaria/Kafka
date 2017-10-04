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

public class DataSource3 implements DataSourceApi {

	private String apikey = "8a001c283f20156b";

	@Override
	public HashMap<String, String> getData() {
		String cities[] = { "London", "Munich", "Zurich", "Berlin", "Paris" };
		String cntry[] = { "UK/", "Germany/", "Switzerland/", "Germany/", "France/" };
		HashMap<String, String> data = new HashMap<String, String>();
		for (int i = 0; i < 5; i++) {
			String url1 = "http://api.wunderground.com/api/" + apikey + "/conditions/q/" + cntry[i] + cities[i]
					+ ".json";
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
				curr = jsonObj.getJSONObject("current_observation");
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
