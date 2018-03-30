/**
 * 
 */
package spelling;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author 	Frank Eyenga
 * Created: Mar 30, 2018
 * Edited:	Mar 30, 2018
 *
 */
public class DictionaryHashSetMatchCaseTest
{

	/**
	 * Test method for {@link spelling.DictionaryHashSetMatchCase#isWord(java.lang.String)}.
	 */
	@Test
	public void testIsWord()
	{
		DictionaryHashSetMatchCase dict = new DictionaryHashSetMatchCase();
		
		dict.addWord("Christine");
		dict.addWord("hello");
		
		assertTrue(dict.isWord("Christine"));
		assertTrue(dict.isWord("hello"));
		assertTrue(dict.isWord("Hello"));
		assertTrue(dict.isWord("HELLO"));
		assertTrue(dict.isWord("CHRISTINE"));
		
		assertFalse(dict.isWord("word"));
		assertFalse(dict.isWord("helo"));
		assertFalse(dict.isWord("helLo"));
		assertFalse(dict.isWord("HELLo"));
		assertFalse(dict.isWord("christine"));
		assertFalse(dict.isWord("ChrIstine"));

	}

}
