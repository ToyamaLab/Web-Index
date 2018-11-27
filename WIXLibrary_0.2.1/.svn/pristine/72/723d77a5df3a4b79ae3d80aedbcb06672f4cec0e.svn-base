package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.WidComparator;
import data.Entry;

@Service
public class WidComparatorImpl implements WidComparator {

	@Autowired
	KeywordComparatorImpl keywordComparator;
	
	@Override
	public int compare(Object o_1, Object o_2) {
		int diff = ((Entry) o_1).getWid() - ((Entry) o_2).getWid();
		
		if ( diff == 0 ) {
			return keywordComparator.compare(o_1, o_2);
		}
		
		return diff;
	}

}
