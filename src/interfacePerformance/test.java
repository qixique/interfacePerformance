package interfacePerformance;

import interfacePerformance.util.dataStruction;

import java.io.File;
import java.io.IOException;

public class test {

	 public static void main(String[] args) throws IOException  
     {  
//		 String current = new java.io.File( "." ).getCanonicalPath();
//	     System.out.println("Current dir:"+current);
//         try  
//         {  
//               
//             File testDataDir = new File("D:/workspace/interfacePerformance/src/interfacePerformance/testdata");  
//             System.out.println(testDataDir.listFiles().length);  
//             int i = 0 ;   
//             for(File file :testDataDir.listFiles())  
//             {  
//                 i++ ;  
//                 String recognizeText = new testocr().recognizeText(file);  
//                 System.out.print(recognizeText+"\t");  
//   
//                 if( i % 5  == 0 )  
//                 {  
//                     System.out.println();  
//                 }  
//             }  
//               
//         } catch (Exception e)  
//         {  
//             e.printStackTrace();  
//         }  
//   
		 
		 int insertnum=dataStruction.creatTestData("jdbc:mysql://192.168.20.71:9001/jumei_product", "dev", "jmdevcd", "jumei_pool_sku_relationship", "jpsr1.xls", "Sheet1", "Sheet2");
		 System.out.println(insertnum);
     }
}
