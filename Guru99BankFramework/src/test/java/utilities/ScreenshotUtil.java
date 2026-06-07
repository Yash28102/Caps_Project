package utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {

    // Method signature must match exactly
	public static void takeScreenshot(WebDriver driver, String fileName) {

	    try {

	        String timestamp =
	                new SimpleDateFormat("yyyyMMdd_HHmmss")
	                .format(new Date());

	        String path =
	                System.getProperty("user.dir")
	                + "\\Screenshots\\"
	                + fileName + "_" + timestamp + ".png";

	        File src =
	                ((TakesScreenshot) driver)
	                .getScreenshotAs(OutputType.FILE);

	        File dest = new File(path);

	        FileUtils.copyFile(src, dest);

	        System.out.println("Screenshot saved successfully.");
	        System.out.println(path);

	    }
	    catch(Exception e) {
	        System.out.println("Screenshot failed.");
	        e.printStackTrace();
	    }
	}
}