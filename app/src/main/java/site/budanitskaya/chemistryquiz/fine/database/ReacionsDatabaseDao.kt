package site.budanitskaya.chemistryquiz.fine.database

import androidx.room.*

@Dao
interface ReactionsDatabaseDao {

    @Insert
    suspend fun insert(reaction: ReactionEntity)

    @Update
    suspend fun update(reaction: ReactionEntity)

    @Delete
    suspend fun delete(reaction: ReactionEntity)

    @Query("SELECT * FROM reaction_table")
    suspend fun getReactionList(): List<ReactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reactions: List<ReactionEntity>)

}