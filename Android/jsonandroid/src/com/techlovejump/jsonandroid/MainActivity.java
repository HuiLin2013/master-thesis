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

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
 



import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter.FilterListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

 
public class MainActivity extends Activity implements View.OnClickListener{
 
	private ArrayList<HashMap<String, String>> list;
	private ListView listView;
	
	private ListViewAdapter adapter;
    AlertDialog alertDialog;
    private String type;
    private ArrayList<String> jsonKeys = new ArrayList<String>();
    private ArrayList<Integer> sortingButtonId = new ArrayList<Integer>();
    private ArrayList<Integer> filteringEdittxtId = new ArrayList<Integer>();
    private ArrayList<Integer> displayTxtviewId = new ArrayList<Integer>();
    private int keyTotal;
    private Button[] buttons;
    private EditText[] edittxts;
    public LinearLayout sortingButtonView;
    public LinearLayout filteringEdittxtView;
   
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	      
	  //Views
        listView = (ListView)findViewById(R.id.listView1);
        TextView tv1 = (TextView)findViewById(R.id.text1);
        TextView tv2 = (TextView)findViewById(R.id.text2);
        sortingButtonView = (LinearLayout)findViewById(R.id.sortingButton);
        filteringEdittxtView = (LinearLayout)findViewById(R.id.filteringEdittxt);
      
      
        
        //Retrieve parameters from previous Android application via Bundle
        Bundle b = getIntent().getExtras();
        String filename;
        
        if (b != null){        	
        	filename  = b.getString("filename") + ".json";     	        	
        }
        else 
        {
        	//Assign a default file name to the filename variable
        	//filename = "mix_2col_6000row.json";
        	  filename = "num_10col_500row.json";
        }
                    
	//  ReadJson json2table = new ReadJson("mix_10col_10000row.json", this);
        ReadJson json2table = new ReadJson(filename, this);
        
	    //performance timer
	    long loadStart = System.nanoTime();
	    list = json2table.JsonToTable();
	    
	    //Set jsonKeys and keyTotal
	    setJsonKeys(list.get(0));
	    
	    buttons = new Button[keyTotal];
	    edittxts = new EditText[keyTotal];
	 
		TextWatcher watcher = new TextWatcher() {
	        @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
				adapter.getViewID(getCurrentFocus());
				
				// Performance Timer for filtering
				final long filterStart = System.nanoTime(); 
				
				adapter.getFilter().filter(s.toString(), new FilterListener() {
				@Override
				public void onFilterComplete(int count) {
					
					// Performance Timer for filtering
              		long filterEnd = System.nanoTime();
              		long diff = filterEnd - filterStart; 
					
              		TextView tv4 = (TextView)findViewById(R.id.text4);
              		tv4.setText(diff/1000000.0 + " milliseconds (Filtering)");
               }
           });  
       }

       @Override
       public void beforeTextChanged(CharSequence s, int start, int count,int after) {
       }

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
   };
	   
   

	    
	    for(int i = 0; i<keyTotal; i++){
	    	//Button
        	buttons[i] = new Button(this);
        	int buttonId = View.generateViewId();
	    	sortingButtonId.add(buttonId);
	    	buttons[i].setId(buttonId);
	    	buttons[i].setOnClickListener(this);
	    	buttons[i].setText(jsonKeys.get(i));
	    	LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT); // width, height	                   
	    	paramsButton.weight = 1;
	    	buttons[i].setLayoutParams(paramsButton);
        	sortingButtonView.addView(buttons[i]);
	  
        	//EditText
        	edittxts[i] = new EditText(this);
        	int edittxtId = View.generateViewId();
        	filteringEdittxtId.add(edittxtId);
	    	edittxts[i].setId(edittxtId);
	    	edittxts[i].addTextChangedListener(watcher);
	    	LinearLayout.LayoutParams paramsEdittxt = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT); // width, height	                   
	    	paramsEdittxt.weight = 1;
	    	edittxts[i].setLayoutParams(paramsEdittxt);
	    	filteringEdittxtView.addView(edittxts[i]);
	    }
	 
	    for(int i = R.id.col1; i<(keyTotal+R.id.col1); i++){
	    	displayTxtviewId.add(i);
        }
        
	    
	    //Custom adapter
		adapter = new ListViewAdapter(this, list,jsonKeys, keyTotal, filteringEdittxtId, displayTxtviewId);
		listView.setAdapter(this.adapter);
		
		//performance timer
		long loadEnd = System.nanoTime();
		
		long diff = loadEnd - loadStart;
		int totalData = json2table.JsonToTable().size();
		
		tv1.setText("Loading " + totalData  + " JSON entries with native Android Java");
		tv2.setText(diff/1000000.0 + " milliseconds (Loading)");
			
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
		      @Override
		      public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
		      {
		    	int pos=position+1;
		    	Toast.makeText(MainActivity.this, Integer.toString(pos)+" Clicked", Toast.LENGTH_SHORT).show();  
		      }
		      
		});
	
                
}	

	public Comparator<Map<String, String>> ascComparator = new Comparator<Map<String, String>>() {
	    public int compare(Map<String, String> m1, Map<String, String> m2) {
	        return m1.get(type).compareTo(m2.get(type));
	    }
	};
	
	public Comparator<Map<String, String>> descComparator = new Comparator<Map<String, String>>() {
	    public int compare(Map<String, String> m1, Map<String, String> m2) {
	        return m2.get(type).compareTo(m1.get(type));
	    }
	};
	
	public void setJsonKeys(HashMap<String, String> firstMap)
	{
		HashMap<String, String> firstJsonArray = firstMap;
		jsonKeys = new ArrayList<String>();
		
		keyTotal = firstJsonArray.keySet().size();
		for(String key : firstJsonArray.keySet()){
			
			jsonKeys.add(key);
		}		
	}
	
	public void sortingTime (String compareType){
		
		TextView tv3 = (TextView)findViewById(R.id.text3);
		
		long sortStart = System.nanoTime();
		if(compareType == "asc"){
		    Collections.sort(this.adapter.list, ascComparator);
		}
		else if(compareType == "desc")
		{
			Collections.sort(this.adapter.list, descComparator);
		}
	
		this.adapter.notifyDataSetChanged();
		
		
		long sortEnd = System.nanoTime();
		long diff = sortEnd - sortStart;   		
		tv3.setText(diff/1000000.0 + " milliseconds (Sorting)");
			
	}
	
	public void resetButtons(int except)
	{
		
		int exceptIndex = except;
		
		for(int i = 0; i < keyTotal; i++){
			
			if(i == exceptIndex){
				//Do nothing
			}
			else
			{
				Button btReset = (Button)findViewById(sortingButtonId.get(i));
				btReset.setText(jsonKeys.get(i));
			}
		
		}
		
	   
	}
	
	@Override
    public void onClick(View v) {
		
		int viewID = v.getId();
		Button btNow = (Button)findViewById(viewID);
		Object btText = btNow.getText();
			
     for(int i = 0; i < keyTotal; i++){
			
			if(sortingButtonId.get(i).equals(viewID))
			{
				type = new String(jsonKeys.get(i));
				
				resetButtons(i);
				
				if(btText.equals(type) || btText.equals(type + "(DESC)")){
					sortingTime("asc");
					btNow.setText(type + "(ASC)");
				}
				else if(btText.equals(type + "(ASC)")){
					sortingTime("desc");
					btNow.setText(type + "(DESC)");
				}			   					
				break;
			}
		
     }
	 	
        }
		
}

	
	