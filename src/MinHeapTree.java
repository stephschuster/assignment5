
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
		
	}
	
	public Point ExtractMin(){
	
		return null;
	}
	
	public void HeapInsert(Point key){
		
	}
	
	public Point HeapMin(){
		return arr[1]; 
	}
}
