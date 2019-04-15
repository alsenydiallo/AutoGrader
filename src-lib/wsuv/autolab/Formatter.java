package wsuv.autolab;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

import java.util.HashMap;

public interface Formatter {
    void testFinished(final Description description);
    void testStarted(final Description description);
    void testFailed(Failure failure, HashMap<String, HashMap<String, Description>> failedOrIgnoredTest);
    void testRunStarted(final String str);
    void testRunFinished(final String str);

    void printScoreTable(final HashMap<String, HashMap<String, HashMap<String, Double>>> categoriesTestScores);
    void printString(final String str);

    void printResults(final int testsCount, final int testsPassed, final int testsFailed, final int teststIgnored,
                      final double pointsObtained, final double maxScore, final HashMap<String, Double> pointObtainedByCategory);
}
