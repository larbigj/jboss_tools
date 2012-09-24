package net.larbig.jboss.management.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;


public class HTTPAdminTest {

	String host = "127.0.0.1";
	String realm = "ManagementRealm";
	String userName = "test";
	String password = "test32";

	public static void main(String[] args) {
		HTTPAdminTest test = new HTTPAdminTest();
		test.readMessaging();
//		test.readQueue();
//		test.readPost();
//		test.writePost();
	}
	
	
	public void readMessaging() {
		try {
			String urlStr = "http://127.0.0.1:9990/management/subsystem/messaging/:read-resource";
			GetMethod getMethod = new GetMethod(urlStr);
			executeAndRelease(getMethod);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readQueue() {
		try {
			String urlStr = "http://127.0.0.1:9990/management/subsystem/messaging/hornetq-server/default/jms-queue/testQueue66678/:read-resource";
			GetMethod getMethod = new GetMethod(urlStr);
			executeAndRelease(getMethod);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readPost() {
		try {
			PostMethod postMethod = new PostMethod("http://127.0.0.1:9990/management");
			RequestEntity entity = new StringRequestEntity(
					"{\"operation\":\"read-resource\",\"address\":[{ \"subsystem\" : \"messaging\" }, { \"hornetq-server\" : \"default\" }, { \"jms-queue\" : \"testQueue66678\" }]}",
					"application/json"
					,null);
			postMethod.setRequestEntity(entity);
			executeAndRelease(postMethod);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writePost() {
		try {
			PostMethod postMethod = new PostMethod("http://127.0.0.1:9990/management");
			RequestEntity entity = new StringRequestEntity(
					"{\"operation\":\"add\",\"address\":[{ \"subsystem\" : \"messaging\" }, " +
														"{ \"hornetq-server\" : \"default\" }, " +
														"{ \"jms-queue\" : \"testMuh\" }], " +
														"\"entries\" : [ \"java:/queue/testMUHMUH\"]}",
					"application/json"
					,null);
			postMethod.setRequestEntity(entity);
			executeAndRelease(postMethod);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void executeAndRelease(HttpMethodBase mMethod)
			throws IOException, HttpException {
		HttpClient client = new HttpClient();
		executeMethod(client, mMethod);
		printResponse(mMethod);
		mMethod.releaseConnection();
	}
	

	private void executeMethod(HttpClient client, HttpMethodBase postMethod)
			throws IOException, HttpException {
		int status;
		UsernamePasswordCredentials upc = new UsernamePasswordCredentials(userName, password);
		AuthScope as = new AuthScope(host, 9990, realm);
		client.getState().setCredentials(as, upc);
		status = client.executeMethod(postMethod);
		System.out.println("status: " + status);
	}
	
	private void printResponse(HttpMethodBase method) throws IOException {
		InputStream responseBody = method.getResponseBodyAsStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(responseBody));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		System.out.println(sb.toString());
		br.close();
	}

}