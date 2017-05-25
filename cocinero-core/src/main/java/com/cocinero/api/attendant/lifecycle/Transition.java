package com.cocinero.api.attendant.lifecycle;


public class Transition {

    private final State nextState;

    private final Action action;

    public Transition(State nextState, Action action) {
        this.nextState = nextState;
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public State getNextState() {
        return nextState;
    }
}
