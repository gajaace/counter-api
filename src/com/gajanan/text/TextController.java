package com.gajanan.text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gajanan.text.operations.TextOperation;
import com.gajanan.text.wordpro.Wordlet;

@RestController
public class TextController {
	
	String filename = "/files/Sample_Paragraph.txt";
	TextOperation textop = new TextOperation();
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)	
	public HashMap<String, List<HashMap<String, Integer>>> getGreeting(@RequestBody HashMap<String, List<String>> wdarr) 
			throws FileNotFoundException {		
	
		List<Wordlet> wdlist = new ArrayList<Wordlet>();	
	
		for(String wd : wdarr.get("searchText")) {
			wdlist.add(new Wordlet(wd, textop.wordCount(filename, wd)));
		}
					
		List<HashMap<String, Integer>> mylist = new ArrayList<HashMap<String, Integer>>();
		HashMap<String, Integer> hashmp;
		for(Wordlet w : wdlist) {
		hashmp = new HashMap<String, Integer>();
		hashmp.put(w.getWord(), w.getCount());
		mylist.add(hashmp);
		}
		
		HashMap<String, List<HashMap<String, Integer>>> h = new HashMap<String, List<HashMap<String, Integer>>>();
		h.put("Count", mylist);
		return h;	
  }
	
	@RequestMapping(value = "/top/{num}", method = RequestMethod.GET)
 	public void topcount(@PathVariable int num, HttpServletResponse response) 
 	throws IOException {
 		
 		response.setContentType("type/csv");
 		response.setHeader("Content-disposition", "attachment;filename=Top_Count.cvs");
 		
		ArrayList<String> rows = new ArrayList<String>();
		List<Wordlet> wdlist= textop.topCount(filename, num);
		
		rows.add("\n");
		for (Wordlet w : wdlist) {
			rows.add(w.getWord() + " | " + w.getCount());
			rows.add("\n");
		}
 
		Iterator<String> iter = rows.iterator();
		while (iter.hasNext()) {
			String outputString = (String) iter.next();
			response.getOutputStream().print(outputString);
		}
 
		response.getOutputStream().flush();
 		
 	}

}
