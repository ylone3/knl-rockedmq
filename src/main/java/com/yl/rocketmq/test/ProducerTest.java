package com.yl.rocketmq.test;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import com.alibaba.fastjson.JSON;
import com.yl.rocketmq.bean.MsgBean;

public class ProducerTest {

	public static void main(String[] args) throws Exception {
		String namesrvAddr = "192.168.133.129:9876";
		DefaultMQProducer producer = new DefaultMQProducer("ylTestProducerGroupName");
		producer.setNamesrvAddr(namesrvAddr);
		producer.setInstanceName("TopicTest");
		producer.setVipChannelEnabled(false);
		
		producer.start();
		
		MsgBean bean = new MsgBean();
		bean.setId("yltestMsgId09");
		bean.setText("yltestMsgText");
		String str = JSON.toJSONString(bean);
		Message msg = new Message("sampleTopicI" /* Topic */, "TagA" /* Tag */,
				str.getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
		);

		SendResult sendResult = producer.send(msg);

		System.out.printf("%s%n", sendResult);
             
		producer.shutdown();
	}

}
