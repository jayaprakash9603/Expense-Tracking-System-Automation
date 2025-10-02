package com.jaya.utils.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lightweight YAML configuration loader (no external libs) for simple key/value and one-level nesting.
 * Expects a structure like:
 * app:
 *   baseUrl: "http://host" 
 *   loginPath: "/login"
 *   registerPath: "/register"
 */
public final class YamlConfig {

    private static final Logger log = LoggerFactory.getLogger(YamlConfig.class);
    private static final String CLASSPATH_RESOURCE = "config/app-config.yaml";
    private static final String DEFAULT_PATH = "src/main/resources/config/app-config.yaml"; // fallback for IDE/direct runs
    private static final Map<String, String> FLAT = new ConcurrentHashMap<>();
    private static volatile boolean loaded = false;

    private YamlConfig() {}

    private static synchronized void loadIfNeeded() {
        if (loaded) return;

        // 1. Try classpath first (correct approach when running via Maven/packaged tests)
        try (InputStream cp = Thread.currentThread().getContextClassLoader().getResourceAsStream(CLASSPATH_RESOURCE)) {
            if (cp != null) {
                parse(cp);
                loaded = true;
                log.info("Loaded YAML config from classpath: {}", CLASSPATH_RESOURCE);
                return;
            } else {
                log.debug("Classpath resource '{}' not found, falling back to file system path.", CLASSPATH_RESOURCE);
            }
        } catch (Exception e) {
            log.warn("Error loading YAML from classpath (will try filesystem): {}", e.getMessage());
        }

        // 2. Fall back to source path (useful in IDE direct runs)
        Path path = Path.of(DEFAULT_PATH);
        if (Files.exists(path)) {
            try (InputStream fs = Files.newInputStream(path)) {
                parse(fs);
                loaded = true;
                log.info("Loaded YAML config from file system: {}", path.toAbsolutePath());
                return;
            } catch (Exception e) {
                throw new RuntimeException("Failed to load YAML config from filesystem: " + path.toAbsolutePath(), e);
            }
        }

        // 3. Last resort: try a direct relative path without src/ prefix (some CI setups copy resources root)
        Path alt = Path.of("config", "app-config.yaml");
        if (Files.exists(alt)) {
            try (InputStream fs = Files.newInputStream(alt)) {
                parse(fs);
                loaded = true;
                log.info("Loaded YAML config from alt path: {}", alt.toAbsolutePath());
                return;
            } catch (Exception e) {
                throw new RuntimeException("Failed to load YAML config from alt path: " + alt.toAbsolutePath(), e);
            }
        }

        // If we reach here, nothing was loaded.
        loaded = true; // prevent repeated attempts each call
        log.warn("YAML config NOT found in classpath nor filesystem. Config-dependent URLs will be null.");
    }

    private static void parse(InputStream is) throws Exception {
        // Very small hand-rolled parser sufficient for simple k:v and one-level nesting
        String currentPrefix = "";
        for (String rawLine : new String(is.readAllBytes()).split("\n")) {
            String line = rawLine.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;

            // Detect section (ending with :) - only one level supported
            if (!line.startsWith("-") && line.endsWith(":")) {
                currentPrefix = line.substring(0, line.length() - 1).trim();
                continue;
            }

            int idx = line.indexOf(":");
            if (idx == -1) continue;
            String key = line.substring(0, idx).trim();
            String value = line.substring(idx + 1).trim();

            value = cleanValue(value);

            String flatKey = currentPrefix.isBlank() ? key : currentPrefix + "." + key;
            if (!value.isBlank()) {
                FLAT.put(flatKey, value);
            }
        }
    }

    // Remove inline comments outside quotes and surrounding quotes themselves
    private static String cleanValue(String v) {
        // Trim inline comments (# ...) not inside single/double quotes
        boolean inSingle = false, inDouble = false;
        for (int i = 0; i < v.length(); i++) {
            char c = v.charAt(i);
            if (c == '"' && !inSingle) inDouble = !inDouble;
            else if (c == '\'' && !inDouble) inSingle = !inSingle;
            else if (c == '#' && !inSingle && !inDouble) { // start of comment
                v = v.substring(0, i).trim();
                break;
            }
        }
        v = v.trim();
        v = stripQuotes(v);
        return v;
    }

    private static String stripQuotes(String v) {
        if ((v.startsWith("\"") && v.endsWith("\"")) || (v.startsWith("'") && v.endsWith("'"))) {
            return v.substring(1, v.length() - 1);
        }
        return v;
    }

    public static String get(String key) {
        loadIfNeeded();
        return FLAT.get(key);
    }

    public static String require(String key) {
        String val = get(key);
        if (val == null) throw new IllegalStateException("Missing YAML config key: " + key);
        return val;
    }

    public static String getBaseUrl() { return require("app.baseUrl"); }
    public static String getLoginUrl() {
        String path = get("app.loginPath");
        return appendPath(getBaseUrl(), path);
    }
    public static String getRegisterUrl() {
        String path = get("app.registerPath");
        return appendPath(getBaseUrl(), path);
    }

    private static String appendPath(String base, String path) {
        if (path == null || path.isBlank()) return base;
        if (base.endsWith("/") && path.startsWith("/")) return base + path.substring(1);
        if (!base.endsWith("/") && !path.startsWith("/")) return base + "/" + path;
        return base + path;
    }
}
