package com.sri.sessions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



class ItemDAO {
	
	static Item item=new Item();
	private static String  connectionUrl = "";
	private static Properties dbprops = null;
	static ArrayList<Item> items=new ArrayList<Item>();
	static ArrayList<String> s1=new ArrayList<String>();
	static
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("");
		 dbprops = DatabaseProperties.getInstance().getProperties();
		connectionUrl = "jdbc:mysql://" 
				+ dbprops.getProperty("server") 
				+ ":" + dbprops.getProperty("port")
				+ "/" + dbprops.getProperty("database")+"?autoReconnect=true&useSSL=false";
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	{
		System.out.println("Called when the class is initialized");
	}
	
	public static Connection getConnection() throws Exception
	{
		Connection con = DriverManager.getConnection(connectionUrl, dbprops.getProperty("userid"), dbprops.getProperty("password"));
return con;
	}
          

	
		public  static ArrayList<Item> display() {
			Connection con=null;
			List items=new ArrayList<Item>();
			try {
				con=getConnection();
				PreparedStatement stmt=con.prepareStatement("select * from item");
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) 
				{
					item=new Item(rs.getString("u_name"),rs.getInt("price"),rs.getInt("quantity"));
					items.add(item);
				}
			}
			catch(Exception e) {
				System.out.println(e);
			}
			finally {
				if(con!=null) {
					try {
						con.close();
					}
					catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return new ArrayList<Item>(items);
		}
		
}