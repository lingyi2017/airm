/**
 * 
 */
package com.infosoul.mserver.common.spring;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p> Title: </p>
 * 
 * <p> Description: </p>
 * 
 * <p> Copyright: Copyright (c) 2014 by ACTEC </p>
 * 
 * <p> Company: Free-Lancer </p>
 * 
 * @author: free lance
 * @version: 1.0
 * @date: 2014年7月18日 下午2:49:08
 * 
 */
public abstract class BaseTest {
    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected static ApplicationContext appContext;

    @BeforeClass
    public static void setUp() throws Exception {
        try {
            long start = System.currentTimeMillis();
            logger.info("正在加载配置文件...");

            appContext = new ClassPathXmlApplicationContext(new String[] { "spring-config.xml",
                    "spring-config-struts.xml" });

            logger.info("配置文件加载完毕,耗时：" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(BaseTest.class.getResource("/"));
    }

    protected boolean setProtected() {
        return false;
    }

    @Before
    public void autoSetBean() {
        appContext.getAutowireCapableBeanFactory().autowireBeanProperties(this,
                DefaultListableBeanFactory.AUTOWIRE_BY_NAME, false);
    }

    @AfterClass
    public static void tearDown() throws Exception {
    }
}
