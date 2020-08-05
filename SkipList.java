import java.util.Random;
import java.util.Arrays;

public class SkipList extends MAIN {
	
	public Node head , tail ;
	public Random r ;
	public int hor , ver ;
	
	public SkipList() {
		
		r = new Random() ;
		Node p1 = new Node(Integer.MIN_VALUE) ;
		Node p2 = new Node(Integer.MAX_VALUE) ;
		
		head = p1 ;
		tail = p2 ;
		head.height = H ;
		tail.height = H ;
		head.d_height = num_bands-1 ;
		tail.d_height = num_bands-1 ;
		head.r_height = 1 ;
		tail.r_height = 1 ;
		p1.right = p2 ;
		p2.left = p1 ;
		
		for(int i=1 ; i<H ; i++) {
			
			Node n1 = new Node(Integer.MIN_VALUE , head.d_height , head.r_height , head.height , head.counters) ;
			Node n2 = new Node(Integer.MAX_VALUE , tail.d_height , tail.r_height , tail.height , tail.counters) ;
			p1.down = n1 ;
			p2.down = n2 ;
			n1.up = p1 ;
			n2.up = p2 ;
			n1.right = n2 ;
			n2.left = n1 ;
			p1 = n1 ;
			p2 = n2 ;
			
		}
		
		
//		while(head!=null) {
//			System.out.println(head.key);
//			head = head.right;
//		}
	}
	
	public Node search_initialization(int k) {
	
		Node p ;
		p = head ;
		
		while(true) {
			
			while((p.right.key)!=Integer.MAX_VALUE && p.right.key<=k) {
				p = p.right ;
			}
			
			if(p.down!=null && p.key!=k) {
				p = p.down ;
			}else {
				break ;
			}
		}
		
		return (p) ;
		
	}
	
	public void insert_initialization(int k, int dh) {
		
		Node p ;
		p = search_initialization(k) ;
//		System.out.println(p.key);
//		print();
		if(k!=p.key) {
			
			Node q = new Node(k);
			
			q.left = p ;
			q.right = p.right ;
			p.right.left = q ;
			p.right = q ;
			
			//int dh = 0 , rh = 1 ;
			int rh = 1 ;
			
//			while(r.nextDouble()<0.5) {
//				
//				if(dh >= num_bands-1)
//					break ;
//				dh++ ;
//				
//			}
//			if(k==3||k==4)
//				{q.d_height = 2 ;dh=2;}
//			else
				q.d_height = dh ;
			
			while(r.nextDouble()<0.5) {
				
				rh++ ;
				
			}
			
			q.r_height = rh ;
			
			int d = (int)Math.pow(2, num_bands) - (int)Math.pow(2, num_bands-dh);
			if(rh+d > H)
				q.height = H ;
			else {
				
				if(rh+d == 0) {
					q.height = 1 ;
				}else {
					q.height = rh+d ;
				}
			}
			
			for(int i=1 ; i<q.height ; i++) {
				
				while(p.up == null) {
					p = p.left ;
				}
				p = p.up ;
				
				Node e = new Node(q.key , q.d_height , q.r_height , q.height , q.counters) ;
				
				e.left = p ;
				e.right = p.right ;
				e.down = q ;
				p.right.left = e ;
				p.right = e ;
				q.up = e ;
				q = e;
				
			}
		}
		
	}
	
