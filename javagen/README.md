javagen：一个将指定输入的文本语句（比如json串）生成java代码的工具

# 一个Demo
首先，传入一个指定标准的json，该json一般都是自动生成的，不过在本demo中根据标准自定义即可。参考demo.json。

# 接口泛化调用
在本工程中，我会将服务/方法全部泛化处理，

Q: 为什么要将接口/服务泛化处理
比如服务提供方提供如下接口:
```java
interface UserService{
    User getUser(Integer userId);
}
```
那么在调用端就必须要知道该接口的协议，也就是必须要要引入该接口的相关jar包。