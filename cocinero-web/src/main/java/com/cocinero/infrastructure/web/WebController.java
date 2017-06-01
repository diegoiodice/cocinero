package com.cocinero.infrastructure.web;

import com.cocinero.infrastructure.web.message.FlashHandler;

public interface WebController {

    void setFlashHandler(FlashHandler flashHandler);
}
