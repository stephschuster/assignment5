
public class PointDataStructure implements PDT {


	MedianNode root;
	MaxHeapTree maxHeap;
	MinHeapTree minHeap;
	
	public PointDataStructure(Point[] points, Point initialYMedianPoint)
	{
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
		
		
	}

	@Override
	public void addPoint(Point point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point[] getPointsInRange(int XLeft, int XRight) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numOfPointsInRange(int XLeft, int XRight) {
		// TODO Auto-generated method stub
		return 0;
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

