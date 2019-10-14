package com.adityaherlambang.siswasqlite.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.adityaherlambang.siswasqlite.model.Mahasiswa;

import java.util.List;

@Dao
public interface MahasiswaDao {

    @Query("SELECT * FROM mahasiswa ORDER BY id")
    List<Mahasiswa> loadAllMahasiswas();

    @Insert
    void insertMahasiswa(Mahasiswa mahasiswa);

    @Update
    void updateMahasiswa(Mahasiswa mahasiswa);

    @Delete
    void delete(Mahasiswa mahasiswa);

    @Query("SELECT * FROM mahasiswa WHERE id = :id")
    Mahasiswa loadMahasiswaById(int id);

}
