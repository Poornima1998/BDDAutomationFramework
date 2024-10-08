package com.test.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;

import com.test.utils.Utils.Config;

public class Log {

    private static final Logger logger=LogManager.getLogger(Log.class);
    private static String screenShotFolderPath;
//    private static String reportPortal;
//    private static ExtentReporter extentReporter = new ExtentReporter();
    private static ReportPortal reportPortal = new ReportPortal();
    private static boolean isReportPortalEnabled;
    private static AtomicInteger screenshotIndex = new AtomicInteger();
    
    
    
    static final String TEST_TITLE_HTML_BEGIN = "&emsp; <div class=\"test-title\"> <strong><font size = \"3\" color = \"#000080\">" ;
    static final String TEST_TITLE_HTML_END = "</font></strong></div>&emsp; <div><strong>Steps:</strong><br></div>";
    static final String TEST_TITLE_HTML_END1 = "</font> </strong> </div>&emsp;";

    static final String TEST_CON_HTML_BEGIN ="&emsp; <div class=\"test-title\"> <strong><font size = \"3\" color = \"#0000FF\">" ;
    static final String TEST_CON_HTML_END = "</font> </strong> </div>&emsp;" ;
    
    static final String MESSAGE_HTML_BEGIN = "&emsp;<div class=\"test-message\">";
    static final String MESSAGE_HTML_END = "</div>";
    
    
    static final String PASS_HTML_BEGIN =  "<div class=\"test-result\"><br><font color=\"green\"><strong> ";
    static final String PASS_HTML_END1 =" </strong></font> ";
    static final String PASS_HTML_END2  = "</div>&emsp; ";
    
    static final String FAIL_HTML_BEGIN = "<div class=\"test-result\"><br><font color=\"red\">strong> ";
    static final String FAIL_HTML_END1 = " </strong></font> ";
    static final String FAIL_HTML_END2 = "</div>&emsp; ";

    static final String SKIP_EXCEPTION_HTML_BEGIN = "<div class=\"test-result\"><br><font color=\"orangel\"><strong>";
    static final String SKIP_HTML_END1 = " </strong></font> ";
    static final String SKIP_HTML_END2 = " </strong></font> ";
    
    static final String EVENT_HTML_BEGIN = "&emsp; &emsp; <div class-\"test-event\"> <font color-\"maroon\"> <small> ";
    static final String EVENT_HTML_END = "</small> </ font> </div>";
    
    
    static final String  TRACE_HTML_BEGIN = "<div class=\"test-event\"> <font color=\"brown\"> <small> &emsp; &emsp";
    static final String  TRACE_HTML_END =  "</ small> </font> </div>";
    
    static final String PERFORMANCE_HTML_BEGIN = "<script src=\"";
    static final String PERFORMANCE_HTML_END = "\"></script>";
    
    private static final String BEGIN_DECS = "****                         ";
    private static final String END_DECS =   "                         ****";
    
