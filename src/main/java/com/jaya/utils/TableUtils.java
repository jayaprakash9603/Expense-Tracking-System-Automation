package com.jaya.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class TableUtils {
    private WebDriver driver;

    public TableUtils(WebDriver driver) {
        this.driver = driver;
    }

    // 🔹 Get all rows of a table
    public List<WebElement> getAllRows(By tableLocator) {
        WebElement table = driver.findElement(tableLocator);
        return table.findElements(By.tagName("tr"));
    }

    // 🔹 Get all headers
    public List<String> getAllHeaders(By tableLocator) {
        WebElement table = driver.findElement(tableLocator);
        List<WebElement> headers = table.findElements(By.xpath(".//th"));
        List<String> headerTexts = new ArrayList<>();
        for (WebElement header : headers) {
            headerTexts.add(header.getText().trim());
        }
        return headerTexts;
    }

    // 🔹 Get all cells of a row (by index)
    public List<WebElement> getCellsFromRow(By tableLocator, int rowIndex) {
        List<WebElement> rows = getAllRows(tableLocator);
        if (rowIndex >= rows.size()) throw new IndexOutOfBoundsException("Row index out of range");
        return rows.get(rowIndex).findElements(By.tagName("td"));
    }

    // 🔹 Get specific cell value
    public String getCellText(By tableLocator, int rowIndex, int colIndex) {
        return getCellsFromRow(tableLocator, rowIndex).get(colIndex).getText().trim();
    }

    // 🔹 Get all data from a column
    public List<String> getColumnData(By tableLocator, int colIndex) {
        List<String> columnData = new ArrayList<>();
        List<WebElement> rows = getAllRows(tableLocator);

        for (int i = 1; i < rows.size(); i++) { // skip header row
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
            if (colIndex < cells.size()) {
                columnData.add(cells.get(colIndex).getText().trim());
            }
        }
        return columnData;
    }

    // 🔹 Search in a column
    public int findRowIndexByColumnValue(By tableLocator, int colIndex, String searchValue) {
        List<String> columnData = getColumnData(tableLocator, colIndex);
        for (int i = 0; i < columnData.size(); i++) {
            if (columnData.get(i).equalsIgnoreCase(searchValue)) {
                return i + 1; // +1 because row 0 is usually header
            }
        }
        return -1;
    }

    // 🔹 Click a cell (button/link inside cell)
    public void clickCell(By tableLocator, int rowIndex, int colIndex) {
        getCellsFromRow(tableLocator, rowIndex).get(colIndex).click();
    }

    // 🔹 Get all data from table as 2D List
    public List<List<String>> getTableData(By tableLocator) {
        List<List<String>> tableData = new ArrayList<>();
        List<WebElement> rows = getAllRows(tableLocator);

        for (WebElement row : rows) {
            List<String> rowData = new ArrayList<>();
            List<WebElement> cells = row.findElements(By.tagName("td"));
            for (WebElement cell : cells) {
                rowData.add(cell.getText().trim());
            }
            if (!rowData.isEmpty()) tableData.add(rowData);
        }
        return tableData;
    }

    // 🔹 Get row count
    public int getRowCount(By tableLocator) {
        return getAllRows(tableLocator).size();
    }

    // 🔹 Get column count
    public int getColumnCount(By tableLocator) {
        List<WebElement> rows = getAllRows(tableLocator);
        if (rows.isEmpty()) return 0;
        return rows.get(0).findElements(By.tagName("td")).size();
    }

    // 🔹 Check if table contains value
    public boolean containsValue(By tableLocator, String value) {
        List<List<String>> data = getTableData(tableLocator);
        for (List<String> row : data) {
            if (row.contains(value)) {
                return true;
            }
        }
        return false;
    }
}
