package com.jaya.utils;


import com.jaya.utils.*;
import org.openqa.selenium.WebDriver;

public class UtilityManager {
    private final WebDriver driver;
    private ActionUtils actionUtils;
    private AlertUtils alertUtils;
    private FileUtils fileUtils;
    private FrameUtils frameUtils;
    private JsUtils jsUtils;
    private ScreenshotUtils screenshotUtils;
    private SelectUtils selectUtils;
    private TableUtils tableUtils;
    private WaitUtils waitUtils;
    private WindowUtils windowUtils;

    public UtilityManager(WebDriver driver) {
        this.driver = driver;
        initializeUtils();
    }

    private void initializeUtils() {
        this.actionUtils = new ActionUtils(driver);
        this.alertUtils = new AlertUtils(driver);
        this.fileUtils = new FileUtils();
        this.frameUtils = new FrameUtils(driver);
        this.jsUtils = new JsUtils(driver);
        this.screenshotUtils = new ScreenshotUtils(driver);
        this.selectUtils = new SelectUtils();
        this.tableUtils = new TableUtils(driver);
        this.waitUtils = new WaitUtils(driver);
        this.windowUtils = new WindowUtils(driver);
    }

    public ActionUtils getActionUtils() { return actionUtils; }
    public AlertUtils getAlertUtils() { return alertUtils; }
    public FileUtils getFileUtils() { return fileUtils; }
    public FrameUtils getFrameUtils() { return frameUtils; }
    public JsUtils getJsUtils() { return jsUtils; }
    public ScreenshotUtils getScreenshotUtils() { return screenshotUtils; }
    public SelectUtils getSelectUtils() { return selectUtils; }
    public TableUtils getTableUtils() { return tableUtils; }
    public WaitUtils getWaitUtils() { return waitUtils; }
    public WindowUtils getWindowUtils() { return windowUtils; }
}