package com.jaya.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class SelectUtils {

    public void selectByVisibleText(WebElement element, String text) {
        new Select(element).selectByVisibleText(text);
    }

    public void selectByIndex(WebElement element, int index) {
        new Select(element).selectByIndex(index);
    }

    public void selectByValue(WebElement element, String value) {
        new Select(element).selectByValue(value);
    }

    public List<String> getAllOptions(WebElement element) {
        return new Select(element).getOptions()
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
