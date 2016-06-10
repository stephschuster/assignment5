
public class MinHeapTree {

	Point[] arr;
	//number of elements in the heap
	int Size;
	
	public void MinHeapTree(Point[] arr,int numOfElements,int arrayLength){
		arr= new Point[arrayLength];
		Size=numOfElements;
		
		for (int i=Size/2;i>0;i--)
			Heapify(i);
		
	}
	
	private void Heapify(int index){
		int l=UtilsClass.Left(index),r=UtilsClass.Right(index);
		int min=index;
		
		if (l<Size && (arr[l].getY() < arr[min].getY()))
			min=l;
		if( r<=Size && (arr[r].getY() < arr[min].getY()))
			min=r;
		if (min!= index)
			UtilsClass.swap(arr, index, min);
			Heapify(min);
	}
	
	public Point ExtractMin(){
		if (Size==0)
			throw new RuntimeException("Empty Heap");
		Point min=new Point(arr[1]);
		arr[1]=new Point(arr[Size]);
		Size--;
		Heapify(1);
		return min;
	}
	
	public void HeapInsert(Point key){
		Size++;
		arr[Size]= new Point(Integer.MIN_VALUE,Integer.MIN_VALUE);
		IncreaseKey(Size, key);
	}
	public Point HeapMin(){
		return arr[1]; 
	}
}