    private static final String EMSP =   "&emsp;";
    private static final String REPORT_EMSP =   "<!-- Report -->&emsp;";
    private static final String BUG_ID =   " BUGID:";
    private static final String COMMENT =   " COMMENTS:";
    private static final String TEST_EXECUTION_STARTS =   "--- Test Execution Starts ---";
    private static final String TEST_EXECUTION_ENDS =   "--- Test Execution Ends ---";
    private static final String NBSP =   "&nbsp";
    private static final String AND_EMSP =   "---&emsp;";
    private static final String JUSTIFY_STRING_75 =   "%-75s";
    private static final String DATE_FORMAT =   "dd MMM HH:mm:ss SSS";
    private static final String REPORTER_LOG_FAIL =   "<!---FAIL--->";
    private static final String FATAL =   "Fatal";
    private static final String TARGET_BLANK =   "' target='_blank'>";
    
    
    
    
//    static final String TEST_TITLE_HTML_BEGIN = "&emsp; <div class=\"test-title\"><strong><font size=\"3\" color=\"#000080\">";
//    static final String TEST_TITLE_HTML_END = "</font></strong></div>&emsp;<div><strong>Steps:</strong></div>";
//
//    static final String TEST_CON_HTML_BEGIN = "&emsp; <div class=\"test-title\"><strong><font size=\"3\" color=\"#0000FF\">";
//    static final String TEST_CON_HTML_END = "</font></strong></div>&emsp;";
//
//    static final String MESSAGE_HTML_BEGIN = "&emsp;<div class=\"test-message\">";
//    static final String MESSAGE_HTML_END = "</div>";
//
//    static final String PASS_HTML_BEGIN = "<div class=\"test-result\"><br><font color=\"green\"><strong>";
//    static final String PASS_HTML_END1 = "</strong></font>";
//    static final String PASS_HTML_END2 = "</div>&emsp;";
//
//    static final String FAIL_HTML_BEGIN = "<div class=\"test-result\"><br><font color=\"red\"><strong>";
//    static final String FAIL_HTML_END1 = "</strong></font>";
//    static final String FAIL_HTML_END2 = "</div>&emsp;";
//
//    static final String SKIP_EXCEPTION_HTML_BEGIN = "<div class=\"test-result\"><br><font color=\"orange\"><strong>";
//    static final String SKIP_HTML_END1 = "</strong></font>";
//    static final String SKIP_HTML_END2 = "</div>&emsp;";
//
//    static final String EVENT_HTML_BEGIN = "&emsp;&emsp;<div class=\"test-event\"><font color=\"maroon\"><small>";
//    static final String EVENT_HTML_END = "</small></font></div>";
//
//    static final String TRACE_HTML_BEGIN = "<div class=\"test-event\"><font color=\"brown\"><small>&emsp;&emsp;";
//    static final String TRACE_HTML_END = "</small></font></div>";
//
//    static final String PERFORMANCE_HTML_BEGIN = "<script src=\"";
//    static final String PERFORMANCE_HTML_END = "\"></script>";
//
//    private static final String BEGIN_DECS = "**** ";
//    private static final String END_DECS = " ****";
//
//    private static final String EMSP = "&emsp;";
//    private static final String REPORT_EMSP = "<!-- Report -->&emsp;";
//    private static final String BUG_ID = "BUGID:";
//    private static final String COMMENT = "COMMENTS:";
//    private static final String TEST_EXECUTION_ENDS = "--- Test Execution Ends ---";
//    private static final String NBSP = "&nbsp;";
//    private static final String AND_EMSP = "---&emsp;";
//    private static final String JUSTIFY_STRING_75 = "%-75s";
//    private static final String DATE_FORMAT = "dd MMM HH:mm:ss SSS";
//    private static final String REPORTER_LOG_FAIL = "<!---FAIL--->";
//    private static final String FATAL = "Fatal";
//    private static final String TRAGET_BLANK = "' target='_blank'>";

    

    static {
    	
        try {
            File screenShotFolder = new File(Reporter.getCurrentTestResult().getTestContext().getOutputDirectory());
            screenShotFolderPath = screenShotFolder.getParent() + File.separator + "ScreenShot" + File.separator;
            if (!new File(screenShotFolderPath).exists()) {
                new File(screenShotFolderPath).mkdir();
            }
            FileUtils.cleanDirectory(new File(screenShotFolderPath));
//            reportPortal = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("reportPortal");
            isReportPortalEnabled = Boolean.parseBoolean(
                    Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("reportPortal")
                );
            Boolean.parseBoolean(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("printconsoleoutput"));
           
        } catch (Exception e) {
            logger.error("Error initializing Log1 class", e);
        }
    }

    public static Logger lsLog4j() {
		return LogManager.getLogger(Thread.currentThread().getName());
    	
    }
    
