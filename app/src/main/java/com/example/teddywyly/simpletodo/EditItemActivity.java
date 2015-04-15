package com.example.teddywyly.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {

    private EditText etEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        setupEditText();
        return true;
    }

    private void setupEditText() {
        etEditItem = (EditText)findViewById(R.id.etEditItem);
//        String item = getIntent().getStringExtra("item");
        TodoItem item = (TodoItem)getIntent().getSerializableExtra("item");
        etEditItem.setText(item.getBody());
        etEditItem.setSelection(item.getBody().length());
        etEditItem.requestFocus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSavedItem(View view) {
        Intent data = new Intent();
        data.putExtra("item", etEditItem.getText().toString());
        setResult(RESULT_OK, data);
        this.finish();
    }
}
