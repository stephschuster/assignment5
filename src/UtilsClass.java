
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
		
		
}
