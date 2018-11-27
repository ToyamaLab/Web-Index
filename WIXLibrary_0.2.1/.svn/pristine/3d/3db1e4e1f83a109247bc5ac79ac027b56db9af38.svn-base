package service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;

import service.Canonicalization;
import data.Constant;
import data.Entry;

@Service
public class CanonicalizationImpl implements Canonicalization {
	
//	private List<Entry> entryList;
	
	public CanonicalizationImpl() {
	}
	
	@Override
	public void newCanonicalizedForm(String author, String fileName, List<Entry> entryList) throws IOException {
		CsvListWriter writer = null;
		writer = new CsvListWriter(new FileWriter(Constant.WIX_FILE_MANAGEMENT_DIR.getValue() + "/" + author + "/" + fileName),CsvPreference.EXCEL_PREFERENCE);
		
		List<String> row = new ArrayList<String>();
		
//		for ( int i = 0; i < entryList.size(); i++ ) {
//			row.add(String.valueOf(entryList.get(i).getWid()));
//			row.add(String.valueOf(entryList.get(i).getEid()));
//			row.add(entryList.get(i).getKeyword());
//			row.add(entryList.get(i).getTarget());
//			row.add(String.valueOf(entryList.get(i).getClickCount()));
//			
//			writer.write(row);
//			row.clear();
//		}
		
		for ( Entry elem : entryList ) {
			row.add(String.valueOf(elem.getWid()));
			row.add(String.valueOf(elem.getEid()));
			row.add(elem.getKeyword());
			row.add(elem.getTarget());
			row.add(String.valueOf(elem.getClickCount()));
			
			writer.write(row);
			row.clear();
		}
		
		writer.close();
	}

//	public List<Entry> getEntryList() {
//		return entryList;
//	}
//
//	public void setEntryList(List<Entry> entryList) {
//		this.entryList = entryList;
//	}

}
