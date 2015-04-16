package com.example.teddywyly.simpletodo;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by teddywyly on 4/15/15.
 */
public class EditTodoItemDialog extends DialogFragment {

    private EditText mEditText;
    private RatingBar mRatingBar;
    // Pardon the breaking of MVC here but this just makes sense to me..
    public TodoItem item;

    public interface EditTodoItemDialogListener {
        void onFinishEditTodoItemName(String itemName, int priority);
    }

    public EditTodoItemDialog() {}

    public static EditTodoItemDialog newInstance(String title) {
        EditTodoItemDialog frag = new EditTodoItemDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_item, container);
        setupRatingBar(view);
        setupEditText(view);
        setupSaveButton(view);

        return view;
    }

    public void setupRatingBar(View view) {
        mRatingBar = (RatingBar) view.findViewById(R.id.rb_priority);
        mRatingBar.setRating(item.getPriority()+1);
    }

    public void setupEditText(View view) {
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        mEditText.setText(item.getBody());
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void setupSaveButton(View view) {
        Button saveButton = (Button) view.findViewById(R.id.btn_Accept);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mEditText.getText().toString();
                if (!text.isEmpty()) {
                    EditTodoItemDialogListener listener = (EditTodoItemDialogListener) getActivity();
                    listener.onFinishEditTodoItemName(text, (int)mRatingBar.getRating()-1);
                    dismiss();
                }
            }
        });
    }
}
