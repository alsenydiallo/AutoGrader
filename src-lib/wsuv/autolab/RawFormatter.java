package wsuv.autolab;

import com.google.gson.Gson;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

import java.io.PrintStream;
import java.util.HashMap;

public class RawFormatter implements Formatter {
    private PrintStream stream;

    private static final String BANNER = "-------------------------------------------------------------------------------";

    public RawFormatter() {
        this(System.out);
    }
    public RawFormatter(final PrintStream stream){
        this.stream = stream;
    }

    @Override
    public void testStarted(final Description description){
        if (description.getAnnotation(Secret.class) == null && !description.getTestClass().isAnnotationPresent(Trace.class)) {
            stream.println("Starting: " + description.getMethodName());
        }
    }

    @Override
    public void testFinished(final Description description){
        if (description.getAnnotation(Secret.class) == null && !description.getTestClass().isAnnotationPresent(Trace.class)) {
            stream.println("Finished: " + description.getMethodName() + "\n");
        }
    }

    @Override
    public void testFailed(Failure failure, HashMap<String, HashMap<String, Description>> failedOrIgnoredTest){
        Description testMethod = failure.getDescription();
        Class testClass = testMethod.getTestClass();

        failedOrIgnoredTest.get(testClass.getCanonicalName()).put(failure.getDescription().getMethodName(), failure.getDescription());

        if (testClass.isAnnotationPresent(Secret.class) ||
                testMethod.getAnnotation(Secret.class) != null) {
            stream.println(failure.getException());
        } else {
            stream.println("Failed: " + failure.getDescription().getMethodName());
            if (testClass.isAnnotationPresent(Trace.class) || testMethod.getAnnotation(Trace.class) != null) {
                stream.println(failure.getException());
            }
            if (testClass.isAnnotationPresent(Hint.class)) {
                Hint failureClassHint = (Hint) testClass.getAnnotation(Hint.class);
                stream.println("Hint: " + failureClassHint.hint());
            }
            else if (testMethod.getAnnotation(Hint.class) != null) {
                Hint failureMethodHint = testMethod.getAnnotation(Hint.class);
                stream.println("Here's a hint: " + failureMethodHint.hint());
            } else {
                stream.println("Hint: " + failure.getException().getMessage());
            }
        }
    }

    @Override
    public void testRunStarted(final String msg) {
        stream.println(msg);
        stream.println(BANNER);
    }

    @Override
    public void testRunFinished(final String msg) {
        stream.println(BANNER);
    }

    @Override
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

    @Override
    public void printString(final String str){
        stream.println(str);
    }

    @Override
    public void printResults(final int testsCount, final int testsPassed, final int testsFailed, final int teststIgnored,
                             final double pointsObtained, final double maxScore, final HashMap<String, Double> pointObtainedByCategory)
    {
        stream.println("Number of tests executed: " + testsCount);
        stream.println("Number Passed/Failed/Ignored: " + testsPassed + "/" + testsFailed + "/" + teststIgnored);
        stream.println("Maximum points possible: " + maxScore);
        stream.println("Total points obtained: " + pointsObtained);

        Gson gson = new Gson();
        String json = gson.toJson(pointObtainedByCategory);
        stream.println(json);
    }

}

