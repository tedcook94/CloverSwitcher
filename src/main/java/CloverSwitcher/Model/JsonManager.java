package CloverSwitcher.Model;

import CloverSwitcher.Controller.MainWindow;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class JsonManager {

    public static void writeToFile() {

        ObservableList<BootEntry> entryList = EntryList.getEntryList();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (BootEntry entry : entryList) {
            JSONObject entryJsonObj = new JSONObject();
            entryJsonObj.put("entryName", entry.getEntryName());
            entryJsonObj.put("uuid", entry.getUuid());
            jsonArray.add(entryJsonObj);
        }

        jsonObject.put("entryList", jsonArray);

        try (FileWriter file = new FileWriter("bootEntries.json")) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFromFile() {

        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("bootEntries.json"));
            JSONArray entryList = (JSONArray) jsonObject.get("entryList");
            Iterator<JSONObject> iterator = entryList.iterator();

            while (iterator.hasNext()) {
                JSONObject entry = iterator.next();
                EntryList.addEntry(new BootEntry(entry.get("entryName").toString(), entry.get("uuid").toString()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No bootEntries file found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
