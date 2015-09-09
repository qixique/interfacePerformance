package interfacePerformance;

public class count {
	 public static int count(String line, String target)  
     {  
     
   int count = 0;  
   int index = -1;  
   while (true) {  
    index = line.indexOf(target, index + 1);  
    if (index !=-1) {  
     count++; 
    } else {  
     break;  
    }  
   }  
 
   return count;  
  } 
}
