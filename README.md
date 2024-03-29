# WebExperiment_LoginSystem

## 本系统前端使用HTML/CSS/JS，后端使用Servlet+Tomcat，数据库使用MySQL，前后端通过AJAX异步请求json数据实现前后端分离。

### 以下是本系统实现的主要功能：

#### --登录页功能：

1. 登录页天气预报，使用高德API获取用户定位及当地天气预报
2. 使用用户名/邮箱/手机号作为账号登录，根据用户类型跳转个人主页/管理员页面
3. 用户注册功能，密码使用MD5加密
4. 使用cookie实现七天免登录功能
5. 忘记密码，可通过邮箱验证码验证后重置密码、也可通过安全信息验证后重置密码。
6. 页面显示灰色滤镜

#### --用户个人主页功能：

1. 修改用户基本资料，包括昵称、性别、学校、邮箱、手机号等内容
2. 头像修改
3. 密码修改
4. 申请账号注销
5. 安全退出登录，清除cookie和服务器session。

#### --管理员页面功能：

1. 分页查询数据库中的用户信息、用户个人详细信息
2. 通过手机号、邮箱、学号精确搜索
3. 通过用户名、姓名、学校、学院、专业和籍贯进行模糊搜索
4. 删除/禁用/启用用户
5. 批量删除用户

#### --后端功能：

1. 数据的增删改查
2. 跨域过滤器，解决跨域携带cookie问题
3. 认证过滤器，只有登录的用户才能获取到数据。非登录用户访问个人主页/管理员页面、已登录用户访问登陆页面、普通用户访问管理员页面将自动重定向到新的页面。
4. 邮箱验证码发送功能
