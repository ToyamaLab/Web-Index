package findindex.service.impl;

import java.util.Comparator;

import core.data.Product;

/**
 * @author kosuda
 */
public class DataComparatorImpl implements Comparator<Object> {
	
	@Override
	public int compare(Object o_1, Object o_2) {
		return ((Product) o_1).getStart() - ((Product) o_2).getStart();
	}

}
