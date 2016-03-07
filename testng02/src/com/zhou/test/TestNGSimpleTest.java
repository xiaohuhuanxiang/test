package com.zhou.test;

import org.testng.Reporter;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;


import org.testng.reporters.XMLReporter;

public class TestNGSimpleTest {

	@Test
	public void testAdd() {
		System.out.println("&&&&&&&&&&&&&&&&TestNGSimpleTest");
		String str = "TestNG is working fine";
		assertEquals("TestNG is working fine", str);
		//If you need to log messages that should appear in the generated HTML reports, you can use the class org.testng.Reporter
		
		//位置 ： index.html---> reporter output
		//file:///C:/Users/zhoupp/workspace/testng02/test-output/emailable-report.html
		//com.zhou.test.TestNGSimpleTest.testAdd 的功能
		Reporter.log("M3 WAS CALLED");

	}
	
	@Test
	public void testAdd2() {
		
		String str = "TestNG is working fine";
		assertEquals("TestNG is working fine", str);
	}
	
	
	
	
	// 一个suite  + 1个class
	/*
	<suite name="TmpSuite" >
	  <test name="TmpTest" >
	    <classes>
	      <class name="com.zhou.test.TestNGSimpleTest"  />
	    <classes>
	    </test>
	</suite>
	*/
	public static void main(String[] args) {
		XmlSuite suite = new XmlSuite();
		suite.setName("TmpSuite");
		System.out.println(suite);//  [Suite: "TmpSuite" ]
		XmlTest test = new XmlTest(suite);
		test.setName("TmpTest");//   
		System.out.println(test);//[Test: "TmpTest" verbose:1[parameters:][metagroups:] [included: ][excluded: ]  classes: packages:] 
		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(new XmlClass("com.zhou.test.TestNGSimpleTest"));
		System.out.println(classes);// [[XmlClass class=com.zhou.test.TestNGSimpleTest]]
		test.setXmlClasses(classes) ;
		
		
		//然后你可以将XmlSuite传递给TestNG：
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		System.out.println(suites);//[[Suite: "TmpSuite"   [Test: "TmpTest" verbose:1[parameters:][metagroups:] [included: ][excluded: ]  classes:[XmlClass class=com.zhou.test.TestNGSimpleTest]  packages:]  ]]
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		System.out.println(tng);//org.testng.TestNG@6e2c634b
		tng.run();
		
		//[org.testng.reporters.jq.Main@31221be2, org.testng.reporters.SuiteHTMLReporter@685f4c2e, org.testng.reporters.JUnitReportReporter@3eb07fd3, org.testng.reporters.XMLReporter@2ef1e4fa, [FailedReporter passed=0 failed=0 skipped=0], org.testng.reporters.EmailableReporter2@2b71fc7e]
		System.out.println(tng.getReporters());
		System.out.println(tng.getReporters().size());//6
		
		 XMLReporter xMLReporter= new XMLReporter();
		 System.out.println(xMLReporter.getOutputDirectory());//null
		 
		
	}

}
