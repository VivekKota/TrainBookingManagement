package com.Train.model;

public class Train {

	private int trainNo;
	private String trainName;
	private String source;
	private String destination;
	private String intermediate;
	private int noOfSeatsAvailable;
	private String seatNo;

	public Train(int trainNo, String trainName, String source, String destination, String intermediate,
			int noOfSeatsAvailable, String seatNo) {
		super();
		this.trainNo = trainNo;
		this.trainName = trainName;
		this.source = source;
		this.destination = destination;
		this.intermediate = intermediate;
		this.noOfSeatsAvailable = noOfSeatsAvailable;
		this.seatNo = seatNo;
	}

	public Train() {
		super();
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getIntermediate() {
		return intermediate;
	}

	public void setIntermediate(String intermediate) {
		this.intermediate = intermediate;
	}

	public int getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(int trainNo) {
		this.trainNo = trainNo;
	}

	public int getNoOfSeatsAvailable() {
		return noOfSeatsAvailable;
	}

	public void setNoOfSeatsAvailable(int noOfSeatsAvailable) {
		this.noOfSeatsAvailable = noOfSeatsAvailable;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	@Override
	public String toString() {
		return "Train [trainNo=" + trainNo + ", trainName=" + trainName + ", source=" + source + ", destination="
				+ destination + ", intermediate=" + intermediate + ", noOfSeatsAvailable=" + noOfSeatsAvailable
				+ ", seatNo=" + seatNo + "]";
	}
	
	

}
