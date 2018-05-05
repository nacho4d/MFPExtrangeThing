package com.ibm.jp.questionnaire.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtil {

    /**
     * ロガーを生成する。
     * @param name ロガー名
     * @return ロガー
     */
    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.addHandler(new ConsoleHandler() {
            {
                setOutputStream(System.out);
                setLevel(Level.ALL);
            }
        });
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        return logger;
    }

}
