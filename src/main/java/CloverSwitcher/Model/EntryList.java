package CloverSwitcher.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EntryList {

    private static ObservableList<BootEntry> entryList = FXCollections.observableArrayList();

    public static ObservableList<BootEntry> getEntryList() { return  entryList; }
    public static void setEntryList(ObservableList<BootEntry> list) { entryList = list; }

    public static void addEntry(BootEntry entry) { entryList.add(entry); }
    public static void deleteEntry(BootEntry entry) { entryList.remove(entry); }
    public static void updateEntry(int index, BootEntry entry) { entryList.set(index, entry); }
}
