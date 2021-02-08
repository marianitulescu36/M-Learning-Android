package com.example.first;

import android.content.Context;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Room;

@Database(entities = {User.class,Curs.class}, version = 1, exportSchema = false)
public abstract class CursuriDB extends RoomDatabase {

    private final static String DB_NAME = "cursuri.db";
    private static CursuriDB instanta;

    public static CursuriDB getInstance(Context context) {
        if (instanta == null) {
            instanta = Room.databaseBuilder(context, CursuriDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instanta;
    }

    public abstract CursuriDao getCursuriDao();
    public abstract UseriDao getUseriDao();

}

