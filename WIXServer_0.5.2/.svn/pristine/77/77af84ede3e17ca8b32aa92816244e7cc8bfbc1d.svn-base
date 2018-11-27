package core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Random;

public class WixUtilities {
	
	private static LogUtil logger;
	
	public static void printMem() {
		logger = new LogUtil(WixUtilities.class);
		DecimalFormat f1 = new DecimalFormat("#,###MB");
	    DecimalFormat f2 = new DecimalFormat("##.#");
	    
	    long free = Runtime.getRuntime().freeMemory() / 1024 / 1024;
	    long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
	    long max = Runtime.getRuntime().maxMemory() / 1024 / 1024;
	    long used = total - free;
	    double ratio = (used * 100 / (double)total);
	    
	    logger.debug("MEM INFO :"
	    	+ "合計 = " + f1.format(total)
	    	+ ", 使用量 = " + f1.format(used) + "(" + f2.format(ratio) + "%)"
	    	+ ", 使用可能最大 = " + f1.format(max)
	    );
	}
	
	public static String getNowTimestamp(){
		String timestamp = "";
		Calendar cal1 = Calendar.getInstance();

	    int year = cal1.get(Calendar.YEAR);
	    int month = cal1.get(Calendar.MONTH) + 1;
	    int day = cal1.get(Calendar.DATE);
	    int hour = cal1.get(Calendar.HOUR_OF_DAY);
	    int minute = cal1.get(Calendar.MINUTE);
	    int second = cal1.get(Calendar.SECOND);

	    StringBuffer dow = new StringBuffer();
	    
	    switch ( cal1.get(Calendar.DAY_OF_WEEK) ) {
	    	case Calendar.SUNDAY:
	    		dow.append("Sun.");
	    		break;
	    	case Calendar.MONDAY: 
	    		dow.append("Mon."); 
	    		break;
	    	case Calendar.TUESDAY: 
	    		dow.append("Tues."); 
	    		break;
	    	case Calendar.WEDNESDAY: 
	    		dow.append("Wed."); 
	    		break;
	    	case Calendar.THURSDAY: 
	    		dow.append("Thurs."); 
	    		break;
	    	case Calendar.FRIDAY: 
	    		dow.append("Fri."); 
	    		break;
	    	case Calendar.SATURDAY: 
	    		dow.append("Sat."); 
	    		break;
	    	default:
	    		break;
	    }
	    
	    timestamp = year + "/" + month + "/" + day + " " + dow + " " + hour + ":" + minute + ":" + second;
	    
		return timestamp;
	}
	
	public static String getRandomStr(int max) {
		if ( max <= 0 ) {
			return null;
		}
		
		String WIXLoginID = "";
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random rnd = new Random();
	    StringBuffer buf = new StringBuffer();
	    
	    for ( int i = 0; i < max; i++ ) {
	    	int val=rnd.nextInt(chars.length());
	    	buf.append(chars.charAt(val));
	    }
	    
	    WIXLoginID = buf.toString();
	    
		return WIXLoginID;
	}
	
	/**
	 * 実行しているマシンのホスト名を取得します。
	 * @return ホスト名
	 */
	public static String getHostName() {
		logger = new LogUtil(WixUtilities.class);
		
	    try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	    
	    return "UnknownHost";
	}
	
}
