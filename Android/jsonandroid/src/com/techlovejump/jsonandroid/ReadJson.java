package com.techlovejump.jsonandroid;

import static com.techlovejump.jsonandroid.Constants.FIRST_COLUMN;
import static com.techlovejump.jsonandroid.Constants.FOURTH_COLUMN;
import static com.techlovejump.jsonandroid.Constants.SECOND_COLUMN;
import static com.techlovejump.jsonandroid.Constants.THIRD_COLUMN;
import static com.techlovejump.jsonandroid.Constants.FIFTH_COLUMN;
import static com.techlovejump.jsonandroid.Constants.SIXTH_COLUMN;
import static com.techlovejump.jsonandroid.Constants.SEVENTH_COLUMN;
import static com.techlovejump.jsonandroid.Constants.EIGHTH_COLUMN;
import static com.techlovejump.jsonandroid.Constants.NINTH_COLUMN;
import static com.techlovejump.jsonandroid.Constants.TENTH_COLUMN;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.lang.String;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;





import android.content.Context;
import android.content.res.AssetManager;

public class ReadJson {

	private static Context mContext;
	private static String fileName;
 
	
	public ReadJson()
	{
		
	}
	
	public ReadJson(String filename, Context jsonContext){
		 
		mContext = jsonContext;
		fileName = filename;
	}
	
	
	public String converJsonToStringFromAssetFolder() throws IOException {
        AssetManager manager = mContext.getAssets();
        InputStream file = manager.open(fileName);
        
        byte[] data = new byte[file.available()];
        file.read(data);
        file.close();
        return new String(data);
    } 
		
     public ArrayList<HashMap<String, String>> JsonToTable(){
          

		  ArrayList<HashMap<String, String>>  list = new ArrayList<HashMap<String,String>>();
		  
	        try {
	 	  	 		    
	        	JSONArray jsonArray = new JSONArray(converJsonToStringFromAssetFolder());
	        		             		
				int count = jsonArray.length(); // get totalCount of all jsonObjects
				for(int i=0 ; i< count; i++){   // iterate through jsonArray 
					JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position 
					HashMap<String,String> temp=new HashMap<String, String>();
		
					for(Iterator<String> iter = jsonObject.keys(); iter.hasNext();){
		             
						String key = (String) iter.next();
						String value =  jsonObject.get(key).toString();
						
		                 temp.put(key, value);
						      		
		              }
					
					 list.add(temp);
				
	     	}
	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return list;
	}
}
