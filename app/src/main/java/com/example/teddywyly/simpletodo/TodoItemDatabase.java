package com.example.teddywyly.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teddywyly on 4/15/15.
 */
public class TodoItemDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todoListDatabase";

    private static final String TABLE_TODO = "todo_items";

    // Todo Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_BODY = "body";
    private static final String KEY_PRIORITY = "priority";

    public TodoItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Construct a table for todo items
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_BODY + " TEXT,"
                + KEY_PRIORITY + " INTEGER" + ")";
        db.execSQL(CREATE_TODO_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1) {
            // Wipe older tables if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            // Create tables again
            onCreate(db);
        }
    }


    public void addToDoItem(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BODY, item.getBody());
        values.put(KEY_PRIORITY, item.getPriority());

        db.insertOrThrow(TABLE_TODO, null, values);
        db.close();
    }

    public TodoItem getTodoItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TODO,
                new String[] { KEY_ID, KEY_BODY, KEY_PRIORITY },
                KEY_ID + "= ?", new String[] { String.valueOf(id) },
                null, null, "id ASC", "100");

        if (cursor != null) {
            cursor.moveToFirst();
        }

        TodoItem item = new TodoItem(cursor.getString(1), cursor.getInt(2));
        item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
        return item;
    }

    public List<TodoItem> getAllTodoItems() {

        List<TodoItem> todoItems = new ArrayList<TodoItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_TODO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TodoItem item = new TodoItem(cursor.getString(1), cursor.getInt(2));
                item.setId(cursor.getInt(0));
                todoItems.add(item);
            } while (cursor.moveToNext());
        }
        return todoItems;
    }

    public int getTodoItemCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    public int updateTodoItem(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BODY, item.getBody());
        values.put(KEY_PRIORITY, item.getPriority());
        int result = db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
        db.close();
        return result;
    }

    public void deleteTodoItem(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
        db.close();
    }

}
