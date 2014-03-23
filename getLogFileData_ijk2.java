/*
 * Author: Kun Suo
 * Email: ksuo@uccs.edu
 * Date: 2014-03-21
 * Use third party java read/write xls APIs: http://sourceforge.net/projects/jxls/files/
 * input: Log file
 * output: excel file
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class readtext {
	private ArrayList<String> strings = new ArrayList<String>();
	private ArrayList<String> resultStrings = new ArrayList<String>();
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
	public ArrayList<String> Filter(int i, int j, int k){		
		for (int p = 0; p < strings.size(); p++) {
			if((p+1)%j==i){
				String eachLineString = strings.get(p);
				String[] eachLineStringArray = eachLineString.split("\\s+");
				//以多个空格为分割符分割字符串
				
                for (int q = 0; q < eachLineStringArray.length; q++) {
					if (q == k) {
						//替换掉字符串结尾的","
						String s = eachLineStringArray[q].replaceAll(",", "");
						System.out.println(s);
						resultStrings.add(s);
					}
				}
			}			
		}
		
		return resultStrings;
	}
	
	
	public boolean createExcel(String fileurl, ArrayList<String> body){
		boolean createFlag = true;
		
		WritableWorkbook wwb;
		WritableSheet sheet;
		WritableCellFormat wcf;
		WritableFont font;
		jxl.write.Label tempContext;
		try {
			wwb = Workbook.createWorkbook(new File(fileurl));
			sheet = wwb.createSheet("sheet1", 0);
			
			font = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE); 
			wcf = new WritableCellFormat(font);			
			wcf.setBackground(Colour.WHITE);
			wcf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);			
			
			for (int i = 0; i < body.size(); i++) {
				tempContext = new jxl.write.Label(0, i, body.get(i), wcf);
				sheet.addCell(tempContext);				
			}
			
			wwb.write();
			wwb.close();
		} catch (IOException e) {
			e.printStackTrace();
			createFlag = false;
		} catch (RowsExceededException e) {
			e.printStackTrace();
			createFlag = false;
		} catch (WriteException e) {
			e.printStackTrace();
			createFlag = false;
		}
		
		return createFlag;
	}
	
	
    public static void main(String[] args) {
    	String url = "/Users/suokun/Desktop/m1g.log";
    	String url2 = "/Users/suokun/Desktop/output.xls";
    	ArrayList<String> arr = new ArrayList<String>();
    	
		readtext rt = new readtext();
		rt.strings = rt.readTextFile(url);
		arr = rt.Filter(2, 4, 8);
		
		boolean flag = rt.createExcel(url2, arr);
		if (flag) {
			System.out.println("create successful!");
		}
	}
}
