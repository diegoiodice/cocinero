package com.cocinero.infrastructure.web.controller;

import com.cocinero.infrastructure.web.notification.FlashHandler;

public interface WebController {

    void setFlashHandler(FlashHandler flashHandler);
}
