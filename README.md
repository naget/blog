# 翼灵物联工作室博客系统

### 系统架构为springboot+jpa+thymeleaf。
#### 须注意：
- 由于springboot对jsp支持不好，官方建议不要使用jsp，所以本次项目不采用jsp，使用thymeleaf渲染html。
- 静态资源放在resources的static下，模板（html）文件放在resources的templates下
- 项目下分为config（配置），controller（视图及路由控制器），model（映射实体），repository（存放数据库方法定义类或接口），service（逻辑处理包），utils（工具包）。
- 修改applicatio.properties中的数据库配置。
