/* Name: Victor Zhu
COSI 12B, Spring 2016
Email: telandis@brandeis.edu
Date: 4/13/16
Programming Assignment Extra Credit

This program codes for an anagrams object that returns all words that can be formed from the
letters of the given phrase, and prints out all the possible anagrams of a phrase.
*/
import java.util.*;

public class Anagrams {

	//fields
	private Set<String> dictionary;
	
	//constructor
	public Anagrams(Set<String> dictionary) {
		if(dictionary == null) {//throws IllegalArgumentException if variable is null
			throw new IllegalArgumentException();
		}
		this.dictionary = dictionary;
	}
	
	//instance methods
	public Set<String> getWords(String phrase) {//this method returns all words that can be formed from phrase
		if(phrase == null) {//throws IllegalArgumentException if variable is null
			throw new IllegalArgumentException();
		}
		Set<String> matchingWords = new TreeSet<String>();//initializes set
		LetterInventory wordInv = new LetterInventory(phrase);//creates letter object based on phrase
		for(String words: this.dictionary) {//for loop that adds every word in the dictionary that letter contains to set
			if(wordInv.contains(words)) {
				matchingWords.add(words);
			}
		}
		return matchingWords;//returns set of words made from phrase
	}
	
	//method to find all anagrams for a phrase
	public void print(String phrase) {
		if(phrase == null) {
			throw new IllegalArgumentException();
		}
		LetterInventory letters = new LetterInventory(phrase);//creates letter object based on phrase
		ArrayList<String> anagramWords = new ArrayList<String>();//creates array to hold words that are an anagram
		Set<String> remainingWords = this.getWords(phrase);//creates set of words contained in phrase
		explore(letters, remainingWords, anagramWords, remainingWords.size() + 1);//recursive search to find all anagrams of phrase
	}
	//method to find all anagrams for a phrase with no more words than max number
	public void print(String phrase, int max) {
		if(phrase == null || max < 0) {//throws IllegalArgumentException if variable is null
			throw new IllegalArgumentException();
		}
		LetterInventory letters = new LetterInventory(phrase);//creates letter object based on phrase
		ArrayList<String> anagramWords = new ArrayList<String>();//creates array to hold words that are an anagram
		Set<String> remainingWords = this.getWords(phrase);//creates set of words contained in phrase
		if(max == 0) {//if user inputs 0, assumes no limit in size of anagram
			max = remainingWords.size() + 1;
		}
		explore(letters, remainingWords, anagramWords, max);//recursive search to find all anagrams of phrase of max words
	}

	private void explore(LetterInventory letters, Set<String> remainingWords, ArrayList<String> anagramWords, int max) {
		boolean checkContains = false;//initialize boolean
		for(String x: remainingWords) {//this for loops checks all remainingWords to see if they can be formed from letters left
			if(letters.contains(x)) {
				checkContains = true;//if this is true even once, then the anagram isn't complete yet
			}
		}
		if(!checkContains) {//if no more words can be obtained, runs statements in the if statement, and go back
			if(letters.isEmpty() && anagramWords.size() <= max) {//checks that all letters are used and the size of the anagram is no more than max
				System.out.println(anagramWords);//prints out the anagram
			}
		} else {//recursive step
			for(String i: remainingWords) {//iterates over every word in set
				Set<String> remainingWords2 = new TreeSet<String>(remainingWords);
				//creates new set for next recursive search by duplicating previous set
				remainingWords2.remove(i);//removes word being examined in this loop from new set
				if(letters.contains(i)) {//if examined word is in letters object
					anagramWords.add(i);//add word to anagram set
					letters.subtract(i);//remove word from letters object
					explore(letters, remainingWords2, anagramWords, max);//next recursive search using new remainingWords set
					//at this point all possible paths from this point are searched, return to previous recursive step
					anagramWords.remove(i);//remove word from anagram set
					letters.add(i);//add word back to letters object
				}
				//if the if statement isn't run, then returns to previous recursive step
			}
		}
	}
}