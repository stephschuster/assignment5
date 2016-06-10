
class MedianNode {

	private MaxHeapTree left;
	private MinHeapTree rigth;
	private Point value;
	
	public MedianNode(Point initialYMedianPoint){
		value=initialYMedianPoint;
	}
	
	public void setLeft(MaxHeapTree heap){
		left=heap;
	}
	
	public void setRigth(MinHeapTree heap){
		rigth=heap;
	}
}
