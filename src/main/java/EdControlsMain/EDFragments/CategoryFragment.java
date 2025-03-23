package EdControlsMain.EDFragments;

import EdControlsMain.BaseClasses.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CategoryFragment extends BaseTest {

//    public CategoryFragment() {
//        super(null);
//    }

    public CategoryFragment(WebDriver driver) {
        super(driver);
    }

    public static void addQuestionsToCategory(WebElement categoryElement, int index, List<String> questionTypes) throws InterruptedException {
        // **Step 1: Add first "Yes/No" question (default)**
        addYesNoQuestion(categoryElement);
        Thread.sleep(2000);

        int questionNumber = 1;

        // **Step 2: Add custom questions based on the provided list**
        for (String questionType : questionTypes) {
            WebElement addQuestionButton = categoryElement.findElement(By.xpath(".//button[contains(text(), 'Add question')]"));
            WaitUtilsFragment.waitForWebElementToClickable(addQuestionButton);
            addQuestionButton.click();
            Thread.sleep(2000);

            WebElement questionElement = categoryElement.findElement(By.xpath(".//div[@data-rbd-draggable-id='qstn-" + index + "-" + questionNumber + "']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", questionElement);
            addCustomQuestion(questionElement, questionType);
            Thread.sleep(2000);

            questionNumber++;
        }

        // **Step 3: Add text box only to the last category**
        List<WebElement> categories = driver.findElements(By.xpath(".//div[@class='category']"));
        if (!categories.isEmpty() && categoryElement.equals(categories.get(categories.size() - 1))) {
            WebElement addTextBoxQuestion = categoryElement.findElement(By.xpath(".//button[contains(text(), 'Add text box')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addTextBoxQuestion);
        }
    }

    // **Method to check if a "Yes/No" question is already present**
  /*  private static boolean isYesNoQuestionPresent(WebElement categoryElement) {
        try {
            List<WebElement> yesNoQuestions = categoryElement.findElements(By.xpath(".//div[contains(@class, 'MuiSelect-root') and text()='Yes/No/NA']"));
            return !yesNoQuestions.isEmpty();
        } catch (Exception e) {
            System.out.println("Error checking for Yes/No question: " + e.getMessage());
            return false;
        }
    }*/

    public static void addYesNoQuestion(WebElement categoryElement) {
        try {
            WebElement addQuestionButton = categoryElement.findElement(By.xpath(".//button[contains(text(), 'Add question')]"));
            WaitUtilsFragment.waitForWebElementToClickable(addQuestionButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addQuestionButton);
            System.out.println("Added question to category.");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Add Question button not found. Skipping...");
        }
    }

    private static void addCustomQuestion(WebElement questionElement, String questionType) {
        try {
            WebElement dropdown = questionElement.findElement(By.xpath(".//div[contains(@class, 'MuiSelect-root') and @role='button']"));
            WaitUtilsFragment.waitForWebElementToClickable(dropdown);
            dropdown.click();
            Thread.sleep(1000); // Wait for dropdown to open

            // **Ensure the dropdown has fully loaded before selecting the option**
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li[contains(text(), '" + questionType + "')]")));

            // **Scroll to the option to ensure visibility before clicking**
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
            Thread.sleep(500); // Small delay before clicking

            option.click();
            System.out.println("Selected question type: " + questionType);
            Thread.sleep(1000); // Allow time for selection to register
        } catch (Exception e) {
            System.out.println("Error selecting question type: " + e.getMessage());
        }
    }



}
