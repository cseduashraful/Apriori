package dataMining;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.parsers.FactoryConfigurationError;

public class functions {
	public static void calculateFrequencyN(String filename) {
		// TODO Auto-generated method stub
		try{
			FileInputStream fstream = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
			  //System.out.println (strLine);
			  processString(strLine);
			  Main.totalTransaction++;
			}
			br.close();
		}catch(Exception e){
			
		}
	}
	static void processString(String str){
		StringTokenizer st = new StringTokenizer(str, " ");
        while (st.hasMoreTokens()){
        	 int item = Integer.parseInt(st.nextToken());
        	 Main.freq[item]++;
        	 
        }    	 
	}
	public static void generateL1(int[] freq, ArrayList<Large> L) {
		// TODO Auto-generated method stub
		Large L1 = new Large(); 
		for(int i = 0;i<Main.lim;i++){
			if(freq[i]>Main.suppportThreshold){
				Itemset it = new Itemset();
				it.set.add(i);
				it.freq = freq[i];
				L1.lrg.add(it);
			}
		}
		L.add(L1);
	}
	public static void printFrequentItemset(ArrayList<Large> L, int pos) {
		// TODO Auto-generated method stub
		pos--;
		System.out.println("\n\nFrequesnt itemset of "+pos+"length");
		Large tmpL = L.get(pos);
		for(int i = 0;i<tmpL.lrg.size();i++){
			Itemset it = tmpL.lrg.get(i);
			printItemset(it);
		}
	}
	public static void printItemset(Itemset it) {
		// TODO Auto-generated method stub
		System.out.print("{");
		for(int i=0;i<it.set.size();i++){
			System.out.print(it.set.get(i)+",");
		}
		System.out.println("}: "+it.freq);
	}
	public static void recursiveProcess(ArrayList<Large> L, int length, String filename) {
		// TODO Auto-generated method stub
		Large candidate = generateCandidate(L,length);// can of length+1
		calculateCandidateFrequency(candidate,filename);
		addToFrequentItemset(L,candidate);
		printFrequentItemset(L, length+1);
		
		if(L.get(length).lrg.size()!=0) recursiveProcess(L, length+1, filename);
		
	}
	private static void addToFrequentItemset(ArrayList<Large> L, Large candidate) {
		// TODO Auto-generated method stub
		Large tmpL = new Large();
		for(int i = 0;i <candidate.lrg.size();i++){
			Itemset it = candidate.lrg.get(i);
			if(it.freq>Main.suppportThreshold) tmpL.lrg.add(it);
		}
		L.add(tmpL);
	}
	private static void calculateCandidateFrequency(Large candidate,
			String filename) {
		// TODO Auto-generated method stub
		try{
			FileInputStream fstream = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
			  //System.out.println (strLine);
			  Itemset tran = getItemsetFromString(strLine);
			  for(int i = 0;i<candidate.lrg.size();i++){
				  Itemset canIt = candidate.lrg.get(i);
				  if(isContained(tran, canIt)) canIt.freq++;
			  }
			}
			br.close();
		}catch(Exception e){
			
		}
		
	}
	private static Itemset getItemsetFromString(String strLine) {
		// TODO Auto-generated method stub
		StringTokenizer st = new StringTokenizer(strLine, " ");
		Itemset it = new Itemset();
        while (st.hasMoreTokens()){
        	 int item = Integer.parseInt(st.nextToken());
        	 it.set.add(item);
        	 
        }    	
		return it;
	}
	private static Large generateCandidate(ArrayList<Large> L, int length) {
		// TODO Auto-generated method stub
		Large tmpL = L.get(length-1);
		Large candidate = new Large();
		///join
		for(int i = 0; i<tmpL.lrg.size()-1;i++){
			Itemset first = tmpL.lrg.get(i);
			for(int j = i+1; j<tmpL.lrg.size();j++){
				Itemset second = tmpL.lrg.get(j);
				Itemset joined = getMergedItemset(first,second);
				if(joined==null) break;
				boolean flag = holdProperty(joined,tmpL); 
				if(flag) candidate.lrg.add(joined);
				
			}
		}
		return candidate;
	}
	public static int fact(int n){
		if(n==0) return 1;
		return n*fact(n-1);
	}
	
	private static boolean holdProperty(Itemset joined, Large tmpL) {
		// TODO Auto-generated method stub
		int jSize = joined.set.size();
		int nos = fact(jSize)/fact(jSize-1);
		int fnos = 0;
		for(int i = 0; i<tmpL.lrg.size();i++){
			Itemset test = tmpL.lrg.get(i);
			if(isContained(joined,test)) fnos++;
		}
		if(fnos==nos) return true;
		return false;
	}
	private static boolean isContained(Itemset joined, Itemset test) {
		// TODO Auto-generated method stub
		int start = 0;
		for(int i = 0; i<test.set.size();i++){
			int it = test.set.get(i);
			int flg = 0;
			
			for(int j = start;j<joined.set.size();j++){
				int it2 = joined.set.get(j);
				if(it2==it) {
					flg = 1;
					start = j+1;
					break;
				}
			}
			if(flg==0) return false;
		}
		return true;
	}
	private static Itemset getMergedItemset(Itemset first, Itemset second) {
		// TODO Auto-generated method stub
		int matched = 0,i;
		for(i = 0; i<first.set.size(); i++){
			if(first.set.get(i)!=second.set.get(i)) break;
		}
		if(i==first.set.size()-1){
			Itemset merged = new Itemset();
			for(int j = 0;j<first.set.size();j++){
				int itn =  first.set.get(j);
				merged.set.add(itn);
			}
			int itn = second.set.get(second.set.size()-1);
			merged.set.add(itn);
			return merged;
		}
		return null;
	}
	
}
