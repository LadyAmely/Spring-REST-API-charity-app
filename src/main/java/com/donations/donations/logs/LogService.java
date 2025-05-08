package com.donations.donations.logs;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;


@Service
public class LogService implements LoggerService{

    private static final Logger logger =  LoggerFactory.getLogger(LogService.class);

    @Override
    public void logInfo(String message) {
        logger.info(message);
    }

    @Override
    public void logWarn(String message) {
        logger.warn(message);
    }

    @Override
    public void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

}
