import core.ServiceFacade;
import types.CalculateWageInput;
import types.CalculateWageOutput;
import types.LoadDataInput;
import types.LoadDataOutput;

public class Main {
    public static void main(String[] args) {
        ServiceFacade serviceFacade = new ServiceFacade();

        LoadDataInput loadDataInput = new LoadDataInput();
        LoadDataOutput loadDataOutput = serviceFacade.loadData(loadDataInput);

        CalculateWageInput calculateWageInput = new CalculateWageInput();
        calculateWageInput.setEntryList(loadDataOutput.getEntryList());

        CalculateWageOutput calculateWageOutput = serviceFacade.calculateWage(calculateWageInput);


    }
}
