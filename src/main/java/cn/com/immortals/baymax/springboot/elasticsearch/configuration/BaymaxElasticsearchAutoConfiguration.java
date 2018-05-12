package cn.com.immortals.baymax.springboot.elasticsearch.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhuowen_pan on 2018/5/8.
 */
@Configuration
@Import({BaymaxElasticsearchConfigure.class})
@EnableConfigurationProperties({BaymaxElasticsearchProperties.class})
public class BaymaxElasticsearchAutoConfiguration {

    public BaymaxElasticsearchAutoConfiguration() {
    }

}
