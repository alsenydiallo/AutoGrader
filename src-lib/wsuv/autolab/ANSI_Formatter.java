package wsuv.autolab;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.PrintStream;
import java.util.HashMap;

public class ANSI_Formatter implements Formatter{
    private PrintStream stream;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BRIGHT_BLACK = "\u001B[90m";
    private static final String BANNER = "-------------------------------------------------------------------------------";

    public ANSI_Formatter(final PrintStream stream){
        this.stream = stream;
    }

    public void printBanner(){
        stream.println(BANNER);
    }

    public void printHeader(final Description description){
        if (description.getAnnotation(Secret.class) == null && !description.getTestClass().isAnnotationPresent(Trace.class)) {
            stream.println(ANSI_GREEN + "Starting: " + ANSI_RESET + description.getMethodName());
        }
    }

    public void printFooter(final Description description){
        if (description.getAnnotation(Secret.class) == null && !description.getTestClass().isAnnotationPresent(Trace.class)) {
            stream.println(ANSI_GREEN + "Finished: " + ANSI_RESET + description.getMethodName());
            stream.println();
        }
    }

    public void printStat(final Result result, final HashMap<String, Double> pointObtainedByCategory, final HashMap<String, Double> categoriesMaxScore){
        double pointsObtained = 0.0;
        for(double v : pointObtainedByCategory.values()){
            pointsObtained += v;
        }
        double maxScore = 0.0;
        for(double v : categoriesMaxScore.values()) {
            maxScore += v;
        }
        int run = result.getRunCount();
        int passed = run - result.getFailureCount() - result.getIgnoreCount();
        int ignored = result.getIgnoreCount();
        int failed = result.getFailureCount();

        stream.println("Number of tests executed: " + run);
        stream.println("Number Passed/Failed/Ignored: " + passed + "/" + failed + "/" + ignored);
        String pointsStr = String.format("TOTAL Score is %.1f of %.1f", pointsObtained, maxScore);

        stream.println(ANSI_YELLOW + pointsStr + ANSI_RESET);
        stream.println(ANSI_YELLOW + pointObtainedByCategory.toString() + ANSI_RESET);
    }

    public void printScoreTable(final HashMap<String, HashMap<String, HashMap<String, Double>>> categoriesTestScores){
        stream.println(BANNER);
        for(String cls: categoriesTestScores.keySet()){
            stream.println("Class :" + cls);
            for(String category : categoriesTestScores.get(cls).keySet()){
                stream.println(category);
                stream.println(categoriesTestScores.get(cls).get(category));
            }
            stream.println();
        }
        stream.println(BANNER);
    }

    public void printString(final String str){
        stream.println(str);
    }

    public void printFailedTest(Failure failure, HashMap<String, HashMap<String, Description>> failedOrIgnoredTest){
        Description testMethod = failure.getDescription();
        Class testClass = testMethod.getTestClass();

        failedOrIgnoredTest.get(testClass.getCanonicalName()).put(failure.getDescription().getMethodName(), failure.getDescription());

        if (testClass.isAnnotationPresent(Secret.class) ||
                testMethod.getAnnotation(Secret.class) != null) {
                stream.println(ANSI_RED + failure.getException() + ANSI_RESET);
        } else {
            stream.println(ANSI_RED + "Failed: " + ANSI_RESET + failure.getDescription().getMethodName());
            if (testClass.isAnnotationPresent(Trace.class) || testMethod.getAnnotation(Trace.class) != null) {
                stream.println(ANSI_RED + failure.getException() + ANSI_RESET);
            }
            if (testClass.isAnnotationPresent(Hint.class)) {
                Hint failureClassHint = (Hint) testClass.getAnnotation(Hint.class);
                stream.println(ANSI_CYAN + "Hint: " + ANSI_RESET + failureClassHint.hint());
            }
            else if (testMethod.getAnnotation(Hint.class) != null) {
                Hint failureMethodHint = testMethod.getAnnotation(Hint.class);
                stream.println(ANSI_CYAN + "Here's a hint: " + ANSI_RESET + failureMethodHint.hint());
            } else {
                stream.println(ANSI_CYAN + "Hint: " + ANSI_RESET + failure.getException().getMessage());
            }
        }
    }
}

