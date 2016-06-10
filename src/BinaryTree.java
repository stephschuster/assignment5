
public class BinaryTree {
	BinaryNode root;
	
	private BinaryNode createNode(int start, int end, Point[] array){
		BinaryNode result = new BinaryNode();
		if(end > start){
			int middle = ((end-start)/2)+start;
			result.data = array[middle];
			if(middle!=start)
				result.left = createNode(start, middle-1, array);
			if(middle!=end)
				result.right = createNode(middle+1, end, array);
		}else if(end==start){
			result.data = array[end];
			result.right = null;
			result.left = null;
		}else{
			return null;
		}
		
		result.accumulateHeight = result.data.getY();
		result.accumulateNodes = 1;
		
		if(result.left != null){
			result.accumulateHeight += result.left.accumulateHeight;
			result.accumulateNodes += result.left.accumulateNodes;
		}
		if(result.right != null){
			result.accumulateHeight += result.right.accumulateHeight;
			result.accumulateNodes += result.right.accumulateNodes;
		}
		
			
		return result;
	}

	public void createTreeFromSortedArray(Point[] sorted, int size) {
		root = createNode(0, size-1, sorted);
	}
	
	public BinaryNode findSmallestInRange(int x){
		BinaryNode current = root;
		boolean isFound = false;
		while(current != null && !isFound){
			if(current.data.getX() < x){
				current = current.right;
			} else if(current.data.getX() > x && current.left != null && current.left.data.getX() >= x){
				current = current.left;
			} else if(current.data.getX() >= x){
				isFound = true;
			}else{
				current = null;
			}
		}
		
		return current;
	}
	
	public BinaryNode findBiggerInRange(int x){
		BinaryNode current = root;
		boolean isFound = false;
		while(current != null && !isFound){
			if(current.data.getX() > x){
				current = current.left;
			} else if(current.data.getX() < x && current.right != null && current.right.data.getX() <= x){
				current = current.right;
			} else if(current.data.getX() <= x){
				isFound = true;
			}else{
				current = null;
			}
		}
		
		return current;
	}
}
