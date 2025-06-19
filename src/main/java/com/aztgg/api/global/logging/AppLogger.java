package com.aztgg.api.global.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.logstash.logback.marker.Markers.appendFields;

public class AppLogger {

    private static final Logger systemLogger = LoggerFactory.getLogger("SYSTEM_LOG");
    private static final Logger traceLogger = LoggerFactory.getLogger("TRACE_LOG");

    public static void infoLog(String msg) {
        systemLogger.info(msg);
    }

    public static void infoLog(String var1, Object... var2) {
        systemLogger.info(var1, var2);
    }

    public static void warnLog(String msg) {
        systemLogger.warn(msg);
    }

    public static void warnLog(String var1, Object... var2) {
        systemLogger.warn(var1, var2);
    }

    public static void debugLog(String msg) {
        systemLogger.debug(msg);
    }

    public static void debugLog(String var1, Object... var2) {
        systemLogger.debug(var1, var2);
    }

    public static void traceLog(String msg) {
        systemLogger.trace(msg);
    }

    public static void traceLog(String var1, Object... var2) {
        systemLogger.trace(var1, var2);
    }

    public static void traceLog(TraceLogDto traceLogDto) {
        traceLogger.trace(appendFields(traceLogDto), "trace logging");
    }

    public static void errorLog(String msg, Exception e) {
        systemLogger.error("{}", msg, e);
    }
}
