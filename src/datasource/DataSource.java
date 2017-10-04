package datasource;

import java.util.HashMap;

public abstract class DataSource {
	protected DataSourceApi dataSourceApi;

	protected DataSource(DataSourceApi dataSourceApi) {
		this.dataSourceApi = dataSourceApi;
	}

	public abstract HashMap<String, String> getData();

}
