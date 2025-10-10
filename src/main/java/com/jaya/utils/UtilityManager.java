package com.jaya.utils;

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
        if (driver != null) {
            initializeUtils();
        }
    }

    private void initializeUtils() {
        if (driver == null) {
            return; // defer until driver available
        }
        if (this.actionUtils == null) {
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
    }

    public ActionUtils getActionUtils() { if (actionUtils == null) initializeUtils(); return actionUtils; }
    public AlertUtils getAlertUtils() { if (alertUtils == null) initializeUtils(); return alertUtils; }
    public FileUtils getFileUtils() { if (fileUtils == null) initializeUtils(); return fileUtils; }
    public FrameUtils getFrameUtils() { if (frameUtils == null) initializeUtils(); return frameUtils; }
    public JsUtils getJsUtils() { if (jsUtils == null) initializeUtils(); return jsUtils; }
    public ScreenshotUtils getScreenshotUtils() { if (screenshotUtils == null) initializeUtils(); return screenshotUtils; }
    public SelectUtils getSelectUtils() { if (selectUtils == null) initializeUtils(); return selectUtils; }
    public TableUtils getTableUtils() { if (tableUtils == null) initializeUtils(); return tableUtils; }
    public WaitUtils getWaitUtils() { if (waitUtils == null) initializeUtils(); return waitUtils; }
    public WindowUtils getWindowUtils() { if (windowUtils == null) initializeUtils(); return windowUtils; }
}