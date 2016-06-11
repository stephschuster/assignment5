
public class AVLCountingTree {
	AVLNode root;
	
	private AVLNode createNode(int start, int end, Point[] array, int sum){
		AVLNode result = new AVLNode();
		
		if(end > start){
			int middle = ((end-start)/2)+start;
			result.data = array[middle];
			result.counter = middle-sum;
			if(middle!=start)
				result.left = createNode(start, middle-1, array, sum);
			if(middle!=end)
				result.right = createNode(middle+1, end, array, middle);
		}else if(end==start){
			result.data = array[end];
			result.counter = end-sum;
			result.right = null;
			result.left = null;
		}else{
			return null;
		}
		
		result.accumulateHeight = result.data.getY();
		
		if(result.left != null){
			result.accumulateHeight += result.left.accumulateHeight;
		}
		if(result.right != null){
			result.accumulateHeight += result.right.accumulateHeight;
		}
		
			
		return result;
	}

	public void createTreeFromSortedArray(Point[] sorted, int size) {
		root = createNode(0, size-1, sorted, 0);
	}
	
	public int findSmallestPositionInRange(int x){
		return findSmallestPositionInRange(x, root, 0);
	}
	
	public int findSmallestPositionInRange(int x, AVLNode current, int counter){
		if(current != null){
			if(current.data.getX() < x){
				return findSmallestPositionInRange(x, current.right, counter + current.counter);
			} else if(current.data.getX() >= x){
				int result = findSmallestPositionInRange(x, current.left, counter);
				if(result > -1 && result < counter+current.counter)
					return result;
				else
					return counter+current.counter;
			}
		}
		
		return -1;
	}
	
	public int findBiggerPositionInRange(int x){
		return findBiggerPositionInRange(x, root, 0);
	}
	
	public int findBiggerPositionInRange(int x, AVLNode current, int counter){
		if(current != null){
			if(current.data.getX() > x){
				return findBiggerPositionInRange(x, current.left, counter);
			} else if(current.data.getX() <= x){
				int result = findBiggerPositionInRange(x, current.right, counter + current.counter);
				if(result > counter+current.counter)
					return result;
				else
					return counter+current.counter;
			}
		}
		
		return -1;
	}
	
	public AVLNode findSmallestInRange(int x){
		AVLNode current = root;
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
	
	public AVLNode findBiggerInRange(int x){
		AVLNode current = root;
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

	public void Insert(Point data){
		
	}
	
	public void Remove(Point data){
		
	}

	public void fillArray(Point[] result, int xLeft, int xRight) {
		fillArray(result, xLeft, xRight, 0, root);
	}
	
	private int fillArray(Point[] result, int xLeft, int xRight, int index, AVLNode current) {
		if(current != null){
			if(current.data.getX() >= xLeft && current.data.getX() <= xRight){
				result[index] = current.data;
				index++;
				index = fillArray(result, xLeft, xRight, index, current.left);
				return fillArray(result, xLeft, xRight, index, current.right);
			} else if(current.data.getX() > xRight){
				return fillArray(result, xLeft, xRight, index, current.left);
			} else{
				return fillArray(result, xLeft, xRight, index, current.right);
			}
		}
		return index;
	}
}
