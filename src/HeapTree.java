
public class HeapTree {
	private Comparable[] nodes;
	private int size;
	
	public HeapTree(Comparable[] array){
		nodes = array;
		size = array.length;
	}
	
	public void BuildMiddleHeap(){
		for(int i=(size/2)-1; i >= 0; i--){
			MiddleHeapify(i);
		}
	}
	
	public void MiddleHeapInsert(){
		
	}
	
	public Comparable HeapMiddle(){
		if(size >= 1)
			return nodes[0];
		return null;
	}
	
	public Comparable ExtractMiddle(){
		if(size>=1)
			return nodes[0];
		return null;
	}
	
	public void MiddleHeapify(int index){
		int left = Left(index);
		int right = Right(index);
		int largest = index;
		if(left <= size && nodes[left].compareTo(nodes[index]) > 0){
			largest = left;
		}
		if(right <= size && nodes[right].compareTo(nodes[largest])>0){
			largest = right;
		}
		
		if(largest != index){
			Comparable temp = nodes[largest];
			nodes[largest] = nodes[index];
			nodes[index] = temp;
			MiddleHeapify(largest);
		}
	}
	
	public void HeapIncreaseKey(){
		
	}
	
	public int Left(int i){
		return 2*i;
	}
	
	public int Right(int i){
		return (2*i) + 1; 
	}
}
