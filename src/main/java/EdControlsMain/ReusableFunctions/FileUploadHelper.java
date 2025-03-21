package EdControlsMain.ReusableFunctions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class FileUploadHelper {

    public static void uploadImageUsingAppleScript(String imageType, String fileExtension) {
        // Step 1: Construct the filename based on arguments
        String targetFileName = imageType + fileExtension;

        // Step 2: Get the file path from Downloads folder
        String downloadsPath = System.getProperty("user.home") + "/Downloads";
        File targetFile = findFileByName(downloadsPath, targetFileName);

        if (targetFile == null) {
            System.out.println("No matching file found: " + targetFileName);
            return;
        }

        String filePath = targetFile.getAbsolutePath();
        System.out.println("Uploading file: " + filePath);

        // Step 3: Use AppleScript to select and confirm the file
        executeAppleScriptForFileSelection(filePath);
    }

    private static File findFileByName(String directoryPath, String targetFileName) {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }

        File[] files = dir.listFiles((d, name) -> name.equalsIgnoreCase(targetFileName));

        if (files == null || files.length == 0) {
            return null;
        }

        // If multiple matches exist, return the most recent one
        return Arrays.stream(files)
                .max(Comparator.comparingLong(File::lastModified))
                .orElse(null);
    }

    private static void executeAppleScriptForFileSelection(String filePath) {
        try {
            filePath = filePath.replace("\"", "\\\"");  // Escape double quotes

            String script =
                    "tell application \"System Events\"\n" +
                            "    tell process \"Google Chrome\"\n" +
                            "        delay 1\n" +
                            "        keystroke \"G\" using {command down, shift down}\n" +  // Open 'Go to Folder'
                            "        delay 1\n" +
                            "        keystroke \"" + filePath + "\"\n" +  // Type the full file path
                            "        delay 1\n" +
                            "        keystroke return\n" +  // Press Enter to navigate
                            "        delay 1\n" +
                            "        keystroke return\n" +  // Select the file
                            "        delay 1\n" +
                            "        keystroke return\n" +  // Confirm selection
                            "    end tell\n" +
                            "end tell";

            String[] args = {"osascript", "-e", script};
            Process process = Runtime.getRuntime().exec(args);
            process.waitFor();
            System.out.println("AppleScript executed successfully!");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }

}


