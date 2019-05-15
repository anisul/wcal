package core;

import dao.Dao;
import types.BaseInput;
import types.BaseOutput;

public class AbstractHelper {
    protected Dao dao;
    protected BaseInput input;
    protected BaseOutput output;

    protected void init(BaseInput input, BaseOutput output, Dao dao) {
        this.input = input;
        this.output = output;
        this.dao = dao;
    }


    protected void checkPermission(){
        //empty implementation
    }

    protected void executeHelper(){
        //empty implementation
    }

    public final BaseOutput execute() {
        checkPermission();
        executeHelper();

        return output;
    }
}
