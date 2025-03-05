package EdControlsMain.BaseClasses;

import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.*;

public class HighlightListener extends BaseTest implements WebDriverListener  {

    private WebElement lastHighlightedElement = null;  // Store last highlighted element

    public HighlightListener(WebDriver driver) {
        super(driver);
    }

    private void highlightElement(WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Remove highlight from the previous element
        if (lastHighlightedElement != null) {
            try {
                js.executeScript("arguments[0].style.border=''", lastHighlightedElement);
            } catch (Exception e) {
                // Element might be stale or not in DOM anymore
            }
        }

        // Highlight the new element
        js.executeScript("arguments[0].style.border='2px solid rgba(255, 0, 0, 0.7)'", element);

        // Update last highlighted element
        lastHighlightedElement = element;
    }


    @Override
    public void beforeClick(WebElement element) {
        highlightElement(element, ((WrapsDriver) element).getWrappedDriver());
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        highlightElement(element, ((WrapsDriver) element).getWrappedDriver());
    }

    private boolean shouldExcludeHighlight(WebElement element) {
        String classAttribute = element.getDomAttribute("id");
        if (classAttribute != null) {
            return classAttribute.contains("map-container") || classAttribute.contains("leaflet-container");
        }
        return false;
    }

}
