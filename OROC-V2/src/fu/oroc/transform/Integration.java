package fu.oroc.transform;

import java.util.HashSet;
import java.util.Set;

import fu.oroc.java.Concept;
import fu.oroc.java.Relation;

public class Integration {
	Set<Concept> new_concepts = new HashSet<Concept>();
	
	public Set<Concept> integrate(Set<Concept> concepts) {
		for(Concept c : concepts) {
				/* If partOf(obj_b, c) and obj_b does not belong to problem, then replace c 
				 * with obj_b.
				 */
		}
		return new_concepts;
	}
}
