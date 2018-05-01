package com.cepheis;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/ctx-mq-consumer.xml", "classpath:/ctx-mq-producer.xml"})
public class BaseTest {

}
