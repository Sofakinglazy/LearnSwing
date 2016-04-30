package swing;

import java.util.EventObject;

public class FormEvent extends EventObject{
	
	private String name;
	private String occupation;
	private int ageId;
	private String emp;
	private String taxId;
	private boolean ukCitizen;
	private String gender;

	public FormEvent(Object source, String name, String occupation, int ageId, String emp,
			String taxId, boolean ukCitizen, String gender) {
		super(source);
		
		this.name = name;
		this.occupation = occupation;
		this.ageId = ageId;
		this.emp = emp;
		this.taxId = taxId;
		this.ukCitizen = ukCitizen;
		this.gender = gender;
	}

	public String getTaxId() {
		return taxId;
	}

	public boolean isUkCitizen() {
		return ukCitizen;
	}

	public String getEmp() {
		return emp;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public int getAgeId() {
		return ageId;
	}

	public void setAgeId(int ageId) {
		this.ageId = ageId;
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

	public String getGender() {
		return gender;
	}
	
	
	
}
