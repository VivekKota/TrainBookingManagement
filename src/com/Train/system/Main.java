package com.Train.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.Train.database.DbConnection;
import com.Train.database.TicketDao;
import com.Train.database.TrainDao;
import com.Train.database.UserDao;
import com.Train.model.Ticket;
import com.Train.model.Train;
import com.Train.model.User;

public class Main {

	public Train searchTrain(String source, String destination) {

		String intermediate;
		int flag1 = 0, flag2 = 0;

		List<String> intermediates = new ArrayList<String>();
		List<Train> trains = new ArrayList<Train>();

		TrainDao trainDao = new TrainDao();
		trains = trainDao.showAllTrains();

		for (int i = 0; i < trains.size(); i++) {

			intermediates.clear();
			flag1 = 0;
			flag2 = 0;

			intermediate = trains.get(i).getIntermediate();
			String str[] = intermediate.split(",");
			intermediates.add(trains.get(i).getSource());
			for (String station : str) {
				intermediates.add(station);
			}
			intermediates.add(trains.get(i).getDestination());

			for (int j = 0; j < intermediates.size() - 1; j++) {
				if (source.equalsIgnoreCase(intermediates.get(j))) {
					flag1 = 1;
					break;
				}
			}

			for (int k = 1; k < intermediates.size(); k++) {
				if (destination.equalsIgnoreCase(intermediates.get(k))) {
					flag2 = 1;
					break;
				}
			}
			if (flag1 == 1 && flag2 == 1) {
				return trains.get(i);
			}
		}
		return null;
	}

