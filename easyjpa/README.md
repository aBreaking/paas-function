一个超级简单的jpa,实现通用的增删改查功能，分分钟解放你的配置文件，一键搞定

# 说明
一个简单的ORM框架，一键解决常见的增删改查场景。

无需多余的jpa配置，类本身就是配置。

目标是讲对象本身即看作jpa的配置，达到极简的配置以及通用增删改查的实现。

# easyjpa限制
1.0版本：
暂不支持各种一对多、多对多、多对一的关系。建议表应该纯净点，不建议使用外键，外键建议在本表中保留其他的主键；

# 对象/表的建议
对象所有的类型都应该为包装类型。
建议每个表应该有主键，建议自然主键，没有自然主键也建议应该业务主键。

# 总体设计
## 映射层(mapper)
首先，需要将实体对象映射按照一定规则映射成数据库表字段。包括:类名<->表名、属性名<->列名等映射。

主要映射关系是对象的属性名到列名映射。封装了一个对象来描述这样的映射关系：Matrix。


## 数据库访问层(dao)
在这一层封装了通用的增删改查方法，