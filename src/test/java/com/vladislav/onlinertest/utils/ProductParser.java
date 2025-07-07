package com.vladislav.onlinertest.utils;

import com.vladislav.onlinertest.core.element.Element;
import com.vladislav.onlinertest.models.Product;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProductParser {

    public List<Product> parseProducts(List<WebElement> productElements) {
        List<Product> products = new ArrayList<>();
        log.info("Found product elements: {}", productElements.size());

        for (WebElement el : productElements) {
            try {
                String name = Element.byCss(".product__title-link").getTextFromNestedElement(el);
                String priceText = Element.byCss(".product__price-value_primary").getTextFromNestedElement(el);
                String description = Element.byCss(".product__description").getTextFromNestedElement(el);

                BigDecimal price = parsePrice(priceText);
                Product product = new Product(name, description, price);
                products.add(product);

                log.debug("Parsed product: {}", product);
            } catch (Exception e) {
                log.warn("Failed to parse product element: {}", e.getMessage(), e);
            }
        }

        return products;
    }


    private BigDecimal parsePrice(String text) {
        text = text.replaceAll("[^\\d,]", "").replace(",", ".");
        return new BigDecimal(text);
    }
}