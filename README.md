# raspiController_Android
这个app将尝试控制树莓派小车，在使用时需与树莓派在同一wifi下

请先在树莓派中运行runOnRespberrypi.py系列文件，然后再使用此app

https://github.com/icecoins/runOnRaspberryPi

在打开app后填入/修改树莓派ip与将使用的端口，点击run即可跳转到操作页面

通信使用socket，若连接断开，应用将每1.5秒尝试重新建立连接

视频流获取使用webview，若页面打开失败，页面就会打开失败，进而reload一个错误页面

逻辑代码全部由java构成

控制界面有左右两个摇杆，左边摇杆是控制车的，右边摇杆原计划是控制舵机的

但是鸽了

注意：bug非常多，很可能打开就崩溃，请不要过于惊讶

Version 25.10.21 
