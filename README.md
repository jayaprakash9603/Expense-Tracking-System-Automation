# Expense Tracking System - Automation Framework

## 📌 Overview
This project contains the **automation test framework** for the **Expense Tracking System** application.  
It is designed to validate critical functionalities like **expense creation, budget association, reporting, and analytics**, ensuring the application remains stable and production-ready.

The framework is built using **Selenium, Java, and TestNG**, and follows industry best practices like the **Page Object Model (POM)**, **Factory Design Pattern**, and **ThreadLocal WebDriver management**.

---

## 🚀 Features
- 🔹 Cross-browser support (Chrome, Firefox, Edge)
- 🔹 Page Object Model (POM) for maintainable and reusable code
- 🔹 Thread-safe WebDriver using **ThreadLocal**
- 🔹 Centralized driver and options management
- 🔹 Utility classes for alerts, frames, windows, navigation, file handling, and screenshots
- 🔹 Config-driven execution (browser, base URL, timeouts)
- 🔹 Easy integration with CI/CD pipelines (Jenkins/GitHub Actions)
- 🔹 Extensible for future modules (API testing, database validation)

---

## 🏗️ Tech Stack
- **Language:** Java 17
- **Automation Tool:** Selenium WebDriver
- **Test Runner:** TestNG
- **Build Tool:** Maven
- **Design Patterns:** Page Object Model, Factory Pattern, Singleton Driver
- **Reporting:** TestNG Reports / Extent Reports (optional)
- **Version Control:** GitHub

---

## 📂 Project Structure
```
expense-tracking-automation/
│
├── src/main/java/com/jaya/
│   ├── factory/          # DriverFactory, BrowserOptionsFactory, Managers
│   ├── pages/            # Page Object classes (LoginPage, ExpensePage, DashboardPage, etc.)
│   ├── utils/            # Utilities (AlertUtils, FrameUtils, ScreenshotUtils, etc.)
│   └── config/           # Configuration reader
│
├── src/test/java/com/jaya/tests/
│   ├── BaseTest.java     # Test setup/teardown
│   ├── GoogleTest.java   # Example test
│   └── ExpenseTests.java # Real automation tests
│
├── pom.xml               # Maven dependencies
├── testng.xml            # TestNG suite config
└── README.md             # Project documentation
```

---

## ⚙️ Setup & Execution

### 1️⃣ Clone Repository
```bash
git clone https://github.com/your-username/expense-tracking-automation.git
cd expense-tracking-automation
```

### 2️⃣ Install Dependencies
Make sure you have **Maven** and **Java 17** installed.

```bash
mvn clean install
```

### 3️⃣ Run Tests
```bash
mvn test
```

Or run specific suite:
```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

## 🧪 Example Test
```java
@Test
public void testAddExpense() {
    driver.get("https://expense-tracker-app.com");
    LoginPage login = new LoginPage(driver);
    DashboardPage dashboard = login.login("user", "password");
    ExpensePage expensePage = dashboard.navigateToExpenses();
    expensePage.addExpense("Food", 250, "Cash");
    Assert.assertTrue(expensePage.isExpenseDisplayed("Food", 250));
}
```

---

## 🔮 Future Enhancements
- ✅ API-level testing for expense services
- ✅ Database verification layer
- ✅ Dockerized execution with Selenium Grid
- ✅ Advanced reporting with Extent Reports / Allure
- ✅ Parallel test execution on cloud providers (BrowserStack, LambdaTest)

---

## 👨‍💻 Author
**Jaya Prakash J**  
📧 [jjayaprakash9603@gmail.com]  
🔗 [Your LinkedIn/GitHub Profile]

---