package br.gov.rj.detran.devops.service.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map.Entry;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

public class AppInfo {

	private static final String UNKNOW = "unknow";

	private final String name;
	private final String description;
	private final String version;
	private final String hostName;

	public AppInfo(ApplicationContext context, String name, String description) {
		this.name = name;
		this.description = description != null ? description : name;
		this.version = this.version(context);
		this.hostName = this.hostName();
	}

	private String hostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return UNKNOW;
		}
	}

	private String version(ApplicationContext context) {
		Entry<String, Object> entry = context.getBeansWithAnnotation(SpringBootApplication.class).entrySet().stream()
				.findFirst().get();
		String version = entry.getValue().getClass().getPackage().getImplementationVersion();
		return version != null ? version : UNKNOW;
	}

	public String getVersion() {
		return version;
	}

	public String getName() {
		return name;
	}

	public String getHostName() {
		return hostName;
	}

	public String getDescription() {
		return description;
	}

	public String info() {
		return String.format("Aplicação %s - %s versão %s rodando no host %s .", this.name, this.description,
				this.version, this.hostName);
	}

}
