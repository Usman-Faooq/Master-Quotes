package com.ussoft.masterquotes.roomdbwork;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ussoft.masterquotes.dataclass.BookMarkData;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DAO {

    @Insert
    void insertLike(BookMarkData data);

    @Query("SELECT EXISTS(SELECT * FROM BookMarkData WHERE `Quote Content` = :quote)")
    Boolean isBookMarkExist(String quote);

    @Query("SELECT * FROM BookMarkData")
    List<BookMarkData> retriveBookMarks();

    @Query("DELETE FROM BookMarkData WHERE `Quote Content` =:quote ")
    void removeBookMark(String quote);

}
