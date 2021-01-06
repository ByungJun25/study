# 목표
자바의 인터페이스에 대해 학습하세요.

## 학습 내용
* [Interface](#Interface)
  * [Interface란](#Interface란)
  * [Interface 정의하기](#Interface-정의하기)
  * [Interface 구현하기](#Interface-구현하기)
  * [Interface 사용하기](#Interface-사용하기)
  * [Interface의 상속](#Interface의-상속)
  * [Interface의 default method](#Interface의-default-method)
  * [Interface의 static method](#Interface의-static-method)
  * [Interface의 private method](#Interface의-private-method)
* [참고 사이트](#참고-사이트)

# Interface

## Interface란
`Interface`는 클래스들이 구현해야 하는 동작을 지정하는데 사용되는 추상 자료형입니다. 우리가 어떤 프로그램을 여러명이서 개발한다고 하였을때, 각자가 맡은 기능에 대해 인터페이스를 정의해두면, 우리는 그 기능이 내부적으로 어떻게 구현되었는지 몰라도 그 기능을 사용하는데 문제가 없을것입니다. 예를 들자면, `A 개발자`가 `A1`이라는 기능을 만드는데 이 기능은 `A()`라고 호출하면 된다고 인터페이스를 정의하였으면, `B 개발자`는 `A1`기능을 이용하는 `B1`이라는 기능을 만들때, `A()`으로 `A1`기능을 호출하기만 하면될뿐, `A1`이 어떻게 구성되어있는지는 몰라도 되는것입니다.

`Interface`는 다음의 특징을 가지고 있습니다.
- 상수와 메서드 헤더만 선언할 수 있습니다.
- 인스턴스화 될 수 없습니다.
- 다른 클래스에 의해 구현될 수 있습니다.
- 일반 클래스를 상속할 수 없습니다.
- **여러 다른 인터페이스를 상속 할 수 있습니다.**
- `Java 8`부터 메서드 body를 구현할 수 있는 `default`와 `static` 메서드를 선언 할 수 있습니다.
-  `Java 9`부터 `private`와 `private static` 메서드를 선언 할 수 있습니다.

## Interface 정의하기
`Interface`는 `interface` 키워드를 이용하여 선언할 수 있습니다.  

```java
public interface InterfaceName extends OtherInterface, OtherInterface2 {
    
    int number = 10;

    void method();

    default void print() {
        System.out.println("print method!");
    }

    static void staticMethod() {
        System.out.println("staticMethod!");
    }

    private void privateMethod() {
        System.out.println("privateMethod!");
    }

    private static void privateStaticMethod() {
        System.out.println("privateStaticMethod!");
    }
}
```

`Interface`를 정의할때는 다음의 조건이 있습니다.
- `interface`에는 `public`과 `package-private` 접근 지시 제어자만 사용할 수 있습니다.
- 상수(`static final` 키워드가 붙은 변수)와 메서드 헤더만 선언 할 수 있습니다.
- 모든 변수는 암묵적으로 `static final`으로 취급됩니다.
- 모든 메서드 시그니처는 암묵적으로 `public`으로 취급됩니다.
- 인터페이스 내의 모든 메서드는 이미 정의상 추상적이기 때문에, `abstract` 키워드가 필요하지 않습니다.

## Interface 구현하기


## Interface 사용하기


## Interface의 상속


## Interface의 default method


## Interface의 static method


## Interface의 private method


## 참고 사이트
* [Oracle Java Document](https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html)