/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aa_RangeBasedLookUp;

public class BST<K extends Comparable<? super K>,M> 
    implements Dictionary<K,M> {
    
    public BSTNode root;

    public BST() {
	root = null;
    }

    public void insert(K key, M metadata) throws DuplicateException {
	if(root == null)
	    root = new BSTNode(new Entry(key,metadata));
	else
	    insert(root,new Entry(key,metadata));
    }

    private void insert(BSTNode currentPosition,Entry<K,M> inputItem) 
            throws DuplicateException {
	// Check to see if item is already in the tree
	if(currentPosition.data.compareTo(inputItem) == 0) {
	    throw new DuplicateException("Key already exists");
	}
	// Try to insert into left or right subtree
	if(inputItem.compareTo(currentPosition.data) < 0) {
	    if(currentPosition.left == null)
		currentPosition.left = new BSTNode(inputItem);
	    else
		insert(currentPosition.left,inputItem);
	}
	else {
	    if(currentPosition.right == null) 
		currentPosition.right = new BSTNode(inputItem);
	    else
		insert(currentPosition.right,inputItem);
	}
    }

    @Override
    public Entry<K,M> lookup(K key) {
	if( key == null )
	    return  null;
	return lookup(root,key).data;
    }
    
    private BSTNode lookup( BSTNode currentPosition, K key ) {
	if(currentPosition == null)
	    return null;
	if(currentPosition.data.key.compareTo(key) == 0) 
	    return currentPosition;
	if(currentPosition.data.key.compareTo(key) > 0)
	    return lookup(currentPosition.left,key);
	return lookup(currentPosition.right,key);
    }

    @Override
    public Entry<K,M> delete(K item) {
        
        if(item == null)
	    return null;
        
	BSTNode tmp = lookup(root,item);
        
	if(tmp != null)
	   root = delete(root,tmp.data);
	return tmp.data;
    }
    
    private BSTNode delete(BSTNode currentPosition,Entry<K,M> toDelete) {
	// If tree is null, do no work
	if(currentPosition == null)
	    return currentPosition;
	
	// If found, see what can be performed on tree
	if(currentPosition.data.compareTo(toDelete) == 0) {
	    if(currentPosition.left == null && currentPosition.right == null)
		return null;
	    if(currentPosition.left == null)
		return currentPosition.right;
	    if(currentPosition.right == null)
		return currentPosition.left;
	    // Node has two children, find inorder successor
	    currentPosition.data = smallestKey(currentPosition.right);
	    currentPosition.right = delete(currentPosition.right,currentPosition.data);
	    return currentPosition;
	}
	else if(toDelete.compareTo(currentPosition.data) < 0) {
	    currentPosition.left = delete(currentPosition.left,toDelete);
	    return currentPosition;
	}
	else {
	    currentPosition.right = delete(currentPosition.right,toDelete);
	    return currentPosition;
	}
    }
    
    private Entry<K,M> smallestKey(BSTNode currentPosition) {
        if(currentPosition.left == null)
	    return currentPosition.data;
        return smallestKey(currentPosition.left);
    }


    @Override
    public void printRange(K begin, K end, 
			   java.io.PrintWriter p) {
       	printRange(root,begin,end,p);
    }
    
    private void printRange(BSTNode currentPosition, K begin, K end,
			    java.io.PrintWriter p) {
       	// if there is no node or begin node is greater than end node,
	// do nothing
	if(currentPosition == null || (begin.compareTo(end) > 0)) {
	    return;
	}
	
	// If the current node is greater than the begin node, check it's
	// left subtree for values to print
        if(begin.compareTo((K)currentPosition.data.key) < 0) 
		printRange(currentPosition.left,begin,end,p);
	
	// If the node is between the begin and end node and or equal
	// to one or both, print this node
	if(currentPosition.data.key.compareTo(begin) >= 0 
                && currentPosition.data.key.compareTo(end) <= 0) 
		p.println(currentPosition.data.toString());

	// If the current node is less than the end node, check it's right
	// subtree for values to print
	if(end.compareTo((K)currentPosition.data.key) > 0) 
		printRange(currentPosition.right,begin,end,p);
    }

    @Override
    public void printAll(java.io.PrintWriter p) {
	printAll(root,p);
    }
    
    private void printAll(BSTNode n, java.io.PrintWriter p) {
	if(n == null) 
	    return;
	printAll(n.left,p);
	p.println(n.data.toString());
        printAll(n.right,p);
    }
    
}

class BSTNode<K extends Comparable<? super K>,M> {

    Entry<K,M> data;    // Holds the key of each node
    BSTNode left,right;  // Holds the pointers for left and right child

    /**
     * Constructor used for node with null child pointers
     *
     * @param k the key for the node
     **/
    public BSTNode( Entry<K,M> k ) {
	this(k,null,null);
    }

    /**
     * The main constructor for the BSTNode class
     *
     * @param k the node
     * @param l the node's left child pointer
     * @param r the node's right child pointer
     **/
    public BSTNode( Entry<K,M> k, BSTNode l, BSTNode r) {
        data = k;
        left = l;
	right = r;
    }
}