	public void update_counters() {
		
		Node q = head ;
		//go to the right corner
		while(q.key != Integer.MAX_VALUE) {
			q = q.right ;
		}
		//go to the bottomost right
		while(q.down != null) {
			q = q.down ;
		}
		//go to the left and update counters of the elements
		int succ = q.key ;
		q = q.left ;
		while(q != null) {
			
			int imright = q.key ;
			int k=0; ;
			q.counters[num_bands - q.d_height] = q.counters[num_bands - q.d_height] + 1 ;
			while(q.up!=null) {
				q=q.up;
			}
			for(int i=1 ; i<q.height ; i++) {
				
				if(q.right.height <= q.height && q.right.key != imright) {
					
					for(int j = 1 ; j<=num_bands ; j++) {
						q.counters[j] = q.counters[j] + q.right.counters[j] ;
						imright = q.right.key ;
					}
					
				}
				//change it to right pointer
				if(imright == succ) {
					k++;
					break;
				}
				
				q = q.down ;
			}
			
			if(k==0) {
				
				if(q.right.height <= q.height && q.right.key != imright) {
					
					for(int j = 1 ; j<=num_bands ; j++) {
						q.counters[j] = q.counters[j] + q.right.counters[j] ;
					}
					
				}
			}
			
			while(q.down != null) {
				q = q.down ;
			}
			succ = q.key ;
			q = q.left ;
			
		}
		
		
//		Node q = head ;
//		//go to the right corner
//		while(q.key != Integer.MAX_VALUE) {
//			q = q.right ;
//		}
//		//go to the bottomost right
//		while(q.down != null) {
//			q = q.down ;
//		}
//		//go to the left and update counters of the elements
//		q = q.left ;
//		while(q != null) {
//			
//			int imright = q.key ;
//			int i ;
//			q.counters[num_bands - q.d_height] = q.counters[num_bands - q.d_height] + 1 ;
//			for(i=1 ; i<q.height ; i++) {
//				
//				if(q.right.key == Integer.MAX_VALUE) {
//					break;
//				}else if(q.right.height <= q.height) {
//					if(q.right.key != imright) {
//						for(int j = 1 ; j<=num_bands ; j++) {
//							q.counters[j] = q.counters[j] + q.right.counters[j] ;
//							imright = q.right.key ;
//						}
//					}
//				}else {
//					break;
//				}
//				
////				if(q.right.key == q.key+1) {
////					break;
////				}
//				
//				q = q.up ;
//			}
//			
//			if(i == q.height && q.right.height<=q.height && q.right.key != imright ) {
//				for(int j=1 ; j<=num_bands ; j++) {
//					q.counters[j] = q.counters[j] + q.right.counters[j] ;
//				}
//			}
//			
//			while(q.down != null) {
//				q = q.down ;
//			}
//			
//			q = q.left ;
//			
//		}
//		
		
		
//		Node p = head ;
//		Node q = head ;
//		Node r = head ;
//		
//		while(r.down!=null) {
//			r = r.down ;
//			p = p.down ;
//			q = q.down ;
//		}
//		
//		q = q.right ;
//		p = p.right ;
//		r.counters[1] = r.counters[1] + 1 ;
//		
//		while(q.key != Integer.MAX_VALUE) {
//			p = q ;
//			//q.counters[num_bands-q.d_height] = q.counters[num_bands-q.d_height] + 1 ;
//			while(p.key != Integer.MAX_VALUE && q.height >= p.height) {
//				q.counters[num_bands-p.d_height] = q.counters[num_bands-p.d_height] + 1 ;
//				p = p.right ;
//			}
//			r.counters[num_bands-q.d_height] = r.counters[num_bands-q.d_height] + 1 ;
////			if(q.height == p.height) {
////				q.counters[num_bands-p.d_height] = q.counters[num_bands-p.d_height] + 1 ;
////			}
//			q = q.right ;
//		}
//		
//		
		
		
//		Node p = head ;
//		Node q = head ;
//		Node r = head ;
//		Node s = head ;
//		
//		while(p.key != Integer.MAX_VALUE) {
//			if(p.d_height == num_bands-1) {
//				q.counters[1] = q.counters[1] + 1 ;
//			}
//			p = p.right ;
//			s = s.right ;
//		}
//		
//		p = head ;
//		p = p.right ;
//		int x = q.counters[1] ;
//		
//		while(p.key != Integer.MAX_VALUE) {
//			if(p.d_height == num_bands-1) {
//				p.counters[1] = --x ;
//			}
//			p = p.right ;
//		}
//		s = p ;
//		
//		for(int band = num_bands-2 ; band >= 0 ; band--) {
//			int w = (int)Math.pow(2,num_bands-band-1) ;
//			for(int i = 0 ; i < w ; i++) {
//				System.out.println("abcd");
//				r = r.down ;
//				s = s.down ;
//			}
//			
//			q = r ;
//			p = r ;
//			
//			while(q.key != Integer.MAX_VALUE) {
//			while(q.height >= p.height && p.key != Integer.MAX_VALUE) {
//				if(p.d_height == band) {
//					q.counters[num_bands-band] = q.counters[num_bands-band] + 1;
//				}
//				p = p.right ;
//			}
//			q = q.right ;
//			}
//			s = q ;
//		}
//		System.out.println("start");
//		print();
//		System.out.println("done");
	}
	
