package cn.com.immortals.baymax.springboot.elasticsearch.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuowen_pan on 2018/5/8.
 */
@ConfigurationProperties(
        prefix = "baymax.elasticsearch"
)
@Data
public class BaymaxElasticsearchProperties {

    private String urls;

    private String clusterName = "cube-elasticsearch";

    private Boolean sniff = true;

    private String protocol = "http";

}
