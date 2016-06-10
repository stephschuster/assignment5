import javax.management.RuntimeErrorException;


public class MaxHeapTree {
<<<<<<< HEAD
	private Point[] nodes;
	private int size;
	
	public MaxHeapTree(Point[] array){
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
	
	public void BuildMaxHeap(){
		for(int i=(size/2)-1; i >= 0; i--){
			MaxHeapify(i);
		}
	}
	
	public void MaxHeapInsert(){
		
	}
	
	public Point HeapMax(){
		if(size >= 1)
			return nodes[0];
		return null;
	}
	
	public Point ExtractMax(){
		if(size>=1)
			return nodes[0];
		// remove maximum
		return null;
	}
	public void MaxHeapify(int index){
		int left = Left(index);
		int right = Right(index);
		int largest = index;
		if(left <= size && (nodes[left].getY() > nodes[index].getY() || 
		  (nodes[left].getY() == nodes[index].getY() && nodes[left].getX() > nodes[index].getX()))){
			largest = left;
		} 
		if(right <= size && (nodes[right].getY() > nodes[largest].getY() || 
		  (nodes[right].getY() == nodes[largest].getY() && nodes[right].getX() > nodes[largest].getX()))){
			largest = right;
		}
		
		if(largest != index){
			Point temp = nodes[largest];
			nodes[largest] = nodes[index];
			nodes[index] = temp;
			MaxHeapify(largest);
		}
	}
	
	public int Left(int i){
		return 2*i;
	}
	
	public int Right(int i){
		return (2*i) + 1; 
	}
=======

	Point[] arr;
	//number of elements in the heap
	int Size;
	public MaxHeapTree(Point[] arr,int numOfElements,int arrayLength){
		arr= new Point[arrayLength];
		Size=numOfElements;
		
		for (int i=Size/2;i>0;i--)
			Heapify(i);
		 
	}
	
	private void Heapify(int index){
		int l=UtilsClass.Left(index),r=UtilsClass.Right(index);
		int max=index;
		
		if (l<Size && (arr[l].getY() > arr[max].getY()))
			max=l;
		if( r<=Size && (arr[r].getY() > arr[max].getY()))
			max=r;
		if (max!= index)
			UtilsClass.swap(arr, index, max);
			Heapify(max);
	}
	public Point ExtractMax(){
		if (Size==0)
			throw new RuntimeException("Empty Heap");
		Point max=new Point(arr[1]);
		arr[1]=new Point(arr[Size]);
		Size--;
		Heapify(1);
		return max;
	}
	
	private void IncreaseKey(int i, Point key){
		if (key.getY()<arr[i].getY())
			throw new RuntimeException("New key is smaller than current key");
		arr[i]= new Point(key);
		while (i>1 && arr[UtilsClass.Parent(i)].getY()< arr[i].getY()){
			UtilsClass.swap(arr, i, UtilsClass.Parent(i));
			i=UtilsClass.Parent(i);
		}
			
	}
	
	public void HeapInsert(Point key){
		Size++;
		arr[Size]= new Point(Integer.MIN_VALUE,Integer.MIN_VALUE);
		IncreaseKey(Size, key);
	}
	public Point HeapMax(){
		return arr[1]; 
	}
	
>>>>>>> master
}
