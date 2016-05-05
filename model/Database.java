package model;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Database {

	public static final String url = "jdbc:mysql://localhost:3306/people";
	public static final String user = "root";
	public static final String password = "root";

	private LinkedList<Person> people;
	private Connection conn;

	public Database() {
		people = new LinkedList<>();
	}

	public void addPerson(Person person) {
		people.add(person);
	}

	public List<Person> getPeople() {
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
		Person.count = 1;
		people.clear();
	}

	public void removePersonAt(int index) {
		people.remove(index);
	}

	public void connect() throws Exception {
		if (conn != null)
			return;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver cannot be found.");
		}

		conn = DriverManager.getConnection(url, user, password);
	}

	public void disconnect() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void save() throws SQLException {
		String checkSql = "select count(*) as count from person where id = ?";
		PreparedStatement checkStmt = conn.prepareStatement(checkSql);

		String insertSql = "insert into person"
				+ "(id, name, age, employment_status, tax_id, uk_citizen, gender, occupation)"
				+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insertStmt = conn.prepareStatement(insertSql);

		String updateSql = "update person "
				+ "set name = ?, age = ?, employment_status = ?, tax_id = ?, uk_citizen = ?, gender = ?, occupation = ? "
				+ "where id = ?";
		PreparedStatement updateStmt = conn.prepareStatement(updateSql);

		for (Person p : people) {
			int id = p.getId();
			Age age = p.getAge();
			Employment emp = p.getEmp();
			Gender gender = p.getGender();
			String name = p.getName();
			String occupation = p.getOccupation();
			String taxId = p.getTaxId();
			boolean isUKCitizen = p.isUkCitizen();

			// Check if the person in the database
			checkStmt.setInt(1, id);
			ResultSet checkResult = checkStmt.executeQuery();
			checkResult.next();
			int count = checkResult.getInt(1);

			if (count == 0) {
				int col = 1;
				insertStmt.setInt(col++, id);
				insertStmt.setString(col++, name);
				insertStmt.setString(col++, age.name());
				insertStmt.setString(col++, emp.name());
				insertStmt.setString(col++, taxId);
				insertStmt.setBoolean(col++, isUKCitizen);
				insertStmt.setString(col++, gender.name());
				insertStmt.setString(col++, occupation);

				insertStmt.executeUpdate();
			} else {
				int col = 1;
				updateStmt.setString(col++, name);
				updateStmt.setString(col++, age.name());
				updateStmt.setString(col++, emp.name());
				updateStmt.setString(col++, taxId);
				updateStmt.setBoolean(col++, isUKCitizen);
				updateStmt.setString(col++, gender.name());
				updateStmt.setString(col++, occupation);
				updateStmt.setInt(col++, id);

				updateStmt.executeUpdate();
			}
		}
		updateStmt.close();
		insertStmt.close();
		checkStmt.close();
	}

	public void load() throws SQLException {
		clear();

		String loadSql = "select id, name, age, employment_status, tax_id, uk_citizen, gender, occupation "
				+ "from person " + "order by id";
		Statement loadStmt = conn.createStatement();

		ResultSet resultSet = loadStmt.executeQuery(loadSql);
		
		while (resultSet.next()){
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			String age = resultSet.getString("age");
			String emp = resultSet.getString("employment_status");
			String taxId = resultSet.getString("tax_id");
			boolean isUkCitizen = resultSet.getBoolean("uk_citizen");
			String gender = resultSet.getString("gender");
			String occupation = resultSet.getString("occupation");
			
			Person person = new Person(id, name, occupation, Age.valueOf(age), Employment.valueOf(emp), taxId, isUkCitizen, Gender.valueOf(gender));
			addPerson(person);
			
		}
		
		loadStmt.close();
		
	}
}
