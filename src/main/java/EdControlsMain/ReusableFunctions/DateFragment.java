package dhruvakumar.ReusableFunctions;

import dhruvakumar.BaseClasses.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateFragment extends BaseTest {
    public DateFragment(WebDriver driver) {
        super(driver);
    }
    public void datePicker()
    {
        // Get today's date and calculate the target date (7 days later)
        LocalDate today = LocalDate.now();
        LocalDate targetDate = today.plusDays(7);
        String expectedMonthYear = targetDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        String expectedDay = String.valueOf(targetDate.getDayOfMonth());

        // Iterate through months to reach the target month
        while (true) {
            String displayedMonthYear = driver.findElement(By.className("month-year-class")).getText(); // Update locator
            if (displayedMonthYear.equals(expectedMonthYear)) {
                break;
            }
            driver.findElement(By.className("next-month-button")).click(); // Update locator
        }

        // Get all date elements
        List<WebElement> dates = driver.findElements(By.className("day-class")); // Update locator

        for (WebElement date : dates) {
            String day = date.getText();

            // Check if the date is in the past (disable validation)
            if (date.getAttribute("class").contains("disabled")) {
                System.out.println("Validation Passed: Past date is disabled -> " + day);
            }

            // Click on the expected date
            if (day.equals(expectedDay) && !date.getAttribute("class").contains("disabled")) {
                date.click();
                System.out.println("Selected date: " + targetDate);
                break;
            }
        }
    }




}
