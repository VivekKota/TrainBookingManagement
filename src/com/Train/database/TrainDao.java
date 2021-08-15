package com.Train.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Train.model.Train;

public class TrainDao {

	TicketDao ticketDao = new TicketDao();

	public void addTrain(Train train) {

		try {

			Connection con = DbConnection.connect();
			PreparedStatement pst = con.prepareStatement("insert into train values(?,?,?,?,?,?,?)");
			pst.setInt(1, train.getTrainNo());
			pst.setString(2, train.getTrainName());
			pst.setString(3, train.getSource());
			pst.setString(4, train.getDestination());
			pst.setString(5, train.getIntermediate());
			pst.setInt(6, train.getNoOfSeatsAvailable());
			pst.setString(7, train.getSeatNo());

			int rows = pst.executeUpdate();
			System.out.println(rows + " rows updated");

			con.commit();

		} catch (Exception e) {

			System.out.println("Error in adding train to the database");
			e.printStackTrace();
		}

	}

	public List<Train> showAllTrains() {

		try {

			Connection con = DbConnection.connect();
			Statement st = con.createStatement();
			String sql = "select * from train";
			ResultSet rs = st.executeQuery(sql);

			List<Train> trains = new ArrayList<Train>();
			while (rs.next()) {
				int trainNo = rs.getInt("trainNo");
				String trainName = rs.getString("trainName");
				String source = rs.getString("source");
				String destination = rs.getString("destination");
				String intermediate = rs.getString("intermediate");
				int noOfSeatsAvailable = rs.getInt(6);
				String seatNo = rs.getString("seatNo");

				Train train = new Train(trainNo, trainName, source, destination, intermediate, noOfSeatsAvailable,
						seatNo);
				trains.add(train);
			}
			return trains;

		} catch (Exception e) {

			System.out.println("Error in showing trains");
			e.printStackTrace();
		}
		return null;
	}

	public List<Train> findTrain(String trainName) {

		try {

			Connection con = DbConnection.connect();
			PreparedStatement pst = con.prepareStatement("Select * from train where trainName = ? ");
			pst.setString(1, trainName);
			ResultSet rs = pst.executeQuery();

			List<Train> trains = new ArrayList<Train>();
			while (rs.next()) {

				int trainNo = rs.getInt("trainNo");
				String Name = rs.getString("trainName");
				String source = rs.getString("source");
				String destination = rs.getString("destination");
				String intermediate = rs.getString("intermediate");
				int noOfSeatsAvailable = rs.getInt("noOfseatsAvailabe");
				String seatNo = rs.getString("seatNo");

				Train train = new Train(trainNo, Name, source, destination, intermediate, noOfSeatsAvailable, seatNo);
				trains.add(train);
			}
			return trains;

		} catch (Exception e) {

			System.out.println("Error in finding trains");
			e.printStackTrace();
		}
		return null;

	}

	public void bookTicket(String source, String destination, List<String> passengers, Train train) {

		try {

			Connection con = DbConnection.connect();

			int noOfSeatsLeft = train.getNoOfSeatsAvailable() - passengers.size();
			
			//spliting the seat numbers and adding to arraylist 
			String[] seats = train.getSeatNo().split(",");
			List<String> seatNo = new ArrayList<String>();
			for (String  no : seats) {
				seatNo.add(no);
			}
			
			//removing a seat and adding to ticket of passsenger 
			String seat;
			List<String> ticketSeatNoList = new ArrayList<String>();
			for(int k=0 ; k<passengers.size(); k++)
			{
				int index =seatNo.size() -1;
				seat = seatNo.get(index);
				seatNo.remove(index);
				ticketSeatNoList.add(seat);
			}
			
			//remaining seats to string and updating in train
			String seatNoList = seatNo.toString();
			seatNoList = seatNoList.replace("[", "").replace("]", "").replace(" ", "");

			
			PreparedStatement pst = con.prepareStatement("update train set noOfSeatsavailabe = ?, seatNo=? where trainNo =? ");
			pst.setInt(1, noOfSeatsLeft);
			pst.setString(2, seatNoList);
			pst.setInt(3, train.getTrainNo());

			int rows = pst.executeUpdate();
			System.out.println(rows + " rows updated");
			
			System.out.println("From :" + source);
			System.out.println("To :" + destination);
			System.out.println("PNR : 1");
			System.out.println("Train No : " + train.getTrainNo());
			for (int i = 0; i < passengers.size(); i++) {
				System.out.println("Passenger: " + passengers.get(i) + " Seat No :" + ticketSeatNoList.get(i));
			}
			
			String passengersList = passengers.toString();
			passengersList = passengersList.replace("[", "").replace("]", "").replace(" ", "");
			
			String ticketList = ticketSeatNoList.toString();
			ticketList = ticketList.replace("[", "").replace("]", "").replace(" ", "");

			ticketDao.addTicket(source, destination, train.getTrainNo(), passengersList, ticketList);

			con.commit();

		} catch (Exception e) {
			System.out.println("Error in booking tickets");
			e.printStackTrace();
		}

	}

	public void updateTrain(int trainNo, int noOfSeatsAvailable, String seatList) {
		
		try {
			Connection con = DbConnection.connect();

			PreparedStatement pst = con.prepareStatement("update train set noOfSeatsAvailabe= ?, seatNo=? where trainNo = ?");
			pst.setInt(1,noOfSeatsAvailable );
			pst.setString(2, seatList);
			pst.setInt(3, trainNo);
			
			int rows = pst.executeUpdate();
			System.out.println(rows + " rows updated");

			con.commit();

		} catch (Exception e) {
			System.out.println("Error in updating train ticket after cancelling users");
			e.printStackTrace();
		}
		
	}
	
	
	public Train findTrainByNo(int trainNo) {

		try {

			Connection con = DbConnection.connect();
			PreparedStatement pst = con.prepareStatement("Select * from train where trainNo = ? ");
			pst.setInt(1, trainNo);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {

				int no = rs.getInt("trainNo");
				String name = rs.getString("trainName");
				String source = rs.getString("source");
				String destination = rs.getString("destination");
				String intermediate = rs.getString("intermediate");
				int noOfSeatsAvailable = rs.getInt("noOfseatsAvailabe");
				String seatNo = rs.getString("seatNo");

				Train train = new Train(no, name, source, destination, intermediate, noOfSeatsAvailable, seatNo);
				
				return train;
			}

		} catch (Exception e) {

			System.out.println("Error in finding trains");
			e.printStackTrace();
		}
		return null;

	}
	
	
	

}
