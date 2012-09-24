package net.larbig.jboss.management.dmr;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.as.controller.client.OperationBuilder;
import org.jboss.dmr.ModelNode;

public abstract class AbstractManager {
	
	public void safeClose(final Closeable closeable) {
        if (closeable != null) try {
            closeable.close();
        } catch (Exception e) {
           System.out.println("Fehler " + e.toString());
        }
    }
    
	public ModelNode execute(ModelNode newNode) throws UnknownHostException,IOException {
		ModelControllerClient client = ModelControllerClient.Factory.create(InetAddress.getByName(DMRAdminTest.serverIp),DMRAdminTest.serverPort);
		try {
			 return client.execute(new OperationBuilder(newNode).build());
		} finally {
			safeClose(client);
		}
	}

}
