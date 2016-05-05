package model;

import java.io.Serializable;

public class Person implements Serializable{

	private static final long serialVersionUID = 10L;

	public static int count = 1;
	
	private int id;
	
	private String name;
	private String occupation;
	private Age age;
	private Employment emp;
	private String taxId;
	private boolean ukCitizen;
	private Gender gender;
	
	public Person(String name, String occupation, Age age, Employment emp, String taxId, boolean ukCitizen,
			Gender gender) {
		this.name = name;
		this.occupation = occupation;
		this.age = age;
		this.emp = emp;
		this.taxId = taxId;
		this.ukCitizen = ukCitizen;
		this.gender = gender;
		
		id = count;
		count++;
	}
	
	public Person(int id, String name, String occupation, Age age, Employment emp, String taxId, boolean ukCitizen,
			Gender gender) {
		this(name, occupation, age, emp, taxId, ukCitizen, gender);
		this.id = id;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Age getAge() {
		return age;
	}

	public void setAge(Age age) {
		this.age = age;
	}

	public Employment getEmp() {
		return emp;
	}

	public void setEmp(Employment emp) {
		this.emp = emp;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public boolean isUkCitizen() {
		return ukCitizen;
	}

	public void setUkCitizen(boolean ukCitizen) {
		this.ukCitizen = ukCitizen;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public String toString(){
		return id + ": " + name; 
	}
	
}
