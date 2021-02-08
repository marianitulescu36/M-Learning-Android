package com.example.first;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;
@Dao
public interface CursuriDao {
    @Transaction
    public default void deleteAndCreate(Curs curs) {
        delete(curs);
        insert(curs);
    }

    @Insert
    void insert(Curs curs);

    @Insert
    public void insert(List<Curs> cursuri);

    @Query("select * from cursuri")
    public List<Curs> getAll();

    @Query("delete from cursuri")
    public void deleteAll();

    @Query("delete from cursuri where id = :id1")
    public void deleteWhere(long id1);

    @Query("UPDATE cursuri SET titlu = :titlu, continut= :content, tipCurs= :tip WHERE id =:id")
    void update(String titlu, String content, String tip,  int id);

    @Delete
    public void delete(Curs curs);
}
