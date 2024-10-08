package com.test.utils;

import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportPortal {
    
    private static final Logger logger = LoggerFactory.getLogger(ReportPortal.class);

    public static void emitLog(String message, String level, Date time) {
        // Replace this with actual ReportPortal logging logic.
        logger.info("ReportPortal Log - Level: {}, Time: {}, Message: {}", level, time, message);
    }

    public static void emitLog(String message, String level, Date time, File attachment) {
        // Replace this with actual ReportPortal logging logic with attachment.
        logger.info("ReportPortal Log with Attachment - Level: {}, Time: {}, Message: {}, Attachment: {}",
                    level, time, message, attachment.getName());
    }
}
