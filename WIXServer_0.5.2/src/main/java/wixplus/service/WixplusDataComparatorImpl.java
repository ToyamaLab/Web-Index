package wixplus.service;

import java.util.Comparator;

import core.data.Entry;


/**
 * WixPlusにおけるResultSetのソートアルゴリズム実装
 * @author haseshun
 */
public class WixplusDataComparatorImpl implements Comparator<Object> {
	
	@Override
	public int compare(Object o_1, Object o_2) {
		return ((Entry) o_2).getClickCount() - ((Entry) o_1).getClickCount();
	}

}
