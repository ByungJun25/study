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
* [EnumMap](#EnumMap)
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
**`enum`이 만들어질때, 컴파일러에 의해 자동적으로 몇몇 메서드가 추가됩니다.**

1. `values()` 메서드
`values()` 메서드는 현재 호출하는 `enum`의 모든 값들을 가진 배열을 반환합니다. 이때 반환되는 배열내의 원소 순서는 선언된 순서와 동일합니다.

    ```java
    public enum Number {
        ZERO, ONE, TWO, THREE; 
    }

    public class Main {
        public static void main(String[] args) {
            Arrays.stream(Number.values()).forEach(n -> System.out.println(n.name())); // ZERO, ONE, TWO, THREE 출력
        }
    }
    ```

2. `valueOf(String name)` 메서드
`valueOf(String name)` 메서드는 호출하는 `enum`타입에서 주어지는 이름과 일치하는 값을 반환하는 메서드입니다. 이 메서드는 내부적으로 다시 `java.lang.Enum`클래스의 `valueOf(Class<T> enumType, String name)` 메서드를 호출하도록 되어있습니다.

    ```java
    public enum Number {
        ZERO, ONE, TWO, THREE; 
    }

    public class Main {
        public static void main(String[] args) {
            System.out.println(Number.valueOf("ONE").name()); // ONE 출력
            System.out.println(Number.valueOf("TEST").name()); // IllegalArgumentException 발생
        }
    }
    ```

## enum, 언제 사용할까?


## enum을 확장해보자


# java.lang.Enum
`enum`은 `java.lang.Enum`을 상속하고 있습니다. 따라서 `enum` 타입의 변수들은 모두 `java.lang.Enum` 클래스의 메서드를 사용할 수 있습니다.

`java.lang.Enum`의 메서드들은 `final`로 선언되어 있기때문에, `enum`에서 재정의가 불가능합니다.

1. `name` 메서드
`name` 메서드는 `enum` 상수의 이름을 반환합니다. (여기서 이름은 정확히 선언된 이름을 의미합니다.)

    ```java
    public enum Number {
        ZERO, ONE, TWO, THREE; 
    }

    public class Main {
        public static void main(String[] args) {
            System.out.println(Number.ONE.name()); // ONE 출력
        }
    }
    ```

3. `ordinal` 메서드
`ordinal` 메서드는 `enum` 상수가 선언된 순서를 반환합니다. 단, 0부터 시작합니다.

    ```java
    public enum Number {
        ZERO, ONE, TWO, THREE; 
    }

    public class Main {
        public static void main(String[] args) {
            System.out.println(Number.ONE.ordinal()); // 1 출력
        }
    }
    ```

1. `compareTo` 메서드
`compareTo` 메서드는 호출하는 `enum`의 `ordinal` 값에서 타켓 `enum`의 `ordinal`값을 뺀 결과값을 반환합니다.

    ```java
    public enum Number {
        ZERO, ONE, TWO, THREE; 
    }

    public class Main {
        public static void main(String[] args) {
            System.out.println(Number.ZERO.compareTo(Number.ONE)); // -1 출력
            System.out.println(Number.ONE.compareTo(Number.ZERO)); // 1 출력
        }
    }
    ```

4. `getDeclaringClass` 메서드
`getDeclaringClass` 메서드는 호출하는 `enum` 상수에 대응하는 클래스를 반환합니다.

    ```java
    public enum Number {
        ZERO, ONE, TWO, THREE; 
    }

    public class Main {
        public static void main(String[] args) {
            System.out.println(Number.ONE.getDeclaringClass()); // enum 타입의 Number class 반환
        }
    }
    ```

이 외에도 `equals`, `hashCode`, `toString`등의 `java.lang.Object`클래스의 메서드도 있습니다. 

# EnumSet


# EnumMap
`EnumMap`은 `enum`을 키로 가지는 Map의 구현체입니다.

```java
public enum Number {
    ZERO, ONE, TWO, THREE;
}

public class Main {
    public static void main(String[] args) {
        Map<Number, String> enumMap = new EnumMap<>(Number.class); // 생성자를 통해 어떤 키를 가지는지 알려줘야 합니다.

        enumMap.put(Number.ZERO, "zero");
        enumMap.put(Number.ONE, "one");

        System.out.println(enumMap.get(Number.ONE)); // one 출력.
    }
}
```

`EnumMap`은 일반 다른 Map들 보다 더 빠른 성능을 보입니다. 이러한 이유는 key로 사용되는 값들이 이미 알고 있는 `enum` 타입 상수이기때문에, 더 빠른 해시계산과 비교가 가능하기때문입니다. 또한 `EnumMap`은 `enum`상수가 정의된 순서대로 정렬된 Map(ordered Map)입니다. `enum`의 순서에 맞춰 데이터를 정렬할때 매우 손쉽게 할 수 있습니다.

## 참고 사이트
- [Oracle - Enum Types](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html)
- [Oracle - Java 11 specs](https://docs.oracle.com/javase/specs/jls/se11/html/jls-8.html#jls-8.9)
- [Oracle - Class Enum<E extends Enum<E>>](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Enum.html)
- [Enum Tricks: Two Ways to Extend Enum Functionality](https://dzone.com/articles/enum-tricks-two-ways-to-extend-enum-functionality)
- [Baeldung - A Guide to EnumMap](https://www.baeldung.com/java-enum-map)
- [Baeldung - Guide to EnumSet](https://www.baeldung.com/java-enumset)