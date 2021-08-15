package com.Train.model;

public class Ticket {

	private int pnr;
	private String source;
	private String destination;
	private int trainNo;
	private String name;
	private String seatNo;

	public Ticket() {
		super();
	}

	public Ticket(int pnr, String source, String destination, int trainNo, String name, String seatNo) {
		super();
		this.pnr = pnr;
		this.source = source;
		this.destination = destination;
		this.trainNo = trainNo;
		this.name = name;
		this.seatNo = seatNo;
	}

	public int getPnr() {
		return pnr;
	}

	public void setPnr(int pnr) {
		this.pnr = pnr;
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

	public int getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(int trainNo) {
		this.trainNo = trainNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	@Override
	public String toString() {
		return "Ticket [pnr=" + pnr + ", source=" + source + ", destination=" + destination + ", trainNo=" + trainNo
				+ ", name=" + name + ", seatNo=" + seatNo + "]";
	}

}
