package com.example.note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton floatingButton;
    ArrayAdapter noteArrayAdapter;
    dataBaseHelper DataBaseHelper;
    FloatingActionButton deleteButton;
    Notes deleteNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view);
        floatingButton = findViewById(R.id.floatingActionButton3);
        DataBaseHelper = new dataBaseHelper(MainActivity.this);
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setVisibility(View.GONE);
        ShowItems();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,Editor.class);
                Notes note = (Notes)listView.getItemAtPosition(position);
                try {
                    intent.putExtra("Note", note);
                    intent.putExtra("Position",position);
                    startActivity(intent);
                }
                catch (Exception e)
                {

                }

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteNote = (Notes)parent.getItemAtPosition(position);
                deleteButton.setVisibility(View.VISIBLE);
                return true;
            }
        });
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Editor.class);
                startActivity(intent);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper.deleteOne(deleteNote);
                ShowItems();
                deleteButton.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,"Note Deleted",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void ShowItems(){
        List<Notes> everyone = DataBaseHelper.getEveryone();
        MyAdapter adapter = new MyAdapter(MainActivity.this,R.layout.rew,everyone);
        listView.setAdapter(adapter);
    }
    class MyAdapter extends ArrayAdapter<Notes>{
        Context context;
        int resource;
        public MyAdapter(@NonNull Context context, int resource, @NonNull List<Notes> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String title = getItem(position).getTitle();
            String desc = getItem(position).getDescription();
            int ID = getItem(position).getID();
            Notes note = new Notes(title,desc,ID);
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource,parent,false);
            TextView tvTitle = (TextView)convertView.findViewById(R.id.Title);
            tvTitle.setText(title);
            return convertView;
        }
    }
}