package com.ussoft.masterquotes.dataclass;

import java.io.Serializable;
import java.util.Random;

public class QuotesData implements Serializable {

    String text, author;

    public QuotesData(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
