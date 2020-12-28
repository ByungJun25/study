# 목표
자바의 패키지에 대해 학습하세요.

## 학습 내용
* [Package](#Package)
  * package란(#package란)
  * pacakge를 사용하는 이유(#pacakge를-사용하는-이유)
  * package 생성(#package-생성)
  * package 네이밍(#package-네이밍)
* [Import](#Import)
  * import란(#import란)
  * import 사용법(#import-사용법)
  * import static 키워드(#import-static-키워드)
* [클래스패스](#클래스패스)
  * CLASSPATH 환경변수(#CLASSPATH-환경변수)
  * classpath 옵션(#classpath-옵션)
* [접근 제어지시자(Access Modifier)](#접근-제어지시자Access-Modifier)
* [참고 사이트](#참고-사이트)

## Package

### package란
패키지(package)란 접근 보호 및 name space 관리를 제공하는 관련 타입의 그룹을 의미합니다. 쉽게 말하면 파일시스템의 폴더처럼 서로 관련된 `class`, `interface`, `enumeration`, `annoataion` 타입을 한곳에 묶어두는 것을 의미합니다.

### pacakge를 사용하는 이유
- 쉽게 관련된 타입을 정할 수 있기때문입니다.
- 관련된 기능을 가진 클래스를 어디서 찾을 수 있는지 쉽게 알 수 있기때문입니다.
- 서로 다른 패키지의 같은 이름의 타입을 서로 충돌없이 사용할 수 있기때문입니다.

### package 생성
package는 `pacakge` 키워드를 가진 선언문을 통해 생성 할 수 있습니다.

```java
package com.example.demo;

public class Demo {
    
}
```

pacakge 생성에는 다음의 제약 조건이 있습니다.
- package 선언문은 무조건 소스 파일의 첫 줄에 있어야합니다.
- pacakge 선언문은 하나의 소스파일에 단 한개만 존재해야합니다.

### package 네이밍
package의 이름을 지정할때는 다음의 제약 조건이 있습니다.
- package의 이름은 파일이 위치한 폴더의 경로이름과 같아야합니다.
- package의 이름에 Java 언어의 예약어를 사용할 수 없습니다.

보통 package의 이름을 정할때, 다음의 규약을 따릅니다. (필수는 아니지만 권장됩니다.)
- pacakge의 이름을 java로 시작하지 않습니다. 왜냐하면 이 이름은 java vendor사에서 사용하고 있기때문에, 사용할 경우 패키지 명이 혼동될 수 있습니다.
- 클래스의 이름과 충돌을 방지하기 위해 package의 이름은 모두 소문자로 지정합니다.
- 회사나 단체의 경우, package의 시작 이름은 그 회사나 단체의 도메인의 역순으로 지정합니다.  
    |예시 package 명|설명|
    |---|---|
    |com.example.demo|example.com 도메인을 가진 회사의 demo pacakge|
    |org.example.demo|example.org 도메인을 가진 단체의 demo pacakge|
- 그 외에는 각 회사나 단체내의 규약에 따라 이름을 정합니다.

## Import

### import란
서로 다른 패키지에서 클래스를 사용하기 위해서는 클래스명 앞에 꼭 패키지명을 붙여야 합니다. 하지만 `import` 키워드를 통해 사용하고자 하는 클래스의 패키지명을 선언해 주면, 서로 다른 패키지더라도 패키지명 없이 클래스를 바로 사용할 수 있습니다. 

### import 사용법
`import` 키워드는 `import 패키지명.클래스명`으로 사용할 수 있습니다.

```java
import java.util.List; // java.util 패키지의 List 클래스를 import
import java.util.ArrayList; // java.util 패키지의 ArrayList 클래스를 import

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
    }
}
```

또한 하나의 패키지에 속한 다수의 클래스를 import할 때는 `import 패키지명.*`으로 사용할 수도 있습니다.

```java
import java.util.*; // java.util 패키지의 모든 클래스를 import

public class Main {
    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
    }
}
```

다음의 경우에는 import를 하지 않아도 문제없이 클래스를 사용할 수 있습니다.
- `java.lang` 패키지
- 같은 패키지

```java
// package com.example.demo;
public class Demo {

}

// package com.example.demo;
public class Main {
    public static void main(String[] args) {
        Demo demo = new Demo(); // 별다른 import없이 사용이 가능합니다.
        System.out.println("TEST!"); // java.lang 패키지에 속한 System 또한 별다른 import 없이 사용이 가능합니다.
    }
}

```

### import static 키워드
static 변수나 static 메서드를 다른 패키지에서 바로 사용하고자 할 때 `import static`을 사용할 수 있습니다. `import static`를 사용하면 정적 변수나 정적 메서드를 클래스명을 적지 않아도 바로 사용할 수 있습니다.

보통 테스트 코드 작성 시, Assertions 클래스의 정적 메서드들은 `import static`을 사용해 선언하고, 바로 이용하곤 합니다.

```java
import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Main {
    public static void main(String[] args) {
        double PI_Value = PI; // PI라는 static 변수를 `Math`라는 클래스명 없이 바로 사용할 수 있습니다.
        assertEquals(PI, PI_Value); // assertEquals라는 Assertions 클래스의 static 메서드를 바로 사용할 수 있습니다.
    }
}
```

## 클래스패스


### CLASSPATH 환경변수


### classpath 옵션


## 접근 제어지시자(Access Modifier)
접근 제어지시자는 클래스, 메서드, 인스턴스 및 클래스 변수를 선안할 때 사용됩니다.

접근 제어지시자에는 다음의 종류가 있습니다.

||클래스내부|동일패키지|상속받은클래스|이외의 영역|
|---|---|---|---|---|
|private| O | X | X | X |
|default(no modifier) / package-private| O | O | X | X |
|protected| O | O | O | X |
|public| O | O | O | O |

* `public`
    : 어디에서나 접근 가능.
* `protected`
    : 같은 패키지거나 상속 받은 경우에만 접근 가능.
* `package-private`
    : 같은 패키지에서만 접근 가능.
* `private`
    : 해당 클래스 내에서만 접근 가능.

```java
// package com.example.demo
public class Demo {
    private int private_number = 10;
    protected int protected_number = 20;
    int default_number = 30;
    public int public_number = 40;

    public void print() {
        System.out.println(this.private_number); // 해당 클래스에서는 private 타입 변수에 접근이 가능합니다.

        System.out.println(this.protected_number);

        System.out.println(this.default_number);

        System.out.println(this.public_number);
    }
}

// package com.example.demo // 같은 패키지
public class Demo2 {
    public void print() {
        Demo demo = new Demo();
        // System.out.println(demo.private_number); // private 타입이므로 접근이 불가능하여 컴파일 에러가 발생합니다.

        System.out.println(demo.protected_number); // 같은 패키지이므로 protected 변수에 접근이 가능합니다.

        System.out.println(demo.default_number); // 같은 패키지이므로 package-private 변수에 접근이 가능합니다.

        System.out.println(demo.public_number); // public은 어디에서나 접근이 가능합니다.
    }
}

// package com.example.child // 다른 패키지 - 상속
public class ChildDemo extends Demo {
    public void print() {
        Demo demo = new Demo();
        // System.out.println(demo.private_number); // private 타입이므로 접근이 불가능하여 컴파일 에러가 발생합니다.

        // System.out.println(demo.protected_number); // 서로 다른 패키지이기 때문에 protected 타입의 변수에 접근이 불가능하여 컴파일 에러가 발생합니다.

        // System.out.println(demo.default_number); // package-private 타입이므로 접근이 불가능하여 컴파일 에러가 발생합니다.

        System.out.println(demo.public_number); // public은 어디에서나 접근이 가능합니다.

        // System.out.println(this.private_number); // private 타입이므로 접근이 불가능하여 컴파일 에러가 발생합니다.

        System.out.println(this.protected_number); // 상속을 받았으므로 접근이 가능합니다.

        // System.out.println(this.default_number); // package-private 타입이므로 접근이 불가능하여 컴파일 에러가 발생합니다.
    }
}

// package com.example.main // 다른 패키지
public class Main {
    public void print() {
        Demo demo = new Demo();
        // System.out.println(demo.private_number); // private 타입이므로 접근이 불가능하여 컴파일 에러가 발생합니다.

        // System.out.println(demo.protected_number); // protected 타입이므로 접근이 불가능하여 컴파일 에러가 발생합니다.

        // System.out.println(demo.default_number); // package-private 타입이므로 접근이 불가능하여 컴파일 에러가 발생합니다.

        System.out.println(demo.public_number); // public은 어디에서나 접근이 가능합니다.
    }
}
```

## 참고 사이트
* [Oracle java document](https://docs.oracle.com/javase/tutorial/java/package/index.html)