	public static void main(String[] args) {

		Main main = new Main();
		Scanner sc = new Scanner(System.in);

		try {

			User user = new User();
			Train train = new Train();
			Ticket ticket = new Ticket();

			UserDao userDao = new UserDao();
			TrainDao trainDao = new TrainDao();
			TicketDao ticketDao = new TicketDao();

			List<String> intermediates = new ArrayList<String>();
			List<String> seats = new ArrayList<String>();
			List<Train> trains = new ArrayList<Train>();
			List<Ticket> tickets = new ArrayList<Ticket>();
			String source, destination, trainName, intermediate, userName, gender, seatNo;
			int trainNo, noOfSeatsAvailable, pnr, age, userId;

			boolean done = false;
			while (!done) {

				System.out.println("1. Add train");
				System.out.println("2. Show all Trains");
				System.out.println("3. Find a train");
				System.out.println("4. Book a ticket");
				System.out.println("5. cancel ticket");
				System.out.println("6. Print Occupancy chart");
				System.out.println("7. Add user");
				System.out.println("8. Exit");

				int choice = 0;
				System.out.println("Enter your choice");
				choice = sc.nextInt();
				switch (choice) {

				case 1:

					System.out.println("Enter Train Number");
					trainNo = sc.nextInt();
					System.out.println("Enter Train Name");
					trainName = sc.next();
					System.out.println("Enter Source");
					source = sc.next();
					System.out.println("Enter Destination");
					destination = sc.next();
					System.out.println("Enter Intermediate Stations if multiple add ',' between them");
					intermediate = sc.next();
					System.out.println("Enter No of Seats Available");
					noOfSeatsAvailable = sc.nextInt();
					System.out.println("Enter Seat Numbers");
					for (int i = 0; i < noOfSeatsAvailable; i++) {
						seatNo = sc.next();
						seats.add(seatNo);
					}

					seatNo = seats.toString();
					seatNo = seatNo.replace("[", "").replace("]", "").replace(" ", "");

					trainDao.addTrain(new Train(trainNo, trainName, source, destination, intermediate,
							noOfSeatsAvailable, seatNo));
					System.out.println("Train Added");

					break;

				case 2:

					trains = trainDao.showAllTrains();
					for (int i = 0; i < trains.size(); i++) {
						System.out.println(trains.get(i));
					}
					break;

				case 3:

					System.out.println("Enter train Name");
					trainName = sc.next();
					trains = trainDao.findTrain(trainName);

					if (trains != null) {

						for (int i = 0; i < trains.size(); i++) {
							System.out.println(trains.get(i));
						}

					} else {
						System.out.println("No Train Found");
					}
					break;

				case 4:

					System.out.println("Enter User Id");
					userId = sc.nextInt();
					user = userDao.findUser(userId);
					if (user != null) {
						System.out.println("Enter Source");
						source = sc.next();
						System.out.println("Enter Destination");
						destination = sc.next();
						train = main.searchTrain(source, destination);
						int flag3 = 0;

						if (train != null) {

							System.out.println("Enter no of passengers");
							List<String> passengers = new ArrayList<String>();
							int noOfPassengers = sc.nextInt();

							if (train.getNoOfSeatsAvailable() - noOfPassengers + 2 > 0) {

								for (int k = 0; k < noOfPassengers; k++) {
									System.out.println("Enter userName");
									userName = sc.next();
									passengers.add(userName);
								}

								trainDao.bookTicket(source, destination, passengers, train);
								flag3 = 1;

							} else {
								System.out.println("Tickets not avialble for the passengers");
								System.out.println("Total no of seats available are :" + train.getNoOfSeatsAvailable());
							}

						}

						if (flag3 == 0) {

							int flag1 = 0, flag2 = 0;

							System.out.println("No Direct Train Avaiable");
							System.out.println("Checking for Intermediate Trains");

							Train train1 = new Train();
							Train train2 = new Train();

							trains = trainDao.showAllTrains();

							for (int i = 0; i < trains.size(); i++) {

								intermediate = trains.get(i).getIntermediate();
								String str[] = intermediate.split(",");
								intermediates.add(trains.get(i).getSource());
								for (String station : str) {
									intermediates.add(station);
								}

								intermediates.add(trains.get(i).getDestination());

								for (int j = 0; j < intermediates.size(); j++) {
									if (source.equalsIgnoreCase(intermediates.get(j))) {
										flag1 = 1;
										train1 = trains.get(i);
										break;
									}
								}

								for (int k = 0; k < intermediates.size(); k++) {
									if (destination.equalsIgnoreCase(intermediates.get(k))) {
										flag2 = 1;
										train2 = trains.get(i);
										break;
									}
								}
								intermediates.clear();
							}

							if (flag1 == 1 && flag2 == 1) {

								// System.out.println(train1);
								// System.out.println(train2);
								// System.out.println("Intermediate trains are available");

								List<String> train1Stations = new ArrayList<String>();
								List<String> train2Stations = new ArrayList<String>();

								intermediate = train1.getIntermediate();
								String str[] = intermediate.split(",");
								train1Stations.add(train1.getSource());
								for (String station : str) {
									train1Stations.add(station);
								}
								train1Stations.add(train1.getDestination());

								intermediate = train2.getIntermediate();
								String str1[] = intermediate.split(",");
								train2Stations.add(train2.getSource());
								for (String station : str1) {
									train2Stations.add(station);
								}
								train2Stations.add(train2.getDestination());

								ArrayList<String> commonStations = new ArrayList<String>(train1Stations);
								commonStations.retainAll(train2Stations);

								// System.out.println(commonStations.get(0));
								train1 = main.searchTrain(source, commonStations.get(0));
								train2 = main.searchTrain(commonStations.get(0), destination);
								// System.out.println(train1);
								// System.out.println(train2);

								if (train1 != null && train2 != null) {

									System.out.println("Intermediate trains are available");
									System.out.println("Enter no of passengers");
									List<String> passengers = new ArrayList<String>();
									int noOfPassengers = sc.nextInt();
									if ((train1.getNoOfSeatsAvailable() - noOfPassengers + 2 > 0)
											&& (train2.getNoOfSeatsAvailable() - noOfPassengers + 2 > 0)) {
										for (int k = 0; k < noOfPassengers; k++) {
											System.out.println("Enter userName");
											userName = sc.next();
											passengers.add(userName);
										}
										trainDao.bookTicket(source, commonStations.get(0), passengers, train1);
										trainDao.bookTicket(commonStations.get(0), destination, passengers, train2);

									} else {
										System.out.println("Tickets not avialble for the passengers");
										System.out.println("Total no of seats available in train1 :"
												+ train1.getNoOfSeatsAvailable());
										System.out.println("Total no of seats available in train2 :"
												+ train2.getNoOfSeatsAvailable());
									}

								} else {
									System.out.println("No intermediate stations are available");
								}

							} else {
								System.out.println("No Trains are avialble");
							}

						}

					} else {
						System.out.println("First Register as a user");
					}

					break;

				case 5:

					System.out.println("Enter User Id");
					userId = sc.nextInt();
					user = userDao.findUser(userId);
					if (user != null) {

						System.out.println("Enter PNR Number");
						pnr = sc.nextInt();
						ticket = ticketDao.findTicket(pnr);
						if (ticket != null) {

							System.out.println(ticket);
							List<String> userNames = new ArrayList<String>();
							List<String> seatNos = new ArrayList<String>();

							String[] name = ticket.getName().split(",");
							for (String str : name) {
								userNames.add(str);
							}

							int userSize = userNames.size();

							String[] seatNum = ticket.getSeatNo().split(",");
							for (String str : seatNum) {
								seatNos.add(str);
							}

							for (int i = 0; i < seatNos.size(); i++) {
								System.out.println((i + 1) + " " + userNames.get(i) + " " + seatNos.get(i));
							}

							List<String> cancelledSeatList = new ArrayList<String>();
							System.out.println("Enter passenger names and ticketNo respectively and type exit");

							while (!sc.hasNext("exit")) {
								userName = sc.next();
								seatNo = sc.next();
								userNames.remove(new String(userName));

								cancelledSeatList.add(seatNo);
								seatNos.remove(new String(seatNo));
							}
							
							System.out.println(seatNos);

							userSize -= userNames.size();

							train = trainDao.findTrainByNo(ticket.getTrainNo());

							noOfSeatsAvailable = userSize + train.getNoOfSeatsAvailable();

							String[] str = train.getSeatNo().split(",");
							for (String s : str) {
								seats.add(s);
							}

							seats.addAll(cancelledSeatList);

							String updatedSeatList = seats.toString();
							updatedSeatList = updatedSeatList.replace("[", "").replace("]", "").replace(" ", "");

							seatNo = seatNos.toString();
							seatNo = seatNo.replace("[", "").replace("]", "").replace(" ", "");

							userName = userNames.toString();
							userName = userName.replace("[", "").replace("]", "").replace(" ", "");
							
						
							//System.out.println("seatNo" + seatNo);
							//System.out.println("userName"+ userName);

							
							ticketDao.updateTicket(pnr, seatNo, userName);
							
							//System.out.println("noOfSeatsAvailable" +noOfSeatsAvailable);
							//System.out.println("updatedSeatList" +updatedSeatList);

							trainDao.updateTrain(ticket.getTrainNo(), noOfSeatsAvailable, updatedSeatList);

						} else {
							System.out.println("Entered Pnr is incorrect ");
						}

					} else {
						System.out.println("No user Found");
					}

					break;

				case 6:

					System.out.println("Enter train No");
					trainNo = sc.nextInt();
					tickets = ticketDao.printOccupancyChart(trainNo);
					for (int i = 0; i < tickets.size(); i++) {
						System.out.println(tickets.get(i));
					}
					break;

				case 7:

					System.out.println("Enter User Name");
					userName = sc.next();
					System.out.println("Enter user gender");
					gender = sc.next();
					System.out.println("Enter user age");
					age = sc.nextInt();
					userDao.addUser(userName, gender, age);
					
					break;

				case 8:
					done = true;
					System.out.println("Bye");
					break;

				}
			}

		} catch (

		Exception e) {

			System.out.println("Error in main block");
			e.printStackTrace();

		} finally {
			sc.close();
			DbConnection.close();
		}

	}

}
