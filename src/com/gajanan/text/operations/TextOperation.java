package com.gajanan.text.operations;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.gajanan.text.wordpro.Wordlet;

public class TextOperation {
	
	static boolean txopflag = false;
	static List<String> wordlist = new ArrayList<String>();  //sequential list of all words in a file
 	static Set<Wordlet> wordset = new HashSet<Wordlet>();    //set of distinct words(+ their counts) in a file
    static int[] wcount;    //to hold #of occurrence of every distinct word(in wordset) in ascending order

    
//Method to get collection Set of distinct words 
//(This function runs only once per session and uses a flag to ensure this condition)
    public void wordCollect(String filename) throws FileNotFoundException{
   	
     txopflag = true;         
     Scanner scn = null;
     
     try {
  	 scn = new Scanner(getClass().getClassLoader().getResourceAsStream(filename)); 
	 
	 scn.useDelimiter("[^A-Za-z0-9]+");
	 String tempword;
	 while(scn.hasNext()) {
		   tempword = scn.next();
		   wordlist.add(tempword.toLowerCase());
		   wordset.add(new Wordlet(tempword.toLowerCase(), 0));
		 }
		 
	 wcount = new int[wordset.size()];
	
     //scroll through the wordlist to count #of occurrence of every word in the wordset 	 
     int count = 0, j = 0;
     String currword;	 
     for( Wordlet w : wordset ) {
	 count = 0;	 
	 currword = w.getWord();
	 for( String s : wordlist ) {
		if( s.equals(currword) ) count++;
	 }
	 w.setCount(count);    //add the #of occurrence (count) to corresponding wordset entry
	 wcount[j] = count; j++;
	 }	 
     Arrays.sort(wcount);  //sorting the #of occurrences to know the top max counts of words
     
     } finally {
    	 if(scn != null) 
	       scn.close();
       }
  }
   
 
 //Method to obtain and return the #of occurrence of a word
     public int wordCount(String filename, String testword) 
	  throws FileNotFoundException {
    	 
    	 if(txopflag == false) 
    	this.wordCollect(filename);
		
		String wordlower = testword.toLowerCase();
		int count = 0;
		for (Wordlet w : wordset) {
			if(wordlower.equals(w.getWord()))
				count = w.getCount();
		}
	  return count;		
	} 
	
     
 //Method to obtain and return the maximum count words	
	 public List<Wordlet> topCount(String filename, int tcount) 
			 throws FileNotFoundException {
		 
		 if(txopflag == false) 
	   this.wordCollect(filename);
		 
		 List<Wordlet> wlist = new ArrayList<Wordlet>();
		 Set<Wordlet> wlet = new HashSet<Wordlet>();
		 
		 if(tcount > wordset.size()) 
			 tcount = wordset.size();
		
		int t = wcount.length - 1;
		while (tcount > 0) {
			for( Wordlet w : wordset ) 
				if(w.getCount() == wcount[t]) 
					if(wlet.add(w)) {
						wlist.add(w);
						t--; break;
					}			
		 tcount--;
		}
		
		return wlist;		 
	 }  

}
