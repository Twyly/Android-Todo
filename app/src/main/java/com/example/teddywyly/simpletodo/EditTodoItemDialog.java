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
public class EditTodoItemDialog extends DialogFragment implements TextView.OnEditorActionListener {

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
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        mRatingBar = (RatingBar) view.findViewById(R.id.rb_priority);
        mRatingBar.setRating(item.getPriority()+1);
        Button saveButton = (Button) view.findViewById(R.id.btn_Accept);
        mEditText.setText(item.getBody());
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditText.setOnEditorActionListener(this);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTodoItemDialogListener listener = (EditTodoItemDialogListener) getActivity();
                listener.onFinishEditTodoItemName(mEditText.getText().toString(), (int)mRatingBar.getRating()-1);
                dismiss();
            }
        });


        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            dismiss();
            return true;
        }
        return false;
    }

}
