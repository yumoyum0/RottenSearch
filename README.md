# 搜索引擎



## 技术选型

**使用语言**

- Java 17

**核心框架**

- SpringBoot 2.6.7

**数据库**

- mysql 8.0.28
- druid 1.2.9
- mybatis-plus 3.5.1
- redis 6

**测试**

- junit 4.13.2

**依赖管理**

- maven

**工具**

- lombok
- devtools
- jieba-analysis 1.0.2
- fastjson 2.0.2

## 数据库设计

<img src="https://www.yumoyumo.top/wp-content/uploads/2022/05/image-20220509222424853.png" style="zoom:67%;" />

## 已实现功能

### 使用非对称加密RSA和对称加密AES

- 对于传入的请求参数，若是RSA密文，则进行解密
- 对于返回的响应体，若是RSA明文
  - 创建aesKey
  - 用该aesKey对响应数据进行AES加密
  - 对aesKey进行RSA加密
  - 返回 RSA加密后的aesKey 和 aesKey加密过的响应数据