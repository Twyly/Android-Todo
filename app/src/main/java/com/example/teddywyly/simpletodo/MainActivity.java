package com.example.teddywyly.simpletodo;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity implements EditTodoItemDialog.EditTodoItemDialogListener {

    private ArrayList<TodoItem> todoItems = new ArrayList<TodoItem>();
    private ToDoAdapter aTodoItems;
    private ListView lvItems;
    private EditText etNewItem;
    private int editIndex;
    private TodoItemDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNewItem = (EditText)findViewById(R.id.etNewItem);
        lvItems= (ListView)findViewById(R.id.lvItems);
        db = new TodoItemDatabase(this);
        readItems();
        aTodoItems = new ToDoAdapter(this, todoItems);
        lvItems.setAdapter(aTodoItems);
        setupListViewListeners();
    }

    public void setupListViewListeners() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TodoItem item = todoItems.get(i);
                todoItems.remove(i);
                aTodoItems.notifyDataSetChanged();
                db.deleteTodoItem(item);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                launchEditFragment(i);
            }
        });

    }

    private void launchEditFragment(int index) {
        FragmentManager fm = getFragmentManager();
        editIndex = index;
        EditTodoItemDialog editItemDialog = EditTodoItemDialog.newInstance("Some Title");
        editItemDialog.item = aTodoItems.getItem(index);
        editItemDialog.show(fm, "fragment_edit_item");
    }


    private void readItems() {
        todoItems = new ArrayList<TodoItem>(db.getAllTodoItems());
    }

    public void onAddedItem(View view) {
        String text = etNewItem.getText().toString();
        if (text != null && !text.isEmpty()) {
            TodoItem item = new TodoItem(text, 1);
            aTodoItems.add(item);
            etNewItem.setText("");
            // Scroll to bottom
            lvItems.setSelection(aTodoItems.getCount()-1);
            db.addToDoItem(item);
        }
    }
    // Maybe better to have fragment change ToDo item model?
    @Override
    public void onFinishEditTodoItemName(String itemName, int priority) {
        TodoItem item = aTodoItems.getItem(editIndex);
        item.setBody(itemName);
        item.setPriority(priority);
        aTodoItems.notifyDataSetChanged();
        db.updateTodoItem(item);
    }

}
