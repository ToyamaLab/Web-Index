package service;

import java.io.IOException;
import java.util.List;

import data.Entry;

public interface Canonicalization {
	void newCanonicalizedForm(String author, String fileName, List<Entry> entryList) throws IOException;
}
