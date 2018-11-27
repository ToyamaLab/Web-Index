package controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.impl.EntryServiceImpl;
import service.impl.MetaInfoServiceImpl;
import controller.WIXFileInfoController;
import data.Entry;
import data.MetaInfo;

@Controller
public class WIXFileInfoControllerImpl implements WIXFileInfoController {

	@Autowired
	MetaInfoServiceImpl metaInfoService;
	
	@Autowired
	EntryServiceImpl entryService;
	
	@Override
	@RequestMapping(value = "/metainfo/{wid}", method = RequestMethod.GET, headers = { "Accept=application/json" })
	public @ResponseBody MetaInfo getMetaInfoById(
			@PathVariable("wid") int wid) {
		if ( wid == 0 ) {
			throw new RuntimeException("wid is null");
		}
		
		MetaInfo metaInfo = metaInfoService.loadById(wid);
		
		return metaInfo;
	}
	
	@RequestMapping(value = "/metainfo", method = RequestMethod.GET, headers = {"Accept=application/json"})
	public @ResponseBody List<MetaInfo> getAllMetaInfo() {
		List<MetaInfo> metaInfo = metaInfoService.bulkLoad();
		
		return metaInfo;
	}
	
	@RequestMapping(value = "/entry/{wid}/{eid}", method = RequestMethod.GET, headers = {"Accept=application/json"})
	public @ResponseBody Entry getEntryById(
			@PathVariable("wid") int wid,
			@PathVariable("eid") int eid) {
		if ( wid == 0 || eid == 0 ) {
			throw new RuntimeException("wid is null or eid is null");
		}
		
		Entry entry = entryService.loadById(wid, eid);
		
		return entry;
	}
	
	@RequestMapping(value = "/entry", method = RequestMethod.GET, headers = {"Accept=application/json"})
	public @ResponseBody List<Entry> getAllEntry() {
		List<Entry> entries = entryService.bulkLoad();
		
		return entries;
	}
	
	@RequestMapping(value = "/entry/{wid}", method = RequestMethod.GET, headers = {"Accept=application/json"})
	public @ResponseBody List<Entry> getEntryByWid(
			@PathVariable("wid") int wid) {
		List<Entry> entries = entryService.loadByWid(wid);
		
		return entries;
	}
	
}
