package spelling;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class NearbyWordsTest
{
	private String dictFile = "data/words.small.txt"; 

	DictionaryLL emptyDict; 
	DictionaryLL smallDict;
	DictionaryLL largeDict;
	
	NearbyWords driver;
	List<String> results;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		emptyDict = new DictionaryLL();
		smallDict = new DictionaryLL();
		largeDict = new DictionaryLL();

		smallDict.addWord("Hello");
		smallDict.addWord("HElLo");
		smallDict.addWord("help");
		smallDict.addWord("kelp");
		smallDict.addWord("a");
		smallDict.addWord("subsequent");
		
		DictionaryLoader.loadDictionary(largeDict, dictFile);
		
		results = new ArrayList<String>();
	}

	@Test
	public void testDistanceOne()
	{
		driver = new NearbyWords(emptyDict);
		
		results = driver.distanceOne("testing", true);	
		assertTrue("Testing empty dict", results.size() == 0);
		
		driver = new NearbyWords(smallDict);
		
		results = driver.distanceOne("a", true);
		assertTrue("Testing for real word", results.size() == 0);
		
		results = driver.distanceOne("hel", true);
		assertTrue(results.size() == 1);
		assertTrue(results.contains("help"));
		
		results = driver.distanceOne("a", false);
		assertEquals("Testing for all words", results.size(), 77);
		
		results = driver.distanceOne("", false);
		assertTrue("Testing with empty string", results.size() == 26);

	}

	@Test
	public void testSubstitution()
	{
		driver = new NearbyWords(emptyDict);
		
		driver.substitution("", results, false);
		assertTrue("Testing for any word with empty string", results.size() == 0);
		
		driver.substitution("a", results, true);
		assertTrue("Testing for real word with empty dict", results.size() == 0);
		
		driver.substitution("ab", results, false);
		assertTrue("Testing for any word empty dict", results.size() == 50);
		
		driver = new NearbyWords(largeDict);
		results.clear();

		driver.substitution("ab", results, false);
		assertTrue("Testing for any word large dict", results.size() == 50);
		
		smallDict.addWord("stow");
		smallDict.addWord("stop");
		smallDict.addWord("soon");
		driver = new NearbyWords(smallDict);
		results.clear();
		
		driver.substitution("ston", results, true);
		assertEquals("Testing for real word small dict", 3, results.size());
		assertTrue(results.contains("stow") && results.contains("stop") && results.contains("soon"));

	}

	@Test
	public void testInsertions()
	{
		driver = new NearbyWords(emptyDict);
		
		driver.insertions("", results, true);
		assertTrue("Testing for real word with empty dict", results.size() == 0);
		
		driver.insertions("", results, false);
		assertTrue("Testing for any word with empty string", results.size() == 26);

		driver = new NearbyWords(largeDict);
		results.clear();

		driver.insertions("ab", results, false);
		assertEquals("Testing for any word large dict", 76, results.size());
		
		smallDict.addWord("fake");
		smallDict.addWord("acke");
		smallDict.addWord("akem");;
		driver = new NearbyWords(smallDict);
		results.clear();
		
		driver.insertions("ake", results, true);
		assertEquals("Testing for real word small dict", 3, results.size());
		assertTrue(results.contains("fake") && results.contains("acke") && results.contains("akem"));	
	}

	@Test
	public void testDeletions()
	{
		driver = new NearbyWords(emptyDict);
		
		driver.deletions("", results, false);
		assertTrue("Testing for any word with empty string", results.size() == 0);
		
		driver.deletions("abcd", results, true);
		assertTrue("Testing for real word empty dict", results.size() == 0);

		driver = new NearbyWords(largeDict);
		
		driver.deletions("abcd", results, false);
		assertTrue("Testing for any word large dict", results.size() == 4);
		
		smallDict.addWord("flat");
		smallDict.addWord("late");
		smallDict.addWord("fate");;
		driver = new NearbyWords(smallDict);
		results.clear();
		
		driver.deletions("flate", results, true);
		assertEquals("Testing for real word small dict", 3, results.size());
		assertTrue(results.contains("flat") && results.contains("late") && results.contains("fate"));
	}

}
