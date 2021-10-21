package com.softserve.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentAventReporter;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    public static final ExtentReports extentReports = new ExtentReports();
//    public static final ExtentAventReporter avent = new ExtentAventReporter("./extent-reports/extent-report.html");
//    public static final ExtentKlovReporter klov = new ExtentKlovReporter("Parallel_Distributed_Testing", "Sample Extent Report");

    public synchronized static ExtentReports createExtentReports() {

        ExtentSparkReporter reporter = new ExtentSparkReporter("./extent-reports/extent-report.html");
        reporter.config().setReportName("Sample Extent Report");
        ExtentAventReporter avent = new ExtentAventReporter("./extent-reports/extent-report.html");
        ExtentKlovReporter klov = new ExtentKlovReporter("Parallel_Distributed_Testing", "Sample Extent Report");
        extentReports.attachReporter(avent);
        extentReports.attachReporter(klov);
        extentReports.attachReporter(reporter);

        return extentReports;
    }

}
