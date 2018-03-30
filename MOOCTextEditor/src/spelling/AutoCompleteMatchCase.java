package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
* A trie data structure that implements the Dictionary and the AutoComplete
* ADT
* 
* @author 	Frank Eyenga
* Created: September 28, 2017
* Edited:	Mar 28, 2018
*
*/
public class AutoCompleteMatchCase implements Dictionary, AutoComplete
{

	private TrieNode root;
	private int size;

	public AutoCompleteMatchCase()
	{
		root = new TrieNode();
	}

	/**
	 * Insert a word into the trie. For the basic part of the assignment (part 2),
	 * you should convert the string to all lower case before you insert it.
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes into
	 * the trie, as described outlined in the videos for this week. It should
	 * appropriately use existing nodes in the trie, only creating new nodes when
	 * necessary. E.g. If the word "no" is already in the trie, then adding the word
	 * "now" would add only one additional node (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 *         in the dictionary.
	 */
	public boolean addWord(String word)
	{
		word = word.toLowerCase();
		TrieNode curr = root;

		for (int i = 0; i < word.length(); i++)
		{
			TrieNode next = curr.getChild(word.charAt(i));

			if (next == null)
			{
				for (int j = i; j < word.length(); j++)
					curr = curr.insert(word.charAt(j));

				break;
			}

			curr = next;
		}

		if (curr.endsWord()) return false;

		curr.setEndsWord(true);
		size++;

		return true;
	}

	/**
	 * Return the number of words in the dictionary. This is NOT necessarily the
	 * same as the number of TrieNodes in the trie.
	 */
	public int size()
	{
		return size;
	}

	/**
	 * Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week.
	 */
	@Override
	public boolean isWord(String s)
	{
		TrieNode word = findNode(root, s);
		return (word == null) ? false : word.endsWord();
	}

	/**
	 * Return a list, in order of increasing (non-decreasing) word length,
	 * containing the numCompletions shortest legal completions of the prefix
	 * string. All legal completions must be valid words in the dictionary. If the
	 * prefix itself is a valid word, it is included in the list of returned words.
	 * 
	 * The list of completions must contain all of the shortest completions, but
	 * when there are ties, it may break them in any order. For example, if there
	 * the prefix string is "ste" and only the words "step", "stem", "stew", "steer"
	 * and "steep" are in the dictionary, when the user asks for 4 completions, the
	 * list must include "step", "stem" and "stew", but may include either the word
	 * "steer" or "steep".
	 * 
	 * If this string prefix is not in the trie, it returns an empty list.
	 * 
	 * @param prefix
	 *            The text to use at the word stem
	 * @param numCompletions
	 *            The maximum number of predictions desired.
	 * @return A list containing the up to numCompletions best predictions
	 */
	@Override
	public List<String> predictCompletions(String prefix, int numCompletions)
	{
		TrieNode prefix_node = findNode(root, prefix);
		if(prefix_node == null) return new LinkedList<String>();
		
		List<String> 	completions = new LinkedList<String>();
		List<TrieNode> 	node_queque = new LinkedList<TrieNode>();
		List<String>	word_queque = new LinkedList<String>();
		
		node_queque.add(prefix_node);
		word_queque.add(prefix);
		
		while(!node_queque.isEmpty() && completions.size() < numCompletions)
		{
			TrieNode node = node_queque.remove(0);
			String word = word_queque.remove(0);
			
			if(node.endsWord()) 
				completions.add(word);
			
			for (char c : node.getValidNextCharacters())
			{
				node_queque.add(node.getChild(c));
				word_queque.add(word + c);
			}
			
		}
		
		return completions;
	}

	/**
	 * Find the node in the trie corresponding to the sequence of characters given
	 * by the input, if said node exist.
	 * 
	 * @param s
	 *            a sequence of character that will be use to trace down the trie
	 * @return a TrieNode in the Trie corresponding to the sequence of characters
	 *         given by the input if it exist or null if said node does not exist.
	 */
	private TrieNode findNode(TrieNode root, String s)
	{
		s = s.toLowerCase();
		TrieNode curr = root;

		for (int i = 0; i < s.length(); i++)
		{
			curr = curr.getChild(s.charAt(i));
			if (curr == null) return null;
		}

		return curr;
	}

	// For debugging
	public void printTree()
	{
		printNode(root, "");
	}

	/** Do a pre-order traversal from this node down */
	public void printNode(TrieNode curr, String text)
	{
		if (curr == null) return;

		System.out.println(text);

		TrieNode next = null;
		for (Character c : curr.getValidNextCharacters())
		{
			next = curr.getChild(c);
			printNode(next, text + c);
		}
	}

}