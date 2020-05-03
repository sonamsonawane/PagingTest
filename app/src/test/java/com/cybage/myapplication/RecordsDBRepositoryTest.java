package com.cybage.myapplication;

import android.app.Instrumentation;
import android.content.Context;

import com.cybage.myapplication.database.MyDatabase;

import org.junit.Before;

import androidx.room.Room;

public class RecordsDBRepositoryTest {

    private MyDatabase mockDatabse;

    @Before
    public void initDB () {
       // mockDatabse = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), MyDatabase.class).build();
    }
}
