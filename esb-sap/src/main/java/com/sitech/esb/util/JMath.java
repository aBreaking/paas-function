package com.sitech.esb.util;


/**
   * <p>Copyright: Copyright (c) 2006 hartech.cn</p>
   *
   * <p>Website: www.hartech.cn</p>
   *
   * <p>Page: http://www.hartech.cn/blog/blogview.asp?logID=73 </p>
   *
   * @author JTL.zheng@gmail.com
   * @version 1.0
   */
public class JMath {
	
     /**
       * support Numeric format:<br>
       * "33" "+33" "033.30" "-.33" ".33" " 33." " 000.000 "
       * @param str String
       * @return boolean
       */
     public static boolean isNumeric(String str) {
         int begin = 0;
         boolean once = true;
         if (str == null || str.trim().equals("")) {
             return false;
         }
         str = str.trim();
         if (str.startsWith("+") || str.startsWith("-")) {
             if (str.length() == 1) {
                 // "+" "-"
                 return false;
             }
             begin = 1;
         }
         for (int i = begin; i < str.length(); i++) {
             if (!Character.isDigit(str.charAt(i))) {
                 if (str.charAt(i) == '.' && once) {
                     // '.' can only once
                     once = false;
                 }
                 else {
                     return false;
                 }
             }
         }
         if (str.length() == (begin + 1) && !once) {
             // "." "+." "-."
             return false;
         }
         return true;
     }

     /**
       * support Integer format:<br>
       * "33" "003300" "+33" " -0000 "
       * @param str String
       * @return boolean
       */
     public static boolean isInteger(String str) {
         int begin = 0;
         if (str == null || str.trim().equals("")) {
             return false;
         }
         str = str.trim();
         if (str.startsWith("+") || str.startsWith("-")) {
             if (str.length() == 1) {
                 // "+" "-"
                 return false;
             }
             begin = 1;
         }
         for (int i = begin; i < str.length(); i++) {
             if (!Character.isDigit(str.charAt(i))) {
                 return false;
             }
         }
         return true;
     }



     
     public static int findMax(int [] nums) {
    	 int max = nums[0];
    	 int length = nums.length;
    	 for(int i=0; i<length; i++) {
    		 if(nums[i] > max) {
    			 max = nums[i];
    		 }
    	 }
    	 return max;
     }
     
     public static int findMin(int [] nums) {
    	 int max = nums[0];
    	 int length = nums.length;
    	 for(int i=0; i<length; i++) {
    		 if(nums[i] < max) {
    			 max = nums[i];
    		 }
    	 }
    	 return max;
     }
     
     public static boolean isExist(int i, int [] nums) {
    	 boolean flag = false;
     	 for(int j=0; j<nums.length; j++) {
     			if(i==nums[j]) {
     				flag = true;
     				break;
     			} 
     	 }
     	 return flag;
     }
     
     public static int[] findNums(int [] nums) {
    	 int max = nums[0];
    	 int [] result = null;
    	 java.util.ArrayList list = new java.util.ArrayList();
    	 int length = nums.length;
    	 for(int i=0; i<length; i++) {
    		 if(nums[i] > max) {
    			 max = nums[i];
    		 }
    	 }
         if(max > 0) {
        	boolean flag = false;
        	for(int i=1;i<=max;i++) {
        		for(int j=0; j<length; j++) {
        			if(i==nums[j]) {
        				flag = true;
        				break;
        			} 
        		}
        		if(flag == false){
        			list.add(new Integer(i));
        		}
        		flag = false;
        	}	
         }
         if(list!=null && !list.isEmpty()) {
        	 result = new int[list.size()];
        	 java.util.Iterator it = list.iterator();
        	 int i = 0;
        	 while(it.hasNext()) {
        		 result[i] = ((Integer)it.next()).intValue();
        		 i++;
        	 }
         }
    	 return result;
     }
     
     public static String toString(int [] nums) {
    	 StringBuffer sb = new StringBuffer();
    	 if(nums.length>0) {
    		 for(int i=0; i<nums.length; i++) {
    			 if(i<(nums.length-1)) {
    				 sb.append(nums[i]+",");
    			 }
    			 if(i==(nums.length-1)) {
    				 sb.append(nums[i]);
    			 }
    		 }
    	 }
    	 return sb.toString();
     }
     
     public static void main(String[] args) {
    	 //System.out.println(JMath.isNumeric("4.3"));
    	 int [] nums = {1,3,5,7,10};
    	 int [] result = JMath.findNums(nums);
    	 for(int i=0;i<result.length;i++) {
    		 System.out.println(result[i]);
    	 }
     }
}
 