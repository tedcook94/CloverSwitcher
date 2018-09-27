package CloverSwitcher.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BootEntry {

    private final StringProperty entryName;
    private final StringProperty uuid;

    public BootEntry(String name, String id) {
        entryName = new SimpleStringProperty(name);
        uuid = new SimpleStringProperty(id);
    }

    public StringProperty entryNameProperty() { return  entryName; }
    public StringProperty uuidProperty() { return uuid; }
    public String getEntryName() { return entryName.get(); }
    public String getUuid() { return uuid.get(); }

    public void setEntryName(String name) { entryName.set(name); }
    public void setUuid(String id) { uuid.set(id); }
}
