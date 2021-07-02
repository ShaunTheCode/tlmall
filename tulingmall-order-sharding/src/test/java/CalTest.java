import com.tuling.tulingmall.common.api.CommonResult;
import com.tuling.tulingmall.common.exception.BusinessException;
import com.tuling.tulingmall.domain.OrderParam;
import com.tuling.tulingmall.service.OmsPortalOrderService;
import com.tuling.tulingmall.util.SnowFlakeIDGenerator;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author ：楼兰
 * @date ：Created in 2021/4/18
 * @description:
 **/

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class CalTest {

//    @Autowired
//    private OmsPortalOrderService portalOrderService;
//
//    @Test
//    public void generateOrder() throws BusinessException {
//        OrderParam orderParam = new OrderParam();
//        orderParam.setMemberReceiveAddressId(5L);
//        orderParam.setPayType(1);
//        orderParam.setItemIds(Arrays.asList(55L));
//        Long memberId=8L;
//        System.out.println(portalOrderService.generateOrder(orderParam, memberId));
//    }

    public static void main(String[] args) {
        Long[] ids = new Long[]{593116103984001025L,593116341633265664L,
                593116410474377217L,593116541198249984L,593116602653192193L,
                593116676980453377L,593116734727630848L,593116802474029057L,
                593116905846845440L,593116968098705409L};

        Properties properties = new Properties();
        properties.setProperty("worker.id","123");

        SnowflakeShardingKeyGenerator generator = new SnowflakeShardingKeyGenerator();
        generator.setProperties(properties);

        for(int i = 0; i < 10 ; i ++){
//            System.out.println("db = " + (i % 2));
//            System.out.println("oms_order_" + (i+1) % 4 / 2);
            Long id= (Long)generator.generateKey();
            System.out.println(id);
            System.out.println("db = " + (id % 2));
            System.out.println("oms_order_" + (id+1) % 4 / 2);
//            System.out.println("===========================");
            id = ids[i];
            System.out.println(id);
            System.out.println("db = " + (id % 2));
            System.out.println("oms_order_" + (id+1) % 4 / 2);
            System.out.println("===========================");
        }

    }
}
