
public class AVLNode {
	AVLNode left;
	AVLNode right;
	Point data;
	int counter;
	double accumulateY;
	int height;
	
	public boolean isLeaf(){
		return left == null && right == null;
	}
}
