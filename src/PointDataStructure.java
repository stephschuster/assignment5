
public class PointDataStructure implements PDT {

	private Point[] sortedByXPoints;
	private BinaryTree tree;
	private int size;

	MedianNode root;
	MaxHeapTree maxHeap;
	MinHeapTree minHeap;
	
	public PointDataStructure(Point[] points, Point initialYMedianPoint)
	{
<<<<<<< HEAD
		int maxSize = points.length + (int) Math.ceil(10*Math.log(points.length)/Math.log(2));
		sortedByXPoints = new Point[maxSize];
		size = points.length;
		tree = new BinaryTree();
		
		sortByXPoints(points);
		
		tree.createTreeFromSortedArray(sortedByXPoints, size);
	}
	

	private void sortByXPoints(Point[] points) {
		int lastIndex = points.length-1;
		
		if(lastIndex < points[lastIndex].getX()){
			// we got an already sorted array
			// nothing to do
			sortedByXPoints = points;
		}
		else{ //maybe we got an unsorted array between 0 and length-1
			for(int i = 0; i < points.length; i++){
				sortedByXPoints[points[i].getX()] = points[i];
			}
		}
=======
		root= new MedianNode(initialYMedianPoint);
		
		int partRes=Partition(points, initialYMedianPoint);
		//indexes of the two new heaps arrays
		Point MaxArrayIndex= new Point(0,partRes);
		Point MinArrayIndex= new Point(partRes+2,points.length-1);
		
		Point[] MaxHeapArray= new Point[partRes+1];
		for(int i=MaxArrayIndex.getX();i<MaxArrayIndex.getY()+1;i++)
			MaxHeapArray[i+1]=new Point(points[i]);
		MaxHeapTree MaxHeap= new MaxHeapTree(MaxHeapArray,partRes+1,partRes+1+UtilsClass.log(partRes+1,2));
		
		root.setLeft(MaxHeap);
		
		
>>>>>>> master
	}


	@Override
	public void addPoint(Point point) {
		// TODO Auto-generated method stub
		
	}

	
	public Point[] getPointsInRange(int XLeft, int XRight) {
		int totalPoints = numOfPointsInRange(XLeft, XRight);
		int leftIndex = findBiggerIndexByX(XLeft, 0, sortedByXPoints.length);
		Point[] result = new Point[totalPoints];
		for(int i = leftIndex; i < leftIndex + totalPoints; i++){
			result[i-leftIndex] = sortedByXPoints[i];
		}
		return result;
	}

	
	public int numOfPointsInRange(int XLeft, int XRight) {
		
		int leftIndex = tree.findSmallestInRange(XLeft).accumulateNodes;
		int rightIndex = tree.findBiggerInRange(XRight).accumulateNodes;
		return rightIndex - leftIndex + 1;
	}
	
	private int findBiggerIndexByX(int x, int start, int end){
		boolean found = false;
		int result = -1;
		while(end > start && !found){
			int middleIndex = (end-start)/2;
			
			if(sortedByXPoints[middleIndex].getX() == x){
				found = true;
				result = middleIndex;
			} else if(sortedByXPoints[middleIndex].getX() > x){
				end = middleIndex-1;
				// if middle is bigger than x, I want to save it, maybe is the closest number
				result = middleIndex;
			} else{
				// needs to debug, IDK what its the closest number in this case
				start = middleIndex+1;
			}
		}
		if(!found){
			// maybe here I will find the closest number bigger than x
		}
		return result;
	}
	
		@Override
	public double averageHeightInRange(int XLeft, int XRight) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeMedianPoint() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point[] getMedianPoints(int k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point[] getAllPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Execute partition with a given pivot
	 * 
	 * @param points
	 * @param pivot
	 * 
	 * return index of the end of the first array
	 */
	private int Partition(Point[] points, Point pivot){
		
		int left=0,right=points.length-1;
		int pivot_index=IndexofPoint(points,pivot);
		
		while(left<right){
			while(left<points.length-1 && (points[left].getY()) <=pivot.getY())
				left++;
			while( (points[right].getY()) <= pivot.getY() )
				right--;
			if(left<right)
				UtilsClass.swap(points,left,right);
		}
		UtilsClass.swap(points,pivot_index,right);
		return right;
		
	}
	
	//return index of a given point in array of points. If doesn't exist return -1
	private int IndexofPoint(Point[] pointsArr, Point point){
		int size = pointsArr.length;
		for(int i=0;i<size;i++){
			if (pointsArr[i].getY()==point.getY())
				return i;
		
		}
		return -1;
	}
	
}

