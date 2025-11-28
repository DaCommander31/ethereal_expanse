package dev.dacommander31.ethereal_expanse.util;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EETickingTaskManager {
    private static final Map<Runnable, Long> TASKS = new HashMap<>();

    public static void add(Runnable runnable, long ticks) {
        TASKS.put(runnable, ticks);
    }

    public static void tick() {
        Iterator<Map.Entry<Runnable, Long>> iterator = TASKS.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Runnable, Long> task = iterator.next();
            Runnable runnable = task.getKey();
            runnable.run();
            long ticksRemaining = task.getValue();
            if (ticksRemaining <= 0) {
                iterator.remove();
            }
            ticksRemaining--;
            task.setValue(ticksRemaining);
        }
    }
}