	public void promote(Node q) {
		
		int key = q.key ;
		int prevht = q.height ;
		int prevdht = q.d_height ;
//		//updating heights
//		q.d_height = num_bands-1;
//		q.height = H ;
//		Node n = q ; 
//		q = q.left ;
//		hor++ ;
//		while(q != null) {
//			while(q.up != null) {
//				q = q.up ;
//				ver++ ;
//			}
//			//q.counters[num_bands-prevdht] = q.counters[num_bands-prevdht] - 1 ;
//			if(q.height<H) {
//				for(int i=num_bands-prevdht ; i>0 ; i--) {
//					//System.out.println("entered");
//					q.counters[i] = q.counters[i] - n.counters[i] ;
//				}
//			}else {
//				q.counters[num_bands-prevdht] = q.counters[num_bands-prevdht] - 1 ;
//			}
//			q = q.left ;
//		}
		
		int[] delcounters = new int[num_bands+1] ;
		Node n = q ;
		q = q.left ;
		hor++;
		while(q!=null) {
			if(q.height<H) {
				for(int i=1;i<=num_bands;i++) {
					q.counters[i] = q.counters[i] - n.counters[i] ;
				}
				int rkey = n.key ;
				for(int i=1 ; i <= num_bands ; i++) {
					q.counters[i] = q.counters[i] - delcounters[i] ;
				}
				while(q.up != null) {
					q = q.up ;
					if(q.height >= q.right.d_height && q.right.key != rkey) {
						for(int i=1 ; i <= num_bands ; i++) {
							q.counters[i] = q.counters[i] - q.right.counters[i] ;
							delcounters[i] = delcounters[i] + q.right.counters[i] ;
						}
						rkey = q.right.key ;
					}
				}
			}else {
				q.counters[num_bands-prevdht] = q.counters[num_bands-prevdht] - 1 ;
			}
			q = q.left ;
		}
		
		q = search_initialization(key) ;
//		//updating heights
//		q.d_height = num_bands-1;
//		q.height = H ;
//		q.counters[num_bands-prevdht] = q.counters[num_bands-prevdht] - 1 ;
//		q.counters[num_bands-q.d_height] = q.counters[num_bands-q.d_height] + 1 ;
		int r = key ;
		
		int cou=0;
		while(q.down !=null) {
			q = q.down;
			ver++ ;
			cou++ ;
		}
		q.height = H ;
		q.d_height = num_bands-1 ;
		q.counters[num_bands-prevdht] = q.counters[num_bands-prevdht] - 1 ;
		q.counters[1] = q.counters[1] + 1 ;
		for(int count =0 ; count<cou ; count++) {
			q = q.up ;
			ver++ ;
			q.height = H ;
			q.d_height = num_bands-1 ;
		}
		
	//Building pointers
		Node p = q ;
		for(int i=prevht ; i<H ; i++) {
			
			while(p.up == null) {
				p = p.left ;
				hor++ ;
			}
			p = p.up ;
			ver++ ;
			
			Node e = new Node(q.key , q.d_height , q.r_height , q.height , q.counters) ;
			//should i count a ver here ?
			e.left = p ;
			e.right = p.right ;
			e.down = q ;
			p.right.left = e ;
			p.right = e ;
			q.up = e ;
			q = e;
			
			Node w = q.right ;
//			System.out.println(r+" sfs "+w.key+" "+w.height+" "+q.height);
			if(w.height <= q.height && w.key != r) {
				for(int j=1 ; j<=num_bands ; j++) {
					q.counters[j] = q.counters[j] + w.counters[j] ;
				}
				r = w.key ;
//				System.out.println(r+" inside");
			}
			
		}
		
		q = q.left ;
		hor++ ;
		while(q != null) {
			q.counters[1] = q.counters[1] + 1 ;
			q = q.left ;
			hor++ ;
		}
				
//		q = q.left ;
//		while(q.down != null) {
//			q = q.down ;
//		}
//		q = q.right ;
//		Node w ;
//		while(q.key != key) {
//			//System.out.println("cbsjdhf");
//			w = q ;
//			Arrays.fill(q.counters, 0);
//			while(w.key!=key && q.height >= w.height) {
//				q.counters[num_bands-w.d_height] = q.counters[num_bands-w.d_height] + 1 ; 
//				//System.out.println(q.key + "   "+(num_bands-w.d_height)+" sef");
//				w = w.right ;
//			}
//			q = q.right ;
//		}
		
//		while(q.key != key) {
//			q = q.right ;
//		}
//		
//		
//				
//		Node s = q ;
//		Arrays.fill(q.counters,0);
//		while(s.key != Integer.MAX_VALUE && q.height >= s.height) {
//			q.counters[num_bands-s.d_height] = q.counters[num_bands-s.d_height] + 1;
//			s = s.right ;
//		}
//		
//		while(q.up!=null) {
//			q = q.up ;
//		}
//		w = q ;
//		q = q.left ;
//		while(q.left != null) {
////			while(q.up!=null) {
////				q = q.up ;
////			}
//			q.counters[num_bands-w.d_height] = q.counters[num_bands-w.d_height] + 1 ;
//			q = q.left ;
//		}
//		q.counters[num_bands-w.d_height] = q.counters[num_bands-w.d_height] + 1 ;
//		
//		while(q.key!=key) {
//			q = q.right ;
//		}
		
//		for(int i = H ; i>prevht ; i--) {
//			q = q.down ;
//		}
//		q = q.left ;
//		while(q.left != null) {
//			while(q.up!=null) {
//				q = q.up ;
//			}
//			q.counters[num_bands-prevdht] = q.counters[num_bands-prevdht] - 1 ;
//			q = q.left ;
//		}
//		q.counters[num_bands-prevdht] = q.counters[num_bands-prevdht] - 1 ;
		
		//counters
//		int h = H ;
//		int counting = 0 ;
//		int x ;
//		
//		for(x = num_bands-1 ; x >= p.d_height+1 ; x--) {
//			
//			int w = (int)Math.pow(2 , num_bands) - (int)Math.pow(2, num_bands-x) +1 ;
//			
////			for(count = count ; count < w ; count++) {
////				q = q.down ;
////			}
//			
//			if(h==w)
//				counting++ ;
//			
//			while(h>w) {
//				p = p.down ;
//				h-- ;
//				counting++ ;
//			}
//			if(counting > 0) { 
//			
//			Node present = p.right ;
//			int temp = 0 ;
//			
//			while(present.key != Integer.MAX_VALUE) {
//				
//				if(present.height == H) {
//					
//					if( (present.d_height == x && x==num_bands-1 ) || (x == num_bands-1 && present.d_height != x) ) {
//						p.counters[num_bands-x] = present.counters[num_bands-x] + temp + 1 ;
//					}else if( (present.d_height == x && q.d_height != x) || (q.d_height != x && present.d_height != x) ){
//						p.counters[num_bands-x] = present.counters[num_bands-x] + temp ;
//					}
//					
//					break; 
//					
//				}else if(present.height < H && present.d_height == x) {
//					
//					temp++ ;
//					
//				}
//				present = present.right ;
//			}
//			
//			if(present.height != H) {
//				if(num_bands-1 == x)
//					p.counters[num_bands-x] = temp + 1 ;
//				else
//					p.counters[num_bands-x] = temp ;
//			}
//			}
//		}
//		
//		int w = (int)Math.pow(2 , num_bands) - (int)Math.pow(2, num_bands-x) +1 ;
//		
////		for(count = count ; count < w ; count++) {
////			q = q.down ;
////		}
//		
////		if(h==w)
////			counting++ ;
//		
//		while(h>w) {
//			p = p.down ;
//			h-- ;
////			counting++ ;
//		}
//		
//		Node s = p ;
//		
//		while(s!=null) {
//			if(s.height>=p.height) {
//				s.counters[num_bands-p.d_height] = s.counters[num_bands-p.d_height] - 1; 
//			}
//			s = s.left ;
//		}
//		
//		Node top = head ;
//		while(top.key != p.key) {
//			top.counters[1] = top.counters[1] + 1 ;
//			top = top.right ;
//		}
		
		
	}
	
