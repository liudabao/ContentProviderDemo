package com.example.contentproviderdemo;


import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btn_1;
	Button btn_2;
	Button btn_3;
	Button btn_4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	public void initView(){
		btn_1=(Button)findViewById(R.id.button1);
		btn_2=(Button)findViewById(R.id.button2);
		btn_3=(Button)findViewById(R.id.button3);
		btn_4=(Button)findViewById(R.id.button4);
		
        btn_1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("provider", "write");
				Uri uri=Uri.parse("content://com.example.contentproviderdemo.provider/person");
				ContentValues values=new ContentValues();
				values.put("id", "6");
				values.put("name", "he");
				values.put("age", "15");
				Uri newUri=getContentResolver().insert(uri, values);
				Log.e("provider insert", "id "+newUri.getPathSegments().get(1));
				
			}
		});
        
        btn_2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("provider", "update");
				Uri uri=Uri.parse("content://com.example.contentproviderdemo.provider/person/"+6);
				ContentValues values=new ContentValues();
				values.put("age", "115");
				getContentResolver().update(uri, values, null, null);
				
				
			}
		});
        
        btn_3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("provider", "delete");
				Uri uri=Uri.parse("content://com.example.contentproviderdemo.provider/person/"+6);
				getContentResolver().delete(uri, null, null);
				
				
			}
		});    
        
        btn_4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("provider", "query");
				Uri uri=Uri.parse("content://com.example.contentproviderdemo.provider/person");
				Cursor cursor=getContentResolver().query(uri, null, null, null, null);
				if(cursor!=null){
					while(cursor.moveToNext()){
						Log.e("id", "" +cursor.getInt(cursor.getColumnIndex("id")));
						Log.e("name", cursor.getString(cursor.getColumnIndex("name")));
						Log.e("age", "" +cursor.getInt(cursor.getColumnIndex("age")));
					}
				}
				
			}
		}); 
	}

}
