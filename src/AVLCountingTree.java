
public class AVLCountingTree {
	AVLNode root;
	
	public void createTreeFromSortedArray(Point[] sorted) {
		root = createNode(0, sorted.length-1, sorted, 0);
	}
	
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

	public void Insert(Point data){
		root = Insert(data, root, 0);
	}
	
	private AVLNode Insert(Point data, AVLNode current, int counter){
		if(current == null){
			current = new AVLNode(data);
			current.counter = 1;
			current.accumulateY = data.getY();
			return current;
		} else if(data.getX() > current.data.getX()){
			current.right = Insert(data, current.right, current.counter+counter);
			
			int rightHeight = current.right == null ? 0 : current.right.height;
			int leftHeight = current.left == null ? 0 : current.left.height;
			
			if(rightHeight - leftHeight == 2){
				if(data.getX() > current.right.data.getX()){
					current = rotateWithRightChild(current);
				}
				else{
					current = doubleWithRightChild(current);
				}
			}
		} else if(data.getX() < current.data.getX()){
			current.left = Insert(data, current.left, counter);
			current.counter++;
			
			int rightHeight = current.right == null ? 0 : current.right.height;
			int leftHeight = current.left == null ? 0 : current.left.height;
			
			if(leftHeight - rightHeight == 2){
				if(data.getX() < current.left.data.getX()){
					current = rotateWithLeftChild(current);
				}
	            else{
	            	current = doubleWithLeftChild(current);
	            }
			}
		}
		
		int rightHeight = current.right == null ? 0 : current.right.height;
		int leftHeight = current.left == null ? 0 : current.left.height;
		
		current.height = Math.max(rightHeight, leftHeight) + 1;
		current = setAccumulateY(current);
		
		return current;
	}
	
	public void Remove(Point data){
		root = Remove(data, root, 0);
	}
	
	private AVLNode Remove(Point data, AVLNode current, int counter){
		if(current != null){
			if(current.data.equals(data) && current.isLeaf()){
				current = null; // removed
			} else if(current.data.equals(data)){
				if(current.right != null && current.left == null){
					current.right.counter += current.counter;
					return current.right;
				}else if(current.left != null && current.right == null){
					return current.left;
				} else {
					AVLNode successor = current.right.findMinNode();
					current.data = successor.data;
					current.counter += successor.counter;
					current.right.counter -= successor.counter;
					
					current.right = Remove(successor.data, current.right, counter+current.counter);
					
					int rightHeight = current.right == null ? 0 : current.right.height;
					int leftHeight = current.left == null ? 0 : current.left.height;
					if(leftHeight - rightHeight == 2){
						if(current.left.right == null){
							current = rotateWithLeftChild(current);
						}
			            else{
			            	current = doubleWithLeftChild(current);
			            }
					}
					
					current = setAccumulateY(current);
				}
			} else if(current.data.getX() > data.getX()){
				current.left = Remove(data, current.left, counter);
				current.counter--;
				current = setAccumulateY(current);
				
				int rightHeight = current.right == null ? 0 : current.right.height;
				int leftHeight = current.left == null ? 0 : current.left.height;
				if(rightHeight - leftHeight == 2){
					if(current.right.right == null){
						current = rotateWithRightChild(current);
					}
		            else{
		            	current = doubleWithRightChild(current);
		            }
				}
			} else{
				current.right = Remove(data, current.right, counter + current.counter);
				current = setAccumulateY(current);
				
				int rightHeight = current.right == null ? 0 : current.right.height;
				int leftHeight = current.left == null ? 0 : current.left.height;
				if(leftHeight - rightHeight == 2){
					if(current.left.right == null){
						current = rotateWithLeftChild(current);
					}
		            else{
		            	current = doubleWithLeftChild(current);
		            }
				}
			}
		}
		return current;

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
        
        int rightHeight = node.right == null ? 0 : node.right.height;
		int leftHeight = node.left == null ? 0 : node.left.height;
		
        node.height = Math.max(rightHeight, leftHeight) + 1;
        
        int leftLeftHeight = left.left == null ? 0 : left.left.height;
        left.height = Math.max(leftLeftHeight, node.height) + 1;
        
        node.counter = node.counter - left.counter;
        node = setAccumulateY(node);
        left = setAccumulateY(left);
        
        return left;
    }
   
    private AVLNode rotateWithRightChild(AVLNode node)
    {
    	AVLNode right = node.right;
        node.right = right.left;
        right.left = node;
        
        int rightHeight = node.right == null ? 0 : node.right.height;
		int leftHeight = node.left == null ? 0 : node.left.height;
		
        node.height = Math.max(rightHeight, leftHeight) + 1;
        int rightRightHeight = right.right == null ? 0 : right.right.height;
        right.height = Math.max(rightRightHeight, node.height) + 1;
        
        right.counter = right.counter + node.counter;
        node = setAccumulateY(node);
        right = setAccumulateY(right);
        
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
    
    private AVLNode setAccumulateY(AVLNode node){
    	node.accumulateY = node.data.getY();
		if(node.left != null){
			node.accumulateY += node.left.accumulateY;
		}
		if(node.right != null){
			node.accumulateY += node.right.accumulateY;
		}
		
		return node;
    }
}
