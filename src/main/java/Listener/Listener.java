package Listener;

import Abstract.MainFunction;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener extends MainFunction implements ITestListener {

    public void onTestStart(ITestResult ıTestResult) {

    }

    public void onTestSuccess(ITestResult ıTestResult) {
        System.out.println("Test Passed.");
        ScreenShot();
    }

    public void onTestFailure(ITestResult ıTestResult) {
        System.out.println("The error has been received and the Screenshot Created.");
        ScreenShot();

    }

    public void onTestSkipped(ITestResult ıTestResult) {
        ScreenShot();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult ıTestResult) {
        ScreenShot();

    }

    public void onStart(ITestContext ıTestContext) {

    }

    public void onFinish(ITestContext ıTestContext) {

    }
}
