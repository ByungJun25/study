# 목표
자바의 열거형에 대해 학습하세요.

## 학습 내용
* [enum](#enum)
  * [enum 타입이란](#enum-타입이란)
  * [enum 정의하는 방법](#enum-정의하는-방법)
  * [enum이 제공하는 메서드](#enum이-제공하는-메서드)
  * [enum, 언제 사용할까?](#enum-언제-사용할까)
  * [enum을 확장해보자](#enum을-확장해보자)
* [java.lang.Enum](#javalangEnum)
* [EnumSet](#EnumSet)
* [참고 사이트](#참고-사이트)

# enum

## enum 타입이란
`enum`(열거형) 타입은 변수가 미리 정의된 상수의 집합이 될 수 있도록 하는 특별한 **데이터 타입**입니다. 따라서 변수는 미리 정의된 값들중 하나의 값을 가져야합니다.

## enum 정의하는 방법
`enum`타입을 정의하기 위해서는 `enum` 키워드를 사용해야합니다. 또한 `enum` 타입의 필드들은 상수이기때문에, 상수 convention을 따라 대문자로 정의하는것이 좋습니다.

`enum`선언은 `enum` 타입의 class를 정의하는 것입니다.

`enum`을 선언할때는 다음의 제약 사항이 있습니다.
1. `abstract` 및 `final` 키워드를 사용할 수 없습니다.
2. 클래스 내부에 `enum` 타입의 class를 정의하면, 이는 암묵적으로 `static`입니다. 따라서 `enum`타입의 클래스는 `non-static inner class`에서 정의가 불가능합니다.
3. `enum` 타입 클래스는 `extends` 키워드를 사용할 수 없습니다. (`implements` 키워드는 가능합니다.)
4. `enum` 타입 클래스는 오로지 `private` 접근 제어 지시자를 가진 생성자만 선언이 가능합니다.

```java
// 일반 enum type 선언
public enum PaymentType {
    TRANSFER, CREADIT_CARD, PAYPAL;
}

public interface Extendable {
    void method();
}

// 인터페이스를 구현한 enum type 선언
public enum Alphabet implements Extendable {
    A, B;

    void method() {
        // codes..
    }
}

// 내부 enum을 가진 enum type 선언
public enum Time {
    ;
    enum Day {
        FIRST, LAST;
    }

    enum Month {
        JAN, FEB;
    }
}
```

## enum이 제공하는 메서드
`enum`이 만들어질때, 컴파일러에 의해 자동적으로 몇몇 특별한 메서드가 추가됩니다. 

1. values 메서드

2. 


## enum, 언제 사용할까?

## enum을 확장해보자

# java.lang.Enum

# EnumSet

## 참고 사이트
- [Oracle - Enum Types](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html)
- [Oracle - Java 11 specs](https://docs.oracle.com/javase/specs/jls/se11/html/jls-8.html#jls-8.9)
- [Oracle - Class Enum<E extends Enum<E>>](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Enum.html)
- [Enum Tricks: Two Ways to Extend Enum Functionality](https://dzone.com/articles/enum-tricks-two-ways-to-extend-enum-functionality)