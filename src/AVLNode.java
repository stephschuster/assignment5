
public class AVLNode {
	AVLNode left;
	AVLNode right;
	Point data;
	int counter;
	double accumulateY;
	int height;
	
	public AVLNode(Point data){
		this.data = data;
		left = null;
		right = null;
		counter = 0;
		accumulateY = 0;
		height = 0;
	}
	
	public AVLNode(){
		this(null);
	}
	
	public boolean isLeaf(){
		return left == null && right == null;
	}
	
	public AVLNode findMinNode(){
		AVLNode current = this;
		
		while(current.left != null){
			current = current.left;
		}
		
		return current;
	}
}
