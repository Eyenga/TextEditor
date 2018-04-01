package spelling;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.processing.Completion;

import org.junit.Before;
import org.junit.Test;


/**
 * @author 	Frank Eyenga
 * Created: Apr 1, 2018
 * Edited:	Apr 1, 2018
 *
 */
public class AutoCompleteMatchCaseTester {

	private String dictFile = "data/words.small.txt"; 

	AutoCompleteMatchCase emptyDict; 
	AutoCompleteMatchCase smallDict;
	AutoCompleteMatchCase largeDict;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		emptyDict = new AutoCompleteMatchCase();
		smallDict = new AutoCompleteMatchCase();
		largeDict = new AutoCompleteMatchCase();

		smallDict.addWord("hello");
		smallDict.addWord("Christine");
		smallDict.addWord("Hodl");
		smallDict.addWord("hold");
		smallDict.addWord("hot");
		smallDict.addWord("hey");
		smallDict.addWord("an");
		smallDict.addWord("subsequent");
		
		DictionaryLoader.loadDictionary(largeDict, dictFile);
	}

	
	/** Test if the size method is working correctly.
	 */
	@Test
	public void testSize()
	{
		assertEquals("Testing size for empty dict", 0, emptyDict.size());
		assertEquals("Testing size for small dict", 8, smallDict.size());
		assertEquals("Testing size for large dict", 4440, largeDict.size());
	}
	
	/** Test the isWord method */
	@Test
	public void testIsWord()
	{
		assertEquals("Testing isWord on empty: Hello", false, emptyDict.isWord("hello"));
		assertEquals("Testing isWord on small: hello", true, smallDict.isWord("hello"));
		assertEquals("Testing isWord on large: hello", true, largeDict.isWord("hello"));
		
		assertEquals("Testing isWord on small: HELLO", true, smallDict.isWord("HELLO"));
		assertEquals("Testing isWord on large: HELLO", true, largeDict.isWord("HELLO"));
		
		assertEquals("Testing isWord on small: Christine", true, smallDict.isWord("Christine"));
		assertEquals("Testing isWord on small: CHRISTINE", true, smallDict.isWord("CHRISTINE"));
		assertEquals("Testing isWord on small: christine", false, smallDict.isWord("christine"));
		

		assertEquals("Testing isWord on large: Christian", true, largeDict.isWord("Christian"));
		assertEquals("Testing isWord on large: CHRISTIAN", true, largeDict.isWord("CHRISTIAN"));
		assertEquals("Testing isWord on large: christian", false, largeDict.isWord("christian"));
		
		assertEquals("Testing isWord on small: hELLO", false, smallDict.isWord("hELLO"));
		assertEquals("Testing isWord on small: christ", false, smallDict.isWord("christ"));
		assertEquals("Testing isWord on large: ChrisTian", false, largeDict.isWord("ChrisTian"));
		assertEquals("Testing isWord on large: Christ", false, largeDict.isWord("Christ"));


		assertEquals("Testing isWord on empty: empty string", false, emptyDict.isWord(""));
		assertEquals("Testing isWord on small: empty string", false, smallDict.isWord(""));
		assertEquals("Testing isWord on large: empty string", false, largeDict.isWord(""));
		
		assertEquals("Testing isWord on small: no", false, smallDict.isWord("no"));
		assertEquals("Testing isWord on large: no", true, largeDict.isWord("no"));
		
		assertEquals("Testing isWord on small: an", true, smallDict.isWord("an"));
		assertEquals("Testing isWord on small: a", false, smallDict.isWord("a"));
		
		
	}
	
	/** Test the addWord method */
	@Test
	public void testAddWord()
	{
		
		assertEquals("Asserting hellow is not in empty dict", false, emptyDict.isWord("hellow"));
		assertEquals("Asserting hellow is not in small dict", false, smallDict.isWord("hellow"));
		assertEquals("Asserting hellow is not in large dict", false, largeDict.isWord("hellow"));
		
		emptyDict.addWord("hellow");
		smallDict.addWord("hellow");
		largeDict.addWord("hellow");

		assertEquals("Asserting hellow is in empty dict", true, emptyDict.isWord("hellow"));
		assertEquals("Asserting hellow is in small dict", true, smallDict.isWord("hellow"));
		assertEquals("Asserting hellow is in large dict", true, largeDict.isWord("hellow"));

		assertEquals("Asserting xyzabc is not in empty dict", false, emptyDict.isWord("xyzabc"));
		assertEquals("Asserting xyzabc is not in small dict", false, smallDict.isWord("xyzabc"));
		assertEquals("Asserting xyzabc is in large dict", false, largeDict.isWord("xyzabc"));		
		
	}
	
	@Test
	public void testPredictCompletions()
	{
		List<String> completions;
		completions = smallDict.predictCompletions("", 0);
		assertEquals(0, completions.size());
		
		completions = smallDict.predictCompletions("",  4);
		assertEquals(4, completions.size());
		assertTrue(completions.contains("an"));
		boolean twoOfThree = completions.contains("hey") && completions.contains("hot") ||
				             completions.contains("hold") && completions.contains("hot") ||
				             completions.contains("hot") && completions.contains("Hodl");
		assertTrue(twoOfThree);
		
		completions = smallDict.predictCompletions("Ho", 2);
		boolean allIn = completions.contains("Hot") && 
				(completions.contains("Hodl") || completions.contains("Hold"));
		assertEquals(2, completions.size());
		assertTrue(allIn);
		
		completions = smallDict.predictCompletions("ho", 2);
		allIn = completions.contains("hot") && completions.contains("hold");
		assertEquals(2, completions.size());
		assertTrue(allIn);
		
		completions = smallDict.predictCompletions("H", 2);
		allIn = completions.contains("Hodl");
		assertEquals(1, completions.size());
		assertTrue(allIn);
		
		completions = largeDict.predictCompletions("christ", 10);
		assertEquals(0, completions.size());
		
		completions = largeDict.predictCompletions("Chri", 10);
		assertEquals(1, completions.size());
		assertTrue(completions.contains("Christian"));
		
		completions = largeDict.predictCompletions("CHRI", 10);
		assertEquals(1, completions.size());
		assertTrue(completions.contains("Christian"));
		
		completions = largeDict.predictCompletions("ChrI", 10);
		assertEquals(0, completions.size());
		
		completions = smallDict.predictCompletions("heL", 10);
		assertEquals(0, completions.size());
	
		completions = smallDict.predictCompletions("x", 5);
		assertEquals(0, completions.size());
	}
	
	
	
	
}
