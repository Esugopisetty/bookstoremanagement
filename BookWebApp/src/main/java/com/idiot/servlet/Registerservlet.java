package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class Registerservlet extends HttpServlet {
	private static final String query = "INSERT INTO BOOKDATA(BOOKNAME,BOOKEDITION,BOOKPRICE) VALUES(?,?,?)";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get printwriter
		PrintWriter pw=res.getWriter();
		//set content type
		res.setContentType("text/html");
		//get 	the book info
		String bookName=req.getParameter("bookName");
		String bookEdition=req.getParameter("bookEdition");
		//float bookPrice=Float.parseFloat(req.getParameter("bookPrice"));
		String bookPriceParam = req.getParameter("bookPrice");
		float bookPrice = 0.0f; // default value if parameter is not provided or empty
		if (bookPriceParam != null && !bookPriceParam.trim().isEmpty()) {
		    bookPrice = Float.parseFloat(bookPriceParam);
		} else {
		    // Handle the case where bookPrice parameter is missing or empty
		}

		
		
		//Load jdbc driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}			
		//generate the connection    
		try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","12345");
			PreparedStatement ps=con.prepareStatement(query);) {
			ps.setString(1,bookName);
			ps.setString(2,bookEdition);
		    ps.setFloat(3,bookPrice);
			int count=ps.executeUpdate();
			if(count==1) {
				pw.println("<h2> record is registered Successfully</h2>");
			}
			else {
				pw.println("<h2> record not registered Successfully</h2>");
			}
			
			
		}
		catch(SQLException se) {
			se.printStackTrace();
			pw.println("<h1>"+se.getMessage()+"</h1>");
		}
		catch(Exception e) {
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"</h1>");
		}
		pw.println("<a href='Home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='bookList'>Book List</a>");
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req ,res);
	}

}
