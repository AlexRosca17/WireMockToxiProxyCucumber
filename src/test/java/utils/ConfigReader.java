package utils;

import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.List;

public class ConfigReader {

    private static XmlSuite suite;

    public static void loadSuite(XmlSuite xmlSuite) {
        suite = xmlSuite;
    }

    public static String getParameter(String name) {
        List<XmlTest> tests = suite.getTests();
        for (XmlTest test : tests) {
            if (test.getParameter(name) != null) {
                return test.getParameter(name);
            }
        }
        return null;
    }
}
