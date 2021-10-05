# Javaの乱数生成器の速度調査

Java17にjava.util.randomパッケージが追加され生成器が選択できるようになったので、いろいろな生成器の速度調査をするためのコード。

旧来のものからJava17で追加されたもの、Apache Commons RNGに実装されているものなどをシングルスレッド、マルチスレッドそれぞれでテストするようになっています。

マルチスレッドは4スレッドで動作するため、4コア以上のCPUで実行することを推奨します。

## 環境

* Java17

* Maven

## 実行方法

リポジトリをclone後、リポジトリのディレクトリに移動して

```
$ mvn clean install
$ java -jar target/benchmarks.jar
```
