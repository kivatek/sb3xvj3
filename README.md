# sb3xvj3

## 構成

- jdk17
- SpringBoot 3
- Gradle (Mavenも可)
- Vue.js 3
- Vite
- MySQL (他のRDBMSも可)

## 実装のコンセプト

- FatJarで簡単に実行できるようにする
- バックエンド/フロントエンドの開発の分離は行いつつもビルドは一括した扱いを試みる
  - frontend-gradle-plugin (frontend-maven-plugin) を使用
- Java側ではビューのリソースは明示的には持たない
- Vue.js側で作成したリソースをresources/static/へ配置する
  - Java側のビューの処理には形式的にはthymeleafを使用するがresources/template/は使用しない<br>resources/static/をテンプレートディレクトリとしてマッピングする<br>Vue.jsが生成したファイルがthymeleafにとってはテンプレートファイルになっているイメージ
- ログイン処理とログイン後の画面への遷移の実装までが当面のマイルストーン

## ディレクトリ構成

- 基本は標準的なGradleプロジェクトの構成
- プロジェクトルートの直下にwebappディレクトリを用意しVue.jsプロジェクトを配置

## 課題

- SPAとSpringBootのルーティングの整合性とかは後で考える

## ビルド

### 実行可能パッケージのビルド
- gradlew bootJar
- バックエンド/フロントエンドのビルド両方が行われます
- 生成されるjarファイルにはフロントエンドの成果物も含みます

### フロントエンドの成果物をminifyしないビルド
- webappディレクトリにて npm run build:dev

### フロントエンドだけの編集作業を行いたいとき
- webappディレクトリにて npm dev
- Viteによるwatch状態のサーバーが立ち上がります
- APIとの連携
  - バックエンドも同時に立ち上げてオリジンが同じになるようプロキシ経由の接続をする
  - 偽装サーバをたてAPIの階層にJSONファイルを配置してGETするだけのアプローチ

## 実行

### 環境変数
実行にあたっては環境変数の設定が必要です。VSCodeなどの中から起動する場合にツールの作法に沿って設定が必要。

- DS1_URL : データベースのエンドポイントURL
- DS1_USERNAME : データベースに接続するためのユーザー名
- DS1_PASSWORD : データベースに接続するためのパスワード

### コマンド
環境変数を設定できているのであればJavaコマンドで単純に実行できます。

プロファイルでインジェクションするオブジェクトが変わるように構成されている場合は意図した状態で動くようプロファイルを指定します。

- java -jar sb3xvj3.jar
- java -jar -Dspring.profiles.active=production sb3xvj3.jar


