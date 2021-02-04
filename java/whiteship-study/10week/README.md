# 목표
자바의 멀티쓰레드 프로그래밍에 대해 학습하세요.

## 학습 내용
* [Process와 Thread](#Process와-Thread)
  * [Process란](#Process란)
  * [Thread란](#Thread란)
* [Thread](#Thread)
  * [Thread 선언과 실행](#Thread-선언과-실행)
    * [Runnable 인터페이스](#Runnable-인터페이스)
    * [Thread 객체](#Thread-객체)
    * [start, run, sleep, interrupt, join](#start-run-sleep-interrupt-join)
  * [Main Thread](#Main-Thread)
  * [데몬 Thread](#데몬-Thread)
  * [Thread 그룹](#Thread-그룹)
  * [Thread 상태](#Thread-상태)
  * [Thread 우선순위](#Thread-우선순위)
* [동기화](#동기화)
  * [메모리 일관성 오류](#메모리-일관성-오류)
  * [synchronized 키워드](#synchronized-키워드)
  * [lock](#lock)
  * [Lock 클래스](#Lock-클래스)
  * [Condition 클래스](#Condition-클래스)
  * [Executor 인터페이스](#Executor-인터페이스)
  * [Atomic 변수](#Atomic-변수)
  * [Concurrent Collection](#Concurrent-Collection)
* [데드락](#데드락)
* [참고 사이트](#참고-사이트)

# Process와 Thread

## Process란
`Process`란 자기 자신의 메모리 영역과 runtime때 필요한 모든 리소스를 가진 실행환경을 말합니다. 쉽게 말해 우리가 프로그램이라고 하는 것을 실행하면 하나의 `Process`가 실행된다고 생각하시면 됩니다. `Process`는 실행될때, OS로부터 실행에 필요한 자원을 할당받습니다.

## Thread란
Thread란 독립적으로 관리될 수 있는 프로그래밍 된 명령어의 가장 작은 단위입니다. Process는 적어도 하나 이상의 Thread를 가지며, Thread는 Process의 리소스를 공유합니다.

# Thread 

## Thread 선언과 실행
`Thread`는 크게 `Runnable`인터페이스 혹은 `Thread` 클래스를 이용하여 선언할 수 있습니다.


## Thread 객체
`Thread` 객체를 상속한 후, `run`메서드를 오버라이딩하면 `Thread`를 선언할 수 있습니다.

```java
public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Sub thread!");
    }
}

public class Main {
    public static void main(String[] args) {
        Thread thread = new MyThread();
        thread.start();
    }
}
```

## Runnable 인터페이스
`Runnable` 인터페이스를 이용하여 선언할수도 있습니다.

`Runnable` 인터페이스의 `run` 메서드를 구현한 후, 이를 Thread 클래스 생성자의 파라미터로 전달합니다.

```java
public class MyThread implements Runnable {
    @Override
    public void run() {
        System.out.println("Sub thread!");
    }
}

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new MyThread());
        thread.start();
    }
}
```

## start, run, sleep, interrupt, join
`Thread` 클래스는 여러 메서드를 가지고 있습니다. 지금부터 각 메서드에 대해 알아보도록 하겠습니다.

1. start & run  
    `start` 메서드는 쓰레드를 실행시키는 메서드입니다(호출 스택을 하나 더 생성). `start`메서드를 호출하면 `Thread.State.NEW`상태가 되고 바로 실행되지는 않습니다. 또한 `start` 메서드는 단 한번만 호출될 수 있습니다. 2번이상 호출할 경우 `IllegalThreadStateException`이 발생합니다.

    `start`를 호출하지 않은 상태에서 `run`메서드를 호출할 경우, 이는 `main`쓰레드에서 단순히 메서드 호출을 할 뿐, 새로운 쓰레드로써 실행되지는 않습니다.

    `run` 메서드는 쓰레드가 실행된 후, 호출되는 실제 쓰레드의 동작을 담당하는 부분입니다. 필히 메서드를 오버라이딩하여 구현해야합니다.

    ```java
    public class MyThread implements Runnable {
        @Override
        public void run() { // 실제 쓰레드 실행시 작동되는 부분.
            System.out.println("Sub thread!");
        }
    }
    
    public class Main {
        public static void main(String[] args) {
            Thread thread = new Thread(new MyThread());
            thread.start(); // 쓰레드 실행.
        }
    }
    ```

1. sleep  
    `sleep` 메서드는 현재 쓰레드의 작동을 주어진 시간동안 미루는 메서드입니다. `sleep` 메서드로 인해 `Timed waiting`된 쓰레드는 외부에 의해 interrupted 될 수 있기때문에, `InterruptedException` 예외를 처리할 수 있도록 해야합니다.

    ```java
    public class MyThread implements Runnable {
        @Override
        public void run() { // 실제 쓰레드 실행시 작동되는 부분.
            try {
                Thread.sleep();
            } catch (InterruptedException e) {
                System.out.println("Interrupted!");
            }
            System.out.println("Sub thread!");
        }
    }
    
    public class Main {
        public static void main(String[] args) {
            Thread thread = new Thread(new MyThread());
            thread.start(); // 쓰레드 실행.
        }
    }
    ```

1. interrupt  
    `interrupt` 메서드는 대기

    ```java
    ```

1. join  


    ```java
    ```


## Main Thread

## 데몬 Thread

## Thread 그룹

## Thread 상태

## Thread 우선순위

## 동기화

## 메모리 일관성 오류

## synchronized 키워드

## lock

## Lock 클래스

## Condition 클래스

## Executor 인터페이스

## Atomic 변수

## Concurrent Collection

## 데드락

## 참고 사이트
- [Oracle Document - Processes and Threads](https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html)
- [https://sujl95.tistory.com/63](https://sujl95.tistory.com/63)
- [https://blog.naver.com/hsm622/222212364489](https://blog.naver.com/hsm622/222212364489)
- [https://www.notion.so/ac23f351403741959ec248b00ea6870e](https://www.notion.so/ac23f351403741959ec248b00ea6870e)