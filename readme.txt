spring_boot打成war包，部署到外部的tomcat步骤：
1，由jar变成war  pom设置  <packaging>war</packaging>
2，忽略tomcat依赖  pom设置  添加依赖
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>
3，新增ServletInitializer类
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
Application.class 为标注有@SpringBootApplication的主启动类

本人使用springboot官方推荐的jar部署方式
执行命令：java -jar mss.jar

集成VUE页面
1，前端打包，生成页面
    修改axios文件夹下的url.js，将顶部的指向，改成prod
    在前端工程根目录下，执行npm install后，执行npm run build，会在dist文件夹下生成index.html和statics文件夹
2，将生成的index.html和statics文件夹，copy进后端工程的static下

后台打jar包
1，将application.properties中的端口号，修改为8080
2，在工程根目录下，执行命令，mvn clean install，生成jar

部署运行系统jar包
1，将生成的jar包放入指定的文件夹下，随便一个用来部署jar包的文件夹
2，在当前文件目录下，执行命令：java -jar mss.jar
3，访问系统主界面：http://localhost:8080/index.html(对外访问就是将本机的的ip替换掉localhost)