package controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import service.impl.UploadServiceImpl;
import controller.testController;

@Controller
@RequestMapping(value = "/test")
public class testControllerImpl implements testController {
	@Autowired
	private UploadServiceImpl uploadService;
	
	public testControllerImpl() {
		
	}
	@Override
	@RequestMapping(method = RequestMethod.POST, headers = { "Accept=application/plain"})
	public @ResponseBody String test(@RequestParam("cid") int[] cid)  {

		StringBuffer sb = new StringBuffer();
		for ( int i = 0; i < cid.length; i++ ) {
			sb.append("cid[" + i + "] = " + cid[i] + "  ");
		}
		
//		for ( int i = 0; i < cid.length; i++ ) {
//			uploadService.insertCategoryWix(Integer.parseInt(cid[i]), 1);
//		}
		return sb.toString();
	}
	
}
