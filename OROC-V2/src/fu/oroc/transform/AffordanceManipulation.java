package fu.oroc.transform;

import java.util.HashSet;
import java.util.Set;

import fu.oroc.java.Concept;
import fu.oroc.java.Relation;

public class AffordanceManipulation {
	public Set<Relation> affManipulate(Set<Concept> concepts) {
		
		Set<Relation> new_relations = new HashSet<Relation>();
		
		for(Concept c1 : concepts) {
			for(Concept c2 : concepts) {
				/* If relation can be drawn between c1 and c2 so that affordance of one or both 
				 * of them is changed. (doubt)
				 */
//				new_relations.add(r);
			}
		}
		return new_relations;
	}
}
