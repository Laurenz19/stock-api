package org.stockapp.stock_api;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


/**
 *Created on 10/02/2022
 *by SAMBANY Michel Laurenzio 
 **/
public class HikariConnection {
	
	private static DataSource dataSource;
	private HikariConfig hikaConfig = new HikariConfig();
	
	public HikariConnection(String host, String port, String db_user, String db_password, String db_name) {
		
		this.hikaConfig.setJdbcUrl("jdbc:mysql://"+host+":"+port+"/"+db_name);
        this.hikaConfig.setUsername(db_user);
        this.hikaConfig.setPassword(db_password);
        this.hikaConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        
        this.hikaConfig.setConnectionTimeout(Duration.ofSeconds(30).toMillis());
        this.hikaConfig.setIdleTimeout(Duration.ofMinutes(2).toMillis());
        
        HikariDataSource hikariDataSource = new HikariDataSource(hikaConfig);
        HikariConnection.dataSource = hikariDataSource;
        
	}
	
	 public Connection getConnection(){
	    	
	    	Connection connection = null;
	    	try {
				connection =  HikariConnection.dataSource.getConnection();
				System.out.println("App connected with the database");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        return connection;
	  }
}
