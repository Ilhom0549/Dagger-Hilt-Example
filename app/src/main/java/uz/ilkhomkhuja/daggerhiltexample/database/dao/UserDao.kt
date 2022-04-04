package uz.ilkhomkhuja.daggerhiltexample.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.ilkhomkhuja.daggerhiltexample.database.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(list: List<UserEntity>)

    @Query("SELECT * FROM UserEntity")
    suspend fun getUsers(): List<UserEntity>

}