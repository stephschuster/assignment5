
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
		
		result.accumulateY = result.data.getY();
		int height = 0;
		if(result.left != null){
			result.accumulateY += result.left.accumulateY;
			height += result.left.height;
		}
		if(result.right != null){
			result.accumulateY += result.right.accumulateY;
			height += result.right.height;
		}
		result.height = height + 1;
			
		return result;
	}

	public void createTreeFromSortedArray(Point[] sorted, int size) {
		root = createNode(0, size-1, sorted, 0);
	}
	
	public int findSmallestPositionInRange(int x){
		return findSmallestPositionInRange(x, root, 0);
	}
	
	private int findSmallestPositionInRange(int x, AVLNode current, int counter){
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
	
	private int findBiggerPositionInRange(int x, AVLNode current, int counter){
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
	
	public double findHeightBefore(int x, boolean includeRange){
		
		return findHeightBefore(x, root, root.accumulateY, includeRange);
	}
	
	private double findHeightBefore(int x, AVLNode current, double accumulateHeight, boolean includeRange){
		if(current != null){
			if(current.data.getX() < x){
				double result = findHeightBefore(x, current.right, accumulateHeight, includeRange);
				if(result ==-1){
					result = accumulateHeight;
					if(!includeRange)
						result = result- current.data.getY();
				}
				
				return result;
			} else{
				if(current.right != null){
					accumulateHeight -= current.right.accumulateY;
				}
				
				double result = findHeightBefore(x, current.left, accumulateHeight-current.data.getY(), includeRange);
				
				if(result == -1 && includeRange){
					result = accumulateHeight;
				}else if(result == -1 && !includeRange){
					result = accumulateHeight-current.data.getY();
				}
				
				return result;
			}
		}
		
		return -1;
	}
	
	public double findHeightAfter(int x){
		
		return findHeightAfter(x, root);
	}
	
	private double findHeightAfter(int x, AVLNode current){
		if(current != null){
			if(current.data.getX() > x){
				return findHeightAfter(x, current.left);
			} else if(current.data.getX() <= x){
				double result = findHeightAfter(x, current.left);
				if(result > -1)
					return current.accumulateY - result;
				else{
					if(current.right!= null)
						return current.right.accumulateY;
					else
						return 0;
				}
			}
		}
		
		return -1;
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
		root = Insert(data, root, root.counter);
	}
	
	private AVLNode Insert(Point data, AVLNode current, int counter){
		if(current == null){
			current = new AVLNode();
			current.data = data;
			current.counter = counter + 1;
		} else if(data.getX() > current.data.getX()){
			current.right = Insert(data, current.right, current.counter);
			if(current.right.height - current.left.height == 2){
				if(data.getX() > current.right.data.getX()){
					current = rotateWithRightChild(current);
				}
				else{
					current = doubleWithRightChild(current);
				}
			}
		} else if(data.getX() < current.data.getX()){
			current.left = Insert(data, current.left, current.counter);
			if(current.left.height - current.right.height == 2){
				if(data.getX() < current.left.data.getX()){
					current = rotateWithLeftChild(current);
				}
	            else{
	            	current = doubleWithLeftChild(current);
	            }
			}
		}
		
		current.height = Math.max(current.left.height, current.right.height) + 1;
		return current;
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

	private AVLNode rotateWithLeftChild(AVLNode node)
    {
    	AVLNode left = node.left;
        node.left = left.right;
        left.right = node;
        node.height = Math.max(node.right.height, node.left.height) + 1;
        left.height = Math.max(left.left.height, node.height) + 1;
        return left;
    }
   
    private AVLNode rotateWithRightChild(AVLNode node)
    {
    	AVLNode right = node.right;
        node.right = right.left;
        right.left = node;
        node.height = Math.max(node.right.height,node.left.height) + 1;
        right.height = Math.max(right.right.height, node.height) + 1;
        return right;
    }
    
    private AVLNode doubleWithLeftChild(AVLNode node)
    {
    	node.left = rotateWithRightChild(node.left);
        return rotateWithLeftChild(node);
    }
   
    private AVLNode doubleWithRightChild(AVLNode node)
    {
    	node.right = rotateWithLeftChild(node.right);
        return rotateWithRightChild(node);
    }
}
