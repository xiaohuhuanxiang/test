1. 引入testng.jar会，报错
Caused by: java.lang.ClassNotFoundException: com.beust.jcommander.ParameterException

解决：

引入jcommander.jar库


2. 命令行参数解析库 JCommander   基于注解、TestNG作者开发

JCommander 是一个非常小的Java 类库，用来解析命令行参数。

3. 测试报告

C:\Users\zhoupp\workspace\testng02\test-output
--------------------------------------
/testng02/test-output/emailable-report.html

1. emailable-report.html是Testng运行完成后自动生成的，经常运行结束后我们会把这个文件作为邮件正文发送给收件人

，如果我们要修改这个文件内容怎么办呢？

1、首先emailable-report.html文件的生成TestNG是实现了IReporter接口，那我们可以直接从源代码中取出这个文件源代码
https://github.com/cbeust/testng/blob/master/src/main/java/org/testng/reporters/EmailableReporter.java
2、针对源代码进行自己修改
3、把修改后的源代码加入自己的工程
4、在build.xml文件中新增自定义的监听器

参考：http://qa.blog.163.com/blog/static/19014700220138585422735/


--------------------------------------


testng 失败自动截图  webdriver  +  lister

testng : 源码

https://github.com/cbeust/testng/tree/master/src/main/java/org/testng

原理：

1. 拓扑排序最经典的应用场景就是对于Jobs/Tasks的规划，即对于存在前后依赖关系的任务如何安排一个计划来执行它们
dependsOnMethods  +  dependsOnGroups
Graph类中的topologicalSort方法就是负责拓扑排序的实现了！

关于环路检测算法的实现，用于在发现循环依赖的时候，检测出具体的循环依赖路径：MethodHelper.topologicalSort方法


因此，在TestNG对于依赖关系检测的拓扑排序中，主要有两个功能：
检测依赖关系的正确性，即不存在任何形式的循环依赖
在保证正确性的前提下，将方法分类，分成只能顺序运行的方法以及可以并发运行的方法
 
以上，就是对TestNG中依赖关系相关核心代码的分析。其核心思想还是使用拓扑排序来建立依赖关系。在以后的系列文章中，还会介绍TestNG是如何实现并发运行测试方法，以及一些其他内容，比如，TestNG的几个常用的扩展点，Method Selector机制，各种Listener等等

依赖性方法这个概念是TestNG中十分受欢迎的一个特性，特别是受到类似Selenium这样的Web应用测试框架的青睐，对web应用中页面的测试十分依赖操作的先后顺序。这类测试通常包含了大量的依赖性方法，这意味使用当前的调度算法很难利用任何的并行。

2. TestNG 并发运行相关的核心概念

目前的算法是简单的：
将所有的测试方法分为两个类别：独立方法，依赖方法。
独立方法会被放在一个线程池中，然后由Executor来运行，一个方法对应一个worker，这样做能够保证最大的并行度。
依赖性方法会被排序，然后由一个只含有一个线程的Executor来运行。

http://blog.csdn.net/dm_vincent/article/details/7664761

有了这个新的实现，TestNG能够给予测试最大化的并发度，一些Selenium的用户也反映他们的测试获得了巨大的提高(时间消耗上从一个小时降低到了十分钟)。


如何并行测试跑case??
http://www.importnew.com/14508.html

1. 如何并行地执行测试方法
<suite name="Test-method Suite" parallel="methods" thread-count="2" >
  <test name="Test-method test" group-by-instances="true">
    <classes>
      <class name="com.howtodoinjava.parallelism.ParallelMethodTest" />
    </classes>
  </test>
</suite>
两个测试方法以及各自相应的beforeMethod和afterMethod方法是在两个独立的线程中执行的

2. 如何并行地执行测试类
<suite name="Test-class Suite" parallel="classes" thread-count="2" >
  <test name="Test-class test" >
    <classes>
      <class name="com.howtodoinjava.parallelism.ParallelClassesTestOne" />
      <class name="com.howtodoinjava.parallelism.ParallelClassesTestTwo" />
    </classes>
  </test>
