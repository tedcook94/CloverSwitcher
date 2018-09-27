package CloverSwitcher.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EntryList {

    private static ObservableList<BootEntry> entryList = FXCollections.observableArrayList();

    public static ObservableList<BootEntry> getEntryList() { return  entryList; }

    public static void addEntry(BootEntry entry) {
        entryList.add(entry);
    }

    public static void saveNewEntry(BootEntry entry) {
        addEntry(entry);
        JsonManager.writeToFile();
    }

    public static void deleteEntry(BootEntry entry) {
        entryList.remove(entry);
        JsonManager.writeToFile();
    }

    public static void updateEntry(int index, BootEntry entry) {
        entryList.set(index, entry);
        JsonManager.writeToFile();
    }
}
