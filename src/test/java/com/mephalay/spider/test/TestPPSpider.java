package com.mephalay.spider.test;

import com.mephalay.model.PPDataBundle;
import com.mephalay.spider.PPSpider;
import mockit.integration.junit4.JMockit;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Mephalay on 2/10/2016.
 */

@RunWith(JMockit.class)
public class TestPPSpider {

    @Test
    public void testPPSpiding() {
        PPDataBundle dataBundle = PPSpider.getInstance().startSpiding(Logger.getRootLogger(), 3);
        System.out.println(dataBundle.getPpCpuList());
    }
}
