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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
 

import java.util.Iterator;
import java.util.List;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
 
@SuppressLint("ViewTag")
public class ListViewAdapter extends BaseAdapter implements Filterable {
 
	public ArrayList<HashMap<String, String>> list;
	private ArrayList<HashMap<String, String>> mOriginalValues;
	private int viewID;
	private ArrayList<String> jsonKeys = new ArrayList<String>();
	private ArrayList<Integer> filteringEdittxtId = new ArrayList<Integer>();
	private ArrayList<Integer> displayTxtviewId = new ArrayList<Integer>();
	private TextView[] txt;
	private int keyTotal;
	private HashMap<String, String> record = new HashMap<String, String>();
	private String compareKey;
	Activity activity;
	
	
	public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> origiList){
		super();
		this.activity=activity;
		this.list=origiList;
	}
	
	public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> origiList,ArrayList<String> keys, int keyCount, ArrayList<Integer> edittxtId, ArrayList<Integer> txtviewId )
	{
		super();
		this.activity=activity;
		this.list=origiList;
		this.jsonKeys = keys;
		this.keyTotal = keyCount;
        this.filteringEdittxtId = edittxtId;
		this.displayTxtviewId = txtviewId;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
 
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
 
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
 

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	
		
		LayoutInflater inflater=activity.getLayoutInflater();
		txt = new TextView[keyTotal];
		
		if(convertView == null){
			
			convertView=inflater.inflate(R.layout.colmn_row, null);
			
		
			
			for(int i = 0 ; i<keyTotal; i++){
				
				txt[i] = (TextView) convertView.findViewById(displayTxtviewId.get(i));
				txt[i].setVisibility(View.VISIBLE);
				convertView.setTag(displayTxtviewId.get(i), txt[i]);
			}
			
	
		
		}
		else{
			// To avoid that Listview rows order changes randomly on scroll 
			for(int i = 0 ; i<keyTotal; i++){
				
				txt[i] = (TextView) convertView.getTag(displayTxtviewId.get(i));				
			}
		}
		

		HashMap<String, String> map=this.list.get(position);
		for(int i = 0 ; i<keyTotal; i++){
			
			txt[i].setText(map.get(jsonKeys.get(i)));		
		}
		
		return convertView;
	}
	
	@Override
	public void notifyDataSetChanged() {
	    super.notifyDataSetChanged();
	}
	
	private ArrayList<HashMap<String, String>> getList()
	{
		return this.list;
	}
	
	
	private void updateList(ArrayList<HashMap<String, String>> filteredList)
	{
		this.list = filteredList;
	}
	
	 public void getViewID(View v)
		{
			
			viewID=v.getId();
			
			for(int i = 0; i < keyTotal; i++){
				
				if(filteringEdittxtId.get(i).equals(viewID))
				{
					compareKey = jsonKeys.get(i);
					break;
				}
				
				
			}
		}
	
	@Override
	public Filter getFilter() {
        Filter filter = new Filter() {
        	
        

			@SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

           
                updateList((ArrayList<HashMap<String, String>>) results.values);
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

			
			
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<HashMap<String, String>> FilteredArrList = new ArrayList<HashMap<String, String>>();
               

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<HashMap<String, String>>(getList()); // saves the original data in mOriginalValues
                }

                /********
                 * 
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)  
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                	if(record.keySet().contains(compareKey))
                        record.remove(compareKey);
                        
                	if(record.keySet().size() == 0){
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                	}
                	else
                	{
                		for (HashMap<String, String> j : mOriginalValues) {
                    		
                            boolean past = true;
         			
         					for(String rKey : record.keySet()){
         						
         						if(j.get(rKey).toString().toUpperCase().startsWith(record.get(rKey).toString().toUpperCase())){
         							
         						}
         						else{
         							past = false;
             						break;
         						}
         							
         					
         				     }
         					
         					
         			if(past){
         					HashMap<String, String> temp = new  HashMap<String, String>();   
         					               						 
       						for(String item : j.keySet()){
         						
         							temp.put(item, j.get(item).toString());
         						}
         					                						
         						FilteredArrList.add(temp);
         				               					
         			}
         			    					
         		  }
         		
         		 	// set the Filtered result to return
         		  	results.count = FilteredArrList.size();
         		  	results.values = FilteredArrList;
                		
                		
                	}
                    
                    
                } else {
                		
                		
                		record.put(compareKey, constraint.toString());
                		
                		
                		for (HashMap<String, String> j : mOriginalValues) {
                		
                                   boolean past = true;
                			
                					for(String rKey : record.keySet()){
                						
                						if(j.get(rKey).toString().toUpperCase().startsWith(record.get(rKey).toString().toUpperCase())){
                							
                						}
                						else{
                							past = false;
                    						break;
                						}
                							
                					
                				     }
                					
                					
                			if(past){
                					HashMap<String, String> temp = new  HashMap<String, String>();   
                					               						 
              						for(String item : j.keySet()){
                						
                							temp.put(item, j.get(item).toString());
                						}
                					                						
                						FilteredArrList.add(temp);
                				               					
                			}
                			    					
                		  }
                		
                		 	// set the Filtered result to return
                		  	results.count = FilteredArrList.size();
                		  	results.values = FilteredArrList;
                          
                		}
                
                return results;
            }
        };
       return filter;
    }	
}
 
