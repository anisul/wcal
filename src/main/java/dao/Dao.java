package dao;

import domain.Entry;

import java.util.Date;
import java.util.List;

public interface Dao {
    public List<Entry> getExcelEntries(Date startDate, Date endDate);
}
