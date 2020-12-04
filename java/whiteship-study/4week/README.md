# 목표
자바가 제공하는 제어문을 학습하세요.

## 학습 내용
* [선택문](#선택문)
  * [if](#if)
  * [if~else](#if-else)
  * [switch](#switch)
* [반복문](#반복문)
  * [while](#while)
  * [do~while](#do-while)
  * [for](#for)
  * [enhanced for](#enhanced-for)
* [분기문](#분기문)
  * [break](#break)
  * [continue](#continue)
  * [return](#return)

## 과제
* [JUnit 5](#JUnit-5)
  * [JUnit이란](#JUnit이란)
  * [JUnit in VSCode](#JUnit-in-VSCode)
* [LinkedList](#LinkedList)
  * [LinkedList란](#LinkedList란)
  * [LinkedList 구현](#LinkedList-구현)
* [Stack](#Stack)
  * [Stack이란](#Stack이란)
  * [Stack 구현](#Stack 구현)
* [Queue](#Queue)
  * [Queue란](#Queue란)
  * [Queue 구현](#Queue 구현)

### 선택문
선택문이란, 어떤 특정 조건에 따라 실행 순서를 변경하도록 제어하는 구문을 말합니다.  
선택문에는 `if`,`if ~ else`, `switch`문이 있습니다.  

#### if
`if`문은 조건식이 참일 경우, 코드를 실행하는 구문을 말합니다.

```java
public static void main(String[] args) {

  int value = 2;
  
  if(value % 2 == 0) { // 조건을 만족하기때문에, 다음의 문장을 출력합니다.
    System.out.println("value는 2의 배수입니다.");
  }
  
  if(value % 3 == 0) { // 조건을 만족하지 않기때문에, 다음의 문장을 출력하지 않습니다.
    System.out.println("value는 2의 배수입니다.");
  }

}
```

#### if~else
앞서 `if`문은 조건식이 참일 경우, 코드를 실행하는 구문이라고 설명했습니다.  
`else`는 `if`의 조건을 만족하지 않을 경우 실행하는 부분입니다.  
`if`문의 경우 중첩 사용이 얼마든지 가능합니다.

```java
public static void main(String[] args) {
		int value = 15;

		// if ~ else 경우
		if (value % 2 == 0) {
			System.out.println("value는 2의 배수입니다.");
		} else {
			System.out.println("value는 2의 배수가 아닙니다.");
		}

		// 중첩 if문
		if (value % 5 == 0) {
			if (value % 3 == 0) {
				System.out.println("value는 5의 배수이면서, 3의 배수입니다.");
			} else {
				System.out.println("value는 5의 배수이지만, 3의 배수가 아닙니다.");
			}
		}

		// if ~ else if ~ else 의 경우
		if (value % 2 == 0) {
			System.out.println("value는 2의 배수입니다.");
		} else if (value % 5 == 0) {
			System.out.println("value는 5의 배수입니다.");
		} else {
			System.out.println("value는 2의 배수도 5의 배수도 아닙니다.");
		}
}
```

#### switch
`switch`문의 경우 앞서 3주차에 설명한 것으로 대신하도록 하겠습니다.  
[switch operator 설명 바로가기](https://github.com/ByungJun25/study/tree/main/java/whiteship-study/3week#switch-%EC%97%B0%EC%82%B0%EC%9E%90java13)  
> `if`문이 여러개일 경우, `switch`문을 사용하는 것이 가독성도 더 좋고, 실행 속도도 더 빠릅니다.

### 반복문
반복문이란, 어떤 특정 조건이 만족하는한 계속해서 실행하는 구문을 말합니다.  
반복문에는 `while`, `do ~ while`, `for` 문이 있습니다.

#### while
`while`문은 조건이 참인 동안, 계속해서 실행하는 구문을 말합니다.  
`while`문은 계속해서 조건식의 결과를 판별하며, 조건이 `false`가 될때까지 코드를 실행합니다.  

```java
public static void main(String[] args) {
		int num = 1;
		while (num < 11) { // num의 값이 11미만일때까지 실행합니다.
			System.out.println("Number is: " + num);
			num++;
		}
}
```

`while`문은 다음과 같이 무한 루프로 사용할 수도 있습니다.
```java
public static void main(String[] args) {
		while (true) {
			// Some codes.
		}
}
```

#### do~while
`do ~ while`문은 `while`문과 달리 조건식을 루프의 바닥에 적는데, 이로 인해 `do`블럭안에 적힌 코드가 항상 최소 한번은 실행되도록 하는 구문입니다.  
```java
public static void main(String[] args) {
    int num = 1;
    do {
      System.out.println("Number is: " + num); // Number is: 1 은 조건에 관계없이 무조건 출력됩니다.
      num++;
    } while (num < 11)
}
```

#### for
`for` 문은 어떤 값의 범위동안만 반복해서 실행하는 구문을 말합니다.  
`for`문은 종종 `for loop`라고 불리는데, 이는 `for`문이 특정 조건을 만족할때까지만 반복적으로 실행되기 때문입니다.  
`for`문의 문법은 다음과 같습니다.  
```java
for(initialization; termination; increment) {
  statement(s)
}
```

위와 같은 `for`문을 사용할때 다음의 내용을 명심하셔야 합니다.
* 초기화 표현식(initialization 부분)는 루프를 초기화 합니다. 또한 이 초기화는 루프가 실행될때 딱 한번만 실행됩니다.
* 초기화 표현식에 선언된 변수는 `for`문 내에서만 유효합니다.
* 종료 표현식(termination 부분)의 결과가 `false`이면, 루프는 종료됩니다.
* 증감 표현식(increment 부분)은 각 반복 후에 실행됩니다.
* 모든 표현식은 옵션이므로 생략이 가능합니다.

```java
public static void main(String[] args) {
    for(int i = 0; i < 10; i++) {
      System.out.println("i is: " + i); //총 10번 출력하게됩니다.
    }
}
```

#### enhanced for
`for`문은 `Collection`이나 `Array`를 통해서 다르게 표현될수도 있습니다. 이러한 표현방식을 `enhanced for`문이라고 말하기도 합니다.  
```java
public static void main(String[] args) {
    int[] numbers = {1,2,3,4,5,6,7,8};

    for(int number: numbers) {
      System.out.println("number is: " + number); //numbers 배열의 크기만큼, 총 8번 출력하게됩니다.
    }
}
```

### 분기문
#### break
반복문에서 `break`문을 만나면 코드의 실행을 중단하고 반복문을 밖으로 나갑니다.  
`break`문은 `labeled break`와 `unlabeled break`가 있습니다.

**1. unlabeled break**  
반복문내에서 `break`문을 만나 루프를 탈출 할때, 가장 가까운 반복문만 탈출하는 `break`문입니다.
```java
public static void main(String[] args) {
  for(int i = 0; i < 3; i++) {
    System.out.println("i is: " + i); // 0부터 2까지 3번 출력됩니다.
    for(int j = 0; j < 5; j++) {
      if(j == 3) {
        break;
      }
      System.out.println("j is: " + j); // 0,1,2만 출력됩니다. 왜냐하면 3일경우 break문을 통해 for문을 벗어나기 때문입니다.
    }
  }
}
```

**2. labeled break**  
반복문내에서 `break`문을 만나 루프를 탈출 할때, 어느 반복문까지 탈출할 것인지를 명시하는 `break`문입니다. 
```java
public static void main(String[] args) {
  PARENT:
  for(int i = 0; i < 10; i++) {
    System.out.println("i is: " + i); // i is 0 만 출력됩니다. 왜냐하면 아래의 내부 for문에서 상위 for문까지 함께 벗어나도록 break문을 사용하였기 때문입니다.
    for(int j = 0; j < 10; j++) {
      if(j == 3) {
        break PARENT;
      }
      System.out.println("j is: " + j);
    }
  }
}
```

#### continue
반복문에서 `continue`문을 만나면, 현재 반복을 넘어가고 다음 반복을 실행하도록 합니다.  
`continue`문은 `labeled continue`와 `unlabeled continue`가 있습니다.

**1. unlabeled continue**  
반복문내에서 `continue`문을 만나 루프를 건너뛸때, 가장 가까운 반복문만 건너뛰는 `continue`문입니다.
```java
public static void main(String[] args) {
  for (int j = 0; j < 10; j++) {
    if (j % 3 != 0) {
      continue;
    }
    System.out.println("j is: " + j); // 0, 3, 6, 9가 출력됩니다. 나머지는 앞의 continue 문을 만나 건너뛰기때문에 출력되지 않습니다.
  }
}
```

**2. labeled continue**  
반복문내에서 `continue`문을 만나 루프를 건너뛸때, 어느 반복문까지 건너뛸지 명시하는 `continue`문입니다.
```java
public static void main(String[] args) {
  PARENT:
  for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 10; j++) {
      if (j % 3 != 0) {
        continue PARENT;
      }
      System.out.println("j is: " + j); // 오로지 0만 3번 출력합니다. 왜냐하면 앞의 continue 문을 통해 상위 for문을 건너뛰기 때문입니다.
    }
    System.out.println("i is: " + i); // 내부 for문의 continue로 인해 출력되지 않습니다.
  }
}
```

#### return
반복문에서 `return`을 만나면, 반복문을 호출한 메서드를 종료합니다.  
`return`문은 메서드의 반환값 정의에 따라 반환값을 가질수도 있고 안 가질수도 있습니다.
```java
public static void main(String[] args) {
  for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 10; j++) {
      if (j % 3 != 0) {
        return;
      }
      System.out.println("j is: " + j); // 오로지 0만 한번 출력합니다. 왜냐하면 앞의 return 문을 통해 메서드가 종료되기 때문입니다.
    }
    System.out.println("i is: " + i); // 내부 for문의 return으로 인해 출력되지 않습니다.
  }
}
```

### JUnit 5

### LinkedList

### Stack

### Queue