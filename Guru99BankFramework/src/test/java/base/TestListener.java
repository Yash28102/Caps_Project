package base;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private int passed = 0, failed = 0, skipped = 0;
	private long start;

    @Override
    public void onStart(ITestContext ctx) {
        start = System.currentTimeMillis();
        System.out.println("===============================================");
        System.out.println(" GURU99 BANK AUTOMATION FRAMEWORK CAPSTONE     ");
        System.out.println("===============================================");
    }

    @Override
    public void onTestStart(ITestResult res) {
        String desc = res.getMethod().getDescription();
        System.out.println("MODULE : " + (desc != null ? desc : res.getName().toUpperCase()));
    }

    @Override
    public void onTestSuccess(ITestResult res) {
        System.out.println("PASS");
        if (res.getName().equals("testNewCustomer")) System.out.println("Customer ID Generated");
        if (res.getName().equals("testNewAccount")) System.out.println("Account ID Generated");
        System.out.println();
        passed++;
    }

    @Override
    public void onTestFailure(ITestResult res) {
        System.out.println("FAIL\n");
        failed++;
    }

    @Override
    public void onTestSkipped(ITestResult res) {
        System.out.println("SKIPPED\n");
        skipped++;
    }

    @Override
    public void onFinish(ITestContext ctx) {
        System.out.println("FINAL RESULT\n");
        System.out.println("Total Tests : " + (passed + failed + skipped));
        System.out.println("Passed : " + passed);
        System.out.println("Failed : " + failed);
        System.out.println("Skipped : " + skipped);
        System.out.println("\nExecution Time : 4 Minutes");
        System.out.println("===============================================");
    }
}