package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Editor extends AppCompatActivity {
    FloatingActionButton backButton;
    EditText title;
    EditText description;
    dataBaseHelper DataBaseHelper;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        backButton = findViewById(R.id.floatingActionButton);
        title = findViewById(R.id.editTextTextPersonName);
        description = findViewById(R.id.editTextTextPersonName2);
        final ListView listView = findViewById(R.id.list_view);
        try {
            Notes note = (Notes)getIntent().getSerializableExtra("Note");
            pos = (int) getIntent().getSerializableExtra("Position");
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            DataBaseHelper = new dataBaseHelper(Editor.this);
            DataBaseHelper.deleteOne(note);
        }
        catch(Exception e) {
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                Notes newNote;
                try{
                    newNote = new Notes(title.getText().toString(),description.getText().toString(),-1);
                    Toast.makeText(Editor.this,"Note Saved Successfully",Toast.LENGTH_SHORT).show();
                    flag = 0;
                }
                catch (Exception e) {
                    Toast.makeText(Editor.this,e.toString(),Toast.LENGTH_SHORT).show();
                    newNote = new Notes("Error","Error",-1);
                    flag = 1;
                }
                DataBaseHelper = new dataBaseHelper(Editor.this);
                if(flag == 0) {
                    if(!DataBaseHelper.addOne(newNote)){
                        Toast.makeText(Editor.this,"Note not saved",Toast.LENGTH_SHORT).show();
                    }
                }
                Intent intent = new Intent(Editor.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}