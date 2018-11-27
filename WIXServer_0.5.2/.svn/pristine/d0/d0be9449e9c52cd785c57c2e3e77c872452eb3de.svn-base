package test;

import static org.junit.Assert.*;

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

import core.service.Rewriter;

import core.data.Product;

@RunWith(Parameterized.class)
public class RewriterImplTest {
	
	private static String ppBody;
	private static List<Product> doNotRewriteList;
	private static List<Product> resultOfDecide;
	private boolean rewriteAnchorText;
	private static String bookmarkedWIX;
	
	private int sampleHtmlNumber;
	
	public RewriterImplTest(boolean rewriteAnchorText, int sampleHtmlNumber) {
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
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\\(^^)/");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("(rewriteAnchorText, sampleHtmlNumber) = (" + rewriteAnchorText + ", " 
				+ sampleHtmlNumber + ") のテスト開始");		
		ppBody = ShareMethods.readSampleFile("prefind/prefind_" + rewriteAnchorText + "_"+ sampleHtmlNumber + ".txt");
		RewriterImplTest.doNotRewriteList = ShareMethods.readObjectFile("prefind/doNotRewriteList_" + rewriteAnchorText + "_"+ sampleHtmlNumber + ".txt");
		
		RewriterImplTest.resultOfDecide = ShareMethods.readObjectFile("find/resultOfFind_" + rewriteAnchorText + "_"+ sampleHtmlNumber + ".txt");
		
		bookmarkedWIX = "";
	}

	@After
	public void tearDown() throws Exception {
		doNotRewriteList.clear();
		resultOfDecide.clear();
		ppBody = "";
		bookmarkedWIX = "";
	}

	@Test
	public void testRewrite() {
		long start = System.currentTimeMillis();
		Rewriter rewriter = new Rewriter(resultOfDecide, doNotRewriteList, ppBody, bookmarkedWIX);
		String newBody = rewriter.rewrite();
		long end = System.currentTimeMillis();
		
		//create sample of rewrite transaction
		//ShareMethods.createSampleFile("rewrite/newBody_" + rewriteAnchorText+ "_"+ sampleHtmlNumber + ".html", newBody);
		
		assertEquals(ShareMethods.readSampleFile("rewrite/newBody_" + rewriteAnchorText+ "_"+ sampleHtmlNumber + ".html").trim(), 
				newBody.trim());
		
		System.out.println("rewrite time = " + (end - start) + "(ms)");
	}
		
}