package classes;



import java.util.List;

import classes.Service;

public class Server {
	private int id;
	private String name;
	private String url;
	private boolean active;
	private List<Service> services;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Server [id=" + id + ", name=" + name + ", url=" + url + ", active=" + active + ", services=" + services
				+ "]";
	}

}
