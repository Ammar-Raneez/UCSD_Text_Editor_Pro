package spelling;

import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

/** 
 * The idea here is to convert the letters exlcuding the first letter to lower case before adding it into the completed words list
 * @author You
 *
 */
public class AutoCompleteMatchCase implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteMatchCase() {
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word) {
		TrieNode traverser = root;
		boolean flag = true;
		
		for(int i=0; i<word.length(); i++) {
			if(!Character.isUpperCase(word.charAt(i))) {
				flag = false;
				break;
			}
		}
				
		for(int i=0; i<word.length(); i++) {
			char letter = word.charAt(i);
			if(i>0 && !flag) {
				letter = Character.toLowerCase(letter);
			}
			
			if(!traverser.getValidNextCharacters().contains(letter)) {
				traverser = traverser.insert(letter);		
			} else {
				traverser = traverser.getChild(letter);		
			}
		}
		
		if(traverser.endsWord()) return false;		
		
		traverser.setEndsWord(true);
		this.size++;
		return true;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size() {
	    return this.size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) {
		TrieNode traverser = root;
		boolean flag = true;
		
		for(int i=0; i<s.length(); i++) {
			if(!Character.isUpperCase(s.charAt(i))) {
				flag = false;
				break;
			}
		}
		
		for(int i=0; i<s.length(); i++) {
			char letter = s.charAt(i);
			if(i>0 && !flag) {
				letter = Character.toLowerCase(letter);
			}
			
			if(!traverser.getValidNextCharacters().contains(letter)) return false;
			traverser = traverser.getChild(letter);
		}
		
		return traverser.endsWord();
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) {
    	 TrieNode traverser = root;
    	 List<String> completedWords = new LinkedList<>();
    	 Queue<TrieNode> queue = new LinkedList<>();
    	 
 		 boolean flag = true;
		
 		 for(int i=0; i<prefix.length(); i++) {
		 	 if(!Character.isUpperCase(prefix.charAt(i))) {
		 		 flag = false;
				 break;
			 }
 		 }
    	  		 
    	 for(int i=0; i<prefix.length(); i++) {
 			char letter = prefix.charAt(i);
 			
 			if(i>0 && !flag) {
 				letter = Character.toLowerCase(letter);
 			}
 			
    		if(!traverser.getValidNextCharacters().contains(letter)) {
				return completedWords;
    		} else {
    			traverser = traverser.getChild(letter);		
    		}
    	 }
    	 
    	 if(traverser.endsWord()) completedWords.add(traverser.getText());
    	 List<Character> children = new LinkedList<>(traverser.getValidNextCharacters());
    	 
    	 for(char character: children) {
    		queue.add(traverser.getChild(character)); 
    	 }
    	     	 
    	 while(!queue.isEmpty() && completedWords.size() != numCompletions) {
    		 traverser = queue.remove();
    		 
    		 if(traverser.endsWord()) completedWords.add(traverser.getText());
    		 
    		 for(Character ch: traverser.getValidNextCharacters()) {
    			 queue.add(traverser.getChild(ch));
    		 }
    	 } 
         return completedWords;
     }

 	// For debugging
 	public void printTree(){
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr){
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
}