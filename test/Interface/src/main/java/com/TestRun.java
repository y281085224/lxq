package com;

import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class TestRun {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        TestNG testNG = new TestNG();
        FileInputStream fis = new FileInputStream("C:\\Users\\lxq\\Desktop\\Java+接口自动化\\修改\\1\\AutoTest2\\src\\main\\resources/testng.xml");

        //识别xml文件中的内容
        Parser p = new Parser(fis);

        //获取xml中所有的suite套件，及里面的所有的内容
        List<XmlSuite> suites =p.parseToList();
        System.out.println(suites);

        for (XmlSuite suite: suites) {
            List<XmlTest> tests = suite.getTests();

            //获取第几个test，从0开始
            XmlTest test = tests.get(0);
            System.out.println(test);

            List<XmlClass> classList = test.getClasses();
            System.out.println(classList);

            //获取suite
            XmlSuite suite1 = test.getSuite();
            System.out.println(suite1);

            /*
            是加在这里的，没看懂
            for (int i=0;i<2;i++){
                XmlTest xmlTest = new XmlTest();
                xmlTest.setClasses(classList);
                xmlTest.setName("site"+i);
                xmlTest.setSuite(suite1);
                tests.add(xmlTest);
            }
             */


        }
        testNG.setXmlSuites(suites);
        testNG.run();
        fis.close();

    }
}
