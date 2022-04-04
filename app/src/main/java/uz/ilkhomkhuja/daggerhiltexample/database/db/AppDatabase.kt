package uz.ilkhomkhuja.daggerhiltexample.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.ilkhomkhuja.daggerhiltexample.database.dao.UserDao
import uz.ilkhomkhuja.daggerhiltexample.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}