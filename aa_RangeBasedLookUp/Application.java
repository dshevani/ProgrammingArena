/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aa_RangeBasedLookUp;

import java.io.*;

public class Application {
    
    private BST<String,String> entries;
    public String title;
    
    public Application (String title) {
	entries = new BST<String,String>();
	this.title = title;
    }
    
    public void add(String name,String email) throws DuplicateException {
	try {
	    entries.insert(name,email);
	} catch (DuplicateException ex) {
	    throw ex;
	}
    }
    
    public void change(String name,String email) throws ItemNotFoundException {
	Entry tmp = entries.lookup(name);
	if(tmp == null)
	    throw new ItemNotFoundException();
	else
	    tmp.changeValue(email);
    }
    
    public void display(java.io.PrintWriter p) {
	p.println("Address book: " + title);
	entries.printAll(p);
    }
    
    public void display(String name,java.io.PrintWriter p) {
	Entry tmp = tmp = (Entry) entries.lookup(name);
	if(find(name))
	    p.println("Entry found: " + tmp);
    }
    
    public void display(String begin,String end,java.io.PrintWriter p) {	
	if(begin == null || end == null) {
	    p.println("Failed to print: Begin and End must be valid Strings");
	    return;
	}

	p.println("Address book: " + title + "   Entries from '" + begin + 
		  "' to '" + end + "'");

	entries.printRange(begin,end,p);
    }
    
    public boolean find(String name)
    {
	if(entries.lookup(name) == null)
	    return false;
	return true;
    }
    
    public void remove(String name) throws ItemNotFoundException {
	Entry tmp = tmp = (Entry) entries.delete(name);
        if(tmp == null)
	    throw new ItemNotFoundException();
    }
    
}
