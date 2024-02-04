package data;

public class Person {

	private String name;
	private String mobileNumber;
	private String emailId;
	private int age;
	private float salary;
	private boolean isMarried;
	private Address address;
	private String[] vehicles;

	public Person(String name, String mobileNumber, String emailId, int age, float salary, boolean isMarried,
			Address address, String[] vehicles) {
		super();
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
		this.age = age;
		this.salary = salary;
		this.isMarried = isMarried;
		this.address = address;
		this.vehicles = vehicles;
	}

}
