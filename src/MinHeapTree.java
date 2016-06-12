
public class MinHeapTree {
	 Point[] arr;
	 int[] auxArr;
	 int sizeAux;
	 
	//number of elements in the heap
	private int Size;
	
	public int returnSize(){
		return Size;
	}
	public MinHeapTree(Point[] newArr,int numOfElements,int arrayLength){
		arr= new Point[arrayLength+1];
		auxArr= new int[arrayLength+1];
		
		Size=numOfElements;
		
		for(int i=0;i<numOfElements;i++){
			arr[i+1]=newArr[i];
		}
		//System.out.println("here");
		for (int i=Size/2;i>0;i--)
			Heapify(i);
		
	}
	
	private void Heapify(int index){
		int l=UtilsClass.Left(index),r=UtilsClass.Right(index);
		int min=index;
		
		
		
		if (l<Size && (arr[l].getY() < arr[min].getY())){
			min=l;
		}
		if( r<=Size && (arr[r].getY() < arr[min].getY())){
			min=r;
		}
		if (min!= index){
			UtilsClass.swap(arr, index, min);
			Heapify(min);
		}
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
	
	private void IncreaseKey(int i, Point key){
		if (key.getY()<arr[i].getY())
			throw new RuntimeException("New key is bigger than current key");
		arr[i]= new Point(key);
		while (i>1 && arr[UtilsClass.Parent(i)].getY()> arr[i].getY()){
			UtilsClass.swap(arr, i, UtilsClass.Parent(i));
			i=UtilsClass.Parent(i);
		}
		
	}
	
	public Point HeapMin(){
		return arr[1]; 
	}
	
	public void CleanAux(){
		sizeAux = 0;
	}
	
	public Point ExtractAux(){
		if(sizeAux > 0){
			Point point = arr[auxArr[0]];
			int child1 = UtilsClass.Left(auxArr[0]);
			int child2 = UtilsClass.Right(auxArr[0]);
			// remove first element
			auxArr[0] = auxArr[sizeAux-1];
			sizeAux--;
			HeapifyAux(0);
			// Increase aux
			auxArr[sizeAux] = child1;
			HeapifyAux(0);
			sizeAux++;
			auxArr[sizeAux] = child2;
			HeapifyAux(0);
			sizeAux++;
			
			return point;
		}
		
		return null;
	}
	
	public void InitAux(){
		auxArr[0] = 0;  
		sizeAux = 1;
	}
	
	private void HeapifyAux(int i){
		int left = UtilsClass.Left(i);
		int right = UtilsClass.Right(i);
		int minimum = i;
		if(left < sizeAux && UtilsClass.ComparePointsByY(arr[auxArr[left]], arr[auxArr[i]]) < 0){
			minimum = left;
		}
		if(right < sizeAux && UtilsClass.ComparePointsByY(arr[auxArr[right]], arr[auxArr[minimum]]) < 0){
			minimum = right;
		}
		
		if(minimum != i){
			int temp = auxArr[i];
			auxArr[i] = auxArr[minimum];
			auxArr[minimum] = temp;
			HeapifyAux(minimum);
		}
	}
//
//	public static void main(String args[]){
//		Point[] points = {
//				new Point(5, 100), 
//				new Point(3, 69),
//				new Point(2, 84),
//				new Point(1, 2),
//				new Point(4, 50),
//				};
//		MinHeapTree myh=new MinHeapTree(points, 5, 9);
//		UtilsClass.printarr(myh.arr, 6);
//		myh.HeapInsert(new Point(9,1));
//		UtilsClass.printarr(myh.arr, 7);
//		myh.HeapInsert(new Point(9,105));
//		UtilsClass.printarr(myh.arr, 8);
//		myh.ExtractMin();
//		UtilsClass.printarr(myh.arr, 7);
//	}
//
	
}
