package com.example.todoapp;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends Activity {
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		etNewItem = (EditText)findViewById(R.id.etNewItem);
		lvItems = (ListView) findViewById(R.id.lvItems);
		readItems();
		todoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todoItems);
		lvItems.setAdapter(todoAdapter);
		setupListViewListener();
	}
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int pos, long id) {
				todoItems.remove(pos);
				todoAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
		});
		lvItems.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View item, int pos,
					long id) {
				Intent i = new Intent(ToDoActivity.this,EditItemActivity.class);
				i.putExtra("item",todoItems.get(pos));
				//Use the Request Code to send the index of the list (pos)
				startActivityForResult(i,pos);
			}
			
			
		});
		
	}
	protected void onActivityResult(int requestCode,int resultCode, Intent data){
		int pos = requestCode;
		if (resultCode == RESULT_OK) {
			String item = data.getExtras().getString("edititem");
			todoItems.set(pos, item);
			todoAdapter.notifyDataSetChanged();
			writeItems();
		}
	}
	public void onAddedItem(View v) {
		String itemText = etNewItem.getText().toString();
		todoAdapter.add(itemText);
		etNewItem.setText("");
		writeItems();
		
	}
	//Read Items from file
	private void readItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir,"todo.txt");
		
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile)); 
		} catch (IOException e){
			todoItems = new ArrayList<String>();
		}		
	}
	//Write Items to File, on Save/edit
	private void writeItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir,"todo.txt");
		try {
			FileUtils.writeLines(todoFile,todoItems);
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}


}
