package CloverSwitcher.Model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class MountManager {

    public static String listDisksWindows() {
        String result = "";

        try {
            String line;

            PrintWriter writer = new PrintWriter("diskpartScript.txt", "UTF-8");
            writer.println("list disk");
            writer.close();

            ProcessBuilder builder = new ProcessBuilder("diskpart.exe", "/s", "diskpartScript.txt");
            Process p = builder.start();
            p.waitFor();

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = br.readLine()) != null) {
                if (result == "") {
                    result = line;
                } else {
                    result += "\n" + line;
                }
            }
            Files.deleteIfExists(FileSystems.getDefault().getPath("diskpartScript.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String listPartitionsWindows(int diskNumber) {
        String result = "";

        try {
            String line;

            PrintWriter writer = new PrintWriter("diskpartScript.txt", "UTF-8");
            writer.println("select disk " + diskNumber);
            writer.println("list partition");
            writer.close();

            ProcessBuilder builder = new ProcessBuilder("diskpart.exe", "/s", "diskpartScript.txt");
            Process p = builder.start();
            p.waitFor();

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = br.readLine()) != null) {
                if (result == "") {
                    result = line;
                } else {
                    result += "\n" + line;
                }
            }
            Files.deleteIfExists(FileSystems.getDefault().getPath("diskpartScript.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String mountPartitionWindows(int diskNumber, int partitionNumber) {
        String result = "";

        try {
            String line;

            PrintWriter writer = new PrintWriter("diskpartScript.txt", "UTF-8");
            writer.println("select disk " + diskNumber);
            writer.println("select partition " + partitionNumber);
            writer.println("assign letter=Z");
            writer.close();

            ProcessBuilder builder = new ProcessBuilder("diskpart.exe", "/s", "diskpartScript.txt");
            Process p = builder.start();
            p.waitFor();

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = br.readLine()) != null) {
                if (result == "") {
                    result = line;
                } else {
                    result += "\n" + line;
                }
            }
            Files.deleteIfExists(FileSystems.getDefault().getPath("diskpartScript.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String unmountPartitionWindows(int diskNumber, int partitionNumber) {
        String result = "";

        try {
            String line;

            PrintWriter writer = new PrintWriter("diskpartScript.txt", "UTF-8");
            writer.println("select disk " + diskNumber);
            writer.println("select partition " + partitionNumber);
            writer.println("remove letter=Z");
            writer.close();

            ProcessBuilder builder = new ProcessBuilder("diskpart.exe", "/s", "diskpartScript.txt");
            Process p = builder.start();
            p.waitFor();

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = br.readLine()) != null) {
                if (result == "") {
                    result = line;
                } else {
                    result += "\n" + line;
                }
            }
            Files.deleteIfExists(FileSystems.getDefault().getPath("diskpartScript.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}