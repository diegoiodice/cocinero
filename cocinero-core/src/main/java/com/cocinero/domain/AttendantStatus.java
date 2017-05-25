package com.cocinero.domain;

import java.util.List;

public enum AttendantStatus {//implements Strategy{

    JOINED,UNCONFIRMED,CONFIRMED,CANCELLED,WAITING
}

interface Strategy {

    Attendant execute(Attendant attendant, Event event, List<Attendant> attendants);
}
