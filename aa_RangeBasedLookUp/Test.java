/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aa_RangeBasedLookUp;

import java.io.*;

// Test class for BST class

public class Test {
    public static void main (String [] args) throws IOException {
	BST<Integer,String> tree = new BST<Integer,String>();
        PrintWriter output = new PrintWriter(System.out);
        try {
            tree.insert(14, "Bhavya");
            tree.insert(1, "Mayuri");
            tree.insert(21, "Anjana");
            tree.insert(9, "Aashka");
            tree.insert(2, "Aarshiya");
            tree.insert(3, "Bhavana");
            tree.insert(13, "Romy");
            tree.insert(4, "Nipun");
            tree.printRange(2, 4, output);
        }
        catch (DuplicateException ex) {
            System.out.println(ex.getMessage());
        }
        output.close();
    }
}
