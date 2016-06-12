
class UtilsClass {

	//Swaps Arr[a] and Arr[b]
		public static void swap(Point[] Arr,int a,int b){
			
			Point temp=new Point(Arr[b]);
			Arr[b]= new Point(Arr[a]);
			Arr[a]=new Point(temp);
		}
		
		public static int log(int x, int base)
		{
		    return (int) (Math.log(x) / Math.log(base));
		}
		
		public static int Left(int i){
			return 2*i;
		}
		public static  int Right(int i){
			return 2*i +1;
		}
		public static  int Parent(int i){
			return i/2;
		}

		public static void printarr(Point[] arr,int size,int start){
			for (int i=start;i<size;i++){
				System.out.print(" ("+arr[i].getX()+","+arr[i].getY()+") ");
			}
		}
		
		public static int ComparePointsByY(Point x, Point y){
			if(x.getY() == y.getY())
				return x.getX() - y.getX();
			return x.getY() - y.getY();
		}

		public static void sleep(){
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
