package com.jaya.config;


import java.time.Duration;

public class FrameworkConfig {
    public static final Duration WAIT_TIMEOUT = Duration.ofSeconds(30);
    public static final Duration POLLING_INTERVAL = Duration.ofMillis(500);
    public static final int MAX_RETRY_ATTEMPTS = 3;
    public static final Duration IMPLICIT_WAIT = Duration.ofSeconds(10);
    public static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(60);
    public static final Duration SCRIPT_TIMEOUT = Duration.ofSeconds(30);

    // Screenshot settings
    public static final String SCREENSHOT_PATH = "test-output/screenshots/";
    public static final boolean TAKE_SCREENSHOT_ON_FAILURE = true;

    // Logging settings
    public static final boolean ENABLE_DETAILED_LOGGING = true;
}