package com.example.five.ui.anli;

import android.os.AsyncTask;

import com.example.five.db.Db;
import com.example.five.entity.Production;

import java.util.List;

public class InitData extends AsyncTask<String,Void, List<Production>> {

    private Production production;
    private AsyncResponse mAsyncResponse;


    @Override
    protected List<Production> doInBackground(String... strings) {
        return Db.Query(strings[0]);
    }

    @Override
    protected void onPostExecute(List<Production> productions) {
        super.onPostExecute(productions);
        production = productions.get(0);
        String anliname,anliprice,anliimage,anliId;
        anliname = production.getProName();
        anliprice = production.getProPrice();
        anliimage = production.getImg_url();
        anliId = production.getProId();
        mAsyncResponse.setNameData(anliname);
        mAsyncResponse.setPriceData(anliprice);
        mAsyncResponse.setImageData(anliimage);
        mAsyncResponse.setIdData(anliId);
    }

    public void setmAsyncResponse(AsyncResponse asyncResponse){
        this.mAsyncResponse = asyncResponse;
    }

    public interface AsyncResponse{
        void setNameData(String i);
        void setPriceData(String i);
        void setImageData(String i);
        void setIdData(String i);
    }
}
