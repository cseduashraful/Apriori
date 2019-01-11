package dataMining;

import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 */
	public static int max = 999999;
	public static int lim = 80;
	public static int suppportThreshold = 3000;
	public static int freq[] = new int[max];
	public static int totalTransaction;
	static ArrayList<Large>L = new ArrayList<Large>();
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename =  "chess.dat";
		functions.calculateFrequencyN(filename);
		functions.generateL1(freq,L);
		functions.printFrequentItemset(L,1);
		functions.recursiveProcess(L,1,filename);

	}

}
