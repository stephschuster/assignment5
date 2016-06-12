import org.jcp.xml.dsig.internal.dom.Utils;



public class PointDataStructure implements PDT {

	private AVLCountingTree tree;
	private int size;

	Point root;
	MaxHeapTree maxHeap;
	MinHeapTree minHeap;
	
	int minSize,maxSize,numAllPoints;
	
	
	public PointDataStructure(Point[] points, Point initialYMedianPoint)
	{
		
		root= new Point(initialYMedianPoint);
		
		
		System.out.println("array before part ");
		UtilsClass.printarr(points, points.length,0);
		System.out.println();
		
		int partRes=Partition(points, root);
		
		
		System.out.println("array after part ");
		UtilsClass.printarr(points, points.length,0);
		System.out.println();
		
		//indexes of the two new heaps arrays
		Point MaxIndex= new Point(0,partRes);
		Point MinIndex= new Point(partRes+2,points.length-1);
		
		
		
		
		
		Point[] MaxHeapArray= new Point[(MaxIndex.getY()-MaxIndex.getX())+2];
			
		for(int i=MaxIndex.getX(),j=0;i<MaxIndex.getY()+1;i++,j++){
			MaxHeapArray[j]=new Point(points[i]);
		}

		maxHeap= new MaxHeapTree(MaxHeapArray,MaxHeapArray.length-1,MaxIndex.getY()+1+UtilsClass.log(MaxIndex.getY()+1,2));		
		
		
		Point[] MinHeapArray= new Point[MinIndex.getY()-MinIndex.getX()+2];
		//System.out.println("minheaparray len "+MinHeapArray.length);
		for(int i=MinIndex.getX(),j=0;i<MinIndex.getY()+1;i++,j++){
			MinHeapArray[j]=new Point(points[i]);
		}
		minHeap=new MinHeapTree(MinHeapArray, MinHeapArray.length-1, MinIndex.getY()+1+UtilsClass.log(MinIndex.getY()+1, 2));
		
		
		// sort by x
		int maxSize = points.length + (int) Math.ceil(10*Math.log(points.length)/Math.log(2));

		size = points.length;
		tree = new AVLCountingTree();
		tree.createTreeFromSortedArray(sortByXPoints(points));
		
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
		root=new Point(temp);
		
		saveStructureValidity();
		
	}

	@Override
	public Point[] getMedianPoints(int k) {
		
		 minSize=minHeap.returnSize();
		 maxSize=maxHeap.returnSize();
		 numAllPoints=minSize+maxSize+1;
		
		Point[] ans=new Point[k];
		int lowerLimit=(int) Math.floor(numAllPoints/2) - (int)Math.ceil((k-1)/2);
		int higherLimit=(int) Math.floor(numAllPoints/2) - (int)Math.floor((k-1)/2);
		int cnt=0;
		
		System.out.println("lowerLimit "+lowerLimit+"max size "+maxSize );
		
		if(k==1){
			ans[0]=root;
		}
		else{
			
		}
		for(int i=lowerLimit,j=0;i<maxSize;i++,j++){
			ans[j]=maxHeap.arr[i];
			cnt++;
			System.out.println("1counter "+cnt);
		}
		System.out.println("2counter "+cnt);
		ans[cnt]=new Point(root);
		cnt++;
		for(int i=0,j=cnt;i<higherLimit&&j<ans.length;i++,j++){
			ans[j]=minHeap.arr[i];
		}
		
		System.out.println("prinat try");
		UtilsClass.printarr(ans, ans.length, 0);
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
	
//		System.out.println("start part ");
//		UtilsClass.printarr(points, numAllPoints);
//		System.out.println("start1 part");
//		
		while(left<right){
			while(left<points.length-1 && (points[left].getY()) <pivot.getY()){
				UtilsClass.printarr(points, numAllPoints,0);
				left++;
			}
			
			while( (points[right].getY()) >= pivot.getY() ){
				UtilsClass.printarr(points, numAllPoints,0);
				right--;
			}
			
			if(left<right){
				UtilsClass.printarr(points, numAllPoints,0);
				UtilsClass.swap(points,left,right);
			}
			
		}
		UtilsClass.printarr(points, numAllPoints,0);
		UtilsClass.swap(points,pivot_index,right);
		UtilsClass.printarr(points, numAllPoints,0);
		UtilsClass.sleep();
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
	

	private void saveStructureValidity(){
		
		 minSize=minHeap.returnSize();
		 maxSize=maxHeap.returnSize();
		 numAllPoints=minSize+maxSize+1;
		
		if (numAllPoints%2!=0 && (minSize!=maxSize)){
			while(minSize>maxSize){
				Point temp=minHeap.ExtractMin();
				maxHeap.HeapInsert(root);
				root=new Point(temp);
				
			}
			while(maxSize>minSize){
				Point temp=maxHeap.ExtractMax();
				minHeap.HeapInsert(root);
				root=new Point(temp);
				
			}
		}
		else if (numAllPoints%2==0 && (minSize+1 !=maxSize)){
			while(minSize+1>maxSize){
				Point temp=minHeap.ExtractMin();
				maxHeap.HeapInsert(root);
				root=new Point(temp);
				
			}
			while(maxSize>minSize+1){
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

