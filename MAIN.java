import java.io.BufferedReader ;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class MAIN {
	public static int H , num_bands , num_elements , index;
	public static FileWriter writer1 , writer2 ;
	public static long[] rc,ac ;
	public static int temporary=0;
	public static void main(String[] args) throws IOException{
		
		rc = new long[5000];
		ac = new long[5000];
		int number;
		for(number=0 ; number<100 ; number++) {
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\burad\\Desktop\\Schmid\\nw_24\\"+Integer.toString(number+1)+".txt"));
//			BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\burad\\Desktop\\Schmid\\Testcase.txt"));
			String add = reader.readLine();
			String[] tokens = add.split(" ");
			
			num_elements = Integer.parseInt(tokens[0]);
			int sum = 0 ;
			
			for(num_bands = 0 ; num_elements>sum ; num_bands++) {
				sum = sum + (int)Math.pow(2,(int)Math.pow(2, num_bands)) ;
			}
			
			H = (int)Math.pow(2, num_bands)-1 ;
//			System.out.println(num_bands+" "+H);
			int i = 1 ;
			
			SkipList SL =  new SkipList();
			
			for(int w=0 ; w<num_bands ; w++) {
				for(int v=1 ; v<=(int)Math.pow(2,(int)Math.pow(2,w)) ; v++) {
					SL.insert_initialization(Integer.parseInt(tokens[i]),num_bands-w-1);
					i++ ;
					if(i>num_elements) {
						break;
					}
				}
				if(i>num_elements) {
					break;
				}
			}
			
			SL.update_counters();
			
//			SL.print();
//			System.out.println(num_bands+" "+H);
			
			BufferedReader read = new BufferedReader(new FileReader("C:\\Users\\burad\\Desktop\\Schmid\\Sequences\\24_24.txt"));
			String seq = read.readLine();
			String tuples[] = seq.split(" ");
			index = 0 ;
			while(index<tuples.length) {
				int temp1 = Integer.parseInt(tuples[index]);
				index++;
				int temp2 = Integer.parseInt(tuples[index]);
				index++;
				System.out.println(temp1+" "+temp2+" "+number);
				SL.print();
				System.out.println("temporary = "+ temporary);
				SL.searchsasl2(temp1,temp2);
			}
			
		}
		
		writer1 = new FileWriter("C:\\Users\\burad\\Desktop\\Schmid\\R_cost.txt");
		writer2 = new FileWriter("C:\\Users\\burad\\Desktop\\Schmid\\A_cost.txt");
		
		for(int num=1;num<rc.length;num++) {
			rc[num] = rc[num]/(number-1);
			writer1.write(Long.toString(rc[num])+"\n");
			ac[num] = ac[num]/(number-1);
			writer2.write(Long.toString(ac[num])+"\n");
		}
		writer1.close();
		writer2.close();
		System.out.println("done");
		
		
		
//		SkipList SL =  new SkipList();
//		SL.print();
//		BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\burad\\Desktop\\Schmid\\Testcase.txt"));
//		String add = reader.readLine();
//		//int i=0;
//		String[] tokens = add.split(" ");
//		
//		num_elements = Integer.parseInt(tokens[0]);
//		int sum = 0 ;
//		
//		for(num_bands = 0 ; num_elements>sum ; num_bands++) {
//			sum = sum + (int)Math.pow(2,(int)Math.pow(2, num_bands)) ;
//		}
//		
//		H = (int)Math.pow(2, num_bands)-1 ;
//		System.out.println(num_bands+" "+H);
//		int i = 1 ;
//		
//		SkipList SL =  new SkipList();
//		
//		for(int w=0 ; w<num_bands ; w++) {
//			for(int v=1 ; v<=(int)Math.pow(2,(int)Math.pow(2,w)) ; v++) {
//				//System.out.println(tokens[i]);
//				SL.insert_initialization(Integer.parseInt(tokens[i]),num_bands-w-1);
//				i++ ;
//				if(i>num_elements) {
//					break;
//				}
//			}
//			if(i>num_elements) {
//				break;
//			}
//		}
//		
////		for(int i=1 ; i<tokens.length ; i++) {
////			SL.insert_initialization(Integer.parseInt(tokens[i]));
////			//System.out.println("y");
////		}
//		
////		System.out.println("add done");
////		SL.print();
//		
//		SL.update_counters();
////		System.out.println();
////		System.out.println("counters done");
////		SL.print();
//		
//		Scanner s = new Scanner(System.in) ;
//		System.out.println("Please enter the number to be searched");
//		int x = s.nextInt();
//		int y = s.nextInt();
//		SL.searchsasl2(x, y);
////		SL.search(x) ;
//		System.out.println("malli all done");
//		SL.print() ;
//		s.close();
//		
//		BufferedReader read = new BufferedReader(new FileReader("C:\\Users\\burad\\Desktop\\Schmid\\Sequences.txt"));
////		writer1 = new FileWriter("C:\\Users\\burad\\Desktop\\Schmid\\R_cost.txt");
////		writer2 = new FileWriter("C:\\Users\\burad\\Desktop\\Schmid\\A_cost.txt");
//		String seq = read.readLine();
//		String tuples[] = seq.split(" ");
//		int index = 0 ;
//		while(index<tuples.length) {
//			int temp1 = Integer.parseInt(tuples[index]);
//			index++;
//			int temp2 = Integer.parseInt(tuples[index]);
//			index++;
//			SL.searchsasl2(temp1,temp2);
//		}
//		writer1.close();
//		writer2.close();
		
		
	}
	
}
