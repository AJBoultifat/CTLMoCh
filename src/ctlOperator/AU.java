package ctlOperator;

import ctlFormula.Formula;

public class AU implements Formula {

    private Formula operand1;
    private Formula operand2;

    public AU(Formula operand1, Formula operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }
    
    public static AU AU(Formula operand1, Formula operand2) {
        return new AU(operand1, operand2);
    }

    public Formula getOperand1() {
        return operand1;
    }

    public Formula getOperand2() {
        return operand2;
    }

    @Override
    public Formula CTLBase() {
        return AU(operand1, operand2);
    }
}
