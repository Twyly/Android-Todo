package com.example.teddywyly.simpletodo;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
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


public class MainActivity extends ActionBarActivity implements EditTodoItemDialog.EditTodoItemDialogListener {

    private ArrayList<TodoItem> todoItems = new ArrayList<TodoItem>();
    private ToDoAdapter aTodoItems;
    private ListView lvItems;
    private EditText etNewItem;
    private int editIndex;

    private TodoItemDatabase db;

    private final int EDIT_ITEM_CODE = 20;


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
//                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                launchEditView(i);
            }
        });

    }

    private void launchEditView(int index) {
        FragmentManager fm = getFragmentManager();
        EditTodoItemDialog editItemDialog = EditTodoItemDialog.newInstance("Some Title");
        editItemDialog.itemString = aTodoItems.getItem(index).getBody();
        editItemDialog.show(fm, "fragment_edit_item");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_CODE) {
//            TodoItem item = data.getExtras().getString("item");
//            todoItems.set(editIndex, item);
//            aTodoItems.notifyDataSetChanged();
//            db.updateTodoItem(item);
        }
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

    @Override
    public void onFinishEditTodoItemName(String itemName) {

    }
}
