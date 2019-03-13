package fu.oroc.java;

public class Tuple { 
	  private Concept c1; 
	  private Concept c2;
	  private String relation_name;
	  
	  public Tuple(String relation_name, Concept c1, Concept c2) { 
	    this.relation_name = relation_name;
		this.c1 = c1; 
	    this.c2 = c2; 
	  }

	public Concept getC1() {
		return c1;
	}

	public void setC1(Concept c1) {
		this.c1 = c1;
	}

	public Concept getC2() {
		return c2;
	}

	public void setC2(Concept c2) {
		this.c2 = c2;
	}

	public String getRelation_name() {
		return relation_name;
	}

	public void setRelation_name(String relation_name) {
		this.relation_name = relation_name;
	} 
} 

