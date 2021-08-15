package com.Train.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	public static Connection con;

	public static Connection connect() {

		try {

			Connection con = null;
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TrainBooking", "root", "7075725533");

			con.setAutoCommit(false);
			return con;

		} catch (Exception e) {

			System.out.println("Error in connecting to  the Database");
			e.printStackTrace();
		}
		return null;

	}

	public static void close() {
		
		if (con != null) {
			try {
				con.close();

			} catch (SQLException e) {

				System.out.println("Error in closing the Connection");
				e.printStackTrace();
			}
		}
	}

}
