package com.Train.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.Train.model.Ticket;

public class TicketDao {

	public void addTicket(String source, String destination, int trainNo, String passengersList, String seatNoList) {

		try {
			Connection con = DbConnection.connect();

			PreparedStatement pst = con
					.prepareStatement("insert into ticket(source,destination, trainNo, name,seatNo) values(?,?,?,?,?)");
			pst.setString(1, source);
			pst.setString(2, destination);
			pst.setInt(3, trainNo);
			pst.setString(4, passengersList);
			pst.setString(5, seatNoList);

			int rows = pst.executeUpdate();
			System.out.println(rows + " rows updated");

			con.commit();

		} catch (Exception e) {
			System.out.println("Error in adding tickets");
			e.printStackTrace();
		}
	}

	public List<Ticket> printOccupancyChart(int trainNo) {

		try {
			Connection con = DbConnection.connect();

			PreparedStatement pst = con.prepareStatement("Select * from ticket where trainNo = ?");
			pst.setInt(1, trainNo);

			ResultSet rs = pst.executeQuery();

			List<Ticket> tickets = new ArrayList<Ticket>();
			while (rs.next()) {
				int pnr = rs.getInt(1);
				String source = rs.getString("source");
				String destination = rs.getString("destination");
				int number = rs.getInt("trainNo");
				String name = rs.getString("name");
				String seatNo = rs.getString("seatNo");

				Ticket ticket = new Ticket(pnr, source, destination, number, name, seatNo);
				tickets.add(ticket);
			}
			return tickets;


		} catch (Exception e) {
			System.out.println("Error in printing Occupancy Chart");
			e.printStackTrace();
		}
		return null;

	}

	public Ticket findTicket(int pnr) {
		
		try {

			Connection con = DbConnection.connect();
			PreparedStatement pst = con.prepareStatement("Select * from ticket where pnr = ? ");
			pst.setInt(1, pnr);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				int id = rs.getInt(1);
				String source = rs.getString("source");
				String destination = rs.getString("destination");
				int trainNo = rs.getInt("trainNo");
				String name = rs.getString("name");
				String seatNo = rs.getString("seatNo");

				Ticket ticket = new Ticket(id, source, destination, trainNo,name,seatNo);
				return ticket;
			}

		} catch (Exception e) {

			System.out.println("Error in finding ticket with pnr ");
			e.printStackTrace();
		}
		return null;

	}

	public void updateTicket(int pnr, String seatNo, String userName) {
		
		try {
			Connection con = DbConnection.connect();

			PreparedStatement pst = con.prepareStatement("update ticket set  name= ?, seatNo=? where pnr =? ");
			pst.setString(1, userName);
			pst.setString(2, seatNo);
			pst.setInt(3, pnr);
			
			int rows = pst.executeUpdate();
			System.out.println(rows + " rows updated");

			con.commit();

		} catch (Exception e) {
			System.out.println("Error in updating ticket after cancelling users");
			e.printStackTrace();
		}
		
	}

	
	
	

}
