# 목표
자바의 멀티쓰레드 프로그래밍에 대해 학습하세요.

## 학습 내용
* [Process와 Thread](#Process와-Thread)
  * [Process란](#Process란)
  * [Thread란](#Thread란)
* [Thread](#Thread)
  * [Thread 선언과 실행](#Thread-선언과-실행)
    * [Thread 객체](#Thread-객체)
    * [Runnable 인터페이스](#Runnable-인터페이스)
    * [sleep, interrupt, join](#sleep-interrupt-join)
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
Process란 자기 자신의 메모리 영역과 runtime때 필요한 모든 리소스를 가진 실행환경을 말합니다. 쉽게 말해 우리가 프로그램이라고 하는 것을 실행하면 하나의 Process가 실행된다고 생각하시면 됩니다.

## Thread란
Thread란 독립적으로 관리될 수 있는 프로그래밍 된 명령어의 가장 작은 단위입니다. Process는 적어도 하나 이상의 Thread를 가지며, Thread는 Process의 리소스를 공유합니다. 

# Thread 

## Thread 선언과 실행


## Thread 객체

## Runnable 인터페이스

## sleep, interrupt, join

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