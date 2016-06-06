import java.io.PrintWriter;

/* Class AVLTree */
class AVLTree
{
    private AVLNode root;

    /* Constructor */
    public AVLTree()
    {
       root = null;
    }
    /* Function to check if tree is empty */
    public boolean isEmpty()
    {
        return root == null;
    }
    /* Make the tree logically empty */
    public void makeEmpty()
    {
        root = null;
    }
    /* Function to insert data */
    public void insert(Comparable data)
    {
        root = insert(data, root);
    }
    /* Function to get height of node */
    private int height(AVLNode t)
    {
        if(t == null){
        	return -1;
        }
        return t.height;
		
    }
    /* Function to max of left/right node */
    private int max(int lhs, int rhs)
    {
        if(lhs > rhs){
        	return lhs;
        }
        return rhs;
    }
    /* Function to insert data recursively */
    private AVLNode insert(Comparable x, AVLNode t)
    {
    	if(t == null){
            t = new AVLNode(x);
    	}
        else if(x.compareTo(t.data) > 0){
            t.right = insert(x, t.right);
            
            if(height(t.right) - height(t.left) == 2){
                if(x.compareTo(t.right.data) > 0){
                    t = rotateWithRightChild(t);
                }
                else{
                    t = doubleWithRightChild(t);
                }
            }
        }
        else if(x.compareTo(t.data) < 0){
            t.left = insert(x, t.left);
            
            if(height(t.left) - height(t.right) == 2){
                if(x.compareTo(t.left.data) < 0){
                    t = rotateWithLeftChild(t);
                }
                else{
                    t = doubleWithLeftChild(t);
                }
            }
        }

        t.height = max(height(t.left), height(t.right)) + 1;
        return t;
    }
    /* Rotate binary tree node with left child */
    private AVLNode rotateWithLeftChild(AVLNode k2)
    {
    	AVLNode newLeft = k2.left;
        k2.left = newLeft.right;
        newLeft.right = k2;
        k2.height = max(height(k2.right), height(k2.left)) + 1;
        newLeft.height = max(height(newLeft.left), k2.height) + 1;
        return newLeft;
    }

    /* Rotate binary tree node with right child */
    private AVLNode rotateWithRightChild(AVLNode k1)
    {
    	AVLNode newRight = k1.right;
        k1.right = newRight.left;
        newRight.left = k1;
        k1.height = max(height(k1.right), height(k1.left)) + 1;
        newRight.height = max(height(newRight.right), k1.height) + 1;
        return newRight;
    }
    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child */
    private AVLNode doubleWithLeftChild(AVLNode k3)
    {
    	k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }
    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child */
    private AVLNode doubleWithRightChild(AVLNode k1)
    {
    	k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }
    /* Functions to count number of nodes */
    public int countNodes()
    {
        return countNodes(root);
    }
    private int countNodes(AVLNode r)
    {
    	if (r != null){
	        int result = 1;
	        result += countNodes(r.left);
	        result += countNodes(r.right);
	        return result;
    	}
    	return 0;
    }
    /* Functions to search for an element */
    public boolean search(Comparable val)
    {
    	return search(root, val);
    }
    private boolean search(AVLNode r, Comparable val)
    {
    	while(r != null){
    		if(val.compareTo(r.data) > 0){
                r = r.right;
    		}
    		else if(val.compareTo(r.data) < 0){
                r = r.left;
    		}
            else{
                return true;
            }
    	}
        return false;
    }
    /* Function for in order traversal */
    public void inorder(PrintWriter out)
    {
    	// not need implementation
    }
    private void inorder(AVLNode r, PrintWriter out)
    {
    	// not need implementation
    }

    public Pair<int[],Integer> getPrivateKey(String sIndex) {
        return getPrivateKey(root, sIndex);
    }

    /*
     * TODO:Shouldn't be in AvlNode class?
     */
    private Pair<int[],Integer> getPrivateKey(AVLNode r, String sIndex) {
    	
    	DecryptedPuzzle val = new DecryptedPuzzle(sIndex, null);
    	int counter = 1;
    	while(r != null){
    		if(val.compareTo((DecryptedPuzzle)r.data) > 0){
                r = r.right;
    		}
    		else if(val.compareTo((DecryptedPuzzle)r.data) < 0){
                r = r.left;
    		}
            else{
            	return new Pair<int[], Integer>
            	(((DecryptedPuzzle)r.data).getPrivateKey(), new Integer(counter));
            }
    		
    		counter++;
    		
    	}
    	return null;
    }
}