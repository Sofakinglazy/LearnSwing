package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

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
	
	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		Person[] persons = people.toArray(new Person[people.size()]);
		
		oos.writeObject(persons);
		
		oos.close();
	}
	
	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		Person[] persons = null;
		try {
			persons = (Person[]) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		people.addAll(Arrays.asList(persons));
		
		ois.close();
	}

	public void clear() {
		Person.count = 0;
		people.removeAll(people);
	}
}
