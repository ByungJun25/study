# 목표
자바가 제공하는 다양한 연산자를 학습하세요.

## 학습 내용
* [산술 연산자](#산술-연산자)
* [비트 연산자](#비트-연산자)
* [관계 연산자](#관계-연산자)
* [논리 연산자](#논리-연산자)
* [instanceof](#instanceof)
* [assignment(=) operator](#assignment(=)-operator)
* [화살표(->) 연산자](#화살표(->)-연산자)
* [3항 연산자](#3항-연산자)
* [연산자 우선 순위](#연산자-우선-순위)
* [switch 연산자(Java13)](#switch-연산자(Java13))
* [참고사이트](#참고사이트)

### 연산자란?
연산자란 연산을 수행하는 기호를 의미합니다.

### 산술 연산자
산술 연산자는 사칙연산을 다루는 기본 연산자를 말합니다. 산술 연산자는 두 개의 피연산자를 가지는 이항 연산자이며, 피연산자들의 결합 방향은 왼쪽에서 오른쪽입니다.  
연산자의 종류는 다음과 같습니다.

|산술 연산자|설명|
|---------|---------|
|`+`| 왼쪽의 피연산자에 오른쪽의 피연산자를 더합니다.|
|`-`| 왼쪽의 피연산자에서 오른쪽의 피연산자를 뺍니다.|
|`*`| 왼쪽의 피연산자에 오른쪽의 피연산자를 곱합니다.|
|`/`| 왼쪽의 피연산자를 오른쪽의 피연산자로 나눕니다.|
|`%`| 왼쪽의 피연산자를 오른쪽의 피연산자로 나눈 후, 나머지를 반환합니다.|

```java
public static main(String[] args) {
    int n1 = 10;
    int n2 = 2;

    int plusOp = n1 + n2;
    int minusOp = n1 - n2;
    int applyOp = n1 * n2;
    int divideOp = n1 / n2;
    int modulusOp = n1 % n2;

    System.out.println("(+) 연산자 결과: "+plusOp); // (+) 연산자 결과: 12
    System.out.println("(-) 연산자 결과: "+minusOp); // (-) 연산자 결과: 8
    System.out.println("(*) 연산자 결과: "+applyOp); // (*) 연산자 결과: 20
    System.out.println("(/) 연산자 결과: "+divideOp); // (/) 연산자 결과: 5
    System.out.println("(%) 연산자 결과: "+modulusOp); // (%) 연산자 결과: 0
}
```

### 비트 연산자
비트 연산자는 비트(bit) 단위로 논리 연산을 할 때 사용하는 연산자입니다.  
비트 단위로 왼쪽이나 오른쪽으로 전체 비트를 이동하거나, 1의 보수를 만들 때도 사용됩니다.  
비트 연산자의 종류는 다음과 같습니다.

|비트 연산자|설명|
|---------|---------|
|`&`| 대응되는 비트가 모두 1이면 1을 반환합니다.(비트 AND 연산)|
|`\|`| 대응되는 비트 중에서 하나라도 1이면 1을 반환합니다.(비트 OR 연산)|
|`^`| 대응되는 비트가 서로 다르면 1을 반환합니다.(비트 XOR 연산)|
|`~`| 비트를 1이면 0으로, 0이면 1로 반전시킵니다.(비트 NOT 연산)|
|`<<`| 명시된 수만큼 비트들을 전부 왼쪽으로 이동시킵니다.(LEFT SHIFT 연산)|
|`>>`| 부호를 유지하면서 지정한 수만큼 비트를 전부 오른쪽으로 이동시킵니다.(RIGHT SHIFT 연산)|
|`>>>`| 지정한 수만큼 비트를 전부 오른쪽으로 이동시키며, 새로운 비트는 0으로 만듭니다.|

```java
public static main(String[] args) {
    int n1 = 10;
    int n2 = -2;

    int andOp = n1 & n2;
    int orOp = n1 | n2;
    int xorOp = n1 ^ n2;
    int notOp = ~n2;

    int leftShiftOp = n2 << 2;
    int rightShiftOp = n2 >> 2;
    int logicalshiftrightOp1 = n1 >>> 2;
    int logicalshiftrightOp2 = n2 >>> 2;

    System.out.println("10과 -2의 (&) 연산자 결과: "+andOp); // 10과 -2의 (&) 연산자 결과: 10
    System.out.println("10과 -2의 (|) 연산자 결과: "+orOp); // 10과 -2의 (|) 연산자 결과: -2
    System.out.println("10과 -2의 (^) 연산자 결과: "+xorOp); // 10과 -2의 (^) 연산자 결과: -12
    System.out.println("-2의 (~) 연산자 결과: "+notOp); // -2의 (~) 연산자 결과: 1
    System.out.println("-2의 (<<) 연산자 결과: "+leftShiftOp); // -2의 (<<) 연산자 결과: -8
    System.out.println("-2의 (>>) 연산자 결과: "+rightShiftOp); // -2의 (>>) 연산자 결과: -1
    System.out.println("10의 (>>>) 연산자 결과: "+logicalshiftrightOp1); // 10의 (>>>) 연산자 결과: 2
    System.out.println("-2의 (>>>) 연산자 결과: "+logicalshiftrightOp2); // -2의 (>>>) 연산자 결과: 1073741823
}
```
* **AND 연산**  
![andOp](./andOp.PNG)

* **OR 연산**  
![orOp](./orOp.PNG)

* **XOR 연산**  
![xorOp](./xorOp.PNG)

* **NOT 연산**  
![notOp](./notOp.PNG)

* **LEFT SHIFT 연산**  
![leftshiftOp](./leftshiftOp.PNG)

* **RIGHT SHIFT 연산**  
![rightshift](./rightshift.PNG)

* **LOGICAL RIGHT SHIFT 연산**  
![logicalshiftrightOp1](./logicalshiftrightOp1.PNG)

* **LOGICAL RIGHT SHIFT 연산**  
![logicalshiftrightOp2](./logicalshiftrightOp2.PNG)


### 관계 연산자


### 논리 연산자


### instanceof


### assignment(=) operator


### 화살표(->) 연산자


### 3항 연산자


### 연산자 우선 순위


### switch 연산자(Java13)


### 참고사이트
