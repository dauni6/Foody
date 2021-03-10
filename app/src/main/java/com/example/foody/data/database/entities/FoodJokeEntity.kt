package com.example.foody.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody.model.FoodJoke
import com.example.foody.util.Constants.Companion.FOOD_JOKE_TABLE

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded // FoodJoke는 text 변수 하나만 있고 복잡한 객체가 아니라서 @Embedded annotation을 사용
    var foodJoke: FoodJoke
) {
    @PrimaryKey(autoGenerate = false) // 딱 하나의 row만 넣고 중복되면 덮어쓰기할 것이기 때문에 autoGenerate를 false로 함
    var id: Int = 0
}