	public Node RandomSelect(int band) {
		
		Node s = head ;
		int level = H ;
		int preband = 1 ;
		
		while(preband <= band) {
			
			while(level-1 > (int)(Math.pow(2, num_bands))-(int)(Math.pow(2, preband)) ) {
				if(s.right.key == Integer.MAX_VALUE) {
					s = s.down ;
					ver++ ;
					level-- ;
				}else if( s.right.height > level ) {
					s = s.down ;
					ver++ ;
					level -- ;
				}else {
					if( r.nextDouble() <= (s.right.counters[band]*1.0/s.counters[band])) {
						s = s.right ; //need not check for max_value as its counters will be 0 and anything non negative cannot be less than zero
						hor++ ;
					}else {
						s = s.down ;
						ver++;
						level -- ;
					}
				}
			}
			while(s.right.height == level) {
//				System.out.println("entered");
//				if(s.counters[preband] == 0) {
//					break;
//				}else 
				if(r.nextDouble() <= (s.right.counters[band]*1.0/s.counters[band])) {
					s = s.right ;
					hor++ ;
				}else {
					break;
				}
			}
			preband++;
		}
		while(s.d_height != num_bands-band && s.right.right!=null) {
			s=s.right;
		}
		while(s.d_height != num_bands-band && s.left.left!=null) {
			s=s.left;
		}
		if(s.key == Integer.MIN_VALUE) {
			s = s.right ;
			hor++ ;
			while(s.d_height != num_bands-band) {
				s = s.right;
				hor++ ;
			}
		}
//		if(s.key == Integer.MAX_VALUE) {
//			s=s.left;
//			while(s.d_height != num_bands-band) {
//				s=s.left;
//			}
//		}
		if(s.d_height != num_bands-band) {
			temporary++;
		}
		return s ;
	}
		
