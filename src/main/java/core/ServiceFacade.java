package core;

import dao.Dao;
import types.LoadDataInput;
import types.LoadDataOutput;

public class ServiceFacade {
    protected Dao dao;

    public LoadDataOutput loadData(LoadDataInput input) {
        LoadDataHelper helper = new LoadDataHelper(dao, input);
        return (LoadDataOutput) helper.execute();
    }
}
