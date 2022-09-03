package com.ussoft.masterquotes.apiwork;

import com.ussoft.masterquotes.dataclass.QuotesData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("quotes")
    Call<ArrayList<QuotesData>> getAllQuotes();
}


