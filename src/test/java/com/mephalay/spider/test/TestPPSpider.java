package com.mephalay.spider.test;

import com.mephalay.spider.PPSpider;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Mephalay on 2/10/2016.
 */

@RunWith(JMockit.class)
public class TestPPSpider {

    @Test
    public void testPPSpiding() {
        PPSpider.getInstance().startSpiding(null);
    }
}