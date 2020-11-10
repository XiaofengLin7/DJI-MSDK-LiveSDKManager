# DJI-MSDK-LiveSDKManager
It is based on DJI-MSDK, and  realize image transport from android to other platforms such as windows. From windows, You can get image from drones.

## Introduction

It was based on DJI Mobile SDK LiveStreamManager and another [project](https://github.com/wrs13634194612/DJI-liveshow-Android) developed by ztest, however, my app got crashed when using his code. Therefore, I write my code according to his project .

Final result was like:

<img src="D:/groundstation/地面站搭建/408957f29120f666c41a59f51499b14.jpg" alt="408957f29120f666c41a59f51499b14" style="zoom: 150%;" />

## Requirements

DJI SDK 4.13.1

Android 5.+

drones according to https://developer.dji.com/cn/mobile-sdk/



My tools was M300, ONEPLUS 8 PRO(API 29) and Android Studio 4.0.1.



## Steps

After installing APKs into Android Platforms.

- Connect phone with RC.
- Build a RTMP server using [nignx-rtmp-module](https://github.com/arut/nginx-rtmp-module) or [smart_rtmp](http://www.qiyicc.com/download/rtmpd.rar) or something like that on windows/Linux.
- Input your RTMP server's url (you can use ipconfig to find) or configure it in livestreammanager.java.
- Click 'Star Live Show'
- using vlc to play the video.



