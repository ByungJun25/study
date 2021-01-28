# 목표
자바의 열거형에 대해 학습하세요.

## 학습 내용
* [enum](#enum)
  * [enum 타입이란](#enum-타입이란)
  * [enum 정의하는 방법](#enum-정의하는-방법)
  * [enum이 제공하는 메서드](#enum이-제공하는-메서드)
  * [enum, 언제 사용할까?](#enum-언제-사용할까)
* [java.lang.Enum](#javalangEnum)
* [EnumSet](#EnumSet)
  * [EnumSet 특징](#EnumSet-특징)
  * [EnumSet 장점](#EnumSet-장점)
  * [EnumSet을 정의하는 다양한 방법](#EnumSet을-정의하는-다양한-방법)
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
`enum`은 주로 정해진 범위의 상수 값을 사용해야할때 많이 사용됩니다. 또한 각 상수별로 자기만의 로직을 가져야할 때도 사용할 수 있습니다.

**`enum`을 사용하면 효과적으로 메서드의 파라미터의 범위를 제한할 수 있고 또한 파라미터 타입의 방어도 효과적으로 할 수 있습니다.**

### 사용 예시  
아래의 예시는 결제 수단에 따라 다른 수수료를 부과하고 또한 결제되는 월에 따라 다른 할인을 제공하도록 작성되어 있습니다.  

요구사항은 다음과 같습니다.
- 결제 타입별 수수료가 정해져 있어, 이에 따라 수수료를 부과해야함.
- 결제 타입별 별도의 월별 할인 정책이 있어, 타입별, 월별 할인을 구현해야함.

```java
public interface Calculable {
    BigDecimal addCommission(BigDecimal price);
    BigDecimal offerSpecialDiscount(BigDecimal price);
}

public enum PaymentType implements Calculable {
    TRANSFER("계좌 이체", 0) { // 계좌 이체시 수수료(0%) 및 할인 정보

        @Override
        public BigDecimal offerSpecialDiscount(BigDecimal price) {
            LocalDate now = LocalDate.now(); // 현재 날짜
            BigDecimal discountPrice = BigDecimal.ZERO;

            switch (now.getMonth()) { 
                case JANUARY: // 1월의 경우 10 퍼센트 할인 제공
                    discountPrice = price.multiply(new BigDecimal(10)).divide(ONE_HUNDRED);
                    break;
                default:
                    break;
            }

            return price.subtract(discountPrice);
        }
    },
    CREADIT_CARD("신용카드 결제", 5) { // 신용카드 결제시 수수료(5%) 및 할인 정보

        @Override
        public BigDecimal offerSpecialDiscount(BigDecimal price) {
            LocalDate now = LocalDate.now();
            BigDecimal discountPrice = BigDecimal.ZERO;

            switch (now.getMonth()) {
                case JUNE: // 6월의 경우에 15퍼센트 할인 제공
                    discountPrice = price.multiply(new BigDecimal(15)).divide(ONE_HUNDRED);
                    break;
                default:
                    break;
            }

            return price.subtract(discountPrice);
        }
    },
    PAYPAL("페이팔 결제", 10) { // 페이팔 결제시 수수료(10%) 및 할인 정보

        @Override
        public BigDecimal offerSpecialDiscount(BigDecimal price) {
            LocalDate now = LocalDate.now();
            BigDecimal discountPrice = BigDecimal.ZERO;

            switch (now.getMonth()) {
                case DECEMBER: // 12월의 경우에 10퍼센트 할인 제공
                    discountPrice = price.multiply(new BigDecimal(10)).divide(ONE_HUNDRED);
                    break;
                default:
                    break;
            }

            return price.subtract(discountPrice);
        }
    };

    private String name;
    private int commissionPercentage;
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    private PaymentType(String name, int commissionPercentage) {
        this.name = name;
        this.commissionPercentage = commissionPercentage;
    }

    public String getName() {
        return this.name;
    }

    public int getCommissionPercentage() {
        return this.commissionPercentage;
    }

    @Override
    public BigDecimal addCommission(BigDecimal price) { // 이미 enum값에 수수료 퍼센트를 넣었기때문에 굳이 따로 구현할 필요가 없습니다.
        BigDecimal additionPrice = price.multiply(new BigDecimal(this.getCommissionPercentage())).divide(ONE_HUNDRED);
        return price.add(additionPrice);
    }

    public abstract BigDecimal offerSpecialDiscount(BigDecimal price); // 할인 정책이 각 타입별로 다르기때문에 공통 메서드가 아닌 개별 구현해야합니다.

}

public class Main {
    public static void main(String[] args) {
        BigDecimal price = new BigDecimal(10000); // 1월에 결제하였다고 가정하겠습니다.

        System.out.println(getPriceByPaymentType(price, PaymentType.TRANSFER)); // 9000 출력
        System.out.println(getPriceByPaymentType(price, PaymentType.CREADIT_CARD)); // 10500 출력
        System.out.println(getPriceByPaymentType(price, PaymentType.PAYPAL)); // 11000 출력
    }

    // 타입별로 간단히 메서드를 호출하여 모든 요구사항에 맞는 가격 계산이 가능합니다.
    public static BigDecimal getPriceByPaymentType(BigDecimal price, PaymentType paymentType) {
        BigDecimal result = price;
        switch (paymentType) {
            case TRANSFER:
                result = PaymentType.TRANSFER.addCommission(PaymentType.TRANSFER.offerSpecialDiscount(price));
                break;
            case CREADIT_CARD:
                result = PaymentType.CREADIT_CARD.addCommission(PaymentType.CREADIT_CARD.offerSpecialDiscount(price));
                break;
            case PAYPAL:
                result = PaymentType.PAYPAL.addCommission(PaymentType.PAYPAL.offerSpecialDiscount(price));
                break;
            default:
                break;
        }

        return result;
    }
}
```

위의 예시를 보면 알 수 있듯이, 이제 월별 할인 정책이 변경되거나, 혹은 수수료가 변경되어도 `enum`클래스 내부의 코드만 수정하면 될뿐, 외부 메인 로직을 수정할 필요는 없게됩니다.

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
`EnumSet`은 `enum` 클래스와 함께 사용되기위해 정의된 특별한 `Set`콜렉션입니다.

## EnumSet 특징
`EnumSet`은 다음의 특징을 가지고 있습니다.
- 오로지 `enum`값만을 가지고 모든 값은 같은 `enum` 클래스이어야 합니다.
- `null`값을 추가할 수 없습니다.
- `thread-safe`하지 않기때문에, 필요시 외부에서 동기화 처리를 해줘야합니다.
- `enum`값이 정의된 순서대로 원소들이 저장됩니다.
- `fail-safe iterator`가 사용되기 때문에, 반복중 컬렉션이 변경되어도 `ConcurrentModificationException`이 발생하지 않습니다.

## EnumSet 장점
`EnumSet`은 `set`에 `enum`값을 저장할때, 필수적으로 먼저 고려되어야 합니다. 그 이유는 다음과 같은 이점때문입니다.
- `EnumSet`의 모든 메서드는 산술 비트 연산을 통해 구현되어져 있습니다. 따라서 매우 빠른 실행 속도를 가지고 있습니다.
- `EnumSet`은 이미 예상된 순서를 가지고 있기때문에 다른 `set`에 비해 빠르게 처리될 수 있습니다.
- `EnumSet`은 `HashSet`과는 달리 `hashcode` 계산을 할 필요가 없기때문에, 보다 더 빠르게 원소를 찾을 수 있습니다.
- `EnumSet`은 `bit vector`를 사용함으로써 더 적은 메모리를 사용합니다.

## EnumSet을 정의하는 다양한 방법
`EnumSet`은 다양한 팩토리 메서드를 지원합니다.

우선 각 예제에 사용될 `enum`클래스는 다음과 같습니다.
```java
public enum Number {
    ZERO, ONE, TWO, THREE, FOUR, FIVE
}
```

1. `allOf` 메서드  
    `allOf` 메서드는 주어지는 `enum` 클래스의 모든 값을 가진 `enumset`을 만듭니다.

    ```java
    public class Main {
        public static void main(String[] args) {
            EnumSet<Number> enumSet = EnumSet.allOf(Number.class);
            System.out.println(enumSet); // [ZERO, ONE, TWO, THREE, FOUR, FIVE] 출력.
        }
    }
    ```

1. `noneOf` 메서드  
    `noneOf` 메서드는 주어지는 `enum` 클래스 타입의 빈 `EnumSet`을 만듭니다.

    ```java
    public class Main {
        public static void main(String[] args) {
            EnumSet<Number> enumSet = EnumSet.noneOf(Number.class);
            System.out.println(enumSet); // [] 출력.
        }
    }
    ```

1. `of` 메서드  
    `of` 메서드는 주어진 `enum` 값을 가진 `EnumSet`을 만듭니다. `of` 메서드는 여러개로 `오버로딩`되어 있는데, 그 중 `가변 함수`의 경우, 다른 메서드들 보다 느리다고 `javadoc`에 설명되어 있습니다. 따라서 상황에 따라 적절히 사용해야 합니다.

    ```java
    public class Main {
        public static void main(String[] args) {
            EnumSet<Number> enumSet = EnumSet.of(Number.ONE, Number.FOUR);
            System.out.println(enumSet); // [ONE, FOUR] 출력.
        }
    }
    ```

1. `range` 메서드  
    `range` 메서드는 파라미터로 주어진 `enum` 값 사이의 모든 값을 가진 `EnumSet`을 만듭니다. **이 메서드 사용시 주의할 사항은 파라미터의 순서는 필히 `enum`이 선언된 순서에 맞게 넣어야 합니다. 역순으로 넣을 경우 `IllegalArgumentException`이 발생하게 됩니다.**

    ```java
    public class Main {
        public static void main(String[] args) {
            EnumSet<Number> enumSet = EnumSet.range(Number.ONE, Number.FOUR);
            System.out.println(enumSet); // [ONE, TWO, THREE, FOUR] 출력.

            EnumSet<Number> enumSet = EnumSet.range(Number.FOUR, Number.ONE); // java.lang.IllegalArgumentException: FOUR > ONE 발생.
        }
    }
    ```

1. `complementOf` 메서드  
    `complementOf` 메서드는 파라미터로 주어진 `enum`값을 제외한 모든 값을 가진 `EnumSet`을 만듭니다.

    ```java
    public class Main {
        public static void main(String[] args) {
            EnumSet<Number> enumSet = EnumSet.complementOf(EnumSet.of(Number.FOUR, Number.ONE));
            System.out.println(enumSet); // [ZERO, TWO, THREE, FIVE] 출력.
        }
    }
    ```

1. `copyOf` 메서드  
    `copyOf` 메서드는 다른 `Collection`의 모든 값을 복사한 `EnumSet`을 만듭니다.

    ```java
    public class Main {
        public static void main(String[] args) {
            List<Number> numbers = new ArrayList<>();
            numbers.add(Number.FIVE);
            numbers.add(Number.ZERO);

            EnumSet<Number> enumSet = EnumSet.copyOf(numbers);
            System.out.println(enumSet); // [ZERO, FIVE] 출력.
        }
    }
    ```

이 외에 다른 메서드는 기존의 다른 `Set`과 동일하게 작동합니다.

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