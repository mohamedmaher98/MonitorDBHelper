package main;

import java.sql.SQLException;
import java.util.List;

import classes.Server;
import classes.Service;
import util.HttpURLUtil;
import util.JDBCConnectionUtil;

public class Test {

	public static void main(String[] args) {
		/*
		 * JDBCConnectionUtil jdbc = new JDBCConnectionUtil(); try { List<Server>
		 * servers = jdbc.loadServers();
		 * 
		 * System.out.println("servers: " + servers.size()); System.out.println();
		 * 
		 * for (Server s : servers) { System.out.println(s.getId() + "  " + s.getName()
		 * + "  " + s.getUrl() + "  active=" + s.isActive());
		 * 
		 * List<Service> list = s.getServices(); if (list == null || list.isEmpty()) {
		 * System.out.println("      (no services)"); } else { for (Service v : list) {
		 * System.out.println("      - " + v.getService_key() + "  |  " + v.getName());
		 * } } System.out.println(); } } catch (SQLException e) {
		 * System.out.println(e.getMessage()); }
		 */

		HttpURLUtil urlUtil = new HttpURLUtil();
		try {
			
			System.out.println(urlUtil.urlToText(
					"https://api.open-meteo.com/v1/forecast?latitude=30.04&longitude=31.24&current=temperature_2m"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JDBCConnectionUtil jdbcConnectionUtil = new JDBCConnectionUtil();
		
		jdbcConnectionUtil.fillWeatherTables();

	}
}