    public static String callerClass() {
		return Thread.currentThread().getStackTrace()[2].getClassName();
    	
    }
    
    
    public static String takeScreenShot(WebDriver driver) {
        String fileName = Reporter.getCurrentTestResult().getName() + "_" + System.nanoTime() + ".png";
        File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShot, new File(screenShotFolderPath + fileName));
        } catch (Exception e) {
            logger.error("Error taking screenshot", e);
        }
        return fileName;
    }

    public static String getScreenShotHyperLink(String fileName) {
        return "<a href=\"." + File.separator + "ScreenShot" + File.separator + fileName + "\" target=\"_blank\">[ScreenShot]</a>";
    }
    
    
    public static void testCaseInfoOld(String description) throws Exception {
    	lsLog4j().log(Level.INFO, "[TESTCASE DESCRIPTION- " + description + " ]");
    	if(Reporter.getOutput(Reporter.getCurrentTestResult()).toString().contains("<div class=\"test-title\">")) {
    		Reporter.log(TEST_TITLE_HTML_BEGIN + description + TEST_TITLE_HTML_END + EMSP);
    	}else {
    		Reporter.log(TEST_TITLE_HTML_BEGIN + description + TEST_TITLE_HTML_END + REPORT_EMSP);
    	}
        ExtentReporter.testCaseInfo(description);
    }
    
    public static void testCaseInfo(String description, WebDriver driver) throws Exception {
        // Retrieve browser and system information
        String browserName = Utils.getBrowserName(driver);
        String browserVersion = Utils.getBrowserVersion(driver);
        String osName = Utils.getOperatingSystem();
        String javaVersion = Utils.getJavaVersion();

        // Log the test case description, browser, and system info to Log4j
        String logMessage = String.format(
            "[TESTCASE DESCRIPTION - %s] [BROWSER - %s %s] [OS - %s] [JAVA - %s]",
            description, browserName, browserVersion, osName, javaVersion
        );
        Reporter.log(TEST_TITLE_HTML_BEGIN + TEST_EXECUTION_STARTS + TEST_TITLE_HTML_END1 + EMSP);
        lsLog4j().log(Level.INFO, logMessage);
        // Get the current test result output and safely check for the test title
        List<String> reporterOutput = Reporter.getOutput(Reporter.getCurrentTestResult());
        if (reporterOutput != null && !reporterOutput.isEmpty() && reporterOutput.toString().contains("<div class=\"test-title\">")) {
            Reporter.log(TEST_TITLE_HTML_BEGIN + description + " | Browser: " + browserName + " " + browserVersion + " | OS: " + osName + " | Java: " + javaVersion + TEST_TITLE_HTML_END + EMSP);
        } else {
            Reporter.log(TEST_TITLE_HTML_BEGIN + description + " | Browser: " + browserName + " " + browserVersion + " | OS: " + osName + " | Java: " + javaVersion + TEST_TITLE_HTML_END + REPORT_EMSP);
        }

        // Log the test case information to ExtentReports
        ExtentReporter.testCaseInfo(description + " | Browser: " + browserName + " " + browserVersion + " | OS: " + osName + " | Java: " + javaVersion);
        System.out.println(description + " | Browser: " + browserName + " " + browserVersion + " | OS: " + osName + " | Java: " + javaVersion);
    }
    
    public static void setThreadNameAsMethodName(String methodName) {
        Thread.currentThread().setName(methodName);
    }
    
    public static void testConditionInfo(String description) {
        Reporter.log(TEST_CON_HTML_BEGIN + description + TEST_CON_HTML_END);
    }

    public static void testCaseResult(WebDriver driver) {
    	if(Reporter.getOutput(Reporter.getCurrentTestResult()).toString().contains("FAILSOFT")) {
    		fail("Test Failed. Check the steps above in red color.", driver);
    	}
    	else if (Reporter.getOutput(Reporter.getCurrentTestResult()).toString().contains("FAIL")) {
            fail("Test Failed. Check the steps above in red color.", driver);
        } else {
            pass("Test Passed.", driver);
        }
    }
    
    public static void endTestCase() throws Exception {
    	lsLog4j().info(BEGIN_DECS + TEST_EXECUTION_ENDS + END_DECS);
        Reporter.log(TEST_CON_HTML_BEGIN + TEST_EXECUTION_ENDS + TEST_CON_HTML_END);
        ExtentReporter.endTest(TEST_EXECUTION_ENDS);
        ExtentReporter.flushAndCloseReport();
    }

    public static void message(String description) {
        Reporter.log(MESSAGE_HTML_BEGIN + description + MESSAGE_HTML_END);
        lsLog4j().log(Level.INFO, description);
        ExtentReporter.info(description);
        System.out.println(description);
        if (isReportPortalEnabled) {
            ReportPortal.emitLog(description, "Debug", Calendar.getInstance().getTime());
        }
    }
    
    public static void message1(String description) {
        Reporter.log(description);
        System.out.println(description);
        lsLog4j().log(Level.INFO, description);
        ExtentReporter.info(description);
    }
    
    public static void message(WebDriver driver, String description, Boolean... takeScreenShot) {
    	boolean finalDecision =true;
        if (takeScreenShot.length>0 && takeScreenShot[0].equals(Boolean.FALSE)) {
        	finalDecision=false;
        }
        if(Config.readProperty("isTakeScreenShot")!=null && Boolean.parseBoolean(Config.readProperty("isTakeScreenShot")) == false) {
        	finalDecision=false;
        }
        if(finalDecision) {
        	String inputFile = takeScreenShot(driver);
        	if(isReportPortalEnabled) {
        		 ReportPortal.emitLog(description, "Debug", Calendar.getInstance().getTime());
        		 new File(screenShotFolderPath + File.separator + inputFile);
        		 Reporter.log(MESSAGE_HTML_BEGIN + description + EMSP +getScreenShotHyperLink(inputFile) + MESSAGE_HTML_END);
        		 ExtentReporter.info(description + " " + getScreenShotHyperLink(inputFile)); 
        	}
        } else {
            Reporter.log(MESSAGE_HTML_BEGIN + description + MESSAGE_HTML_END);
            ExtentReporter.info(description);
        }
        lsLog4j().log(Level.INFO, description);
    }
    
    
    public static void message(WebDriver driver, String description,String screenshotFolderPath, String screenshotFileName) throws IOException {
    	
    	String inputFile = screenshotFileName + ".png";
    	WebDriver augement = new Augmenter().augment(driver);
    	File screenshot = ((TakesScreenshot) augement).getScreenshotAs(OutputType.FILE);
    	FileUtils.copyFile(screenshot, new File(screenshotFolderPath + File.separator + inputFile));
    	FileUtils.copyFile(screenshot, new File(screenShotFolderPath + File.separator + inputFile));
    	Reporter.log(MESSAGE_HTML_BEGIN + String.format(JUSTIFY_STRING_75, description).replace(" ", NBSP) + AND_EMSP 
    			+ getScreenShotHyperLink(inputFile) + MESSAGE_HTML_END);
    	ExtentReporter.info(String.format(JUSTIFY_STRING_75, description).replace(" ", NBSP) + AND_EMSP + getScreenShotHyperLink(inputFile));
        lsLog4j().log(Level.INFO, description);
    }
    
    
    public static String buildMessage(WebDriver driver, String description,String screenshotFolderPath, String screenshotFileName) throws Exception {
    	String inputFile = screenshotFileName + ".png";
    	WebDriver augement = new Augmenter().augment(driver);
    	File screenshot = ((TakesScreenshot) augement).getScreenshotAs(OutputType.FILE);
    	FileUtils.copyFile(screenshot, new File(screenshotFolderPath + File.separator + inputFile));
    	FileUtils.copyFile(screenshot, new File(screenShotFolderPath + File.separator + inputFile));
		return MESSAGE_HTML_BEGIN + String.format(JUSTIFY_STRING_75, description).replace(" ", NBSP) + AND_EMSP 
    			+ getScreenShotHyperLink(inputFile) + MESSAGE_HTML_END;
    	
    }
    
    
	 public static void message(String description,String screenshot) throws Exception {
	    	try {
	    	String inputFile = Reporter.getCurrentTestResult().getName() + "_" + screenshotIndex.incrementAndGet() + ".png";
	    	FileUtils.copyFile(new File(screenshot), new File(screenShotFolderPath + inputFile));
	    	Reporter.log(MESSAGE_HTML_BEGIN + description + getScreenShotHyperLink(inputFile) + MESSAGE_HTML_END);
	    	ExtentReporter.info(description + getScreenShotHyperLink(inputFile));
	        lsLog4j().log(Level.INFO, description);
	    	} catch(FileNotFoundException e) {
	    		Reporter.log(MESSAGE_HTML_BEGIN + description + MESSAGE_HTML_END);
	        	ExtentReporter.info(description);
	    	}
	    }
	 
	 
	 public static void event(String description) {
		    String curDate = new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime());
		    Reporter.log(EVENT_HTML_BEGIN + curDate + " - " + description + EVENT_HTML_END);
	    	lsLog4j().log(Level.DEBUG, description);
