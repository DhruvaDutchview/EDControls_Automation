package EdControlsMain.Resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.*;

public class DataReader {

    public DataReader(WebDriver driver) {
        super();
    }

    //Method to read JSON File
    public static String getValueFromJsonFile(String keyPath) {
        try {
            // Correct file path for macOS/Linux
            String filePath = System.getProperty("user.dir") + "/src/main/java/EdControlsMain/Resources/GlobalData.json";

            // Read JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(filePath));

            // Traverse the JSON structure based on keyPath
            return getJsonValue(rootNode, keyPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getJsonValue(JsonNode node, String keyPath) {
        String[] keys = keyPath.split("\\."); // Split path by '.'

        for (String key : keys) {
            if (key.contains("[")) { // Handle arrays, e.g., "browsers[0].browserName"
                String arrayKey = key.substring(0, key.indexOf("["));
                int index = Integer.parseInt(key.substring(key.indexOf("[") + 1, key.indexOf("]")));

                node = node.get(arrayKey); // Get the array node
                if (node != null && node.isArray() && node.has(index)) {
                    node = node.get(index); // Get the specific array element
                } else {
                    return null; // Array index out of bounds
                }
            } else {
                node = node.get(key); // Get the next node in the hierarchy
            }

            if (node == null) return null; // Return null if key is missing
        }

        return node.asText(); // Convert JSON node to text
    }
    // Method to read properties file and return values as a Map
    public static Map<String, String> readPropertiesFile() {
        Map<String, String> propertiesMap = new HashMap<>();
        try {
            String filePath = System.getProperty("user.dir") + "/src/main/java/EdControlsMain/Resources/GlobalData.properties";
            FileInputStream fileInput = new FileInputStream(filePath);
            Properties prop = new Properties();
            prop.load(fileInput);

            // Store values in HashMap
            propertiesMap.put("browserName", prop.getProperty("browserName"));
            propertiesMap.put("email", prop.getProperty("email"));
            propertiesMap.put("password", prop.getProperty("password"));
            propertiesMap.put("url", prop.getProperty("url"));
            fileInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertiesMap;
    }

    //Getting newly created ticket info
    public static void newTicketJSON() {
        // Set the base URI
        RestAssured.baseURI = "https://dev.edcontrols.com";

        // Make the POST request and capture the response
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer ed13cd05-820d-4a3c-b354-08386ff606ef") // Use your dynamic token
                .header("Accept", "application/json")
                .when()
                .post("/api/v1/securedata/com_dutchview_2a83a404-5eeb-4d24-a107-6e7c29628840")
                .then()
                .statusCode(201)  // Validate the Status Code is 201 Created
                .extract().response();

        // Print the Status Code
        System.out.println("Status Code: " + response.getStatusCode());

        // Print the Full Response
        System.out.println("Response: " + response.asString());

        // Extract specific values from the JSON response
        String id = response.jsonPath().getString("id");
        String rev = response.jsonPath().getString("rev");

        System.out.println("Ticket ID: " + id);
        System.out.println("Revision: " + rev);
    }


}
