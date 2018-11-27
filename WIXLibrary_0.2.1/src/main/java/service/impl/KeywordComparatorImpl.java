package service.impl;

import org.springframework.stereotype.Service;

import data.Entry;
import service.KeywordComparator;
/**
 * FindIndexにおけるResultOfFindのソートアルゴリズム実装
 * 
 * @author kosuda
 */
@Service
public class KeywordComparatorImpl implements KeywordComparator {
	
	@Override
	public int compare(Object o_1, Object o_2) {
		return ((Entry) o_1).getKeyword().compareTo(((Entry) o_2).getKeyword());
	}

}
