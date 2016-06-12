import javax.management.RuntimeErrorException;


public class MaxHeapTree {
	
	Point[] arr;
	private int Size;
	
	int[] auxArr;
	int sizeAux;
	
	public int returnSize(){
		return Size;
	}	
	public MaxHeapTree(Point[] newArr,int numOfElements,int arrayLength){
		arr= new Point[arrayLength+1];
		auxArr= new int[arrayLength+1];
		Size=numOfElements;
		
		
		for(int i=0;i<Size;i++){
			arr[i+1]=newArr[i];
		}
		
		//System.out.println("max array "+arr[1].getX());
		UtilsClass.printarr(arr, Size, 1);
		
		
		for (int i=(int) Math.ceil(Size/2);i>0;i--){
			Heapify(i);
		}
	}
	
	private void Heapify(int index){
		int l=UtilsClass.Left(index),r=UtilsClass.Right(index);
		int max=index;
		
		System.out.println("max "+max+"left"+l+"size"+Size+"right"+r);
		
		if (l<Size && (arr[l].getY() > arr[max].getY())){
			max=l;
		
		}
		if( r<=Size && (arr[r].getY() > arr[max].getY())){
			max=r;

		}
		if (max!= index){
			UtilsClass.swap(arr, index, max);
			Heapify(max);

		}
			
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
	
	public void CleanAux(){
		sizeAux = 0;
	}
	
	public Point ExtractAux(){
		if(sizeAux > 0){
			Point point = arr[auxArr[1]];
			int child1 = UtilsClass.Left(auxArr[1]);
			int child2 = UtilsClass.Right(auxArr[1]);
			// remove first element
			auxArr[1] = auxArr[sizeAux];
			sizeAux--;
			HeapifyAux(1);
			// Increase aux
			sizeAux++;
			auxArr[sizeAux] = -1;//child1;
			IncreaseAuxKey(sizeAux, child1);
	
			sizeAux++;
			auxArr[sizeAux] = -1; //child2;
			IncreaseAuxKey(sizeAux, child2);
			
			return point;
		}
		
		return null;
	}

	public void InitAux(){
		auxArr[1] = 1;  
		sizeAux = 1;
	}
	
	private void HeapifyAux(int i){
		int left = UtilsClass.Left(i);
		int right = UtilsClass.Right(i);
		int maximum = i;
		if(left <= sizeAux && UtilsClass.ComparePointsByY(arr[auxArr[left]], arr[auxArr[i]]) > 0){
			maximum = left;
		}
		if(right <= sizeAux && UtilsClass.ComparePointsByY(arr[auxArr[right]], arr[auxArr[maximum]]) > 0){
			maximum = right;
		}
		
		if(maximum != i){
			int temp = auxArr[i];
			auxArr[i] = auxArr[maximum];
			auxArr[maximum] = temp;
			HeapifyAux(maximum);
		}
	}

	private void IncreaseAuxKey(int index, int key){
		if(key < auxArr[index] || key > Size){
			// error
		} else{
			auxArr[index] = key;
			int parent = UtilsClass.Parent(index);
			while(index > 1 && UtilsClass.ComparePointsByY(arr[auxArr[parent]], arr[auxArr[index]]) < 0){
				int temp = auxArr[parent];
				auxArr[parent] = auxArr[index];
				auxArr[index] = temp;
				index = parent;
				parent = UtilsClass.Parent(index);
			}
		}
	}
}
