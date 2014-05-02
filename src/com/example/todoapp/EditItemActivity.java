package com.example.todoapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends Activity {
	private EditText etItem; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		String itemName = getIntent().getStringExtra("item");
		etItem = (EditText)findViewById(R.id.etItem);
		etItem.setText(itemName, TextView.BufferType.EDITABLE);
		etItem.setSelection(etItem.getText().length());
	}

	public void onSave(View v){
		Intent data = new Intent();
		//Saves the editText data into the intent, sends it back to the TodoActivity
		data.putExtra("edititem",etItem.getText().toString());
		setResult(RESULT_OK,data);
		this.finish();
	}
	public void onSubmit(View v) {
		// closes the activity and returns to the first screen
		this.finish();
	}
}
