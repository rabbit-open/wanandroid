package com.wanandroid.bslee.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wanandroid.bslee.vo.ArticleVO
import com.wanandroid.bslee.vo.HistoryArticleVO

@Database(entities = [ArticleVO::class, HistoryArticleVO::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        private var instance: AppDataBase? = null

        fun init(context: Context) {
            instance =
                Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "wanDb")
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
        }

        fun get(): AppDataBase {
            return instance!!
        }

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }

    }


}