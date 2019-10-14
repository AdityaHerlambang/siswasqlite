package com.adityaherlambang.siswasqlite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.adityaherlambang.siswasqlite.helper.DatabaseHelper;
import com.adityaherlambang.siswasqlite.model.Tugas;
import com.adityaherlambang.siswasqlite.roomdatabase.AppDatabase;
import com.adityaherlambang.siswasqlite.roomdatabase.AppExecutors;
import com.adityaherlambang.siswasqlite.model.Mahasiswa;

import butterknife.BindView;

public class EditActivity extends AppCompatActivity {


    @BindView(R.id.nama) EditText mNama;
    @BindView(R.id.deskripsi) EditText mDeskripsi;

    @BindView(R.id.sulit) RadioButton mSulit;
    @BindView(R.id.mudah) RadioButton mMudah;

    @BindView(R.id.btn_simpan) Button mBtnSimpan;

    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    boolean editMode = false;
    Intent intent;
    int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mNama = findViewById(R.id.nama);
        mDeskripsi = findViewById(R.id.deskripsi);
        mMudah = findViewById(R.id.mudah);
        mSulit = findViewById(R.id.sulit);
        mBtnSimpan = findViewById(R.id.btn_simpan);

        mMudah.setChecked(true);

        intent = getIntent();
        if ((intent != null && intent.hasExtra("update")) || (intent != null && intent.hasExtra("detail"))) {
            if(intent.hasExtra("update")){
                mBtnSimpan.setText("Update");
                ab.setTitle("Update");
                editMode = true;
            }
            else{
                mBtnSimpan.setVisibility(View.GONE);
                ab.setTitle("Detail");
                mId = intent.getIntExtra("detail", -1);
            }

            mId = intent.getIntExtra("id",-1);
            mNama.setText(intent.getStringExtra("nama"));
            mDeskripsi.setText(intent.getStringExtra("deskripsi"));
            if(intent.getIntExtra("status",-1) == 0){
                mMudah.setChecked(true);
                mSulit.setChecked(false);
            }
            else{
                mMudah.setChecked(false);
                mSulit.setChecked(true);
            }
        }

        mBtnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNama.getText().toString().trim().equals("")){
                    mNama.setError("Nama tidak boleh kosong");
                }
                else if(mDeskripsi.getText().toString().trim().equals("")){
                    mDeskripsi.setError("Nama tidak boleh kosong");
                }
                else{
                    onSubmit();
                }
            }
        });

    }

    private void onSubmit(){
        int status = 0;
        if(mSulit.isChecked()){
            status = 1;
        }
        if(editMode){
            edit(status);
        }
        else{
            insert(status);
        }
    }

    private void insert(int status) {
        databaseHelper.insert(mNama.getText().toString().trim(),
                mDeskripsi.getText().toString().trim(), status);
        finish();
    }

    private void edit(int status) {
        databaseHelper.update(mId, mNama.getText().toString().trim(),
                mDeskripsi.getText().toString().trim(), status);
        finish();
    }

}
