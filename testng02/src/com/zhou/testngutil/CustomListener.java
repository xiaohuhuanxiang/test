package com.zhou.testngutil;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Listeners;
import org.testng.reporters.XMLReporter;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

//需要看到的连续状态的测试执行，测试时得到执行，主要用于记录监听器
public class CustomListener extends TestListenerAdapter{

	 private int m_count = 0;
	 
	    @Override
	    public void onTestFailure(ITestResult tr) {
	        log(tr.getName()+ "--Test method failed\n");
	    }
		 
	    @Override
	    public void onTestSkipped(ITestResult tr) {
	        log(tr.getName()+ "--Test method skipped\n");
	       
	    }
		 
	    @Override
	    public void onTestSuccess(ITestResult tr) {
	        log(tr.getName()+ "--Test method success\n");
	    }
		 
	    private void log(String string) {
	        System.out.print(string);
	        if (++m_count % 40 == 0) {
		    System.out.println("");
	        }
	      
	    }
	    
	    /*
	     * 
	     * <suite name="TmpSuite" >
	     * <listeners>
    <listener class-name="CustomListener" />
  </listeners>



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
		
		tng.addListener(new CustomListener() );
		
		
		
		tng.setXmlSuites(suites);
		System.out.println(tng);//org.testng.TestNG@6e2c634b
		tng.run();
		
		//[org.testng.reporters.jq.Main@31221be2, org.testng.reporters.SuiteHTMLReporter@685f4c2e, org.testng.reporters.JUnitReportReporter@3eb07fd3, org.testng.reporters.XMLReporter@2ef1e4fa, [FailedReporter passed=0 failed=0 skipped=0], org.testng.reporters.EmailableReporter2@2b71fc7e]
		System.out.println(tng.getReporters());
		System.out.println(tng.getReporters().size());//6


	}

}
