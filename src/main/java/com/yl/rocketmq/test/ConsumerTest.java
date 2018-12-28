package com.yl.rocketmq.test;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yl.rocketmq.bean.MsgBean;

public class ConsumerTest {

	public static void main(String[] args) throws Exception {
		String namesrvAddr = "192.168.133.129:9876";
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(
				"ylTestConsumerGroupName");
		consumer.setVipChannelEnabled(false);
		
		consumer.setNamesrvAddr(namesrvAddr);
		consumer.setInstanceName("TopicTes1111t");
		consumer.subscribe("sampleTopicI","*");
		
		consumer.registerMessageListener(new MessageListenerConcurrently() {

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(
					List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				System.out.printf("%s Receive New Messages: %s %n,msgBody: %s %n", Thread
						.currentThread().getName(), msgs,  new String(msgs.get(0).getBody()));
				
				MsgBean bean = JSON.parseObject(new String(msgs.get(0).getBody()), new TypeReference<MsgBean>() {});
			    System.out.println(bean);
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});

		consumer.start();

		System.out.printf("Consumer Started.%n");
	}

}
