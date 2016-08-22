package interfacePerformance.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class excel {

	private Workbook workbook;
	private Sheet sheet;
	private String sheetName;
	List<Map<String,Integer>> rowNo;
	Map<String,Integer> mapk;
	Map<String,ArrayList<Map<String, String>>> finalcon;
	/**
	 * @param filePath
	 * @throws BiffException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public excel(String filePath,String sheetName) throws BiffException, FileNotFoundException, IOException
	{
		this.workbook = Workbook.getWorkbook(new FileInputStream(filePath));
		this.sheetName=sheetName;
		this.sheet=workbook.getSheet(this.sheetName);
	}
	/**
	 * get the table fields row number
	 * @param sheetname
	 * @return
	 * @throws BiffException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public  ArrayList<Integer> getKeyRow() throws BiffException, FileNotFoundException, IOException
	{
		ArrayList<Integer> arr=new ArrayList<>();		
		 sheet = workbook.getSheet(this.sheetName);
		for(int i=0;i<sheet.getRows();i++)
		{
			String table=sheet.getCell(0, i).getContents();
			if(table.equals("table"))
			{
				arr.add(i+1);
			}
		}
		
		return arr;
	}

	
	public Map<String, ArrayList<String>> getTableKey() throws BiffException, FileNotFoundException, IOException
	{
		Map<String,ArrayList<String>> map=new HashMap<String, ArrayList<String>>();
		 rowNo=new ArrayList<Map<String,Integer>>();
		 
		int rows=this.sheet.getRows();
		//iterator for how many tables
		for(int tableno=0;tableno<this.getKeyRow().size();tableno++)
		{
			Map<String, Integer> mapk= new HashMap<String, Integer>();
			int fieldy=getKeyRow().get(tableno);
			String table=sheet.getCell(1,fieldy-1).getContents();
			
			//calculate table's count of rows 
			int currentindex=this.getKeyRow().size()-1-tableno;
			int datano=this.getKeyRow().get(currentindex);
			String table1=sheet.getCell(1,datano-1).getContents();
			
			if(tableno==0)
			{
				mapk.put(table1,(rows-datano-1));
			}
			else
			{
				mapk.put(table1,(rows-datano-2));
			}
			
			rows=datano;
			//end calculate
			
			ArrayList<String> al=new ArrayList<String>();
			//iterator for table fields
			for(int i=0;i<sheet.getColumns();i++)
			{
				//遇到空行跳出循环
				if(sheet.getCell(i,fieldy).getContents().equals(""))
					break;
				String content=sheet.getCell(i,fieldy).getContents();
				al.add(content);
			}
			this.mapk=mapk;
		rowNo.add(mapk);
		map.put(table,al);
		}
		return map;
	}
	
	/**
	 * @return
	 * @throws BiffException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Map<String, ArrayList<Map<String, String>>> getdata() throws BiffException, FileNotFoundException, IOException 
	{
		Map<String, ArrayList<String>> map=this.getTableKey();
//		Object[] keys=map.keySet().toArray();
//		System.out.println(map);
//		System.out.println(map.get("jumei_mall").size());
//		Object[] tables=excel.mapk.keySet().toArray();
		ArrayList<Object> tables = new ArrayList<Object>();
		for(int i=0;i<this.rowNo.size();i++)
		{
			tables.add(this.rowNo.get(i).keySet().toArray()[0]);
		}
		Collections.reverse(tables);
//		System.out.println("--------"+this.rowNo);
//		String keyrow=excel.getKeyRow().toString().replace("[", "").replace("]", "");
		int ignore=-1;
		int flag=0;
		String tableflag="id_generator";
		ArrayList<Map<String,String>> maptablevalue=new ArrayList< Map<String,String>>();

		this.finalcon=new HashMap<String, ArrayList<Map<String,String>>>();
		for(int i=0;i<this.sheet.getRows();i++)
		{
			//ignore table and field rows
			if(this.sheet.getCell(0,i).getContents().equals("table"))
			{
				ignore=1;
//				System.out.println("ignore"+i);
				continue;
			}
			if(ignore==1)
			{
				ignore=-1;
				flag++;
				continue;
			}
			String table=(String) tables.get(flag-1);
//			System.out.println("table is:"+table+",size:"+map.get(table).size());
			Map<String,String> mapfield=new HashMap<String, String>();
			//clear tablemap
			if(!tableflag.equals(table))
			{
				mapfield.clear();
//				System.out.println("===yes==="+tableflag+","+table);
				
			}
			for(int j=0;j<map.get(table).size();j++)
			{
				
				String con=this.sheet.getCell(j,i).getContents();
				String tableField=map.get(table).get(j);
				mapfield.put(tableField, con);
			
			}
			if(tableflag.equals(table))
			{
				maptablevalue.add(mapfield);
			}
			else
			{
				maptablevalue=new ArrayList<Map<String,String>>();
				maptablevalue.clear();
				maptablevalue.add(mapfield);
			}
			finalcon.put(table, maptablevalue);
			tableflag=table;
//			System.out.println("mapfield"+mapfield+",maptablevalue"+maptablevalue);
			
		}
//		System.out.println("the final content is :"+finalcon);
		return finalcon;
	}
	
	
	public void delete() throws BiffException, FileNotFoundException, IOException
	{
		System.out.println();
		Object[] tables=this.getdata().keySet().toArray();
		for (Object table : tables) {
			
		
//		String table="jumei_deal_on_sale_status";
		ArrayList<String> keys = getTableKey().get(table);
		List<Map<String, String>> list=finalcon.get(table);
		for(int i=0;i<list.size();i++)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("delete from " +table+   " where ");
			for(int j=0;j<keys.size();j++)
			{
			if(j>0)
				sb.append(" and "+keys.get(j)+ " in ('"+list.get(i).get(keys.get(j))+"')");
			else
				sb.append(keys.get(j)+ " in ('"+list.get(i).get(keys.get(j))+"')");
			}
			System.out.println(sb);
//			sqlLists.add(sb.toString());
		}
		}
	}
	
	public void create() throws BiffException, FileNotFoundException, IOException
	{
		Object[] tables=this.getdata().keySet().toArray();
		for (Object table : tables) {
//		String table="jumei_deal_on_sale_status";
		List<Map<String, String>> list=finalcon.get(table);
		ArrayList<String> keys = getTableKey().get(table);
		StringBuilder sb=new StringBuilder();
		//insert field strunction
		sb.append("insert into "+table+"(");
		sb.append(keys.toString().replace("[", "").replace("]", ""));
		sb.append(") values ");
		//end for field strunction
		for(int i=0;i<list.size();i++)
		{
			sb.append("(");
			for(int j=0;j<keys.size();j++)
			{
			if(j>0)
				sb.append(" , '"+list.get(i).get(keys.get(j))+"'");
			else
				sb.append( "'"+list.get(i).get(keys.get(j))+"'");
			}
			sb.append("),");
		}
		System.out.println(sb);
	}
	}
	public static void main(String[] args) throws BiffException, FileNotFoundException, IOException 
	{
		excel exceladd=new excel("E:/file/2000007260.xls","Sheet1");
		
		excel exceldel=new excel("E:/file/2000007260.xls","Sheet3");
		System.out.println(exceladd.getdata());
		exceldel.delete();
		exceladd.create();
	}
}
