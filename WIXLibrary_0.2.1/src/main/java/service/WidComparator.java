package service;

import java.util.Comparator;
/**
 * FindIndexにおけるResultOfFindのソートアルゴリズム
 * 
 * @author kosuda
 */
public interface WidComparator extends Comparator<Object> {
	public int compare(Object o_1, Object o_2) ;
}
