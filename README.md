プログラマブル・ノート
======================

## 概要

Evernoteをコマンドラインで操作するツールです。
類似ツールにgeeknoteがありますが、それのJava版のようなものです。

## 使い方

`pg-note.jar`はJavaアプリケーションなので、Javaコマンドで実行します。

    java -jar pg-note.jar <yml file> <command> <options>

`yml file`は設定を記述したYAMLファイルを指定します。同梱の`conf.yml`をベースに設定してください。

`command`は、実行するコマンドを指定し、`options`は、コマンドごとのオプションを指定します。どのようなコマンド、オプションがあるかは機能一覧を確認して下さい。

## YAMLファイル

`pg-note.jar`を使用するには、Evernoteデベロッパートークンが必要になります。デベロッパートークンは[認証 - Evernote Developers](https://dev.evernote.com/intl/jp/doc/articles/authentication.php#devtoken)で取得できます。
デベロッパートークンを取得したら、YAMLファイルに記述します。

## 機能一覧 (v0.1)

### list - 全てのノートを取得

    java -jar pg-note.jar <yml file> list

全てのノートを取得します。ただし、一度の取得件数は上限があります。

### create-linknote - リンクノートを作成

    java -jar pg-note.jar <yml file> create-linknote <url>

リンクノートを作成します。

* url
  * リンク元のURL。このURLを元にリンクノートを作成します。

## 課題管理

https://myredmine-u6kapps.rhcloud.com/projects/pg-note
