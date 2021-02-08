package com.example.first;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface UseriDao {

    @Insert
    public void insert(User user);

    @Insert
    public void insert(List<User> useri);

    @Query("select * from useri where username= :username and password= :password")
    User getUser(String username,String password);

    @Query("select * from useri")
    public List<User> getAll();

    @Query("delete from useri")
    public void deleteAll();

    @Query("delete from useri where id = :id1")
    public void deleteWhere(long id1);

    @Query("update useri set username= :username1,password=:password1,email=:email1")
    public void update(String username1,String password1, String email1);

    @Delete
    public void delete(User user);
}
