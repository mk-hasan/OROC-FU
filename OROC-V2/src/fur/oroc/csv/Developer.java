package fur.oroc.csv;

import java.math.BigDecimal;

public class Developer {
	private String name;
	private BigDecimal salary;
	private int age;
	
	
	public Developer(String name, BigDecimal salary, int age){
		this.name = name;
		this.salary=salary;
		this.age=age;	
	}
	
	
	public String getName() {
		return name;
	}
	public BigDecimal getSalary() {
		return salary;
		
	}
	public int getAge() {
		return age;
		
	}
	

}
