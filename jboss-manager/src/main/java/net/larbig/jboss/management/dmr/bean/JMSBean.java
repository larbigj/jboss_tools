package net.larbig.jboss.management.dmr.bean;

public class JMSBean {
	
	
	private String name;
	private Boolean durable;
	private String entries;
	
	
	public JMSBean(String name, Boolean durable, String entries) {
		super();
		this.name = name;
		this.durable = durable;
		this.entries = entries;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getDurable() {
		return durable;
	}
	public void setDurable(Boolean durable) {
		this.durable = durable;
	}
	public String getEntries() {
		return entries;
	}
	public void setEntries(String entries) {
		this.entries = entries;
	}

}
