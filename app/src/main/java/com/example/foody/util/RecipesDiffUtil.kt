package com.example.foody.util

import androidx.recyclerview.widget.DiffUtil
import com.example.foody.model.Result

class RecipesDiffUtil(
    private val oldList: List<Result>,
    private val newList: List<Result>
): DiffUtil.Callback() {
    // 변화하기전의 데이터셋 사이즈
    override fun getOldListSize(): Int  = oldList.size

    // 변화 후 데이터셋 사이즈
    override fun getNewListSize(): Int = newList.size

    // 두 객체가 같은 항목인지 여부를 결정정
   override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    // 두 항목의 데이터가 같은지 여부를 결정, areItemsTheSame()이 true를 반환하는 경우에만 호출
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      return oldList[oldItemPosition] == newList[newItemPosition]
    }

    //1. getOldListSize : 말 그대로 변화하기 전의 데이터셋의 사이즈이다
    //2. getNewListSize : 변화 후의 데이터셋 사이즈.
    //3. areItemsTheSame : 두 아이템이 같으냐? 라는 뜻이다.
    //4. areContentsTheSame : 두 콘텐츠가 같으냐? 라는 뜻이다. areItemTheSame이 true일 때 호출된다.
    //즉, "아이템이 같아? 그럼 콘텐츠까지 완전히 같아?"라고 물어보는 것이다. 지금과 같은 상황에서는 areItemsTheSame과 같은 의미이므로 areItemsTheSame의 결과 값을 리턴해준다
    //5. getChangePayload : 여기에 나와있지 않지만, 이 함수 또한 오버라이드할 수 있다. 이 함수는 아이템이 같은데 콘텐츠가 다를 때(즉, areItemsTheSame은 true인데 areContentsTheSame이 false일 때), 변경 내용에 대한 Payload를 가져온다. 기본적으로 null이다.
    //이 중, 5번 함수는 구현하지 않아도 된다.
    //그 후 이 클래스를 이용하여 위에 적힌 calcDiff함수에 나와있듯이 하면 된다.

    //요약하면 DiffUtil.CallBack을 만들고 거기에 oldList와 newList를 넘겨주고 dispatcherUpdateTo를 호출하면 된다

}