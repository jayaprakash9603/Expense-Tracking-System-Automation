package com.jaya.pages.register;

import com.jaya.base.BasePage;
import com.jaya.pages.register.locators.RegisterPageLocators;
import com.jaya.utils.api.AuthApiClient;
import com.jaya.utils.config.YamlConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.testng.Assert;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterPage extends BasePage {

    private static final Logger LOG = LogManager.getLogger(RegisterPage.class);
    private String lastJwt;

    public RegisterPage load() { return load(null); }

    public RegisterPage load(String url) {
        String urlToUse = url;
        if (urlToUse == null || urlToUse.isBlank()) {
            try {
                urlToUse = YamlConfig.getRegisterUrl();
            } catch (Exception ignored) {}
        }
        if (urlToUse != null && !urlToUse.isBlank()) {
            navigateTo(urlToUse);
        }
        waitForElementToBeVisible(RegisterPageLocators.REGISTER_BUTTON);
        return this;
    }

    public boolean isAt() {
        return isElementDisplayed(RegisterPageLocators.REGISTER_BUTTON);
    }

    // Input helpers
    public RegisterPage firstName(String value) { sendKeys(RegisterPageLocators.FIRST_NAME_INPUT, value); return this; }
    public RegisterPage lastName(String value) { sendKeys(RegisterPageLocators.LAST_NAME_INPUT, value); return this; }
    public RegisterPage email(String value) { sendKeys(RegisterPageLocators.EMAIL_INPUT, value); return this; }
    public RegisterPage password(String value) { sendKeys(RegisterPageLocators.PASSWORD_INPUT, value); return this; }

    public RegisterPage gender(String gender) {
        if (gender == null || gender.isBlank()) return this; // optional now
        String g = gender.trim().toLowerCase();
        if (g.startsWith("f")) clickGender("female", RegisterPageLocators.GENDER_FEMALE_RADIO);
        else if (g.startsWith("m")) clickGender("male", RegisterPageLocators.GENDER_MALE_RADIO);
        return this;
    }

    private void clickGender(String visible, By inputLocator) {
        try {
            click(inputLocator);
            return;
        } catch (Exception ignored) { }
        // Try label
        try {
            click(RegisterPageLocators.genderLabel(capitalize(visible)));
            return;
        } catch (Exception ignored) { }
        // JS fallback if input is hidden
        try {
            WebElement el = driver.findElement(inputLocator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        } catch (Exception e) {
            throw new RuntimeException("Unable to select gender: " + visible, e);
        }
    }

    private String capitalize(String s) { return s.isEmpty()? s : Character.toUpperCase(s.charAt(0))+s.substring(1); }

    public RegisterPage togglePasswordVisibility() {
        click(RegisterPageLocators.PASSWORD_TOGGLE);
        return this;
    }

    // Password visibility helpers
    @SuppressWarnings("deprecation")
    public boolean isPasswordVisible(String expectedValue) {
        WebElement input = findElement(RegisterPageLocators.PASSWORD_INPUT);
        String type = input.getAttribute("type");
        if (!"text".equalsIgnoreCase(type)) return false;
        if (expectedValue == null) return true;
        String current = input.getAttribute("value");
        return expectedValue.equals(current);
    }

    public void assertPasswordVisible(String expectedValue) {
        Assert.assertTrue(isPasswordVisible(expectedValue),
                "Password not visible in plain text or value mismatch. Expected: " + expectedValue);
    }

    public RegisterPage submit() {
        click(RegisterPageLocators.REGISTER_BUTTON);
        return this;
    }

    public RegisterPage refresh() {
        refreshPage();
        waitForElementToBeVisible(RegisterPageLocators.REGISTER_BUTTON);
        return this;
    }

    public RegisterPage register(String first, String last, String email, String password, String gender) {
        return firstName(first).lastName(last).email(email).password(password).gender(gender).submit();
    }

    // ==== API helpers (moved from step definitions) ====
    public Map<String,Object> apiSignin(String email, String password) {
        AuthApiClient apiClient = new AuthApiClient();
        Map<String,Object> signin = apiClient.signinDetails(email, password);
        if (signin == null) {
            LOG.error("Signin API returned null for email='{}'", email);
            throw new AssertionError("Signin API returned null for email=" + email);
        }
        Object tokenObj = signin.get("jwt");
        String token = tokenObj instanceof String ? (String) tokenObj : null;
        if (token == null) {
            LOG.error("JWT missing for email='{}' httpStatus='{}' message='{}' rawBody='{}'", email, signin.get("httpStatus"), signin.get("message"), signin.get("rawBody"));
            throw new AssertionError("JWT missing for email=" + email + " httpStatus=" + signin.get("httpStatus"));
        }
        lastJwt = token;
        LOG.info("API signin success email='{}' httpStatus='{}'", email, signin.get("httpStatus"));
        return signin;
    }

    public Map<String,String> apiProfile() {
        if (lastJwt == null) throw new IllegalStateException("No JWT stored; call apiSignin first");
        AuthApiClient apiClient = new AuthApiClient();
        Map<String,String> profile = apiClient.getProfile(lastJwt);
        if (profile == null) throw new AssertionError("Profile API returned null");
        return profile;
    }

    public String composeFullName(Map<String,String> profile) {
        String actualFirst = profile.get("firstName");
        String actualLast = profile.get("lastName");
        return ((actualFirst == null ? "" : actualFirst) + " " + (actualLast == null ? "" : actualLast)).trim();
    }

    public void assertStoredUserName(String expected, String email, String password) {
        Map<String,Object> signin = apiSignin(email, password);
        Map<String,String> profile = apiProfile();
        String actual = composeFullName(profile);
        LOG.info("Profile fetched email='{}' first='{}' last='{}'", profile.get("email"), profile.get("firstName"), profile.get("lastName"));
        Assert.assertEquals(actual, expected, "Stored trimmed user name mismatch");
    }

    public String getLastJwt() { return lastJwt; }

    // Validation helpers
    public boolean isFirstNameErrorVisible() {
        return isElementDisplayed(RegisterPageLocators.FIRST_NAME_ERROR);
    }

    public RegisterPage clearFirstName() {
        sendKeys(RegisterPageLocators.FIRST_NAME_INPUT, "");
        return this;
    }

    public RegisterPage submitWithBlankFirstNameExpectingError() {
        clearFirstName();
        submit();
        return this;
    }

    public boolean hasSuccessMessage(String text) {
    return waitForToastMessage(text, 5);
    }

    public boolean hasError(String expected) {
        // Prefer top aggregated alert first
        if (isElementDisplayed(RegisterPageLocators.ERROR_ALERT)) {
            return getText(RegisterPageLocators.ERROR_ALERT).contains(expected);
        }
        List<WebElement> errors = findElements(RegisterPageLocators.ERROR_MESSAGES);
        return errors.stream().anyMatch(e -> e.getText().trim().contains(expected));
    }

    // ==== Toast helpers ====
    public boolean waitForToastVisible(long timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(RegisterPageLocators.TOAST_ALERT));
            return true;
        } catch (Exception e) { return false; }
    }

    public boolean waitForToastMessage(String fragment, long timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(RegisterPageLocators.toastMessageContaining(fragment)));
            return true;
        } catch (Exception e) { return false; }
    }

    public String getToastText() {
        return getText(RegisterPageLocators.TOAST_ALERT);
    }

    public void assertToastContains(String fragment) {
        Assert.assertTrue(waitForToastMessage(fragment, 5), "Toast not found containing: " + fragment);
    }

    public void assertError(String expected) { Assert.assertTrue(hasError(expected), "Expected error not found: " + expected); }
    public void assertSuccess(String expected) { Assert.assertTrue(hasSuccessMessage(expected), "Success message not found: " + expected); }

    @SuppressWarnings("deprecation")
    public String getFieldValue(By locator) { return findElement(locator).getAttribute("value"); }

    public RegisterPage clearAll() {
        firstName(""); lastName(""); email(""); password("");
        return this;
    }

    // ================= Accessibility: ARIA error announcement =================
    /**
     * Collect error elements related to form validation and assert each is aria-live polite.
     * Strategy: gather elements containing 'First Name', 'Last Name', 'Email', 'Password' that are visible and have role alert or class error.
     */
    public void assertErrorsAriaLivePolite() {
        java.util.List<org.openqa.selenium.WebElement> candidates = new java.util.ArrayList<>();
        try { candidates.addAll(findElements(RegisterPageLocators.ERROR_MESSAGES)); } catch (Exception ignored) {}
        // Also include global alert if present
        try {
            if (isElementDisplayed(RegisterPageLocators.ERROR_ALERT)) {
                candidates.add(findElement(RegisterPageLocators.ERROR_ALERT));
            }
        } catch (Exception ignored) {}
        java.util.List<org.openqa.selenium.WebElement> filtered = new java.util.ArrayList<>();
        for (org.openqa.selenium.WebElement el : candidates) {
            try {
                String txt = el.getText();
                if (txt == null) continue;
                String t = txt.toLowerCase();
                if (t.contains("first") || t.contains("last") || t.contains("email") || t.contains("password") || t.contains("mandatory")) {
                    filtered.add(el);
                }
            } catch (Exception ignored) {}
        }
        Assert.assertFalse(filtered.isEmpty(), "No validation error elements found to assert aria-live");
        java.util.List<String> missing = new java.util.ArrayList<>();
        for (org.openqa.selenium.WebElement el : filtered) {
            String ariaLive = firstPresentAriaLive(el);
            String role = safeAttr(el, "role");
            boolean ok = (ariaLive != null && ("polite".equalsIgnoreCase(ariaLive) || "assertive".equalsIgnoreCase(ariaLive)))
                    || (ariaLive == null && "alert".equalsIgnoreCase(role)); // role=alert implies assertive announcement
            if (!ok) {
                missing.add("text='" + el.getText().trim() + "' role=" + role + " ariaLive=" + ariaLive);
            }
        }
        Assert.assertTrue(missing.isEmpty(), "Some error elements lack aria-live or role=alert fallback: " + missing);
    }

    @SuppressWarnings("deprecation")
    private String safeAttr(org.openqa.selenium.WebElement el, String name) {
        try { return el.getAttribute(name); } catch (Exception e) { return null; }
    }

    /** Search element and up to 5 ancestors for aria-live attribute. */
    private String firstPresentAriaLive(org.openqa.selenium.WebElement el) {
        String script = "var e=arguments[0]; for(var i=0;i<6 && e;i++){ var v=e.getAttribute('aria-live'); if(v) return v; e=e.parentElement;} return null;";
        try { Object v = ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script, el); return v==null?null:String.valueOf(v); } catch (Exception ex){ return null; }
    }

    // ================= Accessibility & Focus Order Helpers =================
    /**
     * Attempt to move focus using TAB key n times, capturing sequence of element identifiers.
     * This relies on JavaScript to read document.activeElement after each tab because
     * some drivers may not expose native focus order easily.
     */
    public java.util.List<String> captureFocusOrder(int tabs) {
        java.util.List<String> order = new java.util.ArrayList<>();
        // Ensure starting focus at first name field
        try { findElement(RegisterPageLocators.FIRST_NAME_INPUT).click(); } catch (Exception ignored) {}
        order.add(activeElementDescriptor());
        for (int i = 0; i < tabs; i++) {
            // Send TAB key
            try {
                org.openqa.selenium.WebElement body = driver.findElement(org.openqa.selenium.By.tagName("body"));
                body.sendKeys(org.openqa.selenium.Keys.TAB);
                // Small wait to allow focus shift
                try { Thread.sleep(75); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            } catch (Exception e) {
                LOG.warn("Failed to send TAB at iteration {}: {}", i, e.getMessage());
            }
            order.add(activeElementDescriptor());
        }
        return order;
    }

    /** Describe currently focused element with stable info (id, name, placeholder, role). */
    private String activeElementDescriptor() {
        try {
            Object desc = ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "var a=document.activeElement; if(!a) return 'none'; var txt=''; if(a.innerText) txt=a.innerText.trim(); return [a.tagName.toLowerCase(), a.id||'', a.name||'', a.placeholder||'', a.getAttribute('role')||'', a.type||'', txt].join('#');");
            return String.valueOf(desc);
        } catch (Exception e) {
            return "error:" + e.getMessage();
        }
    }

    /** Convenience: expected logical order mapped to simplified descriptors for assertion. */
    public java.util.List<String> expectedLogicalFocusOrder() {
        java.util.List<String> expected = new java.util.ArrayList<>();
        expected.add("input#firstName"); // First Name
        expected.add("input#lastName");  // Last Name
        expected.add("input#email");     // Email
        expected.add("input#password");  // Password
        expected.add("button#register"); // Register Button
        expected.add("button#login");    // Login link (or anchor)
        return expected;
    }

    /**
     * Normalize captured descriptors to simplified form for comparison.
     */
    public java.util.List<String> simplifyDescriptors(java.util.List<String> raw) {
        java.util.List<String> simple = new java.util.ArrayList<>();
        for (String r : raw) {
            String[] parts = r.split("#");
            if (parts.length < 2) { simple.add(r); continue; }
            String tag = parts[0];
            String id = parts[1];
            String name = parts.length > 2 ? parts[2] : "";
            String placeholder = parts.length > 3 ? parts[3] : "";
            String text = parts.length > 6 ? parts[6] : "";
            // Choose id if present else name else placeholder
            // Prefer name attribute for inputs, else placeholder, else visible text, else id
            String key;
            if (!name.isEmpty()) key = name;
            else if (!placeholder.isEmpty()) key = placeholder;
            else if (!text.isEmpty()) key = text.replaceAll("\\s+", " ");
            else key = id;
            simple.add(tag + "#" + key);
        }
        return simple;
    }

    public void assertFocusOrder(java.util.List<String> captured) {
        java.util.List<String> simplified = simplifyDescriptors(captured);
        java.util.List<String> logical = new java.util.ArrayList<>();
        for (String s : simplified) {
            String lower = s.toLowerCase();
            if (lower.contains("first")) addOnce(logical, "First Name");
            else if (lower.contains("last")) addOnce(logical, "Last Name");
            else if (lower.contains("email")) addOnce(logical, "Email");
            else if (lower.contains("password")) addOnce(logical, "Password");
            else if (lower.contains("register")) addOnce(logical, "Register Button");
            else if (lower.contains("login")) addOnce(logical, "Login link");
        }
        java.util.List<String> expected = java.util.Arrays.asList(
                "First Name", "Last Name", "Email", "Password", "Register Button", "Login link");
        Assert.assertEquals(logical, expected, "Focus order mismatch. Raw=" + simplified + " Logical=" + logical);
    }

    private void addOnce(java.util.List<String> list, String value) {
        if (!list.contains(value)) list.add(value);
    }
}
