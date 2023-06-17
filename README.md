# SpringBoot3Demo
a demo project for springboot 3 

## 技术栈
### 1.数据库：mysql
### 2.集成mybatis-plus
数据库：test<br>
代码包含：mapper，entity<br>
实现对表：test_data的CRUD<br>
单元测试：实现getById、selectById查询数据<br>
localhost:8080/test/getdata?username=admin&password=123456&salt=1234

### 3. 集成shiro
代码包含: user, role, permission
实现：save有权限，无delete权限
http://localhost:8080/user/save
http://localhost:8080/user/delete




