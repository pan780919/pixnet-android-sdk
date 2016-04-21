PIXNET SDK for Android
==============

這個 SDK 可以讓你將 PIXNET 的相關資料快速整合進你的 Android 專案中。
詳細 API 資訊請參考 [http://developer.pixnet.pro/](http://developer.pixnet.pro/)
 
This open-source library allows you to integrate PIXNET into your Android APP.
Learn More detail at [http://developer.pixnet.pro/](http://developer.pixnet.pro/) 

##安裝 - Installation#

PIXNET Android SDK 支援使用 Android Studio 開發， 支援 Android 2.3+

##使用 - Usage#
###在使用之前，請先至 PIXNET Developer 註冊新的 APP。
[http://developer.pixnet.pro/#!/apps](http://developer.pixnet.pro/#!/apps)

Application Type 請選擇 Destop / Mobile App

申請完成會拿到以下兩把鑰匙

 1. Consumer Key(client_id)
 2. Consumer Secret
 
新增 String resource: consumer_key & consumer_secret 並分別填入剛拿到的 key & secret

在 build.gradle 的 dependencies 中加入 pixnet sdk
```gradle
dependencies{
  ...
  compile 'net.pixnet.android:pixnet-android-sdk:1.1.0'
  ...
}
```

注意： gradle 中 repositories 需設定為 jcenter
```gradle
repositories {
  jcenter()
}
```

詳細使用範例請參考 sample module

## 聯絡我們

Email: sdk@pixnet.tw

## License
PIXNET SDK is BSD-licensed. We also provide an additional patent grant.


