# 一个选课系统
## 前端（Vue）
### 启动
```
cd 前端/xksystem/xks/
yarn serve
```

### 参考
[Vue官网](https://cn.vuejs.org/).
[vuetify官网](https://vuetifyjs.com/zh-Hans/).
[axios官网](https://github.com/axios/axios).

## 后端Python版本
### 使用虚拟环境，并下载相关库
```
cd 后端_Python/
source datavis/bin/activate
cd xks
pip3 freeze > requirements.txt 
```

### 基本操作（可参考官网）
```
python3 manage.py makemigrations
python3 manage.py migrate
python3 manage.py runserver
```
[服务器网页](http://127.0.0.1:8000/).
[服务器管理网页](http://127.0.0.1:8000/admin/).

### 参考
[Django官网](https://docs.djangoproject.com/zh-hans/).
[restframework官网](https://www.django-rest-framework.org/).
一定要使用requirements里面版本的库，不然会有很多奇怪的bug.

## 后端Java版本
## 启动
build:
```
gradle build
```
run server:
```
./gradlew bootRun
```


