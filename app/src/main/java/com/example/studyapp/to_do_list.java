package com.example.studyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class to_do_list extends AppCompatActivity {
    Button add_button;
    ListView listView;

    ArrayList<String>list;
    EditText editTextText;

    ArrayAdapter<String> arrayAdapter;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        listView = (ListView) findViewById(R.id.listView);
        add_button= (Button) findViewById(R.id.add_button);
        editTextText = (EditText) findViewById(R.id.editTextText);

        list = new ArrayList<String>();
        arrayAdapter= new ArrayAdapter<String>(getApplicationContext(),R.layout.row,list);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = editTextText.getText().toString();

                list.add(names);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();


            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int which_item = i;
                new AlertDialog.Builder(to_do_list.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure ?")
                        .setMessage("Do you want to delete this item")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                list.remove(which_item);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }
}