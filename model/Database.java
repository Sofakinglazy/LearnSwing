package model;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {
	private LinkedList<Person> people;
	
	public Database(){
		people = new LinkedList<>();
	}
	
	public void addPerson(Person person){
		people.add(person);
	}
	
	public List<Person> getPeople(){
		return Collections.unmodifiableList(people);
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

	public void removePersonAt(int index) {
		people.remove(index);
	}
}
