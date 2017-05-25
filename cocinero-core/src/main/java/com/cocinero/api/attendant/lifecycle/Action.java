package com.cocinero.api.attendant.lifecycle;


public interface Action {

    void execute(Context context) throws RetryException;
}
