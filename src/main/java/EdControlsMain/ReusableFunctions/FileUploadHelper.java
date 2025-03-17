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

    public static void uploadLatestImageUsingAppleScript() {
        // Step 1: Get the latest image from Downloads folder
        String downloadsPath = System.getProperty("user.home") + "/Downloads";
        File latestImage = getLatestImage(downloadsPath);
        if (latestImage == null) {
            System.out.println("No image found!");
            return;
        }
        String filePath = latestImage.getAbsolutePath();
        System.out.println("Uploading file: " + filePath);

        // Step 2: Use AppleScript to select and confirm the file
        executeAppleScriptForFileSelection(filePath);
    }

    private static File getLatestImage(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }
        File[] files = dir.listFiles((d, name) -> {
            String lower = name.toLowerCase();
            return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png");
        });
        if (files == null || files.length == 0) {
            return null;
        }
        return Arrays.stream(files)
                .max(Comparator.comparingLong(File::lastModified))
                .orElse(null);
    }

    private static void executeAppleScriptForFileSelection(String filePath) {
        try {
            filePath = filePath.replace("\"", "\\\"");

            // AppleScript to navigate, select, and click "Open"
            String script = "tell application \"System Events\"\n" +
                    "    tell process \"Google Chrome\"\n" +  // Adjust for your browser
                    "        delay 1\n" +
                    "        keystroke \"G\" using {command down, shift down}\n" +  // Open 'Go to Folder'
                    "        delay 1\n" +
                    "        keystroke \"" + filePath + "\"\n" +  // Type file path
                    "        delay 1\n" +
                    "        keystroke return\n" +  // Confirm file path
                    "        delay 1\n" +
                    "        tell application \"System Events\"\n" +
                    "            tell process \"Finder\"\n" +
                    "                delay 1\n" +
                    "                click button \"Open\" of window 1\n" +  // Click the "Open" button
                    "            end tell\n" +
                    "        end tell\n" +
                    "    end tell\n" +
                    "end tell";

            String[] args = { "osascript", "-e", script };
            Process process = Runtime.getRuntime().exec(args);
            process.waitFor();
            System.out.println("AppleScript executed successfully!");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    }


