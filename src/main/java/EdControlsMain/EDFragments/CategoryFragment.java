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
        addYesNoQuestion(categoryElement);
        setFirstQuestionName(categoryElement, index, "Yes/No");

        int questionNumber = 1;
        for (String questionType : questionTypes) {
            WebElement addQuestionButton = categoryElement.findElement(By.xpath(".//button[contains(text(), 'Add question')]"));
            WaitUtilsFragment.waitForWebElementToClickable(addQuestionButton);
            addQuestionButton.click();
            Thread.sleep(1000);

            WebElement questionElement = categoryElement.findElement(By.xpath(".//div[@data-rbd-draggable-id='qstn-" + index + "-" + questionNumber + "']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", questionElement);

            setQuestionName(questionElement, questionNumber, questionType);
            Thread.sleep(1000);
            addCustomQuestion(questionElement, questionType, index);
            Thread.sleep(1000);
            questionNumber++;
        }

        List<WebElement> categories = driver.findElements(By.xpath(".//div[@class='category']"));
        if (!categories.isEmpty() && categoryElement.equals(categories.get(categories.size() - 1))) {
            WebElement addTextBoxQuestion = categoryElement.findElement(By.xpath(".//button[contains(text(), 'Add text box')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addTextBoxQuestion);
        }
    }

    public static void addYesNoQuestion(WebElement categoryElement) {
        try {
            WebElement addQuestionButton = categoryElement.findElement(By.xpath(".//button[contains(text(), 'Add question')]"));
            WaitUtilsFragment.waitForWebElementToClickable(addQuestionButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addQuestionButton);
            System.out.println("Added Yes/No question to category.");
        } catch (Exception e) {
            System.out.println("Add Question button not found. Skipping...");
        }
    }

    private static void setQuestionName(WebElement questionElement, int number, String questionType) {
        try {
            WebElement questionField = questionElement.findElement(By.id("log"));
            questionField.click();
            Thread.sleep(1000);

            String questionText = "Q" + (number + 1) + ": " + questionType;
            WebElement questionTextField = questionElement.findElement(By.xpath(".//div[contains(@class,'fr-element')]"));
            questionTextField.sendKeys(questionText);
            System.out.println("Set question text: " + questionText);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Error setting question text: " + e.getMessage());
        }
    }

    private static void setFirstQuestionName(WebElement categoryElement, int number, String questionType) {
        try {
            WebElement questionElement = WaitUtilsFragment.waitForWebElementAppear(categoryElement.findElement(By.xpath(".//div[@data-rbd-draggable-id='qstn-" + number + "-0']")));
            Thread.sleep(1000);
            WebElement questionField = questionElement.findElement(By.id("log"));
            questionField.click();
            Thread.sleep(1000);

            int questionNumber = (number == 0) ? 1 : (number == 2) ? number - 1 : number;
            String questionText = "Q" + questionNumber + ": " + questionType;
            WebElement questionTextField = questionElement.findElement(By.xpath(".//div[contains(@class,'fr-element')]"));
            questionTextField.sendKeys(questionText);
            System.out.println("Set question text: " + questionText);
            Thread.sleep(1000);
            addCustomQuestion(questionElement, questionType, number);
        } catch (Exception e) {
            System.out.println("Error setting first question text: " + e.getMessage());
        }
    }

    private static void addCustomQuestion(WebElement questionElement, String questionType, int index) {
        try {
            WebElement dropdown = questionElement.findElement(By.xpath(".//div[contains(@class, 'MuiSelect-root') and @role='button']"));
            WaitUtilsFragment.waitForWebElementToClickable(dropdown);
            dropdown.click();
            Thread.sleep(1000);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(), '" + questionType + "')]")));
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
            Thread.sleep(500);
            option.click();

            if (questionType.contains("Multiple choice - multiple answers")) {
                setMultipleChoiceMultipleAnswer(questionElement, questionType, index);
            } else if (questionType.contains("Multiple choice - single answer")) {
                setMultipleChoiceSingleAnswer(questionElement, questionType, index);
            }
            System.out.println("Selected question type: " + questionType);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Error selecting question type: " + e.getMessage());
        }
    }

    public static void setMultipleChoiceMultipleAnswer(WebElement questionElement, String questionType, int number) throws Exception {
        List<WebElement> multipleOptions = questionElement.findElements(By.xpath(".//div[@data-rbd-droppable-id='droppable-option']//div[@class='option__container']"));
        Thread.sleep(1000);
        System.out.println("Total options found: " + multipleOptions.size());
        for (int i = 0; i < multipleOptions.size(); i++) {
            WebElement multipleOption = multipleOptions.get(i);
            multipleOption.click();
            Thread.sleep(500);
            String optionText = "Multiple Answer Option " + (i + 1);
            WebElement questionTextField = multipleOption.findElement(By.xpath(".//div[contains(@class,'fr-element')]"));
            questionTextField.sendKeys(optionText);
            System.out.println("Set option text: " + optionText);
            Thread.sleep(500);
        }
        WebElement categoryContainer = questionElement.findElement(By.xpath(".//ancestor::div[contains(@class,'category')]"));
        categoryContainer.click();
        Thread.sleep(1000);
    }

    public static void setMultipleChoiceSingleAnswer(WebElement questionElement, String questionType, int number) throws Exception {
        List<WebElement> multipleOptions = questionElement.findElements(By.xpath(".//div[@data-rbd-droppable-id='droppable-option']//div[@class='option__container']"));
        Thread.sleep(1000);
        System.out.println("Total options found: " + multipleOptions.size());
        for (int i = 0; i < multipleOptions.size(); i++) {
            WebElement multipleOption = multipleOptions.get(i);
            multipleOption.click();
            Thread.sleep(500);
            String optionText = "Single Answer Option " + (i + 1);
            WebElement questionTextField = multipleOption.findElement(By.xpath(".//div[contains(@class,'fr-element')]"));
            questionTextField.clear();
            questionTextField.sendKeys(optionText);
            System.out.println("Set option text: " + optionText);
            Thread.sleep(500);
        }
        WebElement categoryContainer = questionElement.findElement(By.xpath(".//ancestor::div[contains(@class,'category')]"));
        categoryContainer.click();
        Thread.sleep(1000);
    }



}
