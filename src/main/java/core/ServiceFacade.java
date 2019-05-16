package core;

import dao.Dao;
import impl.DaoImpl;
import types.CalculateWageInput;
import types.CalculateWageOutput;
import types.LoadDataInput;
import types.LoadDataOutput;

public class ServiceFacade {
    Dao dao = new DaoImpl();

    public LoadDataOutput loadData(LoadDataInput input) {
        LoadDataHelper helper = new LoadDataHelper(dao, input);
        return (LoadDataOutput) helper.execute();
    }

    public CalculateWageOutput calculateWage(CalculateWageInput input) {
        CalculateWageHelper helper = new CalculateWageHelper(dao, input);
        return (CalculateWageOutput) helper.execute();
    }
}
