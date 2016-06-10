
public class MaxHeapTree {
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
}
