package com.adityaherlambang.siswasqlite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.adityaherlambang.siswasqlite.adapter.TugasAdapter;
import com.adityaherlambang.siswasqlite.helper.DatabaseHelper;
import com.adityaherlambang.siswasqlite.model.Tugas;
import com.adityaherlambang.siswasqlite.roomdatabase.AppDatabase;
import com.adityaherlambang.siswasqlite.roomdatabase.AppExecutors;
import com.adityaherlambang.siswasqlite.model.Mahasiswa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    RecyclerView mRecyclerView;
    TugasAdapter mAdapter;
    AppDatabase mDb;
    List<Tugas> itemList = new ArrayList<Tugas>();
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();

        floatingActionButton = findViewById(R.id.addFAB);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditActivity.class));
            }
        });

        mRecyclerView = findViewById(R.id.main_recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TugasAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mDb = AppDatabase.getInstance(getApplicationContext());

    }

    private void setToolbar(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("List Tugas");
//        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTugas();
    }

    public void retrieveTugas() {
        itemList.clear();
        mAdapter.notifyDataSetChanged();
        ArrayList<HashMap<String, String>> row = databaseHelper.getAllData();

        for (int i = 0; i < row.size(); i++) {
            int id = Integer.parseInt(row.get(i).get("id"));
            String nama = row.get(i).get("nama_tugas");
            String deskripsi = row.get(i).get("deskripsi");
            int status = Integer.parseInt(row.get(i).get("status"));

            Tugas tugas = new Tugas();

            tugas.setId(id);
            tugas.setNama(nama);
            tugas.setDeskripsi(deskripsi);
            tugas.setStatus(status);

            itemList.add(tugas);
        }

        mAdapter.setData(itemList);
    }
}
