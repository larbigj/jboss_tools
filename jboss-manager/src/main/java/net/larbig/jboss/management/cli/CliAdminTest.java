package net.larbig.jboss.management.cli;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;
import org.jboss.as.cli.CommandLineException;

@SuppressWarnings("unused")
public class CliAdminTest {

	private Log log = LogFactory.getLog(CliAdminTest.class);
	
	private static String CMD_ADD_QUEUE = "/subsystem=messaging/hornetq-server=default/jms-queue=\"jms/wurst32\":add(durable=false,entries=[\"java:/queue/wurst32\"])";
	private static String CMD_READ_MSG = "/subsystem=messaging/:read-resource";

    CommandContext ctx = null;
	
	public static void main(String[] args) {
		CliAdminTest test = new CliAdminTest();
		test.init();
		test.execute();
		test.exit();

	}
	
	public void init(){
		log.info("connect to server");
		try {
            ctx = CommandContextFactory.getInstance().newCommandContext();
//            ctx = CommandContextFactory.getInstance().newCommandContext("user", "password");
            ctx.connectController("127.0.0.1", 9999);
        } catch(CliInitializationException e) {
            throw new IllegalStateException("Failed to initialize CLI context", e);
        } catch (CommandLineException e) {
        	log.error("error connecting to server " + e.toString());
		}	
	}
	
	
	public void exit(){
		log.info("exit server");
		ctx.terminateSession();
	}
	
	
	public void execute(){
		log.info("execute cli command");
		try {
			ctx.handle(CMD_READ_MSG);
		} catch (CommandLineException e) {
			e.printStackTrace();
		}
		
	}
	
}
