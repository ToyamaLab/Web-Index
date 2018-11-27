package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class CreateHtmlFile {
	
	public final static String[] URL = {
		"http://ja.wikipedia.org/wiki/水",
		"http://ja.wikipedia.org/wiki/ウィキペディア",
		"http://ja.wikipedia.org/wiki/ハロウィン",
		"http://ja.wikipedia.org/wiki/スイス",
		"http://en.wikipedia.org/wiki/Wikipedia",
		"http://en.wikipedia.org/wiki/Helloween",
		"http://en.wikipedia.org/wiki/Water",
		"http://en.wikipedia.org/wiki/Switzerland",
		"http://ameblo.jp/mariya-ameblo/",
		"http://ameblo.jp/miwa-guitar/",
		"http://ameblo.jp/yamamotomizukiblog/",
		"http://official.stardust.co.jp/keiko/diary/",
		"http://sportsnavi.yahoo.co.jp/sports/soccer/japan/2013/team/1",
		"http://sportsnavi.yahoo.co.jp/sports/soccer/japan/2013/team/236",
		"http://wpb.shueisha.co.jp/2013/09/16/21810/",
		"http://detail.chiebukuro.yahoo.co.jp/qa/question_detail/q12114501012", 
		"http://detail.chiebukuro.yahoo.co.jp/qa/question_detail/q11114463486",
		"http://detail.chiebukuro.yahoo.co.jp/qa/question_detail/q12113705517",
		"http://detail.chiebukuro.yahoo.co.jp/qa/question_detail/q1189786021",
		"http://detail.chiebukuro.yahoo.co.jp/qa/question_detail/q1244542557",
		"http://www.kasi-time.com/item-68446.html",
		"http://www.kasi-time.com/item-68203.html",
		"http://www.kasi-time.com/item-47947.html",
		"http://www.kasi-time.com/item-11510.html",
		"http://www.kasi-time.com/item-3485.html",
		"http://www.web-songs.com/lyrics.php?aid=457&sid=38322&a=One_Direction&t=What_Makes_You_Beautiful",
		"http://www.web-songs.com/lyrics.php?aid=457&sid=40918&a=One_Direction&t=Live_While_Were_Young",
		"http://www.web-songs.com/lyrics.php?aid=105&sid=34505&a=Taylor_Swift&t=Mean", 
		"http://www.web-songs.com/lyrics.php?aid=108&sid=9250&a=Pink&t=Nobody_Knows",
		"http://www.web-songs.com/lyrics.php?aid=108&sid=9263&a=Pink&t=Stupid_Girls",
		"http://www.nytimes.com/",
		"http://transit.loco.yahoo.co.jp/search/result?flatlon=&from=%E5%AF%8C%E5%A3%AB%E8%A6%8B%E3%83%B6%E4%B8%98&tlatlon=%2C%2C23297&to=%E6%97%A5%E5%90%89%28%E7%A5%9E%E5%A5%88%E5%B7%9D%E7%9C%8C%29&via=&shin=1&ex=1&al=1&hb=1&lb=1&sr=1&expkind=1&ym=201310&d=09&datepicker=&hh=19&m1=2&m2=3&type=1&ws=2&s=0&x=58&y=20&kw=%E6%97%A5%E5%90%89%28%E7%A5%9E%E5%A5%88%E5%B7%9D%E7%9C%8C%29",
		"http://www.apple.com/jp/iphone-5s/features/",
		"http://www.au.kddi.com/mobile/product/smartphone/sol23/feature/",
		"http://matome.naver.jp/", 
		"http://www.youtube.com/",
		"http://www.youtube.com/watch?v=lXs96toe-B8",
		"http://www.tokyodisneyresort.co.jp/tdl/wl/atr_mountain.html",
		"http://www.tokyodisneyresort.co.jp/tds/mh/shw_fantasmic.html",
		"http://laugh-raku.com/archives/2767",
		"http://unixlife.jp/java/mod-proxy-ajp.html",
		"http://yagamix.st.keio.ac.jp/tprofile/personal.html?nickname=91e2344c68d17447f1d0b3dafd8a33f3",
		"http://www.st.keio.ac.jp/departments/faculty/ics.html",
		};
	
	public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {
		for(int i = 0; i < URL.length; i++) {
			String html = "";
			StringBuffer tmpBody = new StringBuffer();
			URL target = new URL(URL[i]);
			try {
				InputStream in = target.openStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				String line = "";
				while((line = reader.readLine()) != null) {
					tmpBody.append(line + System.getProperty("line.separator"));
				}
				html = tmpBody.toString();
				reader.close();in.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
			StringBuffer body = new StringBuffer();
			int bodyStart = 0, bodyEnd = 0;
			for(int j = 0; j < html.length(); j++) {
				String unigram = html.substring(j, j+1);
				if(bodyStart == 0 && "<".equals(unigram)) {
					if(checkBodyStart(html.substring(j, j + 5))) {
						bodyStart = j;
					}
				} else if(bodyStart != 0 && ">".equals(unigram)) {
					if(checkBodyEnd(html.substring(bodyStart, j + 1))) {
						bodyEnd = j + 1;
						break;
					}
				} 
			}
			body.append(html.substring(bodyStart, bodyEnd));
			if(i<10){
				ShareMethods.createSampleFile("body/sample_0" + i + ".html", body.toString());
			}else{
				ShareMethods.createSampleFile("body/sample_" + i + ".html", body.toString());
			}
		}	
	}
	
	private static boolean checkBodyStart(String target) {
		return  target.toLowerCase().startsWith("<body");
	}

	private static boolean checkBodyEnd(String target) {
		return  target.toLowerCase().endsWith("</body>");
	}
	
}