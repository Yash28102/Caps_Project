package runner;

import org.testng.TestNG;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    @Test
    public void executeSuitePipeline() {

        TestNG testng = new TestNG();

        List<String> suites = new ArrayList<>();
        suites.add(System.getProperty("user.dir") + "/testng.xml");

        testng.setTestSuites(suites);
        testng.run();
    }
}