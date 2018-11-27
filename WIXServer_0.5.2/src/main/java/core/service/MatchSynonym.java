package core.service;

import java.util.List;

import core.data.Product;

public class MatchSynonym {
	
	public MatchSynonym() {
		
	}
	
	public boolean match(List<Product> list, Product obj) {
		boolean match = false; 
		
		for ( Product pro : list ) {
			if ( obj.equalsSynonym(pro) ) {
				match = true;
				break;
			}
		}
		
		if ( obj.getWid() < 0 ) {
			obj.setWid(-obj.getWid());
		}
	
		return match;
	}
	
}


