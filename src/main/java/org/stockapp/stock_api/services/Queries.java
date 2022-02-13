package org.stockapp.stock_api.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.stockapp.stock_api.HikariConnection;


/**
 *Created on 10/02/2022
 *by SAMBANY Michel Laurenzio 
 **/
public class Queries {
	
	private HikariConnection hikariConn = new HikariConnection("localhost", "3306", "root", "", "stockdb");
	private Connection connection= hikariConn.getConnection();
	
	public void create(String table, String columns, String values){
		
		String sql = "INSERT INTO " + table +" ("+ columns + ") VALUES (" + values + ")";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			System.out.println("Created");
			System.out.println(statement);
			statement.execute();
			statement.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void update(String table, String values, String conditions) {
		
		String sql = "UPDATE " + table + " SET "+ values + " WHERE " + conditions;
		
		try{	
			PreparedStatement statement = connection.prepareStatement(sql);
			System.out.println("Updated");
			System.out.println(statement);
			statement.execute();
			statement.close();
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	} 
	
	public ResultSet read(String table, String columns, String conditions) {
		
		String sql = "SELECT "+ columns + " FROM " + table;
		
		if(conditions != "") {
			sql = "SELECT "+ columns + " FROM " + table + " WHERE " + conditions;
		}
		
		try{	
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery(sql);
			System.out.println(statement);
			System.out.println(result);
			return result;
			
			
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
			PreparedStatement statement = connection.prepareStatement(sql);
			System.out.println("deleted");
			System.out.println(statement);
			statement.execute();
			statement.close();
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public String maxId(String table) {

		String sql = "SELECT MAX(id) AS max_id  FROM "+table;
		String id= "";
		
		try{	
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			System.out.println(statement);
			System.out.println(result);
			
			while (result.next()){
				id=result.getString("max_id");
			}
			return id;
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
}
