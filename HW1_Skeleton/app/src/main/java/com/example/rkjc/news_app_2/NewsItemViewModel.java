package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.LiveData;
import java.util.List;

public class NewsItemViewModel extends ViewModel {
    private Repository repository; // Keep a Repo Object
    private  LiveData<List<NewsItem>> LiveNewsItem;



    public NewsItemViewModel(){

    }
    public void setUpRepo(Application application){

        repository = new Repository(application);
        LiveNewsItem = repository.getAllNewsItems();

    }


    public LiveData<List<NewsItem>> getAllNewsItems(){
        return LiveNewsItem;
    }



    public void retrieveNewsItems(){
        repository.startSync();
    }


}
