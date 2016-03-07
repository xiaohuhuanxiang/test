package com.zhou.testngutil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Listeners;
import org.testng.reporters.XMLReporter;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;


//// 对TakeScreenshotTest 中失败或跳过的case进行 driver打开的页面截图，并保存到test-output/snapshot 下， 文件名： 日期 + 方法.jpg


//需要看到的连续状态的测试执行，测试时得到执行，主要用于记录监听器
public class TakeScreenShotListener extends TestListenerAdapter{

	 private int m_count = 0;
	 
	    @Override
	    public void onTestFailure(ITestResult tr) {
	        log(tr.getName()+ "--Test method failed\n");
	        takeScreenShot( tr);
	    }
		 
	    @Override
	    public void onTestSkipped(ITestResult tr) {
	        log(tr.getName()+ "--Test method skipped\n");
	        takeScreenShot( tr);
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
	    /**
		 * 自动截图，保存图片到本地以及html结果文件中
		 * 
		 * @param tr
		 */
	    
	    private void takeScreenShot(ITestResult tr) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			String mDateTime = formatter.format(new Date());
			String fileName = mDateTime + "_" + tr.getName()+".jpg";
			
			TakeScreenshot takeScreenshot =new TakeScreenshot();
			takeScreenshot.takeScreenshot(fileName);
			

			Reporter.setCurrentTestResult(tr);

	               //这里实现把图片链接直接输出到结果文件中，通过邮件发送结果则可以直接显示图片
			Reporter.log("<img src=\"../" + fileName + "\"/>");

		}
	    
	    
	    
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
		
		tng.addListener(new TakeScreenShotListener() );
		
		
		
		tng.setXmlSuites(suites);
		System.out.println(tng);//org.testng.TestNG@6e2c634b
		tng.run();
		
		
		System.out.println(tng.getReporters());
		System.out.println(tng.getReporters().size());//6


	}

}
