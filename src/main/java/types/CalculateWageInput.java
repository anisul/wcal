package types;

import domain.Entry;

import java.util.ArrayList;
import java.util.List;

public class CalculateWageInput extends BaseInput {
    List<Entry> entryList = new ArrayList<>();

    public List<Entry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<Entry> entryList) {
        this.entryList = entryList;
    }
}
