package com.item.shopping.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.item.shopping.data.source.local.TypeConverter
import com.item.shopping.domain.model.Favorite
import java.util.*


/**
 * Local DataSource 엔티티
 */
@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey
    val id:Int,
    @ColumnInfo(name = "name")
    val name:String?,
    @ColumnInfo(name = "image")
    val image:String?,
    @ColumnInfo(name = "price", defaultValue = "0")
    val price:Int,
    @ColumnInfo(name = "discount", defaultValue = "0")
    val discount:Int,
    @ColumnInfo(name = "created_date")
    @TypeConverters(TypeConverter::class)
    val regDate: Date
)

fun FavoriteEntity.mapToFavorite():Favorite {
    return Favorite(
        id = this.id,
        name = this.name ?: "",
        image = this.image ?: "",
        price = this.price,
        discount = this.discount
    )
}