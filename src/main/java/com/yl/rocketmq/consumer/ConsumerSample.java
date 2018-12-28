package com.yl.rocketmq.consumer;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yl.rocketmq.bean.MsgBean;

@Component
public class ConsumerSample {

	/**
	 * NameServer 地址
	 */
	@Value("${rocketmq.namesrvAddr}")
	private String namesrvAddr;
	/**
	 * 示例 consumerGroup
	 */
	@Value("${sample.consumerGroup}")
	private String sampleConsumerGroup;
	/**
	 * 示例 topic
	 */
	@Value("${sample.topic}")
	private String sampleTopic;
	

	@PostConstruct
	public void consumerSampleI(){
		System.out.println("consumerSampleI init ");
		try {
			DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(sampleConsumerGroup);
			consumer.setVipChannelEnabled(false);
			
			consumer.setNamesrvAddr(namesrvAddr);
			consumer.setInstanceName("consumerSampleI");
			consumer.subscribe(sampleTopic,"*");
			
			System.out.println("sampleTopic:"+sampleTopic);
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
		} catch (Exception e) {
			e.printStackTrace();
			
			System.out.println("consumerSampleI Start Failed");
		}

		System.out.println("consumerSampleI Started ");
	}
	
}
