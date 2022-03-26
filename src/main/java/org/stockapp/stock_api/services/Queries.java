package org.stockapp.stock_api.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.stockapp.stock_api.DatabaseConnection;


/**
 *Created on 10/02/2022
 *by SAMBANY Michel Laurenzio 
 **/
public class Queries {
	
	private Connection connection;

	public void create(String table, String columns, String values){
		
		String sql = "INSERT INTO " + table +" ("+ columns + ") VALUES (" + values + ")";
		
		try {
			DatabaseConnection dbConnection = new DatabaseConnection("localhost", "3306", "stockdb", "root", "");
			connection = dbConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			System.out.println("Created");
		
			statement.execute();
			statement.close();
			connection.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void update(String table, String values, String conditions) {
		
		String sql = "UPDATE " + table + " SET "+ values + " WHERE " + conditions;
		
		try{
			DatabaseConnection dbConnection = new DatabaseConnection("localhost", "3306", "stockdb", "root", "");
			connection = dbConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			System.out.println("Updated");
			
			statement.execute();
			statement.close();
			connection.close();
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	} 
	
	public void delete(String table, String conditions) {
		String sql = "DELETE FROM " + table;
		
		if(conditions != "") {
			sql = "DELETE FROM " + table + " WHERE "+ conditions;
		}
		
		try{
			DatabaseConnection dbConnection = new DatabaseConnection("localhost", "3306", "stockdb", "root", "");
			connection = dbConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			
			statement.close();
			connection.close();
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public String maxId(String table) {

		String sql = "SELECT MAX(id) AS max_id  FROM "+table;
		String id= "";
		
		try{	
			DatabaseConnection dbConnection = new DatabaseConnection("localhost", "3306", "stockdb", "root", "");
			connection = dbConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			System.out.println(statement);
			System.out.println(result);
			
			while (result.next()){
				id=result.getString("max_id");
			}
			
			statement.close();
			connection.close();
			return id;
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
}
