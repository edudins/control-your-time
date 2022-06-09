package lv.dudins.ControlYourTime.engine;

import lv.dudins.ControlYourTime.literals.MessageTemplate;

public class ClockEngine {
    // Time control variables
    private boolean running;
    private long startTime;

    // Objects
    protected LoggerEngine log;
    protected FileWriterEngine fileWriterEngine;

    public ClockEngine(LoggerEngine loggerEngine) {
        running = false;
        this.log = loggerEngine;
        this.fileWriterEngine = new FileWriterEngine(this.log);
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
            log.announceAndSetLabel(MessageTemplate.UPDATE.get(), Long.toString(time));
            return time;
        } else {
            log.announceAndSetLabel(MessageTemplate.UPDATE.get(), Long.toString(startTime));
            return startTime;
        }
    }
}
