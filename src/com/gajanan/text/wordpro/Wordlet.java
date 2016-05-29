package com.gajanan.text.wordpro;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Wordlet implements Serializable {

	private static final long serialVersionUID = 1L;
	private String word;
	 private int count;

	 public Wordlet() {
	     this.word = null;
		 this.count = 0;
	 }
	 
	 public Wordlet(String word, int count) {
	     this.word = word;
		 this.count = count;
	 } 
	 
	  @XmlElement
	  public String getWord() {
		  return this.word;
	  }
	 
	  public void setWord(String word) {
		  this.word = word;
	  }
	  
	  @XmlElement
	  public int getCount() {
		  return this.count;
	  }
	 
	  public void setCount(int count) {
		  this.count = count;
	  }

	  @Override
	  public boolean equals(Object o) {
		  
	 	  if(o == null) { return false; }
		  if(o == this) { return true;  }
		  if(this.getClass() != o.getClass()) { return false; } 
		  
		  Wordlet w = (Wordlet) o;  
		  return ((this.getWord()).equals(w.getWord()));	  	  
		
	  }
	  
	  @Override
	  public int hashCode()
	  {
		  final int PRIME = 31;
		  int hash = 1;
		  hash = PRIME*hash + this.word.hashCode();
		  return hash;
	  }
	  
}