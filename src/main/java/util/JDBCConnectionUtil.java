package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import classes.Server;
import classes.Service;

public class JDBCConnectionUtil {

	String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	String userName = "Maher";
	String passWord = "Maher123";

	private Connection getConnection() throws SQLException {

		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Connection con = DriverManager.getConnection(url, userName, passWord);
		return con;
	}

	public List<Server> loadServers() throws SQLException {

		Map<Integer, Server> servers_services_map = new LinkedHashMap<Integer, Server>();

		String sql = "select sr.id as SRV_id, sr.name as SRV_NAME ,sr.url ,sr.is_active ,\r\n"
				+ "sc.id as SVC_id ,sc.server_id ,sc.service_key ,sc.name as SVC_name  ,sc.description \r\n"
				+ "from monitor_servers SR left join monitor_services sc  \r\n" + "on sr.id=sc.server_id";

		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet rs = statement.executeQuery();) {
			while (rs.next()) {
				Server server;
				int server_id = rs.getInt("srv_id");
				boolean containsKey = servers_services_map.containsKey(server_id);
				if (!containsKey) {
					server = new Server();
					fillServers(server, rs);
					servers_services_map.put(server_id, server);
				} else {
					Server exisistingServer = servers_services_map.get(server_id);

					exisistingServer.getServices().addAll(fillServices(rs));

				}
			}
		}
		return new ArrayList<Server>(servers_services_map.values());
	}

	public void fillServers(Server server, ResultSet rs) throws SQLException {

		server.setId(rs.getInt("SRV_ID"));
		server.setName(rs.getString("SRV_NAME"));
		server.setUrl(rs.getString("URL"));
		boolean active = rs.getInt("IS_Active") == 1;
		server.setActive(active);
		if ((Integer) rs.getInt("svc_id") != 0)
			server.setServices(fillServices(rs));

	}

	public List<Service> fillServices(ResultSet rs) throws SQLException {
		List<Service> services = new ArrayList<Service>();
		Service service = new Service();
		service.setId(rs.getInt("SVC_id"));
		service.setServer_id(rs.getInt("server_id"));
		service.setName(rs.getString("SVC_name"));
		service.setDescription(rs.getString("description"));
		service.setService_key(rs.getString("service_key"));
		services.add(service);
		return services;
	}

}
