package com.vladislav.onlinertest.models.pages;

import com.vladislav.onlinertest.core.element.Element;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;


public class MainPage {

    private final Element searchInput = Element.byXpath("//*[@id=\"fast-search\"]/div/input");
    private final Element searchResultsFrame = Element.byXpath("//*[@id=\"fast-search-modal\"]/div/div/iframe");

    @Step("Enter search text: '{text}' and switch to results frame")
    public SearchResultsPage inputText(String text) {
        searchInput.enterText(text);
        WebElement frameElement = searchResultsFrame.getElement();
        searchResultsFrame.switchFrame(frameElement);
        return new SearchResultsPage();
    }
}
