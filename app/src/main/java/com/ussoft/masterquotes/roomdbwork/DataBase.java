package com.ussoft.masterquotes.roomdbwork;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ussoft.masterquotes.dataclass.BookMarkData;

@Database(entities = {BookMarkData.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract DAO dao();
}
