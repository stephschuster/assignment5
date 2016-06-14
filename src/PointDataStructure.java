import org.jcp.xml.dsig.internal.dom.Utils;



public class PointDataStructure implements PDT {

	private AVLCountingTree tree;
	private int size;

	Point root;
	MaxHeapTree maxHeap;
	MinHeapTree minHeap;
	
	int numAllPoints;
	
	
	public PointDataStructure(Point[] points, Point initialYMedianPoint)
	{
		// must come before sorting by y
		size = points.length;
		tree = new AVLCountingTree();
		tree.createTreeFromSortedArray(sortByXPoints(points));
		root= new Point(initialYMedianPoint);

		// partition by y
		int pivotPosition=Partition(points, root);
		
		// set max size for heaps
		int maxHeapSize = pivotPosition + (10*UtilsClass.log(points.length,2)) + 1;
		int minHeapSize = points.length - pivotPosition + (10*UtilsClass.log(points.length,2));
		
		// fill the max heap with numbers smaller than median
		Point[] MaxHeapArray= new Point[pivotPosition];
			
		for(int i = 0; i < pivotPosition; i++){
			MaxHeapArray[i] = points[i];
		}

		maxHeap= new MaxHeapTree(MaxHeapArray,MaxHeapArray.length, maxHeapSize);		
		
		// fill the min heap with numbers bigger than median
		Point[] MinHeapArray= new Point[points.length - pivotPosition-1];
		
		for(int i=pivotPosition+ 1,j=0;j<MinHeapArray.length;i++,j++){
			MinHeapArray[j]=new Point(points[i]);
		}
		minHeap=new MinHeapTree(MinHeapArray, MinHeapArray.length, minHeapSize);		
	}

	@Override
	public void addPoint(Point point) {
		if(point.getY()>root.getY()){
			minHeap.HeapInsert(point);
		}
		else {
			maxHeap.HeapInsert(point);
		}
		
		saveStructureValidity();
		tree.Insert(point);
	}
	

	public Point[] getPointsInRange(int XLeft, int XRight) {
		int totalPoints = numOfPointsInRange(XLeft, XRight);
		Point[] result = new Point[totalPoints];
		tree.fillArray(result, XLeft, XRight);
		return result;
	}

	
	public int numOfPointsInRange(int XLeft, int XRight) {
		int leftIndex = tree.findSmallestPositionInRange(XLeft);
		int rightIndex = tree.findBiggerPositionInRange(XRight);
		return rightIndex - leftIndex + 1;
	}
	
	@Override
	public double averageHeightInRange(int XLeft, int XRight) {
		double beforeLeft = tree.findHeightBefore(XLeft, false);
		double beforeRight = tree.findHeightBefore(XRight, true);
		double totalPoints = numOfPointsInRange(XLeft, XRight);
		
		return (beforeRight - beforeLeft)/totalPoints;
	}

	@Override
	public void removeMedianPoint() {
		Point temp=maxHeap.ExtractMax();
		tree.Remove(root);
		
		root=new Point(temp);
		
		saveStructureValidity();
	}

	@Override
	public Point[] getMedianPoints(int k) {
		Point[] ans=new Point[k];
		int lowerLimit=(int)Math.ceil((k-1)/2.0);
		int higherLimit=(int)Math.floor((k-1)/2.0);
		
		ans[0]=root;
		int index = 1;
		maxHeap.CleanAux();
		maxHeap.InitAux();
		for(int i = 0; i < lowerLimit; i++){
			ans[index] = maxHeap.ExtractAux();
			index++;
		}
		
		minHeap.CleanAux();
		minHeap.InitAux();
		for(int i = 0; i < higherLimit; i++){
			ans[index] = minHeap.ExtractAux();
			index++;
		}
		
		return ans;
	}

	@Override
	public Point[] getAllPoints() {
		Point[] result = new Point[size];
		tree.fillArray(result, 0, size-1);
		return result;
	}

	private int Partition(Point[] points, Point pivot){
		
		int left=0,right=points.length-1;
		int pivot_index=IndexofPoint(points,pivot);
		
		while(left<right){
			while(left<points.length-1 && UtilsClass.ComparePointsByY(points[left],pivot)<=0){
				left++;
			}
			
			while(UtilsClass.ComparePointsByY(points[right],pivot) > 0){
				right--;
			}
			
			if(left<right){
				UtilsClass.swap(points,left,right);
			}
			
		}
		
		pivot_index=IndexofPoint(points,pivot);
		UtilsClass.swap(points,pivot_index,right);
		return right;
		
	}
	
	//return index of a given point in array of points. If doesn't exist return -1
	private int IndexofPoint(Point[] pointsArr, Point point){
		int size = pointsArr.length;
		for(int i=0;i<size;i++){
			if (pointsArr[i].equals(point))
				return i;
		
		}
		return -1;
	}
	

	private void saveStructureValidity(){
		 numAllPoints=minHeap.returnSize()+maxHeap.returnSize()+1;
		
		if (numAllPoints%2!=0 && (minHeap.returnSize()!=maxHeap.returnSize())){
			while(minHeap.returnSize()>maxHeap.returnSize()){
				Point temp=minHeap.ExtractMin();
				maxHeap.HeapInsert(root);
				root=new Point(temp);
				
			}
			while(maxHeap.returnSize()>minHeap.returnSize()){
				Point temp=maxHeap.ExtractMax();
				minHeap.HeapInsert(root);
				root=new Point(temp);
				
			}
		}
		else if (numAllPoints%2==0 && (minHeap.returnSize()+1 !=maxHeap.returnSize())){
			while(minHeap.returnSize()+1>maxHeap.returnSize()){
				Point temp=minHeap.ExtractMin();
				maxHeap.HeapInsert(root);
				root=new Point(temp);
				
			}
			while(maxHeap.returnSize()>minHeap.returnSize()+1){
				Point temp=maxHeap.ExtractMax();
				minHeap.HeapInsert(root);
				root=new Point(temp);
				
			}
		}
	}
	
	private Point[] sortByXPoints(Point[] points) {
		Point[] sortedByXPoints = new Point[points.length];
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
		return sortedByXPoints;
	}
	
}

