package com.ussoft.masterquotes.dataclass;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class BookMarkData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Quote Content")
    public String quote_content;

    @ColumnInfo(name = "Author Name")
    public String quote_auther;

    public BookMarkData(String quote_content, String quote_auther) {
        this.quote_content = quote_content;
        this.quote_auther = quote_auther;
    }

    public String getQuote_content() {
        return quote_content;
    }

    public void setQuote_content(String quote_content) {
        this.quote_content = quote_content;
    }

    public String getQuote_auther() {
        return quote_auther;
    }

    public void setQuote_auther(String quote_auther) {
        this.quote_auther = quote_auther;
    }
}