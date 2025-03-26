package EdControlsMain.EDFragments;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.ReusableFunctions.FileUploadHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ImagesFragment extends BaseTest {
    public ImagesFragment(WebDriver driver) {
        super(driver);
    }


    public static void uploadImagesOnProject() throws InterruptedException {
        List<WebElement> imageElements = driver.findElements(By.xpath("//div[@class='image-upload']//span"));
        for (WebElement imageElement : imageElements) {
            Thread.sleep(2000);
            String imageElementText = imageElement.getText().trim();
            System.out.println("Text found: [" + imageElementText + "]");
            System.out.println("Found Image Field: " + imageElementText);

            switch (imageElementText) {
                case "PROJECT IMAGE":
                    System.out.println("Uploading Project Image...");
                    imageElement.click();
                    Thread.sleep(1000);
                    FileUploadHelper.uploadImageUsingAppleScript("projectlogo", ".jpeg");
                    Thread.sleep(2000);
                    clickSvgIconBySectionIndex(1);
                    Thread.sleep(1000);

                case "LOGO IMAGE":
                    System.out.println("Uploading Logo Image...");
                    // Locate the logo image upload element
                    WebElement logoImageElement = driver.findElement(By.xpath("//div[@class='image-upload']//span[text()='Logo image']"));
                    // Scroll the element into view
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", logoImageElement);
                    // Wait for the element to be clickable
                    new WebDriverWait(driver, Duration.ofSeconds(10))
                            .until(ExpectedConditions.elementToBeClickable(logoImageElement));
                    try {
                        logoImageElement.click();
                    } catch (ElementClickInterceptedException e) {
                        // If intercepted, use JavaScript click
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoImageElement);
                    }

                    // Wait before uploading
                    Thread.sleep(1000);
                    // Upload image using AppleScript helper
                    FileUploadHelper.uploadImageUsingAppleScript("companylogo", ".jpeg");
                    // Wait before proceeding
                    Thread.sleep(2000);
                    clickSvgIconBySectionIndex(2);
                    Thread.sleep(2000);
                    return;
                default:
                    System.out.println("No matching image field found.");
                    break;
            }
        }
    }

    public static void clickSvgIconBySectionIndex(int index) {
        List<WebElement> sections = driver.findElements(By.xpath("//section[@class='editor-options']"));

        if (index > 0 && index <= sections.size()) {
            WebElement section = sections.get(index - 1); // Adjust for 0-based index
            WebElement svgIcon = section.findElement(By.xpath(".//em[1]/*[name()='svg']")); // Corrected XPath

            // Wait until the SVG is present and visible
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(svgIcon));

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", svgIcon);

            // Trigger a real click event for SVG using JavaScript
            String jsScript = "var event = new MouseEvent('click', {bubbles: true, cancelable: true, view: window});"
                    + "arguments[0].dispatchEvent(event);";
            ((JavascriptExecutor) driver).executeScript(jsScript, svgIcon);
        } else {
            throw new IndexOutOfBoundsException("Invalid section index: " + index);
        }
    }

}
