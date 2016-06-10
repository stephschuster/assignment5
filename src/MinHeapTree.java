
public class MinHeapTree {
	private Point[] nodes;
	private int size;
	
	public MinHeapTree(Point[] array){
		int maxSize = array.length + (int) Math.ceil(Math.log(array.length) / Math.log(2));
		nodes = new Point[maxSize];
		size = array.length;
		CopyArray(array);
	}
	
	public void CopyArray(Point[] array){
		for(int i = 0; i < array.length; i++){
			nodes[i] = array[i];
		}
	}
	
	public void BuildMinHeap(){
		for(int i=(size/2)-1; i >= 0; i--){
			MinHeapify(i);
		}
	}
	
	public void MinHeapInsert(){
		
	}
	
	public Point HeapMin(){
		if(size >= 1)
			return nodes[0];
		return null;
	}
	
	public Point ExtractMin(){
		if(size>=1)
			return nodes[0];
		// remove minimum
		return null;
	}
	public void MinHeapify(int index){
		int left = Left(index);
		int right = Right(index);
		int shortest = index;
		if(left <= size && (nodes[left].getY() < nodes[index].getY() || 
		  (nodes[left].getY() == nodes[index].getY() && nodes[left].getX() < nodes[index].getX()))){
			shortest = left;
		} 
		if(right <= size && (nodes[right].getY() < nodes[shortest].getY() || 
		  (nodes[right].getY() == nodes[shortest].getY() && nodes[right].getX() < nodes[shortest].getX()))){
			shortest = right;
		}
		
		if(shortest != index){
			Point temp = nodes[shortest];
			nodes[shortest] = nodes[index];
			nodes[index] = temp;
			MinHeapify(shortest);
		}
	}
	
	public int Left(int i){
		return 2*i;
	}
	
	public int Right(int i){
		return (2*i) + 1; 
	}

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
