/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aa_RangeBasedLookUp;

import java.io.*;

/**
 *
 * @author shevanid
 */
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

	// Print from specified range
	entries.printRange(begin,end,p);
    }
    
    public boolean find(String name)
    {
	if(entries.lookup(name) == null)
	    return false;
	return true;
    }
    
    public void remove(String name) throws ItemNotFoundException {
	// Create a Comparable object and try to remove person
	Entry tmp = tmp = (Entry) entries.delete(name);
        if(tmp == null)
	    throw new ItemNotFoundException();
    }
    
    public static void main (String [] args) throws IOException {

///////////////////////////////////////////////////////////////////////////////
// Complete this method by filling in the code for the if blocks and adding any
// necessary local variables.
// This method should create and manipulate AddressBook objects using only
// publicly accessible AddressBook methods.
///////////////////////////////////////////////////////////////////////////////

        final String NEW           = "new";
        final String ADD           = "addEntry";
        final String REMOVE        = "removeEntry";
        final String CHANGE        = "changeEmail";
        final String FIND          = "findName";
        final String DISPLAY_RANGE = "displayRange";
        final String DISPLAY_ALL   = "displayAll";

        PrintWriter out = null;  // PrintWriter for output

        Application addressBook = null; // The addresss book for the App.

        // Put the whole code around a try/catch/finally block so can keep
        // window around (this is not strictly necessary if you are not
        // using the Unix version of CodeWarrior).
        try {

            // Only run the program if there are 1 or 2 command line arguments
            if (args.length < 1 || args.length > 2) {
                System.err.println("usage: java <input file name> || " +
                           "java <input file name> <output file name>");
                return;
            }

            // Create the object to read from a file
            BufferedReader fin = new BufferedReader(new FileReader(args[0]));

	    // Initialize output:
	    if(args.length == 2) {
		out = new PrintWriter( new BufferedWriter( new
		    FileWriter( new File(args[1]))));
	    }
	    else {
		out = new PrintWriter(System.out);
	    }

           ////////////////////////////////////////////////////////////////
           // ADD CODE TO PROCESS THE SECOND COMMAND-LINE ARGUMENT AND
           // SET UP THE PRINTWRITER FOR OUTPUT
           ////////////////////////////////////////////////////////////////

            // Parse every line of the input file
            while (fin.ready()) {
                String line = fin.readLine();

                if (line.equals(NEW)) {
                    // get the title for the new address book
                    String title = fin.readLine();
		    addressBook = new Application(title);
                }

                else if (line.equals(ADD)) {
                    // get the name and email address
                    String name = fin.readLine();
                    String email = fin.readLine();
		    
		    // See if there is an AddressBook
		    if(addressBook == null) {
			out.println("You must first create a new " +
				    "AddressBook by typing 'new', " +
				    "followed by a title.");
			out.println();
		    }
		    else {
			
			// There already is an AddressBook
			try {
			    // Try adding the name,email
			    addressBook.add(name,email);
			} catch (DuplicateException ex) {
			    out.println("Can't add entry: address book " +
					addressBook.title + " already" +
					" has an entry for " + name);
			    out.println();
			}
		    }
                }

                else if (line.equals(REMOVE)) {
                    // get the name on the entry to remove
                    String name = fin.readLine();
		    try {
			addressBook.remove(name);
		    } catch (ItemNotFoundException ex) {
			out.println("Can't remove entry: adress book " +
				    addressBook.title + " does not contain " 
				    + "an entry for " + name);
			out.println();
		    }
                }

                else if (line.equals(CHANGE)) {
                // get the name and the new email address
                    String name = fin.readLine();
                    String email = fin.readLine();

		    // Try to change the persons email
		    try {
			addressBook.change(name,email);
		        out.println("Updated entry: " + name + " -- " +
				    email);
		    } catch (ItemNotFoundException ex) {
			out.println("Can't change entry: " + name + 
				    " is not in address book " + 
				    addressBook.title);
		    }
		    out.println();
                }

                else if (line.equals(FIND)) {
                    // get the name on the entry to find
                    String name = fin.readLine();
		    if(addressBook.find(name))
			addressBook.display(name,out);
		    else {
			out.println(name + " is not in the address " +
				    "book " + addressBook.title);
		    }
		    out.println();
                }

                else if (line.equals(DISPLAY_RANGE)) {
                    // get the beginning and ending values
                    String begin = fin.readLine();
                    String end = fin.readLine();
		    addressBook.display(begin,end,out);
		    out.println();
                }

                else if (line.equals(DISPLAY_ALL)) {
		    addressBook.display(out);
		    out.println();
                }

                // print out any other bad input
                else {
                    out.flush();
                    System.err.println("Unknown command: '" + line + "'");
                }

            } // closes while

            // Always make sure you close the input files
            fin.close();

        // Put the catch in and do a stack trace so any uncaught exceptions
        // will be displayed.
        } catch (Throwable ex) {
            out.flush();
            System.err.println("AddressBook had an uncaught exception in main");
            ex.printStackTrace();

        // This keeps the window open whether the try block ended normally or
        // whether an exception was thrown in the try block.
        }  finally {
            if (out != null) out.flush();
            if (out != null) out.close(); // always close output file
        }
    }
}
