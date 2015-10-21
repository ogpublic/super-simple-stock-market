package com.acme.test;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public abstract class AbstractTest {
	protected ClassPathXmlApplicationContext testContext = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testContext = new ClassPathXmlApplicationContext("simple-stock-market-context-test.xml");
		
		initTest();
	}
	
	protected abstract void initTest();
	
	@SuppressWarnings("unchecked")
	protected <T> T getBean(String beanId) {
		return (T) testContext.getBeanFactory().getBean(beanId);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		testContext.close();
	}
}
