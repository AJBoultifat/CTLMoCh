package ctlOperator;

import kripkeSystem.State;

public class CheckAUResult {
    private ContinuSearching searchContinuation;

    public CheckAUResult(ContinuSearching searchContinuation, State lastStateInCounterExample) {
        this.searchContinuation = searchContinuation;
    }

    public ContinuSearching getSearchContinuation() {
        return searchContinuation;
    }

}
