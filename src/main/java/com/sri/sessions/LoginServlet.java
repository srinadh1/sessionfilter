package com.sri.sessions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginServlet() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		Connection con = null;
		try {
			con = ItemDAO.getConnection();
			 String pwd=HashGenerator.encryptPassword(request.getParameter("password"));
			PreparedStatement stmt = con.prepareStatement("select *from login where userid=? and password=?");
			stmt.setInt(1,Integer.parseInt(request.getParameter("id")));
			stmt.setString(2, pwd);
		    ResultSet rs=stmt.executeQuery();
		   
			if(rs.next()) {
		  
			if(rs.getInt(1)==Integer.parseInt(request.getParameter("id"))&&(rs.getString(2).equals(pwd))) 
			{
					request.getSession(true);
					response.sendRedirect("http://localhost:8080/sessionfilter/Controller");
					
				}
				else {
					response.sendRedirect("http://localhost:8080/sessionfilter/error.jsp");
				}
			}
		
		}
		catch (Exception e) {
			System.out.println(e);
		}
		finally
		{
			if(con != null)
			{
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		}
	}

}
