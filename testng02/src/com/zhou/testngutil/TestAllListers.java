package com.zhou.testngutil;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/*
 * 
 * <suite name="TmpSuite" >
 * <listeners>
<listener class-name="TakeScreenShotListener" />
</listeners>



<test name="TmpTest" >
<classes>
  <class name="com.zhou.test.TakeScreenshotTest"  />
<classes>
</test>
</suite>
 */
public class TestAllListers {

	public static void main(String[] args) {
		
		XmlSuite suite = new XmlSuite();
		suite.setName("TmpSuite");
		System.out.println(suite);//  [Suite: "TmpSuite" ]
		
		
		
		XmlTest test = new XmlTest(suite);
		test.setName("TmpTest");//   
		System.out.println(test);//
		List<XmlClass> classes = new ArrayList<XmlClass>();
		
		classes.add(new XmlClass("com.zhou.test.TakeScreenshotTest"));
		System.out.println(classes);
		test.setXmlClasses(classes) ;
		
		
		//然后你可以将XmlSuite传递给TestNG：
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		System.out.println(suites);
		TestNG tng = new TestNG();
		
		tng.addListener(new TakeScreenShotListener() );//失败时截图
		tng.addListener(new CustomListener() );// 控制台显示 成功失败信息
		tng.addListener(new CustomReporter() );//控制台显示成功失败个数
		tng.addListener(new EmailableReporter() );//修改可发送邮件报告
		
		
		
		tng.setXmlSuites(suites);
		System.out.println(tng);//org.testng.TestNG@6e2c634b
		tng.run();
		
		
		System.out.println(tng.getReporters());
		System.out.println(tng.getReporters().size());//6

	}

}
