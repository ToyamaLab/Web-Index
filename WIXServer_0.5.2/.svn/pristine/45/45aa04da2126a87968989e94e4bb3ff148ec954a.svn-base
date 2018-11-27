package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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

import core.service.PreFinder;

import core.data.Product;

@RunWith(Parameterized.class)
public class PreFinderImplTest {
	
	private static String body;
	private static List<Product> doNotRewriteList;
	private boolean rewriteAnchorText;
	
	private int sampleHtmlNumber;
	
	public PreFinderImplTest(boolean rewriteAnchorText, int sampleHtmlNumber) {
		this.rewriteAnchorText = rewriteAnchorText;
		this.sampleHtmlNumber = sampleHtmlNumber;
	}
	
	@Parameters
	public static List<Object[]> parameter() {
		Object[][] args = new Object[][]{{true, 0}, {true, 1}, {true, 2}, 
				{true, 3}, {true, 4}, {true, 5}, {true, 6}, {true, 7}, {true, 8}, {true, 9},
				{true, 10}, {true, 11}, {true, 12}, {true, 13}, {true, 14},
				{true, 15}, {true, 16}, {true, 17}, {true, 18}, {true, 19},
				{true, 20}, {true, 21}, {true, 22}, {true, 23}, {true, 24},
				{true, 25}, {true, 26}, {true, 27}, {true, 28}, {true, 29},
				{true, 30}, {true, 31}, {true, 32}, {true, 33}, {true, 34},
				{true, 35}, {true, 36}, {true, 37}, {true, 38}, {true, 39},
				{true, 40}, {true, 41}, {true, 42},
				{false, 0}, {false, 1}, {false, 2}, {false, 3}, {false, 4}, 
				{false, 5}, {false, 6}, {false, 7}, {false, 8}, {false, 9},
				{false, 10}, {false, 11}, {false, 12}, {false, 13}, {false, 14},
				{false, 15}, {false, 16}, {false, 17}, {false, 18}, {false, 19},
				{false, 20}, {false, 21}, {false, 22}, {false, 23}, {false, 24},
				{false, 25}, {false, 26}, {false, 27}, {false, 28}, {false, 29},
				{false, 30}, {false, 31}, {false, 32}, {false, 33}, {false, 34},
				{false, 35}, {false, 36}, {false, 37}, {false, 38}, {false, 39},
				{false, 40}, {false, 41}, {false, 42}};
		
		return Arrays.asList(args);
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Junit test start!");
		System.out.println("Junit version is " + junit.runner.Version.id());
		doNotRewriteList = new ArrayList<Product>();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\\(^^)/");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("(rewriteAnchorText, sampleHtmlNumber) = (" + rewriteAnchorText + ", " 
				+ sampleHtmlNumber + ") のテスト開始");
		if ( sampleHtmlNumber < 10 ) {
			body = ShareMethods.readSampleFile("body/sample_0" + sampleHtmlNumber + ".html");
		}else{
			body = ShareMethods.readSampleFile("body/sample_" + sampleHtmlNumber + ".html");
		}
	}

	@After
	public void tearDown() throws Exception {
		doNotRewriteList.clear();
	}

	@Test
	public void testPreFind() {
		long start = System.currentTimeMillis();
		PreFinder preFinder = new PreFinder();
		String ppBody = preFinder.preFind(body, doNotRewriteList, rewriteAnchorText);
		long end = System.currentTimeMillis();
		
		//create sample of pre-find transaction
//		if ( sampleHtmlNumber < 10 ) {
//			ShareMethods.createSampleFile("prefind/prefind_" + rewriteAnchorText + "_0"+ sampleHtmlNumber + ".txt", ppBody);
//		} else {
//			ShareMethods.createSampleFile("prefind/prefind_" + rewriteAnchorText + "_"+ sampleHtmlNumber + ".txt", ppBody);
//		}
		
//		try {
//			if ( sampleHtmlNumber < 10 ) {
//				ShareMethods.createObjectFile("prefind/doNotRewriteList_" + rewriteAnchorText + "_0" + sampleHtmlNumber + ".txt", doNotRewriteList);
//			} else {
//				ShareMethods.createObjectFile("prefind/doNotRewriteList_" + rewriteAnchorText + "_" + sampleHtmlNumber + ".txt", doNotRewriteList);
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		List<Product> correct = new ArrayList<Product>();
		try {
			if ( sampleHtmlNumber < 10 ) {
				correct = ShareMethods.readObjectFile("prefind/doNotRewriteList_" + rewriteAnchorText + "_0"+ sampleHtmlNumber + ".txt");
			} else {
				correct = ShareMethods.readObjectFile("prefind/doNotRewriteList_" + rewriteAnchorText + "_"+ sampleHtmlNumber + ".txt");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if ( sampleHtmlNumber < 10 ) {
			assertEquals(ShareMethods.readSampleFile("prefind/prefind_" + rewriteAnchorText + "_0"+ sampleHtmlNumber + ".txt"), 
				ppBody);
		} else {
			assertEquals(ShareMethods.readSampleFile("prefind/prefind_" + rewriteAnchorText + "_"+ sampleHtmlNumber + ".txt"), 
					ppBody);
		}
		
		assertEquals(correct.size(), doNotRewriteList.size());
		
		for (int i = 0; i < correct.size(); i++) {
			Product correctElement = correct.get(i);
			Product element = doNotRewriteList.get(i);
			assertEquals(correctElement.getMarkup(), element.getMarkup());
			assertEquals(correctElement.getStart(), element.getStart());
		}
		
		System.out.println("prefind time = " + (end - start) + "(ms)");
	}

}