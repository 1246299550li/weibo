# weibo
仿微博简单项目
### 第一章 微博应用业务分析

实现一个仿微博的动态分享Android应用.用户可以将看到的,听到的,想到的,做过的通过文字,图片的方式发布信息,分享给朋友,分享给陌生人,分享自己的生活.应用主要有以下几个功能.

1. 使用Android开发微博客户端

2. 使用PHP开发微博Web服务器

3. 使用MySQL做Web服务器的后台数据库

4. 实现用户客户端注册和登陆功能

5. 实现发表微博、查看微博及微博列表等功能

6. 发送的微博可以添加图片

7. 实现发表评论功能

8. 实现点赞评论和微博的功能

9. 实现个人信息页面及信息修改功能

10. 实现微博转发功能

11. 用户可以随意上传头像

#### 第二章 微博应用系统设计

2.1 系统功能定义

(1) 注册

用户通过微博Android客户端注册账号,若账号未被注册则注册成功

(2) 登录

用户使用注册过的账号密码登录微博Android客户端,若账号密码一致则登录成功

(3) 查看微博列表

用户登录成功后,显示当前热门微博列表,列表展示数据库中最新的50条微博

(4) 查看微博详情

用户点击微博列表中的微博,则跳转到微博详情页面,详情页面展示微博发送用户的头像,昵称,微博发送时间,微博内容,评论列表,点赞数,转发数等详情信息

(5) 点赞微博

用户在微博详情页中可以点击点赞按钮,则对此微博进行点赞,点赞之后则不可以进行取消

(6) 转发微博

用户在微博详情页面可以点击转发按钮,跳转到微博转发页面,用户可以写下自己的转发语,点击发送按钮就转发了目标微博

(7) 评论微博

用户在微博详情页面可以点击评论按钮,从屏幕下方弹出一个评论框用户输入规定字数以下的评论点击评论就发表评论成功

(8) 点赞评论

用户在微博详情页面中的评论列表中点击点赞评论按钮,就能对相应的微博评论进行点赞

(9) 发表微博

用户在微博列表上边的工具栏点击加号按钮,跳转到微博发送页面,用户可以输入微博内容和图片,点击发送按钮则成功发送

(10) 更改用户头像

用户通过滑动屏幕或者点击导航栏到个人信息页面,点击头像,可以更换头像,用户可以从相册中选取图片或者用照相机拍照

(11) 修改用户信息

用户通过滑动屏幕或者点击导航栏到个人信息页面,点击修改按钮,弹出信息更新框,点击确认则成功修改信息

2.2后端API设计

| 序号 | API功能      | API           | 发送                                                   | 返回                                                         |
| ---- | ------------ | ------------- | ------------------------------------------------------ | ------------------------------------------------------------ |
| 1    | 注册         | user/register | user_accountuser_pwduser_sex                           | 1.不存在相同用户返回成功2.存在相同用户失败                   |
| 2    | 登录         | user/login    | user_accountuser_pwd                                   | 1. 账号密码一致返回成功(1.用户头像2.昵称3.性别4.签名5.用户id )2.账号不存在或密码不一致返回失败 |
| 3    | 获取微博列表 | weibo/all     | user_id                                                | 返回数据库逆序微博列表，包括以下内容：-1.微博id 0.微博类型1.发表用户头像2.昵称3.时间4.设备5.内容（转发则附加转发语）6.图片7.转发数8.点赞数9.评论列表（i.评论用户头像ii.昵称iii.评论内容iv.时间v.点赞数vi.评论id） |
| 4    | 获取消息列表 | weibo/notice  | user_id                                                | 本用户被转发、评论、点赞记录，包括以下内容：1.记录类型（2=转发 3=评论 4=点赞）2.互动发起用户头像3.昵称4.时间5.设备6.互动内容7.被互动微博图片or被互动用户头像8.被互动用户昵称9.被互动微博内容 |
| 5    | 发微博       | weibo/add     | weibo_user_idweibo_contentweibo_imgweibo_device        | 1.成功2.失败                                                 |
| 6    | 转发微博     | weibo/forward | weibo_user_idweibo_forward_commentweibo_idweibo_device | 1.成功2.失败                                                 |
| 7    | 点赞         | weibo/like    | user_idtypeid                                          | 1.成功2.失败                                                 |
| 8    | 评论微博     | weibo/comment | user_idweibo_idcontent                                 | 1.成功2.失败                                                 |
| 9    | 修改信息     | user/info     | user_iduser_iconuser_nicknameuser_sexuser_intro        | 1.成功2.失败                                                 |