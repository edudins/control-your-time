package lv.dudins.ControlYourTime.engine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static lv.dudins.ControlYourTime.literals.MessageTemplate.UPDATE;

public class ClockEngine {
    // Time control variables
    private boolean running;
    private boolean paused;
    private long startTime = 0;
    private long finalTime = 0;
    private long pausedTime = 0;

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
        if (!paused) {
            startTime = System.currentTimeMillis();
            running = true;
        } else {
            log.announce(UPDATE, "System is paused.");
        }
    }

    public void stop() {
        finalTime = getTimeMillis();
        running = false;
        paused = false;
        fileWriterEngine.updateFile(elapsedTimeString(finalTime));
        resetTimes();
    }

    public void pause() {
        // save pausedTime when paused
        if (!paused) {
            paused = true;
            running = false;
            pausedTime = System.currentTimeMillis();
        } else {
            paused = false;
            running = true;
            long pausedDelta = System.currentTimeMillis() - pausedTime;
            startTime = startTime + pausedDelta;
            pausedTime = 0;
        }
    }

    private Runnable displayTime() {
        return new Runnable() {
            public void run() {
                if (running) {
                    var time = elapsedTimeString(getTimeMillis());
                    log.announceAndSetStatusLabel(UPDATE, "Running ...");
                    log.setInfoLabel(time);
                }
            }
        };
    }

    private void resetTimes() {
        finalTime = 0;
        startTime = 0;
        pausedTime = 0;
    }

    public long getTimeMillis() {
        if (running) {
            return System.currentTimeMillis() - startTime;
        } else {
            return finalTime;
        }
    }

    private String elapsedTimeString(long elapsedTime) {
        long seconds = MILLISECONDS.toSeconds(elapsedTime);
        long minutes = MILLISECONDS.toMinutes(elapsedTime);
        long hours = MILLISECONDS.toHours(elapsedTime);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
