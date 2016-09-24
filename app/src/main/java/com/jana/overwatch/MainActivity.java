package com.jana.overwatch;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private FloatingActionButton mAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        mAddButton = (FloatingActionButton) findViewById(R.id.addButton);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
        Target room1 = new Target(R.drawable.placeholder, "Room 1");
        Target bathroom = new Target(R.drawable.placeholder, "Bathroom");

        ArrayList<Target> targetList = new ArrayList<>();
        targetList.add(room1);
        targetList.add(bathroom);

        MainListAdapter adapter = new MainListAdapter(this, targetList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemPosition = i;

            }
        });
    }
}