	public void demote(int tillband) { 
		for(int i = 1 ; i < tillband ; i++) {
			
			Node p = RandomSelect(i) ;
			//System.out.println(p.key+ " "+p.d_height);
			//System.out.println(num_bands+" "+i);
//			if(p.key == Integer.MIN_VALUE) {
//				p = p.right ;
//				hor++ ;
//				while(p.d_height != num_bands-i) {
//					p = p.right;
//					hor++ ;
//				}
//				print();
//				System.out.println(p.key+" post");
//			}
			
//			if(p.d_height != num_bands-i) {
//				print();
//				System.out.println(p.key);
//				System.out.println(p.d_height+" "+(num_bands-i));
//				System.out.println("enter");
////				return false;
//			}
			
//			System.out.println();
//			for(int place=1 ; place<=num_bands ; place++) {
//				System.out.print(p.counters[place]);
//			}
//			System.out.println();
//			if(num_bands-p.d_height <= i) {
				
				int newht ;
				int dh = (int)Math.pow(2, num_bands) - (int)Math.pow(2, i+1);
				if(p.r_height+dh > H)
					newht = H ;
				else {
					
					if(p.r_height+dh == 0) {
						newht = 1 ;
					}else {
						newht = p.r_height+dh ;
					}
				}
				
				int k = p.key ;
//				if(k==Integer.MAX_VALUE) {
//					print();
//					System.out.println((num_bands-i)+" hkj");
//					while(p.left!=null) {
//						System.out.print(p.d_height);
//						p=p.left;
//					}
//					System.out.println();
////					System.out.println(p.left.key+" abc "+p.left.d_height+" "+(num_bands-i));
//				}
//				if(k==Integer.MIN_VALUE) {
//					print();
//					System.out.println((num_bands-i)+" ldkf");
//					while(p.right!=null) {
//						System.out.print(p.d_height+" "+p.key);
//						p=p.right;
//					}
//					System.out.println();
////					System.out.println(p.right.key+" bcd "+p.right.d_height+" "+(num_bands-i));
//				}
				Node q = head ;
				
				while(true) {
					
					while(q.right.key<=k) {
//						System.out.println(q.counters[num_bands-p.d_height]);
						q.counters[num_bands-p.d_height] = q.counters[num_bands-p.d_height] - 1 ;
//						System.out.println(q.counters[num_bands-p.d_height]);
						q.counters[i+1] = q.counters[i+1] + 1 ;
//						System.out.println(p.d_height+" "+i);
//						for(int place=1 ; place<=num_bands ; place++) {
//							System.out.print(q.counters[place]);
//						}
//						System.out.println();
						q = q.right ;
						hor++;
					}
					
					if(q.key!=k) {
						q = q.down ;
						ver++;
					}else {
						q.counters[num_bands-p.d_height] = q.counters[num_bands-p.d_height] - 1 ;
						q.counters[i+1] = q.counters[i+1] + 1 ;
						break;
					}
				}
				if(newht != H){
				Node n ;
				int temprightkey = q.right.key ;
				if(q.right.height == q.height) {
					for(int place = 1 ; place <= num_bands ; place++) {
//						System.out.println("entered");
//						System.out.println(q.counters[place]);
						q.counters[place] = q.counters[place] - q.right.counters[place] ;
//						System.out.println(q.counters[place]);
					}
				}
				q.left.right = q.right ;
				q.right.left = q.left ;
				q = q.down ;
				ver++;
//				System.out.println(q.height+" "+newht + " newheight");
				for(int x = q.height-1 ; x > newht ; x--) {
//					System.out.println("thrice");
					n = q ;
					
					q = q.left ;
					hor++;
					
					while(q.height == x) {
//						System.out.println("zero");
						for(int place = 1 ; place <= num_bands ; place++) {
							q.counters[place] = q.counters[place] + n.counters[place] ;
						}
						q = q.left ;
						hor++;
					}
					
					while(q.key != n.key) {
//						System.out.println("once");
						q = q.right ;
						hor++;
					}
					
					if(q.right.key != temprightkey) {
//						System.out.println("zero1");
						for(int place = 1 ; place <= num_bands ; place++) {
							q.counters[place] = q.counters[place] - q.right.counters[place] ;
						}
						temprightkey = q.right.key ;
					}
					
					q.left.right = q.right ;
					q.right.left = q.left ;
					q = q.down ;
					ver++;
					
				}
				
				q.up = null ;
				
			}
				while(q != null) {
					q.height = newht ;
					q.d_height = num_bands - (i+1) ;
					q = q.down ;
					ver++;
				}
				
//			}
			
//			System.out.println("mid");
////			print();
			
		}
//		return true;
	}
		
