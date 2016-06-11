
public class AVLNode {
	AVLNode left;
	AVLNode right;
	Point data;
	int counter;
	double accumulateHeight;
	
	public boolean isLeaf(){
		return left == null && right == null;
	}
}
