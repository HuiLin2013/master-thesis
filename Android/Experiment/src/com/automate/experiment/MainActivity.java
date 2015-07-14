package com.automate.experiment;

import com.automate.experiment.R;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.format.DateUtils;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.view.View.OnClickListener;

public class MainActivity extends ActionBarActivity {

	
	private String package_name = "com.techlovejump.jsonandroid";
	private String class_name = "com.techlovejump.jsonandroid.MainActivity";
	private Button btAndroid;
	private Button btHTML5;
 	private TextView viewTime;
	private TextView typeTV;
	private TextView columnTV;
	private TextView rowTV;
	private EditText typeET;
	private EditText columnET;
	private EditText rowET;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btAndroid = (Button) findViewById(R.id.button1);
		btHTML5 = (Button) findViewById(R.id.button2);
		viewTime = (TextView) findViewById(R.id.textView1);	
		typeTV = (TextView) findViewById(R.id.typeTV);	
		columnTV = (TextView) findViewById(R.id.columnTV);	
		rowTV = (TextView) findViewById(R.id.rowTV);	
		typeET = (EditText) findViewById(R.id.typeET);	
		columnET = (EditText) findViewById(R.id.columnET);	
		rowET = (EditText) findViewById(R.id.rowET);	
		
		
	    
		viewTime.setText(DateUtils.formatDateTime(this, System.currentTimeMillis(),
		DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE));
	
		btAndroid.setOnClickListener(new OnClickListener(){			
			public void onClick(View arg0)
			{
				launchAndroidCall(getFilename());
			}
		});
	
		btHTML5.setOnClickListener(new OnClickListener(){			
			public void onClick(View arg0)
			{
				
				
				launchHTML5Call(getFilename());
			}		
		});
		
	}


	
		public String getFilename(){
		
		String dataType = typeET.getText().toString();
		String dataColumn = columnET.getText().toString();
		String dataRow = rowET.getText().toString();
		
	    String filename = dataType + "_" + dataColumn + "col_" + dataRow + "row";
		
		return  filename;
		
	}
	
	

	protected void launchAndroidCall(String fileName){
		
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(intent.CATEGORY_LAUNCHER);
		intent.setComponent(new ComponentName(package_name, class_name));
		Bundle b = new Bundle();
		b.putString("filename", fileName);
		intent.putExtras(b);
	
		try{			
			startActivityForResult(intent, 1);
		}catch(Exception e){
			e.printStackTrace();			
		}
	}
	
	protected void launchHTML5Call(String fileName) {
	
		String baseAddress = "http://158.234.151.82/experiments/jsonHTML5.html";
		String fileaddress = "filename=" + fileName;
		String address = String.format("%s?%s", baseAddress, fileaddress);
		
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
		
		try{
			startActivityForResult(browserIntent, 2);
		}catch(Exception e){
			e.printStackTrace();			
		}
	}
	
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data)  
     {  
               super.onActivityResult(requestCode, resultCode, data);  
                // check if the request code is same as what is passed  here it is 2  
                 if( requestCode==1 || requestCode==2 )  
                       {  
                        /*  String message=data.getStringExtra("milliseconds");   
                          viewone.setText(message);  
                          */
                	 
                viewTime.setText(DateUtils.formatDateTime(this, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE));
                      
              }  
   }  

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

