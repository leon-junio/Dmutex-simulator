import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A DMutex simulation program that uses the DMutex class and the Process class
 * to simulate a distributed mutual exclusion algorithm.
 * Simulates a distributed mutual exclusion algorithm with a given number of
 * processes and a given execution time. The program will start the processes as
 * Runnable threads and will interrupt them after the given execution time.
 * 
 * @version 1.0.1
 * @author https://github.com/leon-junio
 */
public class Main {

    private static final int ARGS_COUNT = 2;

    public static void main(String[] args) {
        try {
            var argumentsParsed = parseArguments(args);
            int numProcessos = argumentsParsed.get("processNum");
            long execution_time = argumentsParsed.get("time"), startTime = System.currentTimeMillis();
            var dmutex = new DMutex(numProcessos);
            var threadGroup = new ThreadGroup("DmutexProcessGroup");
            List<Thread> threads = new ArrayList<>();
            var processes = new Process[numProcessos];
            System.out.println("Starting simulation to " + numProcessos + " processes.");
            System.out.println("Expected execution time: " + execution_time + "ms.");
            for (int i = 0; i < numProcessos; i++) {
                processes[i] = new Process(i, dmutex);
                var processThread = new Thread(threadGroup, processes[i]);
                processThread.start();
                threads.add(processThread);
            }
            System.out.println("All processes threads started.");
            try {
                Thread.sleep(execution_time);
                threadGroup.interrupt();
                System.out.println("All processes threads interrupted.");
            } catch (InterruptedException e) {
                System.err.println("Main thread was interrupted.");
                throw e;
            }
            System.out.println("Showing simulation results:");
            for (int i = 0; i < numProcessos; i++) {
                System.out.println("Process " + i + " executed " + processes[i].getTotalExecutedOperations()
                        + " operations at critical sections\nand waited a total of " + processes[i].getWaitTime()
                        + "ms to join at critical sections.");
            }
            System.out.println("Simulation Execution time: " + (System.currentTimeMillis() - startTime) + "ms.");
            dmutex.getCriticalSectionReport().forEach((time, id) -> {
                System.out.println(
                        "Process " + id + " entered critical section at moment: " + (time - startTime) + "ms.");
            });
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Parse the arguments passed to the program and return a map with the parsed
     * 
     * @param args Arguments passed to the program
     * @return Map with the parsed arguments
     */
    public static Map<String, Integer> parseArguments(String[] args) throws IllegalArgumentException {
        var argumentsParsed = new HashMap<String, Integer>();
        if (args.length != ARGS_COUNT) {
            help();
            throw new IllegalArgumentException("Invalid number of arguments. Expected 2 arguments.");
        }
        try {
            if (args[0].equals("help")) {
                help();
            }
            for (String arg : args) {
                var argSplit = arg.split("=");
                if (argSplit.length != 2) {
                    throw new IllegalArgumentException("Invalid argument format. Expected -<flag>=<value>.");
                }
                var flag = argSplit[0];
                var value = Integer.parseInt(argSplit[1]);
                if (flag.equals("-p")) {
                    argumentsParsed.put("processNum", value);
                } else if (flag.equals("-t")) {
                    argumentsParsed.put("time", value);
                } else {
                    throw new IllegalArgumentException("Invalid argument flag. Expected -p or -t.");
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid argument type. Expected integer.");
        }
        return argumentsParsed;
    }

    /**
     * Show the help message with the program usage
     */
    public static void help() {
        System.out.println("Usage: -p=<number of processes> -t=<execution time in ms>");
        System.out.println("Example: -p=5 -t=10000");
        System.out.println("This command will start a simulation with 5 processes and will run for 10 seconds.");
        System.out.println("Use the flag 'help' to show this message.");
        System.out.println("See the README.md file for more information.");
        System.exit(0);
    }
}