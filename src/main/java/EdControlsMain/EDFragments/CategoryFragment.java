package EdControlsMain.EDFragments;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.ReusableFunctions.WaitUtils;
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

    public static void addQuestionsToCategory(WebElement categoryElement, List<String> questionTypes, WebDriver driver) {
        boolean isFirstQuestion = true; // Track if this is the first question

        for (String questionType : questionTypes) {
            if (isFirstQuestion) {
                if (questionType.contains("Yes/No")) {
                    addYesNoQuestion(categoryElement);
                } else {
                    addCustomQuestion(categoryElement, questionType);
                }
                isFirstQuestion = false; // Next question should not be the first one
            } else {
                // Click 'Add Question' for the next question
                WebElement addQuestionButton = categoryElement.findElement(By.xpath(".//button[contains(text(), 'Add question')]"));
                WaitUtils.waitForWebElementToClickable(addQuestionButton);
                addQuestionButton.click();

                addCustomQuestion(categoryElement, questionType);
            }
        }
    }

    private static void addCustomQuestion(WebElement categoryElement, String questionType) {
        try {
            WebElement dropdown = categoryElement.findElement(By.xpath(".//div[contains(@class, 'MuiSelect-root') and @role='button']"));
            WaitUtils.waitForWebElementToClickable(dropdown);
            dropdown.click();

            WebElement option = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(".//li[contains(text(), '" + questionType + "')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);


            System.out.println("Selected question type: " + questionType);
        } catch (Exception e) {
            System.out.println("Error selecting question type: " + e.getMessage());
        }
    }

    public static void addYesNoQuestion(WebElement categoryElement) {
        try {
            WebElement addQuestionButton = categoryElement.findElement(By.xpath(".//button[contains(text(), 'Add question')]"));
            WaitUtils.waitForWebElementToClickable(addQuestionButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addQuestionButton);
            System.out.println("Added question to category.");
        } catch (Exception e) {
            System.out.println("Add Question button not found. Skipping...");
        }
    }



}
