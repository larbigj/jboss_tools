package net.larbig.jboss.management.dmr;

import java.util.ArrayList;
import java.util.List;

import net.larbig.jboss.management.dmr.bean.DataSourceBean;

import org.jboss.as.controller.client.helpers.ClientConstants;
import org.jboss.dmr.ModelNode;

public class DatasourceManager extends AbstractManager{

	public void createDatasource(DataSourceBean dsBean) throws Exception{
	    ModelNode newNode = new ModelNode();
	    newNode.get(ClientConstants.OP).set(ClientConstants.ADD);
	    newNode.get(ClientConstants.OP_ADDR).add("subsystem", "datasources");
	    newNode.get(ClientConstants.OP_ADDR).add("data-source",dsBean.getDatasourceName());
	    newNode.get("jndi-name").set(dsBean.getJndiName());
	    newNode.get("connection-url").set(dsBean.getConnectionUrl());
	    newNode.get("driver-name").set(dsBean.getDriverName());
	    newNode.get("user-name").set(dsBean.getUserName());
	    newNode.get("password").set(dsBean.getPassword());
	    newNode.get("pool-name").set(dsBean.getDatasourceName());
	    execute(newNode);
	}

	public boolean checkIfDatasourceExists(String dsName) throws Exception {
	    ModelNode request = new ModelNode();
	    request.get(ClientConstants.OP).set("read-resource");
	    request.get("recursive").set(false);
	    request.get(ClientConstants.OP_ADDR).add("subsystem", "datasources");
	    ModelNode responce = execute(request);
	    ModelNode dataSources = responce.get(ClientConstants.RESULT).get("data-source");
	    if (dataSources.isDefined()) {
	        for (ModelNode dataSource : dataSources.asList()) {
	            String dataSourceName = dataSource.asProperty().getName();
	            if (dataSourceName.equals(dsName)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	public List<DataSourceBean> getDataSources() throws Exception {
		List<DataSourceBean> dataSources = new ArrayList<DataSourceBean>();
		final ModelNode request = new ModelNode();
		request.get(ClientConstants.OP).set("read-resource");
		request.get("recursive").set(true);
		request.get(ClientConstants.OP_ADDR).add("subsystem", "datasources");
		ModelNode response = execute(request);
		List<ModelNode> nodes = response.get(ClientConstants.RESULT).get("data-source").asList();
		for (ModelNode node : nodes) {
			node.asProperty().getName();
			String jndiName = node.asProperty().getValue().get("jndi-name").asString();
			String connectionUrl = node.asProperty().getValue().get("connection-url").asString();
			String driverName = node.asProperty().getValue().get("driver-name").asString();
			String userName = node.asProperty().getValue().get("user-name").asString();
			String password = node.asProperty().getValue().get("password").asString();
			String datasourceName = node.asProperty().getName();
			dataSources.add(new DataSourceBean(jndiName, connectionUrl,driverName, userName, password, datasourceName));
		}

		return dataSources;
	}
}
