package ctlChecker;

import static ctlOperator.ContinuSearching.ABORT;
import static ctlOperator.ContinuSearching.CONTINUE;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ctlFormula.Atom;
import ctlFormula.Formula;
import ctlOperator.AU;
import ctlOperator.CheckAUResult;
import kripkeSystem.KripkeStructure;
import kripkeSystem.State;
import tarjan.TarjansDepthFirstSearchData;

public class CTLModelChecker {


    private KripkeStructure kripkeStructure;

    private Map<State, Map<Formula, Boolean>> labels = new HashMap<>();

    public CTLModelChecker(KripkeStructure kripkeStructure) {
        this.kripkeStructure = kripkeStructure;
    }

    public static boolean satisfies(KripkeStructure kripkeStructure, Formula formula) {
        return new CTLModelChecker(kripkeStructure).satisfies(formula);
    }

    public boolean satisfies(Formula formula) {
        Formula formulaBase = formula;
        return kripkeStructure.getInitStates().stream().allMatch(initialState -> satisfies(initialState, formulaBase));
    }

    private boolean addLabel(State state, Formula formula, boolean value) {
        Map<Formula, Boolean> labelsForState = labels.computeIfAbsent(state, k -> new HashMap<>());
        labelsForState.put(formula, value);
        return value;
    }

    private boolean satisfies(State state, Formula formula) {
        return getLabel(state, formula).orElseGet(() -> computeLabel(state, formula));
    }

    private Optional<Boolean> getLabel(State state, Formula formula) {
        return Optional.ofNullable(labels.getOrDefault(state, new HashMap<>()).get(formula)); //v or n
    }

    private boolean computeLabel(State state, Formula formula) {
        if (formula instanceof Atom) {
            return addLabel(state, formula, state.satisfies((Atom) formula));
        }

        if (formula instanceof AU) {
            checkAU(state, (AU) formula);
            boolean isFormulaSatisfied = getLabel(state, formula).get();

            return isFormulaSatisfied;
        }

        throw new IllegalArgumentException(formula.toString());
    }


    private CheckAUResult checkAU(State state, AU formula) {
        TarjansDepthFirstSearchData dData = new TarjansDepthFirstSearchData();
        return checkAU(state, formula, dData);
    }

    private CheckAUResult checkAU(State state, AU formula, TarjansDepthFirstSearchData tData) {
        Formula op1 = formula.getOperand1();
        Formula op2 = formula.getOperand2();

        tData.visit(state);
      
        Optional<Boolean> label = getLabel(state, formula);
        if (label.isPresent()) {
            if (label.get()) { // general label = true 
                return new CheckAUResult(CONTINUE, null);
            } else { // general label = false
                return new CheckAUResult(ABORT, state);
            }
        }

        if (satisfies(state, op2)) {
          
            addLabel(state, formula, true);
            return new CheckAUResult(CONTINUE, null);
        }

        if (!satisfies(state, op1)) {
            
            addLabel(state, formula, false);
            return new CheckAUResult(ABORT, state);
        }

     
        addLabel(state, formula, false);
        for (State successorState : kripkeStructure.getAllSuccessorStates(state)) {
          
            if (!tData.isVisited(successorState)) {
                
                CheckAUResult checkAUResult = checkAU(successorState, formula, tData);
                if (checkAUResult.getSearchContinuation() == ABORT) {
                    return checkAUResult;
                }
            } else {
               
                if (tData.isOnStack(successorState)) {
                   
                    return new CheckAUResult(ABORT, successorState);
                }
            }
        }

        tData.removeFromStack(state);
        addLabel(state, formula, true);
        return new CheckAUResult(CONTINUE, null);
    }

}
