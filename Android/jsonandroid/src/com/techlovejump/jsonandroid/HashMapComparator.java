package com.techlovejump.jsonandroid;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class HashMapComparator implements Comparator<HashMap<String, String>>{

	
	 private final String key;
	 private final String type;

	    public HashMapComparator(String key, String type)
	    {
	        this.key = key;
	        this.type = type;
	    }
	
	@Override
	public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
		// TODO Auto-generated method stub
		 String firstValue = lhs.get(key);
	     String secondValue = rhs.get(key);
	     int result = 0;
	     
	     if (type=="asc")
	     {
	    	 result = firstValue.compareTo(secondValue);
	     }
	     else if(type=="desc")
	     {
	    	 result = secondValue.compareTo(firstValue);
	     }
	     return result;
	}



}
