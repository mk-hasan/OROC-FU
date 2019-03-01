package fu.oroc.transform;

import java.util.Set;

import fu.oroc.java.Concept;

public class Replacement {
	public Set<Concept> replace(Set<Concept> concepts) {
		for(Concept c : concepts) {
			if(can_be_replaced(c)) {			//Using Object Replacement
				/*
				 * Replace c with the replacement object in the set with each replacement having
				 * the probability according to it's rating. (Same object can undergo the replacement
				 * process multiple times but with declining probability)
				 */
			}
		}
		return concepts;
	}
	
	boolean can_be_replaced(Concept c) {
		return true;
	}
}
