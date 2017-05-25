package com.cocinero.api.attendant.lifecycle;


import com.cocinero.domain.Attendant;

import java.util.LinkedList;
import java.util.List;

public class FSM {

    private List<State> sates = new LinkedList<>();

    public void onEvent(final Attendant attendant,final StateEvent event){
        State state = this.sates.stream()
                .filter(s -> s.getStatus().equals(attendant.getStatus()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Invalid attendant state" + attendant.getStatus()));
        Transition transition = state.getTransitions().getOrDefault(event, new Transition(null, context -> {
            throw new IllegalStateException("Invalid transition");
        }));
        attendant.getEvent().handleAttendantAction(context-> transition.getAction().execute(context),attendant);
    }

    public List<State> getSates() {
        return sates;
    }
}
