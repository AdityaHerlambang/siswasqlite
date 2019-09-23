package com.adityaherlambang.siswasqlite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.adityaherlambang.siswasqlite.database.AppDatabase;
import com.adityaherlambang.siswasqlite.database.AppExecutors;
import com.adityaherlambang.siswasqlite.databinding.ActivityEditBinding;
import com.adityaherlambang.siswasqlite.model.Mahasiswa;

public class EditActivity extends AppCompatActivity {

    private AppDatabase mDb;
    Intent intent;
    int mMahasiswaId;
    EditText mNama, mNim;
    RadioButton mLaki, mPerempuan;
    Button mBtnSimpan;

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
        mNim = findViewById(R.id.nim);
        mLaki = findViewById(R.id.laki);
        mPerempuan = findViewById(R.id.perempuan);
        mBtnSimpan = findViewById(R.id.btn_simpan);

        mDb = AppDatabase.getInstance(getApplicationContext());

        intent = getIntent();
        if ((intent != null && intent.hasExtra("update")) || (intent != null && intent.hasExtra("detail"))) {
            if(intent.hasExtra("update")){
                mBtnSimpan.setText("Update");
                ab.setTitle("Update");
                mMahasiswaId = intent.getIntExtra("update", -1);
            }
            else{
                mBtnSimpan.setVisibility(View.GONE);
                ab.setTitle("Detail");
                mMahasiswaId = intent.getIntExtra("detail", -1);
            }


            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Mahasiswa mahasiswa = mDb.mahasiswaDao().loadMahasiswaById(mMahasiswaId);
                    setUiData(mahasiswa);
                }
            });
        }

        mBtnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

    }

    private void setUiData(final Mahasiswa mahasiswa){
        if (mahasiswa == null) {
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNama.setText(mahasiswa.getNama());
                mNim.setText(mahasiswa.getNim());
                if(mahasiswa.getJenisKelamin() == 1){
                    mLaki.setChecked(true);
                }
                else{
                    mPerempuan.setChecked(true);
                }
            }
        });

    }

    private void onSubmit(){
        int jenisKelamin = 0;
        if(mLaki.isChecked()){
            jenisKelamin = 1;
        }
        final Mahasiswa mahasiswa = new Mahasiswa(
                mNama.getText().toString(),
                mNim.getText().toString(),
                jenisKelamin
        );

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra("update")) {
                    mDb.mahasiswaDao().insertMahasiswa(mahasiswa);
                } else {
                    mahasiswa.setId(mMahasiswaId);
                    mDb.mahasiswaDao().updateMahasiswa(mahasiswa);
                }
                finish();
            }
        });
    }

}
