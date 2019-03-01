package fu.oroc.transform;

import java.util.HashSet;
import java.util.Set;

import fu.oroc.java.*;

public class Decomposition {
	
	public Set<Concept> decompose(Set<Concept> concepts) {
		for(Concept c : concepts) {
			if(Decomposable(c)) {			// Using Object Composition
				/*
				 * Replace c with the decomposed objects in the set with each decompostion having
				 * the probability according to it's rating.
				 */
			}
		}
		return concepts;
	}
	
	boolean Decomposable(Concept c) {
		return true;
	}
}
