package util;

import java.security.PrivateKey;
import java.security.KeyStore.TrustedCertificateEntry;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import classes.Server;
import classes.Service;
import classes.WeatherReading;
import classes.WeatherRuns;

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

	public void fillWeatherTables() {
		String url = "https://api.open-meteo.com/v1/forecast?latitude=30.04&longitude=31.24&current=temperature_2m";
		String textFromClient = null;
		HttpURLUtil urlUtil = new HttpURLUtil();
		WeatherReading weatherReading = new WeatherReading();
		WeatherRuns runs = new WeatherRuns();
		String insetInWeatherReading = "INSERT INTO WEATHER_READINGS(LATU, LOGU, TEMP,READING_TIME, RECORDED_AT) values  (?,?,?,?,?)";
		String insetInWeatherRuns = "INSERT INTO WEATHER_RUNS(start_date, reading_duration, is_success,erro_message) values  (?,?,?,?)";
		long before = System.currentTimeMillis();
		runs.setStartDate(LocalDateTime.now());
		try {

			textFromClient = urlUtil.urlToText(url);
			
			runs.setSuccess(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			runs.setSuccess(false);
			runs.setErrorMassage(e.getMessage());
			return;
		} finally {
			runs.setReadingDuration((int) (System.currentTimeMillis() - before));

			try (Connection conn = getConnection();
					PreparedStatement runsStatement = conn.prepareStatement(insetInWeatherRuns)) {
				fillweatherRuns(runsStatement, runs);
			} catch (SQLException e) {
				System.out.println("could not save the run: " + e.getMessage());
			}
		}

		ObjectMapper mapper = new ObjectMapper();
		JsonNode tree = null;
		try {
			tree = mapper.readTree(textFromClient);

		} catch (JsonProcessingException e) {
			e.printStackTrace();

		}

		weatherReading.setTemp(tree.get("current").get("temperature_2m").asDouble());
		weatherReading.setLatu(tree.get("latitude").asDouble());
		weatherReading.setLogu(tree.get("longitude").asDouble());
		weatherReading.setReadingTime(LocalDateTime.parse(tree.get("current").get("time").asText()));
		weatherReading.setRecordedAt(LocalDateTime.now());

		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement(insetInWeatherReading)) {

			statement.setDouble(1, weatherReading.getLatu());
			statement.setDouble(2, weatherReading.getLogu());
			statement.setDouble(3, weatherReading.getTemp());
			statement.setTimestamp(4, Timestamp.valueOf(weatherReading.getReadingTime()));
			statement.setTimestamp(5, Timestamp.valueOf(weatherReading.getRecordedAt()));

			statement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("insert failed: " + e.getMessage());
		}

	}

	private void fillweatherRuns(PreparedStatement statement1, WeatherRuns runs) throws SQLException {
		statement1.setTimestamp(1, Timestamp.valueOf(runs.getStartDate()));
		statement1.setInt(2, runs.getReadingDuration());
		statement1.setInt(3, runs.isSuccess() ? 1 : 0);
		statement1.setString(4, runs.getErrorMassage());

		statement1.executeUpdate();

	}

}
