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