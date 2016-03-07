package com.zhou.test;

import org.testng.Reporter;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;



public class TakeScreenshotTest {

	@Test
	public void testAdd() {
		System.out.println("&&&&&&&&&&&&&&&&TestNGSimpleTest");
		String str = "TestNG is working fine";
		assertEquals("TestNG is working fine", str);
		 Reporter.log("M3 WAS CALLED");

	}
	
	@Test
	public void testAdd2() {
		
		String str = "TestNG is working fine";
		assertEquals("TestNG is working fine3", str);// 运行失败的case
	}
	
	
	
	

	public static void main(String[] args) {
		
		 
		
	}

}
