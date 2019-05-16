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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements Dao {
    private static final String CSV_FILE_PATH = "HourList201403.csv";

    public List<Entry> getExcelEntries() {
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
                entry.setEntryDate(AppUtil.convertStringToLocalDateTime(csvRecord.get("Date")));

                LocalDateTime localDateTime = entry.getEntryDate();
                long hourComponent = localDateTime.getHour();
                long minuteComponent = localDateTime.getMinute();

                entry.setEntryDate(entry.getEntryDate().minusHours(hourComponent).minusMinutes(minuteComponent));

                int startHourComponent = Integer.parseInt(csvRecord.get("Start").split(":")[0]);
                int endHourComponent = Integer.parseInt(csvRecord.get("End").split(":")[0]);

                entry.setStartTime(AppUtil.convertStringToLocalDateTime(entry.getEntryDate(), csvRecord.get("Start"), false));
                entry.setEndTime(AppUtil.convertStringToLocalDateTime(entry.getEntryDate(), csvRecord.get("End"), startHourComponent > endHourComponent));

                entryList.add(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entryList;
    }
}
