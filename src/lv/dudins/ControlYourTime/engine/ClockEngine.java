package lv.dudins.ControlYourTime.engine;

import lv.dudins.ControlYourTime.literals.MessageTemplate;

import java.util.Timer;
import java.util.TimerTask;

public class ClockEngine {
    // Time control variables
    private boolean running;
    private long startTime;

    // Objects
    LoggerEngine loggerEngine;
    FileWriterEngine fileWriterEngine = new FileWriterEngine();

    public ClockEngine(LoggerEngine loggerEngine) {
        running = false;
        this.loggerEngine = loggerEngine;
    }

    public void start() {
        running = true;
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        startTime = getTime();
        running = false;
        // write file
    }

    public void toggle() {
        if (running) {
            stop();
        } else {
            start();
        }
    }

    public long getTime() {
        if (running) {
            long time = System.currentTimeMillis() - startTime;
            loggerEngine.announceAndSetLabel(MessageTemplate.UPDATE.getTemplate(), Long.toString(time));
            return time;
        } else {
            loggerEngine.announceAndSetLabel(MessageTemplate.UPDATE.getTemplate(), Long.toString(startTime));
            return startTime;
        }
    }
}
