package com.cybage.myapplication.database;


import android.content.Context;

import com.cybage.myapplication.dao.RecordDao;
import com.cybage.myapplication.model.Records;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Records.class},exportSchema = false, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    private static String DB_NAME = "data_usage_db";
    private static volatile MyDatabase INSTANCE;

    public static MyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract RecordDao recordDao();

}
