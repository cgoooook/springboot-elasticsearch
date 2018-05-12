package cn.com.immortals.baymax.springboot.elasticsearch.configuration;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * @author zhuowen_pan on 2018/5/8.
 */
@Configuration("cube-elasticsearch-config-for-spring")
public class BaymaxElasticsearchConfigure {

    @Autowired
    private BaymaxElasticsearchProperties baymaxElasticsearchProperties;


    @Bean
    public TransportClient esClient() {
        Settings settings = Settings.builder()
                .put("cluster.name", baymaxElasticsearchProperties.getClusterName())
                .put("client.transport.sniff", baymaxElasticsearchProperties.getSniff()).build();
        TransportClient client = new PreBuiltTransportClient(settings);
        String urls = baymaxElasticsearchProperties.getUrls();
        assert urls != null;
        String[] clusterUrls = urls.split(",");
        for (String url : clusterUrls) {
            String[] ipAndPort = url.split(":");
            assert ipAndPort.length == 2;
            try {
                String addr = ipAndPort[0];
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(addr), Integer.parseInt(ipAndPort[1])));
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format("Elasticsearch Urls : %s can't parse or connect, Please Check it.", url));
            }
        }
        return client;

    }


}
