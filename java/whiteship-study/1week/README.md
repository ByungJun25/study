# 목표
자바 소스 파일(.java)을 JVM으로 실행하는 과정 이해하기.

## 학습 내용
* [JVM](#JVM)
  * [JVM이란](#JVM이란)
  * [JVM의 구성 요소](#JVM의-구성-요소)
    * [Class Loaders](#Class-Loaders)
      * [Bootstrap Class Loader](#Bootstrap-Class-Loader)
      * [Extension Class Loader](#Extension-Class-Loader)
      * [System Class Loader](#System-Class-Loader)
      * [Class Loader 동작 방식](#Class-Loader-동작-방식)
    * [Run-Time Data Areas](#Run-Time-Data-Areas)
      * [Method Area](#Method-Area)
      * [Heap Area](#Heap-Area)
      * [Stack Area](#Stack-Area)
      * [PC Registers](#PC-Registers)
      * [Native method stacks](#Native-method-stacks)
    * [Execution Engine](#Execution-Engine)
      * [Interpreter](#Interpreter)
      * [JIT Compiler](#JIT-Compiler)
        * [JIT 컴파일러란](#JIT-컴파일러란)
        * [JIT 컴파일러 동작 방식](#JIT-컴파일러-동작-방식)
      * [Garbage Collector](#Garbage-Collector)
    * [Java Native Interface](#Java-Native-Interface)
    * [Native Libraries](#Native-Libraries)
* [JDK와 JRE](#JDK와-JRE)
  * [JDK란](#JDK란)
  * [JRE란](#JRE란)
* [컴파일 및 실행](#컴파일-및-실행)
  * [컴파일이란](#컴파일이란)
  * [자바코드를 컴파일해보자](#자바코드를-컴파일해보자)
  * [자바코드를 실행하는 방법](#자바코드를-실행하는-방법)
* [바이트코드](#바이트코드)
  * [바이트코드란](#바이트코드란)
  * [바이트코드 보는법](#바이트코드-보는법)
* [참고사이트](#참고사이트)

# JVM

## JVM이란
`Java Virtual Machine(JVM)`은 자바 프로그램을 실행하는 가상 머신의 구현체입니다.

`JVM`은 바이트 코드를 해석하고 클래스 정보를 메모리 영역에 저장하며, 이를 실행합니다.

`JVM`을 통해 자바 프로그램은 플랫폼에 종속적이지 않고 실행될 수 있습니다.

## JVM의 구성 요소
`JVM`은 다음과 같이 구성됩니다.
- 클래스 로더 시스템
- 실행 시점 메모리 영역
- 실행 엔진
- JNI(자바 네이티브 인터페이스)
- 네이티브 메소드 라이브러리

![JVM Architecture](./JVM_Architecture_PlatformEngineer.PNG)  
[이미지 출처: PlatformEngineer.com](https://medium.com/platform-engineer)

## Class Loaders
- `Class Loader`는 런타임 중에 Java 클래스를 JVM에 동적으로 로드합니다.
- `Class Loader`는 `JRE`의 구성 요소중 하나입니다.
- `Class Loader` 덕분에 `JVM`에서는 자바 프로그램을 실행하기위해 파일 시스템을 알 필요가 없습니다.
- `Class Loader`가 특정 클래스가 필요할때만 클래스를 로드하기때문에, 모든 클래스를 한번에 다 메모리에 로드할 필요가 없습니다.
- `Class Loader`에는 `Boostrap Class Loader`, `Extension Class Loader`, `System Class Loader`가 있습니다.

## Bootstrap Class Loader
- `Bootstrap Class Loader`는 모든 클래스 로더 인스턴스의 상위 클래스 역할을 담당합니다.
- `Bootstrap Class Loader`는 JDK 내부 클래스들(`$JAVA_HOME/jre/lib`에 위치한 코어 라이브러리)을 로드하는 역할을 담당합니다.
- `Bootstrap Class Loader`는 `core JVM`의 구성 요소이며 네이티브 코드로 작성되어 있습니다. 따라서 각 다른 플랫폼마다 구현이 다를 수 있습니다.

## Extension Class Loader
- `Extension Class Loader`는 `Bootstrap Class Loader`의 하위 클래스입니다.
- `Extension Class Loader`는 standard core 자바 클래스들의 확장 클래스들을 로드하는 역할을 담당합니다.
- `Extension Class Loader`는 `$JAVA_HOME/lib/ext` 폴더 또는 `java.ext.dirs`이라는 시스템 변수로 등록된 폴더의 확장 클래스들을 로드합니다.
- `Extension Class Loader`로 로드된 클래스들은 해당 플랫폼에서 실행되는 모든 어플리케이션에서 사용이 가능합니다.

## System Class Loader
- `System Class Loader`는 모든 application 레벨의 클래스들을 `JVM`으로 로드합니다.
- `System Class Loader`는 `classpath`로 주어진 경로에서 파일들을 로드합니다.
- `System Class Loader`는 `Extension Class Loader`의 하위 클래스입니다.

## Class Loader 동작 방식
`Class Loader`는 `JVM`으로부터 클래스를 요청받으면 해당 클래스에 대한 정의를 찾아서 런타임에 이를 로드하도록 합니다.  
해당 클래스를 찾을때는 패키지명을 포함한 클래스 이름인 `Fully Qualified Class Name`을 사용합니다.

`java.lang.ClassLoader.loadClass()` 메서드가 클래스 정의를 런타임에 로드하는 역할을 합니다. 만약 클래스가 로드되어 있지 않다면, 재귀적으로 부모 클래스 로더에 이를 위임합니다.

만약 클래스를 로드하지 못한다면 `NoClassDefFoundError`나 `ClassNotFoundException`이 발생하게 됩니다.

## Run-Time Data Areas
`JVM`은 자바 프로그램을 실행하기 위해 여러 다양한 메모리 영역을 정의하고 있습니다.

## Method Area
- `Method Area(메서드 영역)`는 `PermGen(permanent generation space)`이라고 불리며 `JVM`이 시작할때 생성됩니다.
- `Method Area`는 모든 클래스에 대해 필드나 메서드 데이터, 생성자, 런타임 constant pool등과 같은 구조를 저장합니다.
- `Method Area`는 `JVM`의 쓰레드가 이 영역을 공유합니다.
- `Method Area`를 위한 메모리는 연속적일 필요가 없습니다.

## Heap Area
- `Heap Area(힙 영역)`는 모든 클래스 인스턴스 및 배열에 메모리를 할당하기 위해 사용됩니다.
- `GC(Garbage Collector)`가 각 인스턴스의 힙 메모리를 회수합니다.
- `Heap Area`는 다음의 3가지로 구성됩니다.
    - Eden Space: `Young Generation space`의 한 부분이며, 객체를 생성할때 `JVM`이 이 영역으로부터 메모리를 할당합니다.
    - Survivor Space: `Young Generation space`의 한 부분이며, `Garbage Collector`의 `minor GC` 단계때 회수되지 않은 객체를 가지고 있습니다.
    - Tenured Space: `Old Generation space`라고 불리기도 하며, 오랜 기간 회수되지 않은 객체를 가지고 있습니다. 보통 `Young Generation object`에 대해 임계값이 설정되고, 설정된 임계값을 충족한 객체들이 이 공간으로 이동됩니다.
- `Heap Area`는 `JVM`이 시작될때 생성됩니다.
- `JVM`의 모든 쓰레드가 이 영역을 공유합니다.
- `Heap Area`를 위한 메모리는 연속적일 필요가 없습니다.

## Stack Area
- `Stack Area(스택 영역)`는 데이터를 프레임으로써 저장하며, 각 프레임은 지역 변수, 부분 결과값, 중첩된 메소드 호출을 저장합니다.
- `JVM`은 새 쓰레드를 생성할때마다 `Stack Area`를 생성합니다.
- `Stack Area`는 각 쓰레드별로 분리됩니다. (공유되지 않습니다.)
- 각 stack을 `Stack Frame` 혹은 `Activation record`라고 부릅니다. 각 프레임은 다음의 요소를 가지고 있습니다.
    - Local Variable Array: 모든 지역 변수 및 메소드의 파라미터를 가지고 있습니다.
    - Operand Stack: 중간 계산 결과를 저장하기위해 사용됩니다.
    - Frame Data: 부분 결과값을 저장하는데 사용되거나 메서드에 대한 값을 반환합니다. 예외 발생시, Exception 테이블에 대한 참조를 저장하는데 사용됩니다.
- `Stack Area`를 위한 메모리는 연속적일 필요가 없습니다.

## PC Registers
- `PC Registers`는 현재 실행중인 명령어의 주소를 저장합니다.
- 만약 현재 실행중인 명령어가 네이티브 메서드의 일부인 경우, 값이 정의되지 않습니다.
- `JVM`의 각 쓰레드는 별도의 `PC Register`를 가지고 있습니다. 

## Native method stacks
> 네이티브 메서드란 자바 언어 이외의 언어로 작성된 메서드를 의미합니다.

- `Native method stacks`는 `C stacks`라고 알려져있기도 하며, 네이티브 메서드에 대한 정보를 저장합니다.
- 네이티브 메서드가 기계 코드로 컴파일될때마다, `Native method stack`을 통해 네이티브 메서드의 상태를 추적합니다.

## Execution Engine
- `Execution Engine`은 메모리 영역에 있는 정보를 이용하여 명령을 실행합니다.

## Interpreter
- `Interpreter(인터프린터)`는 바이트코드를 한줄 한줄 읽어가며 실행합니다.
- `Interpreter`는 하나의 메서드가 여러번 호출될때, 매번 새롭게 해석하기때문에 느린 단점이 있습니다. 이러한 단점을 완화하기위해 `JIT Compiler`를 사용합니다.

## JIT Compiler

## JIT 컴파일러란
- `Just-In-Time(JIT) Compiler`는 인터프리터(`Interpreter`)의 단점을 보완하기 위해 도입된 컴파일러입니다. 
- `JIT Compiler`는 자주 호출되는 메서드의 바이트 코드를 적절한 런타임 시점에 네이트브 코드(기계어)로 변환해주는 컴파일러입니다.
- 네이티브 코드는 캐시에 보관되기 때문에, 한번 컴파일된 코드는 계속 빠르게 수행될 수 있습니다.
- `JIT 컴파일러`는 또한 자바 프로그램의 최적화를 담당합니다.

    ![Java & JIT compiler](./Java_JIT_compiler.PNG)  
    [이미지 출처 - NaverD2](https://d2.naver.com/helloworld/1230)

## JIT 컴파일러 동작 방식
JIT 컴파일러는 자주 호출되는 메서드를 파악하여, 바이트코드를 일단 중간 단계의 표현인 IR(Intermediate Representation)로 변환하고, 변환된 IR에 대한 최적화를 수행한 후, 네이티브 코드를 생성합니다. 이렇게 생성된 네이티브 코드는 캐시에 저장되며, 추후 재호출시 바로 네이티브 코드를 실행합니다.

![JIT Compiler](./JIT_Compiler.PNG)  
[이미지 출처 - NaverD2](https://d2.naver.com/helloworld/1230)

> 보다 자세한 내용은 [Naver D2 - JVM Internal](https://d2.naver.com/helloworld/1230)를 참고하시길 바랍니다.

## Garbage Collector
- `Garbage Collector`는 메모리의 관리를 담당합니다.
- `Garbage Collector`는 `Heap Area`를 관측하며, 사용되는 객체와 사용되지 않는 객체를 식별합니다. 이후, 사용되지 않는 객체를 제거합니다.
- `Garbage Collector`는 데몬 쓰레드입니다.
    > 데몬 쓰레드는 다른 일반 쓰레드(데몬 쓰레드가 아닌 쓰레드)의 작업을 돕는 보조적인 역할을 수행하는 쓰레드를 의미합니다.

## Java Native Interface
- `Java Native Interface(JNI)`는 자바 코드와 네이티브 라이브러리사이에서 interface로 작동합니다.
- Java 언어만으로 어플리케이션을 만들기 힘들때, `JNI`를 이용하여 `JVM`에서 네이티브 코드를 실행할 수 있습니다. 또한 반대로 네이티브 메서드가 `JVM`에서 실행되는 코드를 호출할 수 있습니다.

## Native Libraries
- `Native Libraries`는 플랫폼 별 라이브러리이며, 네이티브 메서드 구현을 가지고 있습니다.

# JDK와 JRE

## JDK란
- `Java Development Kit(JDK)`는 자바 프로그램을 실행, 디버깅, 컴파일, 개발하기 위한 환경 및 툴을 제공합니다.
- `JDK`의 핵심 구성요소는 다음과 같습니다.
    - JRE
    - 개발 도구(Development Tools)
        > 각 Development Tool에 대한 내용은 [Baeldung - JDK](https://www.baeldung.com/jvm-vs-jre-vs-jdk#jdk) 에서 참고하실 수 있습니다.

## JRE란
- `Java Runtime Environment(JRE)`은 자바 어플리케이션을 실행하기위한 소프트웨어 컴포넌트 번들입니다.
- `JRE`의 핵심 구성요소는 다음과 같습니다.
    - `JVM` 구현체
    - 자바 프로그램을 실행하기위해 요구되는 클래스들
    - 속성 파일들(Property Files)  
    > `JRE`의 핵심 구성요소에 대한 보다 자세한 내용은 [Baeldung - JRE](https://www.baeldung.com/jvm-vs-jre-vs-jdk#jre) 에서 참고하실 수 있습니다.

# 컴파일 및 실행

## 컴파일이란

## 자바코드를 컴파일해보자

## 자바코드를 실행하는 방법

# 바이트코드

## 바이트코드란

## 바이트코드 보는법

## 참고사이트
* [Naver D2 - JVM Internal](https://d2.naver.com/helloworld/1230)
* [뉴욕피자 - JVM Architecture란?](https://yeon-kr.tistory.com/112)
* [Baeldung - Class Loaders in Java](https://www.baeldung.com/java-classloaders)
* [Baeldung - Difference Between JVM, JRE, and JDK](https://www.baeldung.com/jvm-vs-jre-vs-jdk)