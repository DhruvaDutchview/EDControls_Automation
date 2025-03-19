package EdControlsMain.EdContainers;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.Resources.DataReader;
import EdControlsMain.ReusableFunctions.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class ProjectContainer extends BaseTest {

    public ProjectContainer(WebDriver driver) {
        super(driver);
    }
    private static final String BASE_PATH = "used_numbers_";
    private static final String FILE_EXTENSION = ".txt";

    public static void navigateToProject () throws Exception {
        String projectName = DataReader.getValueFromJsonFile("projectName");
        WebElement projectSearch = driver.findElement(By.xpath("//input[@id='search']"));
        WaitUtils.waitForWebElementAppear(projectSearch);
        projectSearch.sendKeys(projectName + Keys.ENTER);
        List<WebElement> projectsList = driver.findElements(By.xpath("//p[@class='name']"));
        for (WebElement  project : projectsList)
        {
            String projectText = project.getText();
            if (projectText.equalsIgnoreCase(projectName))
            {
                System.out.println(projectText);
                project.click();
                System.err.println("we are inside "+ projectName+" successfully");
            }
        }

    }

    public static void editProject(String projectName) throws Exception {
        Thread.sleep(2000);
        WebElement projectSearch = driver.findElement(By.xpath("//input[@id='search']"));
        WaitUtils.waitForWebElementAppear(projectSearch);
        projectSearch.sendKeys(projectName + Keys.ENTER);
        Thread.sleep(2000);
        List<WebElement> projectsList = driver.findElements(By.xpath("//p[@class='name']"));
        for (WebElement  project : projectsList)
        {
            String projectText = project.getText();
            Thread.sleep(2000);
            if (projectText.equalsIgnoreCase(projectName))
            {
                System.out.println(projectText);
                WebElement editProjectElement = driver.findElement(By.id("project-edit-btn"));
                editProjectElement.click();
                break;
                //System.err.println("we are inside "+ projectName+" successfully");
            }
        }
    }


    public static void initiateNewProjectCreation() throws Exception {
        WebElement newProjectElement = driver.findElement(By.id("create-new-project"));
        WaitUtils.waitForWebElementAppear(newProjectElement);
        Thread.sleep(3000);
        newProjectElement.click();
    }

    public static void projectArchiveDearchive(List<WebElement> projectNameElements, String projectName) throws Exception {
        for (WebElement project : projectNameElements) {
            if (project.getText().equalsIgnoreCase(projectName)) {
                if (driver.findElement(By.xpath("//p[@class='user-role']")).getText().contains("Administrator")) {
                    System.out.println("User Role in:" +projectName + " is Administrator");
                    ProjectContainer.editProject(projectName);
                    WebElement deArchiveElement = driver.findElement(By.id("projArchive"));
                    deArchiveElement.click();
                }
                break;
            }
        }
    }

    public static void navigateToModule(WebElement element) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
        Thread.sleep(2000);
    }

    public enum NameType {
        AUTOMATION_PROJECT("Automation project"),
        LIBRARY_GROUP("Library group"),
        TEMPLATE_GROUP("Template group");

        private final String displayName;

        NameType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getFilePath() {
            return BASE_PATH + name().toLowerCase() + FILE_EXTENSION;
        }
    }

    // Public method to generate a unique name
    public static String getUniqueName(NameType type) {
        HashSet<Integer> usedNumbers = loadUsedNumbers(type);
        int randomNumber;

        do {
            randomNumber = new Random().nextInt(1000) + 1; // Generates a number between 1 and 1000
        } while (usedNumbers.contains(randomNumber));

        usedNumbers.add(randomNumber);
        saveUsedNumbers(usedNumbers, type);

        return type.getDisplayName() + " " + randomNumber;
    }

    // Load previously used numbers from the file
    private static HashSet<Integer> loadUsedNumbers(NameType type) {
        HashSet<Integer> numbers = new HashSet<>();
        File file = new File(type.getFilePath());
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    numbers.add(Integer.parseInt(line.trim()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return numbers;
    }

    // Save used numbers back to the file
    private static void saveUsedNumbers(HashSet<Integer> numbers, NameType type) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(type.getFilePath()))) {
            for (Integer num : numbers) {
                writer.write(num + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



