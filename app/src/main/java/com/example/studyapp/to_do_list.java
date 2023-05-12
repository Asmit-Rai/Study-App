package com.example.studyapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class to_do_list extends AppCompatActivity {

    DatabaseHelper db;
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

        db = new DatabaseHelper(this);

        listView = (ListView) findViewById(R.id.listView);
        add_button = (Button) findViewById(R.id.add_button);
        editTextText = (EditText) findViewById(R.id.editTextText);


        list = new ArrayList<String>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = listView.getItemAtPosition(i).toString();
                Toast.makeText(to_do_list.this, "" + text, Toast.LENGTH_SHORT).show();
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = editTextText.getText().toString();
                if (!names.equals("") && db.insertData(names)) {
                    Toast.makeText(to_do_list.this, "Data Added", Toast.LENGTH_SHORT).show();
                    editTextText.setText((""));
                } else {
                    Toast.makeText(to_do_list.this, "Data not added", Toast.LENGTH_SHORT).show();
                }
                list.add(names);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();


            }
        });
        {
            Cursor cursor = db.viewData();
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No data to show", Toast.LENGTH_SHORT).show();

            } else {
                while (cursor.moveToNext()) {
                    list.add(cursor.getString(1));
                }
                arrayAdapter = new ArrayAdapter(this, R.layout.row, list);
                listView.setAdapter(arrayAdapter);
            }
        }
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