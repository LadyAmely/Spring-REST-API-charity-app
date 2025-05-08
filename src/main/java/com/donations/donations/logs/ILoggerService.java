package com.donations.donations.logs;

public interface ILoggerService {

    public void logInfo(String message);
    public void logWarn(String message);
    public void logError(String message, Throwable throwable);
}
