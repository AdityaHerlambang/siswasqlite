package com.adityaherlambang.siswasqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "db_akademik";

    public static final String tb_tugas = "tb_tugas";

    public static final String id = "id";
    public static final String nama_tugas = "nama_tugas";
    public static final String deskripsi = "deskripsi";
    public static final String status = "status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + tb_tugas + " (" +
                id + " INTEGER PRIMARY KEY autoincrement, " +
                nama_tugas + " TEXT NOT NULL, " +
                deskripsi + " TEXT NOT NULL," +
                status + " TEXT NOT NULL" +
                " )";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tb_tugas);
        onCreate(db);
    }

    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + tb_tugas;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(id, cursor.getString(0));
                map.put(nama_tugas, cursor.getString(1));
                map.put(deskripsi, cursor.getString(2));
                map.put(status, cursor.getString(3));
                wordList.add(map);
            } while (cursor.moveToNext());
        }

        Log.e("select sqlite ", "" + wordList);

        database.close();
        return wordList;
    }

    public void insert(String nama, String deskripsi, int status) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValues = "INSERT INTO " + tb_tugas + " (nama_tugas, deskripsi, status) " +
                "VALUES ('" + nama + "', '" + deskripsi + "', '"+status+"')";

        Log.e("insert sqlite ", "" + queryValues);
        database.execSQL(queryValues);
        database.close();
    }

    public void update(int id, String nama, String deskripsi, int status) {
        SQLiteDatabase database = this.getWritableDatabase();

        String updateQuery = "UPDATE " + tb_tugas + " SET "
                + nama_tugas + "='" + nama + "', "
                + this.deskripsi + "='" + deskripsi + "', "
                + this.status + "='" + status + "'"
                + " WHERE " + this.id + "=" + "'" + id + "'";
        Log.e("update sqlite ", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        String updateQuery = "DELETE FROM " + tb_tugas + " WHERE " + this.id + "=" + "'" + id + "'";
        Log.e("update sqlite ", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

}
