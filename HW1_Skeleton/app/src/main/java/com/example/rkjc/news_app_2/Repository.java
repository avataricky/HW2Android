package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private NewsItemDao mAsyncTaskDao;
    private LiveData<List<NewsItem>> allNewsItems;


        public Repository(Application application){
        NewsItemDataBase database = NewsItemDataBase.getDatabase(application.getApplicationContext());
        mAsyncTaskDao = database.newsDao();
        allNewsItems = mAsyncTaskDao.loadAllNewsItems();
        }

    public void startSync(){
            new SyncNewsItems(mAsyncTaskDao).execute(); // going to get all of the new items from the api
    }

    public void getNewsItems(){ // This gets all of the new items from the database
            new GetNewsItem(mAsyncTaskDao).execute();


    }









    private static  class GetNewsItem extends AsyncTask<Void, Void, LiveData<List<NewsItem>>> {

        private NewsItemDao mAsyncTaskDao;

       public GetNewsItem(NewsItemDao dao){

           mAsyncTaskDao = dao;
       }

    @Override
        protected LiveData<List<NewsItem>> doInBackground(Void... params){
        return mAsyncTaskDao.loadAllNewsItems();

    }

    }

















    private static class SyncNewsItems extends AsyncTask<Void, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        SyncNewsItems(NewsItemDao dao){
            mAsyncTaskDao = dao;
        }




        @Override
        protected Void doInBackground(Void... params){
        NetworkUtils networkUtils = new NetworkUtils();
        URL url = networkUtils.buildURL();
        String data = "";
        try {
            data =  NetworkUtils.getResponseFromHttpUrl(url);
        }catch(IOException e){
            // Inside the Catch
        }

        ArrayList<NewsItem> news = JsonUtils.parseNews(data);

        mAsyncTaskDao.clearAll();
        mAsyncTaskDao.insert(news);
        return null;
        }

    }










    public LiveData<List<NewsItem>> getAllNewsItems() {
        return allNewsItems;
    }
}
