package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import findindex.FindIndex;
import findindex.controller.impl.FindIndexControllerImpl;
import core.service.RmiFunctionImpl;
import core.dao.FindIndexDao;
import core.data.Product;
import core.data.Constant;

@RunWith(Parameterized.class)
public class FinderImplTest {
	
	private boolean rewriteAnchorText;
	private static String ppBody;
	private int minLength;
	
	private String bookmark;
	
	private int sampleHtmlNumber;
	
	private static boolean RMI;
	
	public FinderImplTest(boolean rewriteAnchorText, int sampleHtmlNumber, String bookmark, int minLength) {
		this.rewriteAnchorText = rewriteAnchorText;
		this.sampleHtmlNumber = sampleHtmlNumber;
		this.bookmark = bookmark;
		this.minLength = minLength;
	}
	
	@Parameters
	public static List<Object[]> parameter() {
		Object[][] args = new Object[][]{{true, 0, "128", 1}, {true, 1, "128", 1}, {true, 2, "128", 1}, {true, 3, "128", 1}, {true, 4, "19-29", 1},
				{true, 5, "19-29", 1}, {true, 6, "19-29", 1}, {true, 7, "19-29", 1}, {true, 8, "128", 1}, {true, 9, "128", 1},
				{true, 10, "128", 1}, {true, 11, "128", 1}, {true, 12, "128", 1}, {true, 13, "128", 1}, {true, 14, "128", 1},
				{true, 15, "128", 1}, {true, 16, "128", 1}, {true, 17, "128", 1}, {true, 18, "128", 1}, {true, 19, "128", 1},
				{true, 20, "128", 1}, {true, 21, "128", 1}, {true, 22, "128", 1}, {true, 23, "128", 1}, {true, 24, "128", 1},
				{true, 25, "19-29", 1}, {true, 26, "19-29", 1}, {true, 27, "19-29", 1}, {true, 28, "19-29", 1}, {true, 29, "19-29", 1},
				{true, 30, "19-29", 1}, {true, 31, "128", 1}, {true, 32, "128", 1}, {true, 33, "128", 1}, {true, 34, "128", 1},
				{true, 35, "128", 1}, {true, 36, "128", 1}, {true, 37, "128", 1}, {true, 38, "128", 1}, {true, 39, "128", 1},
				{true, 40, "128", 1}, {true, 41, "128", 1}, {true, 42, "128", 1},
				{false, 0, "128", 1}, {false, 1, "128", 1}, {false, 2, "128", 1}, {false, 3, "128", 1}, {false, 4, "19-29", 1},
				{false, 5, "19-29", 1}, {false, 6, "19-29", 1}, {false, 7, "19-29", 1}, {false, 8, "128", 1}, {false, 9, "128", 1},
				{false, 10, "128", 1}, {false, 11, "128", 1}, {false, 12, "128", 1}, {false, 13, "128", 1}, {false, 14, "128", 1},
				{false, 15, "128", 1}, {false, 16, "128", 1}, {false, 17, "128", 1}, {false, 18, "128", 1}, {false, 19, "128", 1},
				{false, 20, "128", 1}, {false, 21, "128", 1}, {false, 22, "128", 1}, {false, 23, "128", 1}, {false, 24, "128", 1},
				{false, 25, "19-29", 1}, {false, 26, "19-29", 1}, {false, 27, "19-29", 1}, {false, 28, "19-29", 1}, {false, 29, "19-29", 1},
				{false, 30, "19-29", 1}, {false, 31, "128", 1}, {false, 32, "128", 1}, {false, 33, "128", 1}, {false, 34, "128", 1},
				{false, 35, "128", 1}, {false, 36, "128", 1}, {false, 37, "128", 1}, {false, 38, "128", 1}, {false, 39, "128", 1},
				{false, 40, "128", 1}, {false, 41, "128", 1}, {false, 42, "128", 1}};
		return Arrays.asList(args);
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Junit test start!");
		System.out.println("Junit version is " + junit.runner.Version.id());
		String[] command = {"sh", "-c", "ps aux | grep FindIndex"};
		Process process = Runtime.getRuntime().exec(command);
		process.waitFor();
		InputStream in = process.getInputStream();
		BufferedReader br;
		int lineCount = 0;
		try {
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = "";
			while((line = br.readLine()) != null) {
				System.out.println(line);
				lineCount++;
			}
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		RmiFunctionImpl.nowAC = 1;
		RmiFunctionImpl.ansflg = true;
		RmiFunctionImpl.updflg = true;
		
		if (lineCount > 2) {
			System.out.println("FindIndex already launched.");
			RMI = true;
		} else {
			Constant.PROP_PATH.setString("src/test/resources/wix.properties");
			FindIndex.ac_1 = new FindIndexControllerImpl();
			FindIndex.ac_1.createFindIndex();
			RMI = false;
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\\(^^)/");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("(rewriteAnchorText, sampleHtmlNumber, bookmark) = (" + rewriteAnchorText + ", " 
				+ sampleHtmlNumber + ", " + bookmark + "minLength " + minLength + ") のテスト開始");
		ppBody = ShareMethods.readSampleFile("prefind/prefind_" + rewriteAnchorText + "_"+ sampleHtmlNumber + ".txt");
	}

	@After
	public void tearDown() throws Exception {
		ppBody = "";
	}

	@Test
	public void testFind() throws Exception {
		List<Product> resultOfSelect;
		long start = System.currentTimeMillis();
		if (RMI) {
			FindIndexDao findeIndexDao = new FindIndexDao();
			resultOfSelect = findeIndexDao.find(ppBody, bookmark, minLength);
		} else {
			RmiFunctionImpl finder = new RmiFunctionImpl();
			resultOfSelect = finder.find(ppBody, bookmark, minLength);
		}
		long end = System.currentTimeMillis();
		
		//create sample of find transaction
		//ShareMethods.createObjectFile("find/resultOfFind_" + rewriteAnchorText + "_"+ sampleHtmlNumber + ".txt", resultOfSelect);
		
		List<Product> correct = ShareMethods.readObjectFile("find/resultOfFind_" + rewriteAnchorText + "_"+ sampleHtmlNumber + ".txt");
		
		assertEquals(correct.size(), resultOfSelect.size());
		for (int i = 0; i < correct.size(); i++) {
			Product correctElement = correct.get(i);
			Product element = resultOfSelect.get(i);
			assertEquals(correctElement.getWid(), element.getWid());
			assertEquals(correctElement.getEid(), element.getEid());
			assertEquals(correctElement.getKeywordLength(), element.getKeywordLength());
			assertEquals(correctElement.getStart(), element.getStart());
			assertEquals(correctElement.getEnd(), element.getEnd());
			Product correctNext = correctElement.getNext();
			Product next = element.getNext();
			while (correctNext != null && next != null) {
				assertEquals(correctNext.getWid(), next.getWid());
				assertEquals(correctNext.getEid(), next.getEid());
				assertEquals(correctNext.getKeywordLength(), next.getKeywordLength());
				assertEquals(correctNext.getStart(), next.getStart());
				assertEquals(correctNext.getEnd(), next.getEnd());
				correctNext = correctNext.getNext();
				next = next.getNext();
			}
		}
		
		System.out.println("find and select time = " + (end - start) + "(ms)");
	}

}
