package com.example.foody.util

import androidx.recyclerview.widget.DiffUtil
import com.example.foody.model.Result

class RecipesDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>
): DiffUtil.Callback() {
    // 변화하기전의 데이터셋 사이즈
    override fun getOldListSize(): Int  = oldList.size

    // 변화 후 데이터셋 사이즈
    override fun getNewListSize(): Int = newList.size

    // 두 객체가 같은 항목인지 여부를 결정
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

    /**
     * <코틀린의 동등성 연산자> == vs === vs equals
     * https://wooooooak.github.io/kotlin/2019/02/24/kotiln_%EB%8F%99%EB%93%B1%EC%84%B1%EC%97%B0%EC%82%B0/
     *
     * 간단히 말해서 동등성이라 함은 값자체를 비교하는 것을 말한다.
     * 자바에서는
     * int a = 1;
     * int b = 1;
     * 일 때 a == b 는 true이지만
     * String a = "hi";
     * String b = "hi";를 하면
     * a == b는 false이다. 왜냐하면 참조타입에서는 주소값을 비교하려하기 때문이다.
     * 따라서 객체의 값을 비교하려면 eqauls를 사용하여 그 내용물이 같은지 비교할 수 있다.
     * 그러나 코틀린에서는 ==를 사용하여 주소값이아닌 갑의 동승성을 비교한다
     * 따라서 코틀린이라면
     * val a: String = "hi";
     * val b: String = "hi";
     * 는 a == b일때 true이다. 왜냐하면 코틀린의 == 는 내부적으로 equals를 호출하기 때문이다.
     * 그러면 객체의 주소값을 비교하려면 어떻게 하면 될까? 그건 바로 === 연산자를 사용하면 된다.
     * */

}