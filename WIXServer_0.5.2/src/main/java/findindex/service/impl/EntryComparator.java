package findindex.service.impl;

import java.util.Comparator;

import core.data.Entry;

public class EntryComparator implements Comparator<Entry> {
	@Override
	public int compare(Entry e1, Entry e2) {
		String s1 = e1.getKeyword();
		String s2= e2.getKeyword();
		return s1.compareTo(s2);
	}
}
