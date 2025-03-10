package EdControlsMain.ReusableFunctions;

import EdControlsMain.BaseClasses.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateFragment extends BaseTest {
    public DateFragment(WebDriver driver) {
        super(driver);
    }
    public static void datePicker(WebElement dueDateContainer) throws InterruptedException {
        // Get today's date and calculate the target date (7 days later)
        LocalDate today = LocalDate.now();
        LocalDate targetDate = today.plusDays(12);
        String expectedMonthYear = targetDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        String expectedDay = String.valueOf(targetDate.getDayOfMonth());
        System.out.println("Expected date:"+expectedDay);
        // Iterate through months to reach the target month
        while (true) {
            String displayedMonthYear =dueDateContainer.findElement(By.xpath(" //button[@class='react-calendar__navigation__label']")).getText(); // Update locator
            if (displayedMonthYear.equals(expectedMonthYear)) {
                break;
            }
            driver.findElement(By.xpath("//button[contains(@class,'react-calendar__navigation__next-button')]")).click(); // Update locator
        }

        // Get all date elements
        List<WebElement> dates = driver.findElements(By.xpath("//button[contains(@class, 'react-calendar__month-view__days__day')]")); // Update locator

        for (WebElement date : dates) {
            String day = date.getText();

            // Check if the date is in the past (disable validation)
            if (date.getAttribute("class").contains("disabled")) {
                System.out.println("Validation Passed: Past date is disabled -> " + day);
            }

            // Click on the expected date
            if (day.equals(expectedDay) && !date.getAttribute("class").contains("disabled")) {
                Thread.sleep(2000);
             //   JavascriptExecutor js = (JavascriptExecutor) driver;
            //    js.executeScript("arguments[0].click();", date);
                date.click();
                System.out.println("Selected date: " + targetDate);
                break;
            }
        }
    }


    public static void projectDatePicker(Integer number) throws InterruptedException {
        WebElement startDateElement = driver.findElement(By.xpath("//div[@id='sd-date-picker']"));
        WebElement dueDateElement = driver.findElement(By.xpath("//div[@id='ed-date-picker']"));

        // Get today's date
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(number); // Calculate due date based on passed number

        // Select Start Date (Always Today)
        startDateElement.click();
        selectDate(today);

        // Select Due Date
        dueDateElement.click();
        selectDate(dueDate);
    }

    private static void selectDate(LocalDate targetDate) throws InterruptedException {
        String expectedMonthYear = targetDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        String expectedDay = String.valueOf(targetDate.getDayOfMonth());

        // Iterate through months to reach the target month
        while (true) {
            String displayedMonthYear = driver.findElement(By.xpath("//button[@class='react-calendar__navigation__label']")).getText();
            if (displayedMonthYear.equals(expectedMonthYear)) {
                break;
            }
            driver.findElement(By.xpath("//button[contains(@class,'react-calendar__navigation__next-button')]")).click(); // Dummy locator for next button
        }

        // Get all date elements and select the target date
        List<WebElement> dates = driver.findElements(By.xpath("//button[contains(@class, 'react-calendar__month-view__days__day')]"));
        for (WebElement date : dates) {
            if (date.getText().equals(expectedDay) && !date.getAttribute("class").contains("disabled")) {
                Thread.sleep(2000);
                date.click();
                System.out.println("Selected Date: " + targetDate);
                break;
            }
        }
    }




}
