package org.stockapp.stock_api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	Connection connection;

	public DatabaseConnection(String host, String port, String database, String dbUser, String dbPwd){
		super();
		
		String url =String.format("jdbc:mysql://%s:%s/%s", host, port, database);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection(url, dbUser, dbPwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		if(connection != null) {
			System.out.println("Connected");
		}
		return this.connection;
	}
	
	
}
