package spelling;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

/**
 * A class that implements the Dictionary interface with a HashSet
 */
public class DictionaryHashSetMatchCase implements Dictionary
{

	private HashSet<String> dict;

	public DictionaryHashSetMatchCase()
	{
		dict = new HashSet<String>();
	}

	/**
	 * Add this word to the dictionary.
	 * 
	 * @param word
	 *            The word to add
	 * @return true if the word was added to the dictionary (it wasn't already
	 *         there).
	 */
	@Override
	public boolean addWord(String word)
	{
		return dict.add(word);
	}

	/** Return the number of words in the dictionary */
	@Override
	public int size()
	{
		return dict.size();
	}

	/** Is this a word according to this dictionary */
	@Override
	public boolean isWord(String s)
	{
		if(dict.size() == 0 || s.length() < 2)
			return false;
		
		char first = s.charAt(0);
		boolean isUpper = Character.isUpperCase(s.charAt(1));
		String sub;

		for (int i = 2; i < s.length(); i++)
		{
			if (Character.isUpperCase(s.charAt(i)) != isUpper)
				return false;
		}

		if (isUpper)
			sub = s.substring(1).toLowerCase();
		else
			sub = s.substring(1);

		return (dict.contains( (char)(first | 0x20) + sub) || dict.contains(first + sub));
	}

}
