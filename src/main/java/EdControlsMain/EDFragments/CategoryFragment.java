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

    public static void addQuestionsToCategory(WebElement categoryElement, List<String> questionTypes, WebDriver driver) throws InterruptedException {
        boolean isFirstQuestion = true; // Track if this is the first question

        for (String questionType : questionTypes) {
            if (isFirstQuestion) {
                if (questionType.contains("Yes/No")) {
                    addYesNoQuestion(categoryElement);
                    Thread.sleep(2000);
                } else {
                    addCustomQuestion(categoryElement, questionType);
                    Thread.sleep(2000);
                }
                isFirstQuestion = false; // Next question should not be the first one
                Thread.sleep(2000);
            } else {
                // Click 'Add Question' for the next question
                WebElement addQuestionButton = categoryElement.findElement(By.xpath(".//button[contains(text(), 'Add question')]"));
                WaitUtilsFragment.waitForWebElementToClickable(addQuestionButton);
                addQuestionButton.click();
                Thread.sleep(2000);
                addCustomQuestion(categoryElement, questionType);
                Thread.sleep(2000);
            }
        }
    }

    private static void addCustomQuestion(WebElement categoryElement, String questionType) {
        try {
            WebElement dropdown = categoryElement.findElement(By.xpath(".//div[contains(@class, 'MuiSelect-root') and @role='button']"));
            WaitUtilsFragment.waitForWebElementToClickable(dropdown);
            dropdown.click();
            Thread.sleep(2000);
            WebElement option = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(".//li[contains(text(), '" + questionType + "')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);

            Thread.sleep(2000);
            System.out.println("Selected question type: " + questionType);
        } catch (Exception e) {
            System.out.println("Error selecting question type: " + e.getMessage());
        }
    }

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



}