	public void search(int k) {
		
		Node p = search_initialization(k);
		
		if(p.key != k) {	
			//insert_initialization(k); // we can avoid the search initialization here by simply pasting the remaining code of insert_initialization
			
			int level = 1 ;
			
			Node q = new Node(k);
			
			q.left = p ;
			q.right = p.right ;
			p.right.left = q ;
			p.right = q ;
			
			int dh = 0 , rh = 1 ;
			
			while(r.nextDouble()<0.5) {
				
				if(dh >= num_bands-1)
					break ;
				dh++ ;
				
			}
			
			q.d_height = dh ;
			
			while(r.nextDouble()<0.5) {
				
				rh++ ;
				
			}
			
			q.r_height = rh ;
			
			int d = (int)Math.pow(2, num_bands) - (int)Math.pow(2, num_bands-dh);
			if(rh+d > H)
				q.height = H ;
			else {
				
				if(rh+d == 0) {
					q.height = 1 ;
				}else {
					q.height = rh+d ;
				}
			}
			
			
			int z = q.key ;
			
			if(level != q.height) {
				
				while(q.left.height == level) {
					
					q = q.left ;
					Arrays.fill(q.counters, 0);
					if(q.right.key != z) {
						q.counters[num_bands] = q.right.counters[num_bands] + 1 ;
					}else {
						q.counters[num_bands] = 1 ;
					}
					
				}
				
				while(q.key != k) {
					q = q.right ;
				}
			
			}
			
			q.counters[num_bands - q.d_height] = q.counters[num_bands - q.d_height] + 1 ;
			
			int r ;
			Node w = q.right ;
//			
			if(q.height >= w.height) {
				
				for(int j =1 ; j <= num_bands ; j++) {
					q.counters[j] = q.counters[j] + w.counters[j] ;
				}
				r = w.key ;
			
			}else {
				r = q.key ;
			}
			
			Node x = q ;
			for(int i = 1 ; i < q.height ; i++) {
				
				while(x.up == null) {
					x = x.left ;
				}
				x = x.up ;
				
				Node e = new Node(q.key,q.d_height,q.r_height,q.height,q.counters);
				
				e.left = x ;
				e.right = x.right ;
				e.down = q ;
				x.right.left = e ;
				x.right = e ;
				q.up = e ;
				q = e ;
				
				w = q.right ;
				if(w.height <= q.height && w.key != r) {
					for(int j=1 ; j <= num_bands ; j++) {
						q.counters[j] = q.counters[j] + w.counters[j] ;
					}
					r = w.key ;
				}
				
				if(level != q.height) {
					while(q.left.height == level ) {
						q = q.left ;
						Arrays.fill(q.counters,0);
						while(true) {
							while(q.down!=null && q.right.key == z) {
								q = q.down ;
							}
							if(q.right.key != z) {
								for(int j = 1 ; j <= num_bands ; j++) {
									q.counters[j] = q.counters[j] + q.right.counters[j] ;
								}
								z = q.right.key ;
							}else {
								break;
							}
						}
						while(q.up!=null) {
							q = q.up ;
						}
					}
					while(q.key != k) {
						q = q.right ;
					}
				}
				
			}
			
			int dht = q.d_height ;
			q = q.left ;
			while(q!=null) {
				if(q.left!=null) {
					while(q.up!=null) {
						q = q.up ;
					}
				}
				q.counters[num_bands-dht] = q.counters[num_bands-dht] + 1 ;
				q = q.left ;
			}
			
//			}else if(q.height == w.height) {
//			
//				for(int j=1 ; j <= num_bands ; j++) {
//					q.counters[j] = q.counters[j] + w.counters[j] ;
//				}
//			
//			}
			
			
			
//			//counters for q 			
//			Node r = q ;
//			while(r.key != Integer.MAX_VALUE && q.height >= r.height) {
//				q.counters[num_bands-r.d_height] = q.counters[num_bands-r.d_height] + 1 ;
//				r = r.right ;
//			}
//			
//			//create up pointers
//			for(int i=1 ; i<q.height ; i++) {
//				
//				while(p.up == null) {
//					p = p.left ;
//				}
//				p = p.up ;
//				
//				Node e = new Node(q.key , q.d_height , q.r_height , q.height , q.counters) ;
//				
//				e.left = p ;
//				e.right = p.right ;
//				e.down = q ;
//				p.right.left = e ;
//				p.right = e ;
//				q.up = e ;
//				q = e;
//				
//			}
//			
//			//update pointers to the left
//			q = q.left ;
//			while(q.down != null) {
//				q = q.down ;
//			}
//			q = q.right ;
//			Node w ;
//			while(q.key != k) {
//				w = q ;
//				Arrays.fill(q.counters, 0);
//				while(w.key!=k && q.height >= w.height) {
//					q.counters[num_bands-w.d_height] = q.counters[num_bands-w.d_height] + 1 ; 
//					w = w.right ;
//				}
//				q = q.right ;
//			}
//			
//			while(q.up!=null) {
//				q = q.up ;
//			}
//			w = q ;
//			q = q.left ;
//			while(q.left != null) {
//				while(q.up!=null) {
//					q = q.up ;
//				}
//				q.counters[num_bands-w.d_height] = q.counters[num_bands-w.d_height] + 1 ;
//				q = q.left ;
//			}
//			q.counters[num_bands-w.d_height] = q.counters[num_bands-w.d_height] + 1 ;
			
		}else {
			if(p.d_height<num_bands-1) {
				int x = p.d_height ;
				promote(p);
//				System.out.println("promote done");
//				print() ;
//				System.out.println("demote start");
				demote(num_bands - x);
			}
		}
	}
	
