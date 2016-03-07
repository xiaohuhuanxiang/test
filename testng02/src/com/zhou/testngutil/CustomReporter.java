package com.zhou.testngutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;


public class CustomReporter implements IReporter{

	
	 @Override
	    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
	        String outputDirectory) {
	        //Iterating over each suite included in the test
	        for (ISuite suite : suites) {
	            //Following code gets the suite name
	            String suiteName = suite.getName();
		    //Getting the results for the said suite
		    Map<String, ISuiteResult> suiteResults = suite.getResults();
		    System.out.println("$$$$$$$$$$"+ suiteResults.size());// 1
		  
		    
		    for (ISuiteResult sr : suiteResults.values()) {
		        ITestContext tc = sr.getTestContext();
		        System.out.println("Passed tests for suite '" + suiteName +
		             "' is:" + tc.getPassedTests().getAllResults().size());
		        System.out.println("Failed tests for suite '" + suiteName +
		             "' is:" + 
		             tc.getFailedTests().getAllResults().size());
		        System.out.println("Skipped tests for suite '" + suiteName +
		             "' is:" + 
		             tc.getSkippedTests().getAllResults().size());
		        
		        /*
		         
		         	Passed tests for suite 'TmpSuite' is:2
					Failed tests for suite 'TmpSuite' is:0
					Skipped tests for suite 'TmpSuite' is:0 


		         */
		      }
	        }
	    }
	 
	 /*
	  * 
	  
	  <suite name="TmpSuite" >
	     * <listeners>
    			<listener class-name="CustomListener" />
    			<listener class-name="CustomReporter" />
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
		tng.addListener(new CustomReporter() );
		
		
		tng.setXmlSuites(suites);
		System.out.println(tng);//org.testng.TestNG@6e2c634b
		tng.run();
		
		//[org.testng.reporters.jq.Main@31221be2, org.testng.reporters.SuiteHTMLReporter@685f4c2e, org.testng.reporters.JUnitReportReporter@3eb07fd3, org.testng.reporters.XMLReporter@2ef1e4fa, [FailedReporter passed=0 failed=0 skipped=0], org.testng.reporters.EmailableReporter2@2b71fc7e]
		System.out.println(tng.getReporters());
		System.out.println(tng.getReporters().size());//6
		
	}

}
