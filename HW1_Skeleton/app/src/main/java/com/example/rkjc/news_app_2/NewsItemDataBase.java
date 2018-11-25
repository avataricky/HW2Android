package com.example.rkjc.news_app_2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {NewsItem.class}, version = 1)
public abstract class NewsItemDataBase extends RoomDatabase {

    public abstract NewsItemDao newsDao();

    private static volatile NewsItemDataBase INSTANCE;

   public  static NewsItemDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NewsItemDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NewsItemDataBase.class, "news_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}