	public fs_ret_val FingerSearch(Node u , int vkey) {
		
		int dis = 0;
		
		fs_ret_val ret = new fs_ret_val();
		
		if(u.key < vkey) {
			while(u.right.key < vkey) {
				u = u.right ;
				dis++;
				while(u.up != null) {
					u = u.up ;
					dis++;
				}
			}
			if(u.right.key == vkey) {
				u = u.right ;
				dis++ ;
				ret.dis = dis;
				ret.v = u ;
				return ret;
			}
			while(true) {
				while(u.right.key  > vkey) {
					u = u.down ;
					dis++ ;
				}
				u = u.right ;
				dis++ ;
				if(u.key == vkey) {
					break;
				}
			}
		}else {// confirm whether the query can be (u,u)
			while(u.left.key > vkey) {
				u = u.left ;
				dis++ ;
				while(u.up != null) {
					u = u.up ;
					dis++ ;
				}
			}
			if(u.left.key == vkey) {
				u = u.left ;
				dis++ ;
				ret.dis = dis;
				ret.v = u ;
				return ret;
			}
			while(true) {
				while(u.left.key < vkey) {
					u = u.down ;
					dis++ ;
				}
				u = u.left ;
				dis++ ;
				if(u.key == vkey) {
					break;
				}
			}
		}
		ret.dis = dis;
		ret.v = u ;
		return ret ;
	}

	public Node Finger_Search(Node u , int vkey) {
		
		if(u.key < vkey) {
			while(u.right.key < vkey) {
				u = u.right ;
				hor++ ;
				while(u.up != null) {
					u = u.up ;
					ver++ ;
				}
			}
			if(u.right.key == vkey) {
				u = u.right ;
				hor++ ;
//				System.out.println("distance travelled = "+dis);
				return u ;
			}
			while(true) {
				while(u.right.key  > vkey) {
					u = u.down ;
					ver++ ;
				}
				u = u.right ;
				hor++ ;
				if(u.key == vkey) {
					break;
				}
			}
		}else {// confirm whether the query can be (u,u)
			while(u.left.key > vkey) {
				u = u.left ;
				hor++ ;
				while(u.up != null) {
					u = u.up ;
					ver++ ;
				}
			}
			if(u.left.key == vkey) {
				u = u.left ;
				hor++ ;
//				System.out.println("distance travelled = "+dis);
				return u ;
			}
			while(true) {
				while(u.left.key < vkey) {
					u = u.down ;
					ver++ ;
				}
				u = u.left ;
				hor++ ;
				if(u.key == vkey) {
					break;
				}
			}
		}
		
//		System.out.println("distance travelled = "+dis);
		return u ;
	}

