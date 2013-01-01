/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aa_RangeBasedLookUp;

import java.lang.Comparable;

/**
 * 
 * @author shevanid
 */
public class Entry<K extends Comparable<? super K>,M> implements Comparable<Entry<K,M>>{
    
    K key;
    M value;
    
    /*
     *  The Constructor
     */
    public Entry(K key) {
        this(key,null);
    }
    
    /**
     *  Main Constructor
     */
    public Entry(K key, M value) {
        this.key = key;
        this.value = value;
    }
    
    
    /**
     * 
     * @param newValue 
     */
    public void changeValue(M newValue) {
        this.value = newValue;
    }

    /**
     * This method is used to compare the Entry objects
     * @param entry, the Entry to compare
     * @return the result of comparison
     */
    @Override
    public int compareTo(Entry<K,M> entry) {
        return (key.compareTo(entry.key));
    }
    
    /**
     * This method returns String representation of Entry object
     * @return key => value 
     */
    public String toString() {
        return "(" + key.toString() + ":" + value.toString() + ")";
    }
}
