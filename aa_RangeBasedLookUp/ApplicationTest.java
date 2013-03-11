/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aa_RangeBasedLookUp;

import java.io.PrintWriter;

public class ApplicationTest {
    public static void main (String [] args) {
	Application book = new Application("AddressBook");
	
	PrintWriter output = new PrintWriter(System.out);
        try {
	    book.add("Justin","jgoetz@mhub.doit.wisc.edu");
	    book.add("Henry","hhuang@mhub.doit.wisc.edu");
            book.add("Jiawei","jiaweima@wisc.edu");
            book.add("Bob","lukesjr@hotmail.com");
            book.add("Sarah","sndebruin@students.wisc.edu");
            book.add("Zang","zang@wisc.edu");
	} catch (DuplicateException ex) {
	    output.println("A DuplicateException was thrown and caught!");
	}
	output.println("List in print range:");
	book.display(output);
        book.display("Bob", output);
        book.display("Bob", "Jockey", output);
	output.close();
    }
}
