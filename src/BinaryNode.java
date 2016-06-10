
public class BinaryNode {
	BinaryNode left;
	BinaryNode right;
	Point data;
	int accumulateNodes;
	double accumulateHeight;
	
	public boolean isLeaf(){
		return left == null && right == null;
	}
}
