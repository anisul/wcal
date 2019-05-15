package impl;

import dao.Dao;
import domain.Entry;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import utils.AppUtil;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DaoImpl implements Dao {
    private static final String CSV_FILE_PATH = "HourList201403.csv";

    public List<Entry> getExcelEntries(Date startDate, Date endDate) {
        List<Entry> entryList = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());

            for (CSVRecord csvRecord: csvParser) {
                Entry entry = new Entry();
                entry.setPersonName(csvRecord.get("Person Name"));
                entry.setId(Long.parseLong(csvRecord.get("Person ID")));
                entry.setEntryDate(AppUtil.convertStringToDate(csvRecord.get("Date")));
                entry.setStartTime();
                entry.setEndTime();

                entryList.add(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entryList;
    }
}
