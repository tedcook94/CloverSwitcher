package CloverSwitcher.Model;

import java.io.File;
import java.io.PrintWriter;

import com.dd.plist.*;
import org.apache.commons.lang3.SystemUtils;

public class PlistManager {

    public static void setDefaultEntry(File configFile, String uuid) {
        try {
            NSDictionary config = (NSDictionary) PropertyListParser.parse(configFile);
            NSDictionary boot = (NSDictionary) config.objectForKey("Boot");
            boot.put("DefaultVolume", new NSString(uuid));

            System.setProperty("line.separator", "\n");
            PrintWriter writer;
            if (SystemUtils.IS_OS_WINDOWS) {
                writer = new PrintWriter("Z:" + File.separator + "EFI" + File.separator + "CLOVER" + File.separator + "config.plist", "UTF-8");
            } else if (SystemUtils.IS_OS_MAC) {
                writer = new PrintWriter("/Volumes/EFI" + File.separator + "EFI" + File.separator + "CLOVER" + File.separator + "config.plist", "UTF-8");
            } else {
                writer = new PrintWriter("config.plist", "UTF-8");
            }
            writer.print(config.toXMLPropertyList());
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
