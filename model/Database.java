package model;

import java.util.ArrayList;

public class Database {
	private ArrayList<Person> people;
	
	public Database(){
		people = new ArrayList<>();
	}
	
	public void addPerson(Person person){
		people.add(person);
	}
	
	public ArrayList<Person> getPeople(){
		return people;
	}
}