</suite>
两个测试类以及各自相应的beforeClass和afterClass方法是在独立的两个线程中执行的

3. 如何并行地执行同一测试套件内的各个测试组件

<suite name="Test-class Suite" parallel="tests" thread-count="2">
    <test name="Test-class test 1">
        <parameter name="test-name" value="test-method One" />
        <classes>
            <class name="com.howtodoinjava.parallelism.ParallelSuiteTest" />
        </classes>
    </test>
    <test name="Test-class test 2">
        <parameter name="test-name" value="test-method One" />
        <classes>
            <class name="com.howtodoinjava.parallelism.ParallelSuiteTest" />
        </classes>
    </test>
</suite>

两个测试组件是在独立的两个线程中分别执行的




4.  如何配置一个需要在多线程环境中执行的测试方法

public class IndependentTest
{
    @Test(threadPoolSize = 3, invocationCount = 6, timeOut = 1000)
    public void testMethod()
    {
        Long id = Thread.currentThread().getId();
        System.out.println("Test method executing on thread with id: " + id);
    }
}

@Test注解中配置threadPoolSize这个属性来进入多线程模式的。threadPoolSize被设为3，这就说明了该测试方法将会在三个不同的线程中同时执行。
剩余两个属性：invocationCount配置的是该测试方法应被执行的总次数，timeOut配置的是每次执行该测试方法所耗费时间的阈值，超过阈值则测试失败。

看出该测试方法被执行了多次，而且它的执行次数跟invocationCount所指定的次数相同。而且从输出信息中，我们也可以很清晰地看到该测试方法每次执行都是在不同的线程中完成的。当我们需要按照某一固定次数，在多线程环境下运行某些测试方法时，上述特性会很有帮助
----------------------------------

修改eclipse ： 对比test01包中的类不同

http://tech.it168.com/a2013/0906/1530/000001530755_3.shtml




















-------------------------------------
Eclipse 中给项目自动创建ant的build.xml文件 

http://blog.sina.com.cn/s/blog_53a99cf30100f4ci.html

1.  使用 

ant  EmailableReporter

2. 集成jenkins

http://www.cnblogs.com/puresoul/p/4212752.html
-----------------

默认的ReportNG的报告中，是以字母序对执行的方法进行排序的，这不是我们期望的，我们期望是以方法的执行先后顺序来进行排序的


http://tech.it168.com/a2013/0906/1530/000001530755_3.shtml


--------------------------------------
4. 测试报告美化

  http://www.tuicool.com/articles/FJzYBr
http://blog.sina.com.cn/s/blog_68f262210102vvfe.html
http://www.aiuxian.com/article/p-1636871.html
使用TestNG-xslt美化测试报告（用了ReportNG来美化它。在 这里给大家再介绍一下比reportNG还要稍稍美观一点的reporting tool: testNG-xslt）

原理：其实testNG-xslt就是把testNG自动生成的测试报告testng-results.xml进行了一个转换，把它的html输出报告变得更漂亮 而已



美化 自己实现： 

方法一  ： 获取成功率  失败率 跳过率  ;

方法二 ：或者： 直接解析testng-result.xml 提取 后  画图   + 保存为图片  （可以在index.html中 ajax 调用后插入图片）


美化  ReportNG来替代TestNG默认的report  http://tech.it168.com/a2013/0906/1530/000001530755_3.shtml



-----------------



5

问题：

testng必须xml命令行运行？？？？

java如何集成到jenkins？？？  Jenkins+Maven进行Java项目持续集成

1定义maven的pom.xml文件，pom如何定义内容就太多了，网上搜索吧
2将项目提交到版本库软件管理，比如svn
3在jenkins上checkout这个项目，定义maven执行命令，比如 maven package


