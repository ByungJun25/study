# 목표
자바의 Input과 Ontput에 대해 학습하세요.

## 학습 내용
* [I/O](#IO)
    * [Stream](#Stream)
        * [InputStream](#InputStream)
        * [OutputStream](#OutputStream)
        * [Byte Stream](#Byte-Stream)
        * [Character Stream](#Character-Stream)
        * [표준 스트림](#표준-스트림)
    * [Buffer](#Buffer)
    * [Channel](#Channel)
* [File I/O](#File-IO)
* [참고 사이트](#참고-사이트)

## I/O
`I/O` 는 `Input / Output`의 줄임말이며, 이는 컴퓨터와 같은 정보 처리 시스템과 외부 세계(사람 혹은 다른 시스템)간의 통신을 의미합니다.

## Stream
- `Stream`은 데이터의 시퀀스로 정의될 수 있습니다. 또한 스트림의 데이터 흐름은 단방향이기때문에, source와 destination을 가져야합니다.
- `Stream`은 bytes, primitive data types, localized characters, and objects등 여러 다양한 데이터를 지원합니다.

## InputStream
모든 입력 스트림들 중 최상위 클래스로 추상 클래스로 정의되어있습니다. 모든 Byte Stream들은 이 `InputStream`을 상속 받습니다.

- `read()`: 입력 스트림으로부터 바이트를 읽고 이를 반환하는 메서드. 만약 반환 가능한 바이트가 없으면 -1을 반환합니다. 이 메서드는 추상메서드로, 하위 클래스에서 이를 꼭 구현해야합니다.
- `read(byte[] b)`: 입력 스트림으로부터 일부 바이트를 읽어 이를 주어지는 buffer array에 보관하고 읽은 바이트 수를 반환하는 메서드입니다. 만약 주어지는 배열의 크기가 0이면, 바이트를 읽지 않고 0을 반환합니다.
- `read(byte[] b, int off, int len)`: 
- `skip(long n)`: 
- `available()`:
- `close()`:
- `mark(int readlimit)`:
- `reset()`:
- `markSupported()`:

## OutputStream

## Byte Stream
- `Byte Stream`은 `8-bit`의 1 바이트의 입력 및 출력을 하기위해 사용됩니다.
- 모든 byte stream 클래스는 InputStream 및 OutPutStream의 자식 클래스입니다.
- **byte stream들은 에러 발생과 상관없이 항상 close 해주어야합니다.**
- **byte stream은 가장 원시적인 I/O에만 사용해야합니다.**

ByteStream 종류는 다음과 같습니다.

|Class 이름|설명|
|---|---|
|`AudioInputStream`|오디오포맷에 특화된 input stream 클래스|
|`ByteArrayInputStream` / `ByteArrayOutputStream`|바이트배열에 대한 input / output stream 클래스|
|`BufferedInputStream`|다른 `InputStream`에 버퍼기능, mark기능, reset 기능을 추가해주는 input stream 클래스|
|`FileInputStream` / `FileOutputStream`|파일의 바이트를 읽고 쓰는 input / output stream 클래스|
|`FilterInputStream` / `FilterOutputStream`|필드로 가진 input / output stream을 가진 클래스. 클래스 자체적으로는 어떠한 기능의 변경도 없습니다. 단지 모든 메서드가 다시 필드의 input / output stream의 메서드를 호출할 뿐입니다. 따라서 추가적인 custom 로직이 필요할때 사용할 수 있습니다.|
|`ObjectInputStream`|`ObjectOutputStream`에 의해 작성된 원시 데이터 및 객체를 역직렬화 하는 클래스입니다.|
|`ObjectOutputStream`|직렬화 될 수 있는 원시 데이터 및 객체를 스트림으로 만드는 클래스입니다.|
|`PipedInputStream`|바이트 스트림을 읽어 `PipedOutputStream`으로 전달하는 클래스입니다.|
|`PipedOutputStream`|`PipedInputStream`에 의해 읽혀진 스트림을 출력하는 클래스입니다.|
|`SequenceInputStream`|하나 이상의 스트림을 조합하여 하나의 input stream을 만드는 클래스. 조합된 스트림을 순차적으로 읽어나갑니다.|

## Character Stream
`Java`에서는 문자값을 Unicode 규칙을 따라 저장합니다. 

## 표준 스트림

## Buffer

## Channel

## File I/O

## 참고 사이트
- [Oracle Tutorial - I/O](https://docs.oracle.com/javase/tutorial/essential/io/index.html)
- [Jenkov - Java IO Tutorial](http://tutorials.jenkov.com/java-io/index.html)