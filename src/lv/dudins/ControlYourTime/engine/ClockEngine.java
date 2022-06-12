package lv.dudins.ControlYourTime.engine;

import lv.dudins.ControlYourTime.literals.MessageTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ClockEngine {
    // Time control variables
    private boolean running;
    private long startTime;

    // Objects
    protected LoggerEngine log;
    protected FileWriterEngine fileWriterEngine;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ClockEngine(LoggerEngine loggerEngine) {
        running = false;
        this.log = loggerEngine;
        this.fileWriterEngine = new FileWriterEngine(this.log);
        scheduler.scheduleAtFixedRate(displayTime(), 1, 1, SECONDS);
    }

    public void start() {
        running = true;
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        startTime = getTimeMillis();
        running = false;
        fileWriterEngine.updateFile(elapsedTimeString(startTime));
    }

    public void toggle() {
        // TODO: fix timer toggle
        if (running) {
            stop();
        } else {
            start();
        }
    }

    private Runnable displayTime() {
        return new Runnable() {
            public void run() {
                if (running) {
                    log.announceAndSetLabel(MessageTemplate.UPDATE.get(), elapsedTimeString(getTimeMillis()));
                }
            }
        };
    }

    public long getTimeMillis() {
        if (running) {
            return System.currentTimeMillis() - startTime;
        } else {
            return startTime;
        }
    }

    private String elapsedTimeString(long elapsedTime) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
        long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
