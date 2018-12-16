package CloverSwitcher.Model;

import CloverSwitcher.Controller.MainWindow;

import java.io.BufferedReader;
import java.io.File;
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

    public static String listPartitionsMac() {
        String result = "";

        try {
            String line;

            ProcessBuilder builder = new ProcessBuilder("/usr/sbin/diskutil", "list");
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

            File config = new File("Z:" + File.separator + "EFI" + File.separator + "CLOVER" + File.separator + "config.plist");
            PlistManager.setDefaultEntry(config, MainWindow.getEntryToSetAsDefault().getUuid());
            unmountPartitionWindows(diskNumber, partitionNumber);

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

    public static String mountPartitionMac(int diskNumber, int partitionNumber) {
        String result = "";

        try {
            String line;

            File elevator = File.createTempFile("elevator", ".sh");
            PrintWriter writer = new PrintWriter(elevator, "UTF-8");
            writer.println("#!/bin/bash");
            writer.println();
            String diskPartToMount = "/dev/disk" + diskNumber + "s" + partitionNumber;
            writer.println("osascript -e \"do shell script \\\"mkdir /Volumes/EFI && mount -t msdos " + diskPartToMount + " /Volumes/EFI\\\" with administrator privileges\"");
            writer.close();
            elevator.setExecutable(true);

            ProcessBuilder builder = new ProcessBuilder(elevator.getPath());
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
            Files.deleteIfExists(FileSystems.getDefault().getPath(elevator.getPath()));

            File config = new File("/Volumes/EFI" + File.separator + "EFI" + File.separator + "CLOVER" + File.separator + "config.plist");
            PlistManager.setDefaultEntry(config, MainWindow.getEntryToSetAsDefault().getUuid());
            unmountPartitionMac();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String unmountPartitionMac() {
        String result = "";

        try {
            String line;

            File elevator = File.createTempFile("elevator", ".sh");
            PrintWriter writer = new PrintWriter(elevator, "UTF-8");
            writer.println("#!/bin/bash");
            writer.println();
            writer.println("osascript -e \"do shell script \\\"umount /Volumes/EFI\\\" with administrator privileges\"");
            writer.close();
            elevator.setExecutable(true);

            ProcessBuilder builder = new ProcessBuilder(elevator.getPath());
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
            Files.deleteIfExists(FileSystems.getDefault().getPath(elevator.getPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}