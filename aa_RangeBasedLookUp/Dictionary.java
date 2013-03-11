/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aa_RangeBasedLookUp;

import java.lang.Comparable;
import java.io.PrintWriter;

public interface Dictionary<K extends Comparable<? super K>,M> {
    
    public Entry<K,M> lookup(K item);
    
    public void insert(K key, M metadata) throws DuplicateException;
    
    public Entry<K,M> delete(K item);
    
    public void printRange(K begin, K end, PrintWriter p);
    
    public void printAll(PrintWriter p);
    
}
