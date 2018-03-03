package wsuv.autolab;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.HashMap;

public interface Formatter {
    void printString(final String str);
    void printFooter(final Description description);
    void printHeader(final Description description);
    void printBanner();

    void printScoreTable(final HashMap<String, HashMap<String, HashMap<String, Double>>> categoriesTestScores);
    void printFailedTest(Failure failure, HashMap<String, HashMap<String, Description>> failedOrIgnoredTest);
    void printStat(final Result result, final HashMap<String, Double> pointObtainedByCategory, final HashMap<String, Double> categoriesMaxScore);

}
