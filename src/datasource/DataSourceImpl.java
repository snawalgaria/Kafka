package datasource;

import java.util.HashMap;

public class DataSourceImpl extends DataSource {
	public DataSourceImpl(DataSourceApi dataSourceApi) {
		super(dataSourceApi);
	}

	@Override
	public HashMap<String, String> getData() {
		// TODO Auto-generated method stub
		return dataSourceApi.getData();
	}

}
