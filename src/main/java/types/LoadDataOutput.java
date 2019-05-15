package types;

import domain.Entry;

import java.util.ArrayList;
import java.util.List;

public class LoadDataOutput extends BaseOutput {
    List<Entry> entryList = new ArrayList<Entry>();

    public List<Entry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<Entry> entryList) {
        this.entryList = entryList;
    }
}
