package com.adityaherlambang.siswasqlite.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adityaherlambang.siswasqlite.EditActivity;
import com.adityaherlambang.siswasqlite.MainActivity;
import com.adityaherlambang.siswasqlite.R;
import com.adityaherlambang.siswasqlite.database.AppDatabase;
import com.adityaherlambang.siswasqlite.database.AppExecutors;
import com.adityaherlambang.siswasqlite.model.Mahasiswa;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mehdi.sakout.fancybuttons.FancyButton;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MyViewHolder> {
    private Context context;
    private List<Mahasiswa> mMahasiswaList;

    public MahasiswaAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mahasiswa, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.mNama.setText(mMahasiswaList.get(i).getNama());
        myViewHolder.mNim.setText(mMahasiswaList.get(i).getNim());

        Log.d("ADAPTER","ID = "+mMahasiswaList.get(i).getId());

        String jenisKelamin = "Perempuan";
        if(mMahasiswaList.get(i).getJenisKelamin() == 1){
            jenisKelamin = "Laki-laki";
        }
        myViewHolder.mJenisKelamin.setText(jenisKelamin);

    }

    @Override
    public int getItemCount() {
        if (mMahasiswaList == null) {
            return 0;
        }
        return mMahasiswaList.size();

    }

    public void setTasks(List<Mahasiswa> mahasiswaList) {
        mMahasiswaList = mahasiswaList;
        notifyDataSetChanged();
    }

    public List<Mahasiswa> getTasks() {

        return mMahasiswaList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mNama, mNim, mJenisKelamin;
        FancyButton mEdit, mDelete;
        AppDatabase mDb;
        CardView mCard;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mDb = AppDatabase.getInstance(context);
//            name = itemView.findViewById(R.id.person_name);
            mNama = itemView.findViewById(R.id.item_nama);
            mNim = itemView.findViewById(R.id.item_nim);
            mJenisKelamin = itemView.findViewById(R.id.item_jeniskelamin);
            mEdit = itemView.findViewById(R.id.item_btn_edit);
            mDelete = itemView.findViewById(R.id.item_btn_hapus);

            mCard = itemView.findViewById(R.id.item_card);

            mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mahasiswaId = mMahasiswaList.get(getAdapterPosition()).getId();
                    Intent i = new Intent(context, EditActivity.class);
                    i.putExtra("detail", mahasiswaId);
                    context.startActivity(i);
                }
            });


            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mahasiswaId = mMahasiswaList.get(getAdapterPosition()).getId();
                    Intent i = new Intent(context, EditActivity.class);
                    i.putExtra("update", mahasiswaId);
                    context.startActivity(i);
                }
            });

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final SweetAlertDialog sDialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
                    sDialog.setTitle("Hapus Data");
                    sDialog.setContentText("Ingin menghapus data nim "+mMahasiswaList.get(getAdapterPosition()).getNim()+" ?");
                    sDialog.setConfirmButton("Ya", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            final Mahasiswa mahasiswa = mMahasiswaList.get(getAdapterPosition());

                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.mahasiswaDao().delete(mahasiswa);
                                }
                            });
                            ((MainActivity) context).retrieveMahasiswa();
                            sDialog.dismissWithAnimation();
                        }
                    });
                    sDialog.setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    });
                    sDialog.show();
                }
            });
        }
    }
}
