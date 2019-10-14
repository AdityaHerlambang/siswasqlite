package com.adityaherlambang.siswasqlite.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adityaherlambang.siswasqlite.EditActivity;
import com.adityaherlambang.siswasqlite.MainActivity;
import com.adityaherlambang.siswasqlite.R;
import com.adityaherlambang.siswasqlite.helper.DatabaseHelper;
import com.adityaherlambang.siswasqlite.roomdatabase.AppDatabase;
import com.adityaherlambang.siswasqlite.roomdatabase.AppExecutors;
import com.adityaherlambang.siswasqlite.model.Tugas;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mehdi.sakout.fancybuttons.FancyButton;

public class TugasAdapter extends RecyclerView.Adapter<TugasAdapter.MyViewHolder> {
    private Context context;
    private List<Tugas> mTugasList;
    DatabaseHelper databaseHelper;

    public TugasAdapter(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tugas, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TugasAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.mNama.setText(mTugasList.get(i).getNama());
        myViewHolder.mDeskripsi.setText(mTugasList.get(i).getDeskripsi());

        Log.d("ADAPTER","ID = "+mTugasList.get(i).getId());

        String status = "Mudah";
        if(mTugasList.get(i).getStatus() == 1){
            status = "Sulit";
        }
        myViewHolder.mStatus.setText(status);
    }

    @Override
    public int getItemCount() {
        if (mTugasList == null) {
            return 0;
        }
        return mTugasList.size();

    }

    public void setData(List<Tugas> tugasList) {
        mTugasList = tugasList;
        notifyDataSetChanged();
    }

    public List<Tugas> getData() {
        return mTugasList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mNama, mDeskripsi, mStatus;
        FancyButton mEdit, mDelete;
        AppDatabase mDb;
        CardView mCard;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mDb = AppDatabase.getInstance(context);
//            name = itemView.findViewById(R.id.person_name);
            mNama = itemView.findViewById(R.id.item_nama);
            mDeskripsi = itemView.findViewById(R.id.item_deskripsi);
            mStatus = itemView.findViewById(R.id.item_status);
            mEdit = itemView.findViewById(R.id.item_btn_edit);
            mDelete = itemView.findViewById(R.id.item_btn_hapus);

            mCard = itemView.findViewById(R.id.item_card);

            mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = mTugasList.get(getAdapterPosition()).getId();
                    String nama = mTugasList.get(getAdapterPosition()).getNama();
                    String deskripsi = mTugasList.get(getAdapterPosition()).getDeskripsi();
                    int status = mTugasList.get(getAdapterPosition()).getStatus();
                    Intent i = new Intent(context, EditActivity.class);
                    i.putExtra("detail", "detail");
                    i.putExtra("id", id);
                    i.putExtra("nama", nama);
                    i.putExtra("deskripsi", deskripsi);
                    i.putExtra("status", status);
                    context.startActivity(i);
                }
            });


            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = mTugasList.get(getAdapterPosition()).getId();
                    String nama = mTugasList.get(getAdapterPosition()).getNama();
                    String deskripsi = mTugasList.get(getAdapterPosition()).getDeskripsi();
                    int status = mTugasList.get(getAdapterPosition()).getStatus();
                    Intent i = new Intent(context, EditActivity.class);
                    i.putExtra("update", "update");
                    i.putExtra("id", id);
                    i.putExtra("nama", nama);
                    i.putExtra("deskripsi", deskripsi);
                    i.putExtra("status", status);
                    context.startActivity(i);
                }
            });

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final SweetAlertDialog sDialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
                    sDialog.setTitle("Hapus Data");
                    sDialog.setContentText("Ingin menghapus tugas "+mTugasList.get(getAdapterPosition()).getNama()+" ?");
                    sDialog.setConfirmButton("Ya", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            databaseHelper.delete(mTugasList.get(getAdapterPosition()).getId());

                            ((MainActivity) context).retrieveTugas();
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
