package com.Train.model;

public class User {

	private int userid;
	private String userName;
	private String gender;
	private int age;

	public User() {
		super();
	}

	public User(int userid, String userName, String gender, int age) {
		super();
		this.userid = userid;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", userName=" + userName + ", gender=" + gender + ", age=" + age + "]";
	}

}