	public void searchsasl2(int ukey , int vkey){
		
		hor = 0 ;
		ver = 0 ;
		
		Node u = search_initialization(ukey);
		
		long sasl_st = System.nanoTime() ;
		Node v = Finger_Search(u, vkey) ;
		long sasl_rt = System.nanoTime();
		
		int temp = u.d_height ;
		promote(u);
		demote(num_bands-temp);
//		if(!demote(num_bands-temp)) {
//			System.out.println("entered first");
//			return false;
//		}

		int temp2 = v.d_height ;
		promote(v);
		demote(num_bands-temp2);
//		if(!demote(num_bands-temp2)) {
//			System.out.println("entered second");
//			return false;
//		}
		
		long sasl_et = System.nanoTime() ;
		
//		System.out.println("Time taken to complete the process = "+ (sasl_et - sasl_st));
//		System.out.println("Time taken to complete routing process = "+ (sasl_rt - sasl_st));
//		System.out.println("Time taken to complete adjustment process = "+ (sasl_et - sasl_rt));
		rc[index/2] = rc[index/2] + sasl_rt - sasl_st ;
		ac[index/2] = ac[index/2] + sasl_et - sasl_rt ;
//		System.out.println("No. of horizontal moves = "+hor);
//		System.out.println("No. of vertical moves = "+ver);
//		return true;
	}
	
	public void print() {
		
		Node q = head ;
		while(q.down!=null) {
			q = q.down ;
		}
		q = q.right ;
		int[] x = new int[num_elements];
		for(int i=0; i< num_elements ; i++) {
			x[i] = q.key ;
			q=q.right;
		}
		
		Node p = head ;
		
		while(true) {
			int temp = 0;
			while(p.right.right!=null) {
				while(true) {
					if(p.right.key != x[temp]) {
						if(p.right.key<10)
							System.out.print("  ");
						else
							System.out.print("   ");
						temp++;
					}else {
						if(temp>0 && p.key == x[temp-1])
							System.out.print(p.right.key);
						else if(temp>0 && p.key != x[temp-1])
							if(p.right.key<10)
								System.out.print(" "+p.right.key);
							else
								System.out.print("  "+p.right.key);
						else
							System.out.print(p.right.key);
						break;
					}
					
				}
				
				p = p.right ;
				
//				int temp2 = temp;
//				while(p.right.key != x[temp]) {
//					temp++ ;
//				}
//				for(int space = temp2 ; space<temp ; space++) {
//					System.out.print("  ");
//				}
//				System.out.print(p.right.key+" ") ;
//				p = p.right ;
				
			}
			
			while(p.left!=null) {
				
				p = p.left ;
				
			}
			
			if(p.down!=null) {
				
				p = p.down ;
				System.out.println() ;
				
			}else {
				
				break ;
				
			}
			
			
		}
		
		System.out.println();
		System.out.println(H+" height");
		p = head ;
		
		while(p.down != null) {
			p = p.down ;
		}
		
		while(p.right.right!=null) {
//			System.out.print(p.right.key+" "+p.right.d_height+" "+p.right.r_height+" "+p.right.height+" "+" "+p.right.right.key+" "+p.right.left.key+" ");
			System.out.print(p.right.key+" "+p.right.d_height+" "+p.right.r_height+" "+p.right.height+" ");
			for(int i = 1 ; i < p.counters.length ; i++) {
				System.out.print(p.right.counters[i]);
				if(i!=p.counters.length-1) {
					System.out.print("_");
				}
			}
			System.out.println();
			p = p.right ;
		}
		
		System.out.println("MIN_VALUE COUNTERS:");
		p = head ;
//		while(p.right != null) {
//			p= p.right ;
//		}
		for(int i=1 ; i<p.counters.length ; i++) {
			System.out.println(p.counters[i]);
		}
	}

}