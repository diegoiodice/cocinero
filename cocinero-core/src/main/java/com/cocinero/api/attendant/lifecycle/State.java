package com.cocinero.api.attendant.lifecycle;


import com.cocinero.domain.AttendantStatus;

import java.util.HashMap;
import java.util.Map;

public class State {

    private final AttendantStatus status;

    private Map<StateEvent,Transition> transitions = new HashMap<>();

    public State(AttendantStatus status) {
        this.status = status;
    }

    public void addTransition(StateEvent event, State nextState, Action action){
        transitions.put(event,new Transition(nextState,action));
    }

    public AttendantStatus getStatus() {
        return status;
    }

    public Map<StateEvent, Transition> getTransitions() {
        return transitions;
    }
}
