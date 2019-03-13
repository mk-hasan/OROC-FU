package fu.oroc.java;

public class Relation {
	
	private Tuple relation;
		
	public Relation(String relation_name, Concept c1, Concept c2) { 		
		this.relation = new Tuple(relation_name, c1, c2);
	}

	public Tuple getRelation() {
		return relation;
	}

	public void setRelation(Tuple relation) {
		this.relation = relation;
	}
}
