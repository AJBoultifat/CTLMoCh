package kripkeSystem;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KripkeStructure {

    private Set<State> states = new HashSet<>();
    private Set<State> initStates = new HashSet<>();
    private Map<State, Set<State>> transitions = new HashMap<>();

    public void addState(State... states) {
        Collections.addAll(this.states, states);
    }

    public void addIniState(State... states) {
        addState(states);
        Collections.addAll(this.initStates, states);
    }

    public void addFinalState(State... states) {
        addState(states);
        for (State state : states) {
            addTrans(state, state);
        }
    }

    public void addTrans(State src, State dst) {
        transitions.merge(src, Collections.singleton(dst), (oldValue, newValue) -> {
            HashSet<State> mergeV = new HashSet<>(oldValue);
            mergeV.addAll(newValue);
            return mergeV;
        });
    }


    public Set<State> getInitStates() {
        return new HashSet<>(initStates);
    }

    public Set<State> getAllSuccessorStates(State state) {
        return new HashSet<>(transitions.get(state));
    }



}
