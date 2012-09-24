package net.larbig.jboss.management.dmr;

import java.util.ArrayList;
import java.util.List;

import net.larbig.jboss.management.dmr.bean.JMSBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.as.controller.client.helpers.ClientConstants;
import org.jboss.dmr.ModelNode;


public class JMSManager extends AbstractManager {
	
	private Log log = LogFactory.getLog(JMSManager.class);

	public void readQueue(JMSBean bean) throws Exception {
		final ModelNode request = new ModelNode();
		request.get(ClientConstants.OP).set("read-resource");
		request.get("recursive").set(true);
		request.get(ClientConstants.OP_ADDR).add("subsystem", "messaging");
		request.get(ClientConstants.OP_ADDR).add("hornetq-server", "default");
		request.get(ClientConstants.OP_ADDR).add("jms-queue", bean.getName());
		ModelNode response = execute(request);
		log.info(response.get(ClientConstants.RESULT).get("entries").asString());
	}

	public void addQueue(JMSBean bean) throws Exception {
		final ModelNode request = new ModelNode();
		request.get(ClientConstants.OP).set(ClientConstants.ADD);
		request.get("recursive").set(true);
		request.get(ClientConstants.OP_ADDR).add("subsystem", "messaging");
		request.get(ClientConstants.OP_ADDR).add("hornetq-server", "default");
		request.get(ClientConstants.OP_ADDR).add("jms-queue", bean.getName());
		request.get("durable").set(bean.getDurable());
		ArrayList<ModelNode> ent = new ArrayList<ModelNode>();
		ModelNode myent0 = new ModelNode(bean.getEntries());
		ent.add(myent0);
		request.get("entries").set(ent);
		execute(request);
	}

	public List<JMSBean> getQueues() throws Exception {
		List<JMSBean> queues = new ArrayList<JMSBean>();
		final ModelNode request = new ModelNode();
		request.get(ClientConstants.OP).set("read-resource");
		request.get("recursive").set(true);
		request.get(ClientConstants.OP_ADDR).add("subsystem", "messaging");

		ModelNode response = execute(request);
		List<ModelNode> nodes = response.get(ClientConstants.RESULT).get("hornetq-server").get("default").get("jms-queue").asList();
		for (ModelNode node : nodes) {
			String name = node.asProperty().getName();
			Boolean durable = node.asProperty().getValue().get("durable").asBoolean();
			String entries = node.asProperty().getValue().get("entries").asString();
			queues.add(new JMSBean(name, durable, entries));
		}
		return queues;
	}

}
