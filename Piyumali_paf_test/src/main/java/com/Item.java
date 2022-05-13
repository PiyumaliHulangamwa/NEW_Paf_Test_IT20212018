package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Item {

	
	

	//Method to return a DB connection
	public Connection connect()
	{
		Connection con = null;

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/power","root", "1234");
	 
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return con;
	}
	
	
	
	
	//Read operation 
	public String readItems()
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>User name</th>"
					+ "<th>District</th><th>Units</th>"
					+ "<th>Dues</th><th>Date</th>"
					+ "<th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from power";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				String userID = Integer.toString(rs.getInt("userID"));
				String name = rs.getString("name");
				String district = rs.getString("district");
				String units = rs.getString("units");
				String dues = rs.getString("dues");
				String date = rs.getString("date");
	 
				// Add into the html table
				output += "<tr><td>" + name + "</td>";
				output += "<td>" + district + "</td>";
				output += "<td>" + units + "</td>";
				output += "<td>" + dues + "</td>";
				output += "<td>" + date + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-userid='" + userID + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-userid='" + userID + "'></td></tr>";
			}
			
			con.close();
			
			// Complete the html table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the Payment details.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}

	
	
	
	//Insert operation
		public String insertItem(String p_name, String p_district, String p_units, String p_dues, String p_date)
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database";
				}
				// create a prepared statement
				String query = " insert into power(`userID`,`name`,`district`,`units`,`dues`,`date`)values (?, ?, ?, ?, ?,?)"; 
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				 preparedStmt.setInt(1, 0);
				 preparedStmt.setString(2, p_name);
				 preparedStmt.setString(3, p_district);
				 preparedStmt.setString(4, p_units);
				 preparedStmt.setString(5, p_dues);
				 preparedStmt.setString(6, p_date);
		 
				 //execute the statement
				 preparedStmt.execute();
				 con.close();
				 
				 String newItems = readItems();
				 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
				 
				 }
				catch (Exception e)
				{
					output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
							 
					System.err.println(e.getMessage()); 
				 }
				return output;
		}
		


	
		//Update operation
		public String updateItem(String p_userID, String p_name, String p_district, String p_units, String p_dues, String p_date)
	 
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database for updating."; 
				}
				
				// create a prepared statement
				String query = "UPDATE power SET name=?,district=?,units=?,dues=?,date=? WHERE userID=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				
				 // binding values
				 preparedStmt.setString(1, p_name);
				 preparedStmt.setString(2, p_district);
				 preparedStmt.setString(3, p_units);
				 preparedStmt.setString(4, p_dues);
				 preparedStmt.setString(5, p_date);
				 preparedStmt.setInt(6, Integer.parseInt(p_userID));
				 
				 // execute the statement
				 preparedStmt.execute();
				 con.close();
				 
				 String newItems = readItems();
				 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
						 
				System.err.println(e.getMessage());
			}
			return output;
	 } 
		
		
		
		
		//Delete operation
		
		public String deleteItem(String userID)
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database for deleting.";
				}
				
				// create a prepared statement
				String query = "delete from power where userID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(userID));

				// execute the statement
				preparedStmt.execute();
				con.close();
				
				String newItems = readItems();
				 
				output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		 
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
						 
				System.err.println(e.getMessage());
			}
			return output;
		}

	
}