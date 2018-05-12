### SpringBoot 对于 Elasticsearch的支持

	spring本身有对于Elasticsearch的支持，但是支持的版本比较低，要使用低版本的springboot，
	也只能使用低版本的ElasticSearch，但是要使用高版本的Elasticsearch就需要自己整合，本文介绍了一种集成方式。

### 集成思路
	springboot支持自动配置，并且少配置，如果采用原来的配置方式的话，少不了在配置中用注解引用资源文件，这样方式也能
	实现集成，但不是期望的方式，所以采用了自动配置的方式，将内置的一些配置通过spring的配置自动读取实现。
	整合高版本的ElasticSearch需要配置客户端的client，那就配置一个bean交给spring去管理。

### autoconfigure的实现

![这里写图片描述](https://img-blog.csdn.net/20180512135316816?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTAxMDQ4MTU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

这里面采用了```lombok```的加持，所以不需要使用getter和setter方法，这里面定义了简单的配置，如果需要拓展，添加里面的配置即可

```
ConfigurationProperties
```

标记为spring的application中的配置属性，这样在application的配置中添加文件即可，不需要 ```@Value``` 的方式进行获取配置,获取方式如下

![这里写图片描述](https://img-blog.csdn.net/20180512135813846?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTAxMDQ4MTU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### Client的配置

上面实现了一个配置文件中的获取，下面需要实现一个客户端的定义，这里面应用了下面的依赖

```
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>transport</artifactId>
        </dependency>
```

剩下的就是将客户端的client交给spring管理，最好的方式就是定义成spring的bean，bean的定义方式如下：
![这里写图片描述](https://img-blog.csdn.net/20180512140131223?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTAxMDQ4MTU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

这样最简单的一个Client就初始化出来了，剩下的就是具体业务调用了。

### 最后一步

对应的Client初始化出来了，配置信息也加载进来了，这些怎么交给spring，换句话说怎么能让spring加载他们，采用了 ```factories``` 的方式，先定义了一个整合的类，这个类整合了client和properties，具体定义如下：

![这里写图片描述](https://img-blog.csdn.net/20180512140640764?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTAxMDQ4MTU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

这个类中引入了配置和配置，这个类本身也被标注成 ```Configuration ```， 这样一个组装的版的配置就完成了， 剩下的就是将这个注册到spring上了，这里使用的是 ```spring.factories``` 方式

![这里写图片描述](https://img-blog.csdn.net/20180512140907299?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTAxMDQ4MTU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

到此，大功告成。
