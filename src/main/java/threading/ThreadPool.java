package threading;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

public class ThreadPool {
    static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    static String callingClass = null;

    public static void execute(Runnable task) {
        String presentClass = new Throwable().getStackTrace()[1].getClassName();
        presentClass = "Thread " + Thread.currentThread().getId() + ": " + presentClass;
        if (callingClass == null) {
            callingClass = presentClass;
        } else {
            if (!callingClass.equals(presentClass)) {
                throw new ConcurrentModificationException("Double Call: " + callingClass + presentClass);
            }
        }
        executor.execute(task);
    }

    public static <T> void execute(Iterable<T> iterable, Consumer<T> task) {
        iterable.forEach(t -> execute(() -> task.accept(t)));
    }

    public static <T> void executeIteration(Iterable<T> iterable, Consumer<T> task) {
        execute(iterable, Collections::singleton, task);
    }

    public static <T> void execute(Iterable<T> iterable, Function<T, Iterable<T>> mapper, Consumer<T> task) {
        iterable.forEach(t -> execute(() -> mapper.apply(t).forEach(task)));
    }

    public static void finish() {
        executor.shutdown();
        try {
            executor.awaitTermination(100, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        callingClass = null;
    }
}