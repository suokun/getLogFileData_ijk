/*
 * Author: Kun Suo
 * Email: ksuo@uccs.edu
 * Date: 2014-03-21
 * Get data from Log file: rule: from ith row, and jump each j rows, get the kth column data
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class readtext {
	private ArrayList<String> strings = new ArrayList<String>();
	private FileReader fr;
	private BufferedReader br;
	
	
	public ArrayList<String> readTextFile(String url){
		try {
			fr = new FileReader(url);
			br = new BufferedReader(fr);
			String str = null;
			
			while ((str = br.readLine())!=null) {
				strings.add(str);
			}
			
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return strings;
	}
	
	
	//输出第i行，每隔j行输出，输出第k个数据
	//rule: from ith row, and jump each j rows, get the kth column data
	public void printTextFile(int i, int j, int k){		
		for (int p = 0; p < strings.size(); p++) {
			if((p+1)%j==i){
				String eachLineString = strings.get(p);
				String[] eachLineStringArray = eachLineString.split("\\s+");
				
                for (int q = 0; q < eachLineStringArray.length; q++) {
					if (q == k) {
						System.out.println(eachLineStringArray[q]);
					}
				}
			}			
		}
	}
	
	
    public static void main(String[] args) {
    	//log file url
    	String url = "/Users/suokun/Desktop/a.log";
    	
		readtext rt = new readtext();
		rt.strings = rt.readTextFile(url);
		rt.printTextFile(2, 4, 2);   //example: from 2rd row, jump each 4 row, get the 2rd column data
	}
}
