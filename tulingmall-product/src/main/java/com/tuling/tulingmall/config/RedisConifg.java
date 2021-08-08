package com.tuling.tulingmall.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuling.tulingmall.common.constant.RedisKeyPrefixConst;
import com.tuling.tulingmall.dao.FlashPromotionProductDao;
import com.tuling.tulingmall.domain.FlashPromotionParam;
import com.tuling.tulingmall.util.RedisOpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ：图灵学院
 * @date ：Created in 2020/2/17
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description:
 **/
@Slf4j
@Configuration
public class RedisConifg implements InitializingBean {

    @Value(value = "${spring.redis.cluster.nodes}")
    private String redisNodes;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    @Primary
    public RedisTemplate<String,Object> redisTemplate(){
        RedisTemplate<String,Object> template = new RedisTemplate();
        template.setConnectionFactory(connectionFactory);
        // 序列化后会产生java类型说明，如果不需要用“Jackson2JsonRedisSerializer”和“ObjectMapper ”配合效果更好
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);

        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }




    @Autowired
    private FlashPromotionProductDao flashPromotionProductDao;

    /**
     * 加载所有的秒杀活动商品库存到缓存redis中
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //todo  获取所有的秒杀活动中商品
        FlashPromotionParam promotion = flashPromotionProductDao.getFlashPromotion(null);
        //TODO 如果没有秒杀活动 后面会报错
        if (null==promotion){
            return;
        }
        Date now = new Date();
        Date endDate = promotion.getEndDate();//结束时间
        final Long expired = endDate.getTime()-now.getTime();//剩余时间
        //秒杀商品库存缓存到redis
        promotion.getRelation().stream().forEach((item)->{
            redisOpsUtil().setIfAbsent(
                    RedisKeyPrefixConst.MIAOSHA_STOCK_CACHE_PREFIX + item.getProductId()
                    , item.getFlashPromotionCount()
                    , expired
                    , TimeUnit.MILLISECONDS);
        });
    }
    @Bean
    public RedisOpsUtil redisOpsUtil(){
        return new RedisOpsUtil();
    }


    @Bean
    public RedissonClient redissonClient(){
        List<String> clusterNodes = new ArrayList<>();
        String[] nodes=redisNodes.split(",");
        for (int i = 0; i < nodes.length; i++) {
            clusterNodes.add("redis://" + nodes[i]);
        }
        Config config=new Config();
        config.useClusterServers().addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()])).setPassword("111111");
        return Redisson.create(config);
    }

}
