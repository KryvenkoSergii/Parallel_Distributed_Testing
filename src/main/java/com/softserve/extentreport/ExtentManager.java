package com.softserve.extentreport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * We created an ExtentReports object and it can be reachable via createExtentReports() method
 * @author SergiiK
 * 2021-10-17
 */
public class ExtentManager {

    public static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports createExtentReports() {

        ExtentSparkReporter reporter = new ExtentSparkReporter("./extent-reports/extent-report.html");
        reporter.config().setReportName("Sample Extent Report");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("ExtentReports test", "mentor's task for Sergii");
        return extentReports;
    }

}
