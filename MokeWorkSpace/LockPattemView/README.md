2016/02/27

A.学习图案开锁时，用到的内容。视频教程是慕课网 http://www.imooc.com/learn/308

B.本项目有二个Module：lockview、mylockview。第二个Module 更符合视频。以下以此Module做源码解释。

C.工作原理：
    九宫格图案解锁，采用了自定义的View组件。着重在onDraw()和onTouchEvent()二个方法。
    其次采用了一个碎片，设置和检查密码的工作都在碎片中解决而不需要布局。

D.工作流程：
    本项目源码中共有5个Java文件
    LockPatternView.java            自定义View组件
    MainActivity.java               主程序(但非启动页面)
    PasswordFragment.java           设置和检查密码的碎片
    SettingPasswordActivity.java    设置密码的Activity
    WelcomeActivity.java            欢迎页面
    程序启动，进到欢迎页面，检查是否设置过密码，字符串为空没设置过密码，跳转到密码设置页面(Activity)设置密码；
如果已有密码，进入到碎片页面供用户输入密码。
    密码验证时，如果输入和存储的密码一样则跳转到 MainActivity；如果出错不做处理，等待再次输入验证。
    密码设置时，需要至少5位，符合要求后按确定按钮存入；还可以按重置按钮重设密码。

  本代码在 Android Studio V1.5.1下通过。