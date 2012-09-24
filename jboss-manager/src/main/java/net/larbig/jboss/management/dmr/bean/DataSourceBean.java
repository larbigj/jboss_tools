package net.larbig.jboss.management.dmr.bean;

public class DataSourceBean {
	
	private String jndiName;
	private String connectionUrl;
	private String driverName;
	private String userName;
	private String password;
	private String datasourceName;
	
	
	public DataSourceBean(String jndiName, String connectionUrl, String driverName, String userName, String password, String poolName) {
		super();
		this.jndiName = jndiName;
		this.connectionUrl = connectionUrl;
		this.driverName = driverName;
		this.userName = userName;
		this.password = password;
		this.datasourceName = poolName;
	}


	public String getJndiName() {
		return jndiName;
	}


	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}


	public String getConnectionUrl() {
		return connectionUrl;
	}


	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}


	public String getDriverName() {
		return driverName;
	}


	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getDatasourceName() {
		return datasourceName;
	}


	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}



	
	

}
