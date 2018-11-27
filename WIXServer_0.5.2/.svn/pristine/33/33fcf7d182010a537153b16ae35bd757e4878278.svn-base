package core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeString {
	
	 /**
     * 全角アルファベットと半角アルファベットとの文字コードの差
     */
    private static final int DIFFERENCE = 'Ａ' - 'A';

    /**
     * 変更対象全角記号配列
     */
    private static char[] SIGNS2 =
            {
                    '！',
                    '＃',
                    '＄',
                    '％',
                    '＆',
                    '（',
                    '）',
                    '＊',
                    '＋',
                    '，',
                    '−',
                    '．',
                    '／',
                    '：',
                    '；',
                    '＜',
                    '＝',
                    '＞',
                    '？',
                    '＠',
                    '［',
                    '］',
                    '＾',
                    '＿',
                    '｛',
                    '｜',
                    '｝'
                    };

  
    /**
     * 変換対象全角記号かを判定
     * @param pc
     * @return
     */
    private static boolean is2Sign(char pc) {
        for ( char c : ChangeString.SIGNS2 ) {
            if ( c == pc ) {
                return true;
            }
        }
        return false;
    }

    /**
     * 文字列のアルファベット・数値を半角文字に変換
     * @param str
     * @return
     */
    public String convert(String str) {
        char[] cc = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for ( char c : cc ) {
            char newChar = c;
            if ( (( 'Ａ' <= c ) && ( c <= 'Ｚ' )) || (( 'ａ' <= c ) && ( c <= 'ｚ' ))
                    || (( '１' <= c ) && ( c <= '９' )) || is2Sign(c) ) {
                // 変換対象のcharだった場合に全角文字と半角文字の差分を引く
                newChar = (char) (c - ChangeString.DIFFERENCE);
            }
            sb.append(newChar);
        }
        return sb.toString();
    }

    public static void main(String[] args) {

    	String matchstr = "弁天島 (北海道松前町)";
    	String page_title = "弁天島_(松前町)";
    	
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
    
    
    	matchstr = matcher(matchstr);
    	page_title = matcher(page_title);
		
    	System.out.println(matchstr + " " + page_title);
    	System.out.println(matchstr.equals(page_title));
    	System.out.println(contains(matchstr, page_title));

    	
    	if( !matchstr.equals(page_title) || !contains(matchstr, page_title)) { //同じなら消す

        	System.out.println("同じじゃないよ！");
        	System.out.println(matchstr + " " + page_title);

			if ( map.containsKey(matchstr) ) {
				map.get(matchstr).add(page_title);

		    	System.out.println("追加しました！");

			}
			else {
				List<String> keylist = new ArrayList<String>();
				keylist.add(page_title);
				map.put(matchstr, keylist);
		    	System.out.println("追加しました！2");

			}
		}
    }
	public static String preMatch(String str) {
		
		if ( str == "\\" ||
			str == "*" ||
			str == "+" ||
			str == "." ||
			str == "?" ||
			str == "{" ||
			str == "}" ||
			str == "(" ||
			str == ")" ||
			str == "[" ||
			str == "]" ||
			str == "^" ||
			str == "$" ||
			str == "|" ) {
			
			str = "\\" + str; 
		}	
		
		System.out.println(str);

		return str;
	}
	public static boolean nextMatch(String str, String title) {
		
		if ( str.matches("\\(.*\\)") && title.matches("\\(.*\\)") ) {
			System.out.println("a");
			return str.replaceAll("\\(.*\\)", "").equals(title.replaceAll("\\(.*\\)", ""));
			
		} else if ( str.matches("\\(.*\\)") ) {
			System.out.println("b");
			
			return str.replaceAll("\\(.*\\)", "").equals(title);
			
			
		} else if ( title.matches("\\(.*\\)") ) {
			System.out.println("c");
			
			return title.replaceAll("\\(.*\\)", "").equals(str);
			
		}
		System.out.println("d");
		
		return false;
	}
	public static String matcher(String str) {

		str = str.replaceAll("\\s\\(", "(");
		str = str.replaceAll("_\\(", "(");
		str = str.replaceAll("#.*$", "");
		str = str.replaceAll("の?一覧$", "");
		str = str.replaceAll("_?\\(曖昧さ回避\\)", "");
		str = str.replaceAll("[wW]ikipedia:", "");
		str = str.replaceAll("WP:", "");
		str = str.replaceAll("[Hh]elp:", "");
		str = str.replaceAll("H:", "");
		str = str.replaceAll("[Pp]ortal:", "");
		str = str.replaceAll("P:", "");
		str = str.replaceAll("", "");
		str = str.replaceAll("", "");

		return str;

	}
	public static boolean contains(String str, String title) {

		String regex = "\\(.*\\)";

		Pattern p = Pattern.compile(regex);
		Matcher ms = p.matcher(str);
		Matcher mt = p.matcher(title);
		
		if ( ms.find() && mt.find() ) {
			return ms.group().replaceAll( "\\(.*\\)", "").equals(mt.group().replaceAll( "\\(.*\\)", ""));
		} else if ( ms.find() ) {
			return ms.group().replaceAll( "\\(.*\\)", "").equals(mt.group());
		}else if ( mt.find() ) {
			return mt.group().replaceAll( "\\(.*\\)", "").equals(ms.group());
		}
		
		return false; 
		
	}

}


