public class Node extends MAIN{
	public int key , d_height , r_height , height ;
	public Node left , right , up , down ;
	public int[] counters ;
	
	public Node() {
		this.counters = new int[num_bands+1] ;
	}
	
	public Node(int k) {
		this.key = k ;
		this.counters = new int[num_bands+1] ;
	}
	
	public Node(int k , int dh , int rh , int h , int[] c) {
		this.key = k ;
		this.d_height = dh ;
		this.r_height = rh ;
		this.height = h ;
		this.counters = c;
	}
}