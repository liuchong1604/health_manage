package com.health.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.health.sms.config.SmsProperties;
import com.health.sms.utils.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/3/5 11:40
 */
@Component
public class SmsListener {
    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties prop;

    /**
     * 监听短信
     * @param msg
     * @throws ClientException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "HEALTH.SMS.QUEUE",durable = "true"),
            exchange = @Exchange(value = "health.sms.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"sms.verify.code"}
    ))
    public void listenSms(Map<String, String> msg) throws ClientException {
        if (CollectionUtils.isEmpty(msg)) {
            //放弃处理
            return;
        }
        String phone = msg.get("phone");
        String code = msg.get("code");

        if(StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(code)){
            this.smsUtils.sendSms(phone, code,
                    prop.getSignName(),
                    prop.getVerifyCodeTemplate());
        }
    }
}