//            ExtentReporter.debug(description);
	    }
	 
	 
	 public static void event(String description, long duration) {
		    String curDate = new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime());
		    Reporter.log(EVENT_HTML_BEGIN + curDate + " - <b>" + duration + "</b> - " + description + " - " 
		    + Thread.currentThread().getStackTrace()[2].toString() + EVENT_HTML_END);
	    	lsLog4j().log(Level.DEBUG, description);
//         ExtentReporter.debug(currDate + " - <b>" + duration + "</b> - " + description + " - " 
//          + Thread.currentThread().getStackTrace()[2].toString());
	    }
	 
	 public static void trace(String description) {
		    String curDate = new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime());
		    Reporter.log(TRACE_HTML_BEGIN + curDate + " - " + description + TRACE_HTML_END);
	    	lsLog4j().log(Level.TRACE, description);
	    }
	 
	 
	 public static void trace(String description, long duration) {
		    String curDate = new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime());
		    Reporter.log(TRACE_HTML_BEGIN + curDate + " - <b>" + duration + "</b> - " + description + " - " 
		    + Thread.currentThread().getStackTrace()[2].toString() + TRACE_HTML_END);
		    lsLog4j().log(Level.TRACE, description);

	    }
	 

    public static void pass(String description) {
        Reporter.log(PASS_HTML_BEGIN + description + PASS_HTML_END1 + PASS_HTML_END2);
        lsLog4j().log(Level.INFO, description);
        ExtentReporter.pass(description);
        if (isReportPortalEnabled) {
            ReportPortal.emitLog(description, "Info", Calendar.getInstance().getTime());
        }
    }
    
    public static void pass(String description, WebDriver driver) {
    	String inputFile = null;
        if(Config.readProperty("isTakeScreenShot")!=null && Boolean.parseBoolean(Config.readProperty("isTakeScreenShot")) == true)  {
        	inputFile = takeScreenShot(driver);
        	Reporter.log(PASS_HTML_BEGIN + description + PASS_HTML_END1 +getScreenShotHyperLink(inputFile)  + PASS_HTML_END2);
        	ExtentReporter.pass(description + " " + getScreenShotHyperLink(inputFile)); 
        } else {
        	Reporter.log(PASS_HTML_BEGIN + description +  PASS_HTML_END1 + PASS_HTML_END2);
            ExtentReporter.pass(description);
        }
        lsLog4j().log(Level.INFO, description);
    	
        if (isReportPortalEnabled) 
            ReportPortal.emitLog(description, "Info", Calendar.getInstance().getTime());
        lsLog4j().log(Level.INFO, description);
    }

    public static void fail(String description) {
    	lsLog4j().log(Level.ERROR, description);
    	Reporter.log(REPORTER_LOG_FAIL);
    	Reporter.log(FAIL_HTML_BEGIN + description + FAIL_HTML_END1 + FAIL_HTML_END2);
    	ExtentReporter.fail(description); 
    	ExtentReporter.logStackTrace(new AssertionError(description));
    	
    	Assert.fail(description);
        if (isReportPortalEnabled) {
            ReportPortal.emitLog(description, "Error", Calendar.getInstance().getTime());
        }
    }
    
    public static void fail(String description, WebDriver driver) {
    	String inputFile = takeScreenShot(driver);
    	Reporter.log(REPORTER_LOG_FAIL);
    	Reporter.log(FAIL_HTML_BEGIN + description + FAIL_HTML_END1 +getScreenShotHyperLink(inputFile)  + FAIL_HTML_END2);
    	ExtentReporter.fail(description + " " + getScreenShotHyperLink(inputFile)); 
    	ExtentReporter.logStackTrace(new AssertionError(description));
    	lsLog4j().log(Level.ERROR, description);
    	Assert.fail(description);
        if (isReportPortalEnabled) {
            ReportPortal.emitLog(description, "Error", Calendar.getInstance().getTime(), new File(screenShotFolderPath + File.separator + inputFile));
        }
    }
    
    public static boolean hasFailSofts() {
    	return Reporter.getOutput(Reporter.getCurrentTestResult()).toString().contains("FAILSOFT");
    }

    public static void failsoft(String description, WebDriver driver) {
    	String inputFile = takeScreenShot(driver);
    	Reporter.log(FAIL_HTML_BEGIN + description + FAIL_HTML_END1 +getScreenShotHyperLink(inputFile)  + FAIL_HTML_END2);
    	Reporter.log("<!--FAILSOFT-->");
    	ExtentReporter.fail(description + " " + getScreenShotHyperLink(inputFile)); 
    	lsLog4j().log(Level.ERROR, description);
    }
    
    public static void failsoft(String description) {
    	Reporter.log(FAIL_HTML_BEGIN + description + FAIL_HTML_END1 + FAIL_HTML_END2);
    	Reporter.log("<!--FAILSOFT-->");
    	ExtentReporter.fail(description);
    	System.err.println(description);
    	lsLog4j().log(Level.ERROR, description);
    }
    
    public static String buildailsoftMessage(String description, WebDriver driver) {
    	String inputFile = takeScreenShot(driver);
    	return FAIL_HTML_BEGIN + description + FAIL_HTML_END1 +getScreenShotHyperLink(inputFile)  + FAIL_HTML_END2;
    }
    
    public static void exception(Exception e, WebDriver driver) throws Exception {
    	String ScreenshotLink ="";
    	String inputFile="";
    	try {
    	inputFile = takeScreenShot(driver);
    	} catch(Exception ex) {
    		
    	}
    	String eMessage = e.getMessage();
    	if(eMessage!=null && eMessage.contains("\n")) {
    		eMessage = eMessage.substring(0, eMessage.indexOf("\n"));
    	}
    	lsLog4j().log(Level.FATAL, eMessage, e);
    	if(e instanceof SkipException) {
    		Reporter.log(SKIP_EXCEPTION_HTML_BEGIN + eMessage + SKIP_HTML_END1 + ScreenshotLink + SKIP_HTML_END2);
    		ExtentReporter.skip(eMessage + ScreenshotLink);
    		ExtentReporter.logStackTrace(e);
    		if(isReportPortalEnabled) {
    			ReportPortal.emitLog(e.getStackTrace().toString(), FATAL, Calendar.getInstance().getTime(), new File(screenShotFolderPath + File.separator + inputFile));
    			
            } 
    	} else {
    		Reporter.log(REPORTER_LOG_FAIL);
    		Reporter.log(FAIL_HTML_BEGIN + eMessage + FAIL_HTML_END1 + ScreenshotLink + FAIL_HTML_END2);
    		ExtentReporter.fail(eMessage + ScreenshotLink);
    		ExtentReporter.logStackTrace(e);
    		if(isReportPortalEnabled) {
    			ReportPortal.emitLog(e.getStackTrace().toString(), FATAL, Calendar.getInstance().getTime(), new File(screenShotFolderPath + File.separator + inputFile));
    			
            }
        }
    	throw e;
    }
    	
    public static void exception(Exception e) throws Exception {
    	String eMessage = e.getMessage();
    	if(eMessage!=null && eMessage.contains("\n")) {
    		eMessage = eMessage.substring(0, eMessage.indexOf("\n"));
    	}
    	lsLog4j().log(Level.FATAL, eMessage, e);
    	if(e instanceof SkipException) {
    		Reporter.log(SKIP_EXCEPTION_HTML_BEGIN + eMessage + SKIP_HTML_END1 + SKIP_HTML_END2);
    		ExtentReporter.skip(eMessage);
    		ExtentReporter.logStackTrace(e);
    		if(isReportPortalEnabled) {
    			ReportPortal.emitLog(e.getStackTrace().toString(), FATAL, Calendar.getInstance().getTime());
    			
            } 
    	} else {
    		Reporter.log(REPORTER_LOG_FAIL);
    		Reporter.log(FAIL_HTML_BEGIN + eMessage + FAIL_HTML_END1 + FAIL_HTML_END2);
    		ExtentReporter.fail(eMessage);
    		ExtentReporter.logStackTrace(e);
    		if(isReportPortalEnabled) {
    			ReportPortal.emitLog(e.getStackTrace().toString(), FATAL, Calendar.getInstance().getTime());
    			
            }
        }
    	throw e;
    }
    
    
    public static void assertThat(boolean status, String PassMessage, String failMessage) {
    	if(!status) {
    		Log.fail(failMessage);
    	}else {
    		Log.message(PassMessage);
    	}
    }
    
    public static void assertThat(boolean status, String PassMessage, String failMessage, WebDriver driver) {
    	if(!status) {
    		Log.fail(failMessage, driver);
    	}else {
    		Log.pass(PassMessage, driver);
    	}
    }
    
    public static void addSauceLabUrlToReport(WebDriver driver, String sauceLink) {
    	Object params[] = Reporter.getCurrentTestResult().getParameters();
    	if(params == null || params.length == 0) {
    		params = new Object[1];
    	}
    	params[0] = (params[0] == null || !params[0].toString().contains("SauceLab link"))
    			? "SauceLab link: <a href='" + sauceLink + TARGET_BLANK + sauceLink+ "</a>"
    			: params[0] + "<br>SauceLab link: <a href='" + sauceLink + TARGET_BLANK + sauceLink+ "</a>";
    	Reporter.getCurrentTestResult().setParameters(params);
    	ExtentReporter.addAttribute("SauceLab link: <a href='" + sauceLink + TARGET_BLANK + sauceLink + "</a>");
    			
    }
    
    public static void addLambdaJobUrlToReport(WebDriver driver, String lambdaLink) {
    	Object params[] = Reporter.getCurrentTestResult().getParameters();
    	if(params == null || params.length == 0) {
    		params = new Object[1];
    	}
    	params[0] = (params[0] == null || !params[0].toString().contains("LambdaTest link"))
    			? "LambdaTest link: <a href='" + lambdaLink + TARGET_BLANK + lambdaLink+ "</a>"
    			: params[0] + "<br>LambdaTest link: <a href='" + lambdaLink + TARGET_BLANK + lambdaLink+ "</a>";
    	Reporter.getCurrentTestResult().setParameters(params);
    	ExtentReporter.addAttribute("LambdaTest link: <a href='" + lambdaLink + TARGET_BLANK + lambdaLink+ "</a>");
    			
    }
    
    public static void addExecutionEnvironmentToReport(WebDriver driver, String browserAndPlatform) {
    	Object[] obj = browserAndPlatform.split(",");
    	Reporter.getCurrentTestResult().setParameters(obj); 			
    }
    
    public static void performanceReport(String script) {
    	Reporter.log(PERFORMANCE_HTML_BEGIN+ script + PERFORMANCE_HTML_END);
		ExtentReporter.addAttribute(PERFORMANCE_HTML_BEGIN+ script + PERFORMANCE_HTML_END);			
    }
    
    private static String getMethodNameFromException(Exception e, String name) {
    	StackTraceElement[] stacktrace = e.getStackTrace();
    	for(StackTraceElement element : stacktrace) {
    		if(element.getClass().equals(name)) {
    			String methodName = element.getMethodName();
    			return methodName;
    		}
    	}
       	return "";	
    }
    
	public static void printExceptionDetails(Exception e, String name, WebDriver driver) {
    	String methodName = getMethodNameFromException(e, name);
    	String error = e.getMessage();
    	e.printStackTrace();
    	Log.fail("Exception thrown in method: ".concat(methodName).concat(" in the ").concat(name).concat(" class<BR>Error: ").concat(error), driver);
    }
}
