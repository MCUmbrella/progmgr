package vip.floatationdevice.progmgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.LinkedList;

public class DataManager
{
    private static final Logger l = LoggerFactory.getLogger(DataManager.class);
    private static LinkedList<ProgramData> data;

    static class ProgramDataContainer implements Serializable
    {
        public static final long serialVersionUID = 0L;
        public LinkedList<ProgramData> data;
    }
}
