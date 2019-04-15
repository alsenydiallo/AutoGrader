package wsuv.autolab;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

public class JUnitTester extends JUnitCore {

    RunNotifier mynotifier = new RunNotifier();

    public Result run(Runner runner) {
        Result result = new Result();
        RunListener listener = result.createListener();
        mynotifier.addFirstListener(listener);
        try {
            mynotifier.fireTestRunStarted(runner.getDescription());
            runner.run(mynotifier);
            mynotifier.fireTestRunFinished(result);
        } finally {
            /*
            System.out.println("Failures:");
            for(Failure formatter : result.getFailures()) {
                System.out.println(formatter.getDescription() + ": " + formatter.getMessage());
            }
            System.out.println("-------");
            */

            for(Failure f : result.getFailures()) {
                if (f.getException().getClass() == TestScoringException.class) {
                    throw (TestScoringException)f.getException();
                }
            }
            removeListener(listener);
        }
        return result;
    }

    /**
     * Add a listener to be notified as the tests run.
     *
     * @param listener the listener to add
     * @see org.junit.runner.notification.RunListener
     */

    public void addListener(RunListener listener) {
        mynotifier.addListener(listener);
    }

    /**
     * Remove a listener.
     *
     * @param listener the listener to remove
     */
    public void removeListener(RunListener listener) {
        mynotifier.removeListener(listener);
    }

    /**
     *
     *  -c <ConfigFile>
     * @param args
     */
    public static void main(String[] args)  {

        String configFile = "wsuconfig-json.config";
        int clsStartIndex = 0;
        if (args.length >= 1 && args[0].startsWith("-c")) {
            if (args[0].equals("-c")) {
                configFile = args[1];
                clsStartIndex = 2;
            } else {
                configFile = args[0].substring(2);
                clsStartIndex = 1;
            }
        }
        JUnitTester core = new JUnitTester();
        WeightedScoreListener cL = null;

        cL = new WeightedScoreListener(configFile);


        core.addListener(cL);
        Class[] clz = new Class[args.length - clsStartIndex];

        int i = 0;
        try {
            for (i = clsStartIndex; i < args.length; i++) {
                clz[i-clsStartIndex] = Class.forName(args[i]);
            }

            core.run(clz);


        } catch (ClassNotFoundException cnf) {
            System.out.println("SCORING ERROR: Could not find test: " + args[i]);
        }
    }
}
