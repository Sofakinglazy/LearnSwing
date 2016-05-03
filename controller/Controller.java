package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.*;
import swing.*;

public class Controller {
	private Database db;
	
	public Controller(){
		db = new Database();
	}
	
	public List<Person> getDatabase(){
		return db.getPeople();
	}
	
	public void addPerson(FormEvent e) {
		String name = e.getName();
		String occupation = e.getOccupation();
		int ageId = e.getAgeId();
		String emp = e.getEmp();
		String tax = e.getTaxId();
		boolean isUkCitizen = e.isUkCitizen();
		String gen = e.getGender();
		
		Age age = null; 
		switch(ageId){
		case 0:
			age = Age.Child;
			break;
		case 1:
			age = Age.Adult;
			break;
		case 2:
			age = Age.Senior;
			break;
		}
		
		Employment employment = null;
		if (emp.equals("Employed")) employment = Employment.Employed;
		else if (emp.equals("Half-employed")) employment = Employment.HalfEmployed;
		else if (emp.equals("Unemployed")) employment = Employment.Unemployed;
		else employment = Employment.Other;
		
		Gender gender = null;
		if (gen.equals("Male")) gender = Gender.Male;
		else gender = Gender.Female;
		
		Person person = new Person(name, occupation, age, employment, tax, isUkCitizen, gender);
		
		db.addPerson(person);
	}
	
	public void saveToFile(File file) throws IOException{
		db.saveToFile(file);
	}
	
	public void loadFromFile(File file) throws IOException{
		db.loadFromFile(file);
	}

	public void clear() {
		db.clear();
	}

	public void removePersonAt(int index) {
		db.removePersonAt(index);
	}
	
}
