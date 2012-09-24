package net.larbig.jboss.management.dmr;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.larbig.jboss.management.dmr.bean.DataSourceBean;
import net.larbig.jboss.management.dmr.bean.JMSBean;

@SuppressWarnings("unused")
public class DMRAdminTest {

	private Log log = LogFactory.getLog(DMRAdminTest.class);

	public static String serverIp = "127.0.0.1";
	public static Integer serverPort = 9999;

	String jmsName = "weisswurst";
	String jmsJndi = "java:/queue/weisswurst";


	public static void main(String[] args) {
		DMRAdminTest jbm = new DMRAdminTest();
		// jbm.readQueues();
		// System.out.println("################################");
		// jbm.addQueue();
		// System.out.println("################################");
		// jbm.readQueue();
		// System.out.println("################################");
		// jbm.readQueues();
		// jbm.addDatasource();
		jbm.readDataSources();
	}

	private void readQueues() {
		JMSManager jmsMgr = new JMSManager();
		try {
			List<JMSBean> beans = jmsMgr.getQueues();
			for (JMSBean bean : beans) {
				log.info(bean.getName() + " - " + bean.getEntries());
			}
		} catch (Exception e) {
			log.error("error " + e.toString());
		}
	}

	private void readQueue() {
		JMSManager jmsMgr = new JMSManager();
		try {
			jmsMgr.readQueue(new JMSBean(jmsName, false, jmsJndi));
		} catch (Exception e) {
			log.error("error " + e.toString());
		}
	}

	private void addQueue() {
		JMSManager jmsMgr = new JMSManager();
		try {
			jmsMgr.addQueue(new JMSBean(jmsName, false, jmsJndi));
		} catch (Exception e) {
			log.error("error " + e.toString());
		}
	}

	private void addDatasource() {
		DatasourceManager dsm = new DatasourceManager();
		DataSourceBean bean = new DataSourceBean(
				"java:jboss/datasources/MyTestDS3242",
				"jdbc:h2:mem:mytest23424;DB_CLOSE_DELAY=-1", "h2", "sa", "sa",
				"MyTestDS52345");
		try {
			dsm.createDatasource(bean);
		} catch (Exception e) {
			log.error("error " + e.toString());
		}
	}

	private void readDataSources() {
		DatasourceManager dsm = new DatasourceManager();
		try {
			List<DataSourceBean> datasources = dsm.getDataSources();
			for (DataSourceBean ds : datasources) {
				log.info("name : "+ds.getDatasourceName()+" -- jndiName : "+ds.getJndiName()+" -- connectionURL : "+ds.getConnectionUrl());
			}
		} catch (Exception e) {
			log.error("error " + e.toString());
		}
	}

}
