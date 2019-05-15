package core;

import dao.Dao;
import types.LoadDataInput;
import types.LoadDataOutput;

public class LoadDataHelper extends AbstractHelper {
    private LoadDataInput input;
    private Dao dao;

    public LoadDataHelper(Dao dao, LoadDataInput input) {
        init(input, new LoadDataOutput(), dao);
        this.dao = dao;
        this.input = input;
    }

    @Override
    protected void executeHelper() {
        LoadDataOutput output = (LoadDataOutput) this.output;
        output.setEntryList(dao.getExcelEntries(input.getStartDate(), input.getEndDate()));
    }
}
