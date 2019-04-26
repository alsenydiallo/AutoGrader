package wsuv.autolab;

import com.google.gson.*;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;



public class WeightedScoreListener extends RunListener {

    private HashMap<String, Double> classScores; // maximum points a student can obtain from each class
    private HashMap<String, HashMap<String, HashMap<String, Double>>> categoriesTestScores; // hold each method category and it scores
    private HashMap<String, Double> categoriesMaxScore; // The maximum amount of point a student can obtain from each category
    private HashMap<String, HashMap<String, Description>> failedOrIgnoredTest;
    private HashMap<String, Double> pointObtainedByCategory;

    private boolean debug;
    private boolean implicitMethodScores;  // This variable is True in a presence a method without a @score annotation
    private int testCount;  // the number of test ran
    private String style;

    private double maxScore;// The maximum amount of point a student can obtain from the entire project

    Formatter formatter;

    public WeightedScoreListener(String configFile) {
        classScores = new HashMap<>(100);
        maxScore = 0;
        implicitMethodScores = false;
        debug = false;
        testCount = 0;

        this.categoriesMaxScore = new HashMap<>(100);
        this.categoriesTestScores = new HashMap<>(10);
        this.failedOrIgnoredTest = new HashMap<>(10);
        this.pointObtainedByCategory = new HashMap<>(10);

        readJsonConfigFile(configFile);
    }

    public HashMap<String, Double> getCategoriesMaxScore() {
        return categoriesMaxScore;
    }

    public HashMap<String, Double> returnStudentTotalScore() {
        return pointObtainedByCategory;
    }

    public HashMap<String, HashMap<String, HashMap<String, Double>>> getMethodScores() {
        return categoriesTestScores;
    }

    private void readJsonConfigFile(String configFile){
        try{
            System.err.println("Opening config file: " + configFile);
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
            Reader reader = new InputStreamReader(Objects.requireNonNull(inputStream));

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(reader);
            JsonObject jsonObject = element.getAsJsonObject();

            this.debug = !(jsonObject.get("debug").getAsInt() == 0);
            String nm = "RawFormatter";
            if (jsonObject.get("formatter") != null) {
                nm = jsonObject.get("formatter").getAsString();
            }

            try {
                Class cls = Class.forName("wsuv.autolab." + nm);
                this.formatter = (Formatter) cls.getDeclaredConstructor().newInstance();
            } catch (Exception ie) {
                System.err.println(ie);
                throw new IllegalArgumentException("Couldn't set the formatter to '" + nm + "'");
            }

            JsonObject categories = jsonObject.get("scores").getAsJsonObject();
            for (String category : categories.keySet()) {
                double maxScore = categories.get(category).getAsFloat();
                this.categoriesMaxScore.put(category, maxScore);
                this.pointObtainedByCategory.put(category, maxScore);
                this.maxScore += maxScore;
            }
        } catch (JsonSyntaxException e){
            throw new JsonSyntaxException("Bad syntax in config file " + e.getMessage());
        } catch (JsonParseException e){
            throw new JsonParseException("Bad config file " + e.getMessage());
        }

    }

    /**
     *   This method scans the test files
     *   It adds to the variable @categoriesTestScores and @testsFailedOrIgnored each scanned method
     *   in the form [testclassname, [Method name -> score value]]
     *   Throws an exception if the method category isn't declared in the config file
     *   Also, throws an exception if class contains an annotation
     */
    public void testStarted(Description description) {
        // if we don't already know the class score, try to obtain it, or mark it as NAN
        String testclass = description.getTestClass().getCanonicalName();
        if (!classScores.containsKey(testclass)) {
            if (description.getTestClass().isAnnotationPresent(Score.class) ||
                    description.getTestClass().isAnnotationPresent(Category.class)) {
                throw new TestScoringException("Illegal Annotation, Class should not have an annotation");
            }
            classScores.put(testclass, Double.NaN);
            categoriesTestScores.put(testclass, new HashMap<>(30));
            failedOrIgnoredTest.put(testclass, new HashMap<>(30));
        }

        // look for the test/method score and category annotation
        String testName = description.getMethodName();
        Category category = description.getAnnotation(Category.class);
        if(category != null && categoriesMaxScore.containsKey(category.value())){
            String name = category.value();
            assignMethodToCategoryHashMap(description, testclass, testName, name);
        }else if(category == null){
            String name = Category.DEFAULT;
            assignMethodToCategoryHashMap(description, testclass, testName, name);
        }else{
            // this happens if the category annotated for the method isn't declared at the project config file
            throw new TestScoringException("Illegal Annotation, Category not declared in project config file");
        }
        formatter.testStarted(description);
    }

    private void assignMethodToCategoryHashMap(Description description, String testclass, String testName, String name) {
        if(!categoriesTestScores.get(testclass).containsKey(name)){
            categoriesTestScores.get(testclass).put(name, new HashMap<>(10));
        }

        if(description.getAnnotation(Score.class) != null){
            double score = description.getAnnotation(Score.class).score();
            if(score <= 0){
                throw new TestScoringException("Illegal Score annotation value. Method cannot have negative score value");
            }
            categoriesTestScores.get(testclass).get(name).put(testName, score);
        }else{
            categoriesTestScores.get(testclass).get(name).put(testName, Double.NaN);
            implicitMethodScores = true;
        }
    }

    public void testRunStarted(Description description) throws Exception {
        testCount = description.testCount();
        formatter.testRunStarted("Number of tests to execute: " + testCount);
    }


    public void testRunFinished(Result result) throws Exception{
        // if a test was run that had no score annotation, we need to compute some scores...
        if (implicitMethodScores) computeMissingScores();

        double pointsObtained = maxScore;

        // subtract points for failed/ignored tests
        for (String cls : failedOrIgnoredTest.keySet()) {
            for (Entry<String, Description> test : failedOrIgnoredTest.get(cls).entrySet()) {
                Description description = test.getValue();
                if((description.getAnnotation(Category.class) != null)){
                    String name = description.getAnnotation(Category.class).value();
                    pointsObtained = updatePointsObtained(pointsObtained, cls, test, description, name);
                }else{
                    String name = Category.DEFAULT;
                    pointsObtained = updatePointsObtained(pointsObtained, cls, test, description, name);
                }
            }
        }
        formatter.testRunFinished("");
        if(debug) formatter.printScoreTable(categoriesTestScores);

        int testsCount = result.getRunCount();
        int testsPassed = testsCount - result.getFailureCount() - result.getIgnoreCount();
        int teststIgnored = result.getIgnoreCount();
        int testsFailed = result.getFailureCount();


        formatter.printResults(testsCount, testsPassed, testsFailed, teststIgnored,
                        pointsObtained, maxScore, pointObtainedByCategory);
    }

    private double updatePointsObtained(double pointsObtained, String cls, Entry<String, Description> test, Description description, String name) {
        Double point = categoriesTestScores.get(cls).get(name).get(description.getMethodName());
        if (debug) {
            formatter.printString(" -- Subtracting " + point + " for " + cls + "." + test);
        }
        pointsObtained -= point;
        pointObtainedByCategory.replace(name, pointObtainedByCategory.get(name) - point);
        return pointsObtained;
    }


    private TupleIntDbl countUnallocatedTest(HashMap<String, Double> tstscrs) {
        double allocated = 0;
        int wildcard = 0;
        for (Entry<String, Double> tstscr : tstscrs.entrySet()) {
            if (Double.isNaN(tstscr.getValue())) {
                wildcard += 1;
            }
            else {
                allocated += tstscr.getValue();
            }
        }
        return new TupleIntDbl(wildcard, allocated);
    }


    private void computeMissingScores(){
        // first pass, determine how many points are explicitly allocated.
        double allocated_score = 0; // stores allocated points (Score at class level, or if unavailable, at method level)
        int nbr_method_with_no_score = 0; // # of methods with no @Score at class or method level

        for (String cls: classScores.keySet()){
            Double classScore = classScores.get(cls);
            if(Double.isNaN(classScore)){
                // class level score is unknown, accumulate method level scores for each annotation
                TupleIntDbl tid;
                for(String category: categoriesTestScores.get(cls).keySet()){
                    int nbr_method_with_no_score_for_category = 0;
                    double allocated_score_for_category = 0;
                    HashMap<String, Double> category_tests = categoriesTestScores.get(cls).get(category);
                    tid = countUnallocatedTest(category_tests);

                    // track unallocated test for the entire assigment
                    nbr_method_with_no_score += tid.i;
                    allocated_score += tid.d;

                    nbr_method_with_no_score_for_category += tid.i;
                    allocated_score_for_category += tid.d;

                    allocateScore(allocated_score_for_category, nbr_method_with_no_score_for_category, category_tests, categoriesMaxScore.get(category));
                }
            }
            else{
                // class level score is known, so add that to allocated sum
                allocated_score += classScore;
                TupleIntDbl tid;
                for(String category: categoriesTestScores.get(cls).keySet()){
                    int nbr_method_with_no_score_for_category = 0;
                    double allocated_score_for_category = 0;
                    HashMap<String, Double> category_tests = categoriesTestScores.get(cls).get(category);
                    tid = countUnallocatedTest(category_tests);

                    nbr_method_with_no_score_for_category += tid.i;
                    allocated_score_for_category += tid.d;

                    allocateScore(allocated_score_for_category, nbr_method_with_no_score_for_category, category_tests, categoriesMaxScore.get(category));
                }
            }
        }
        if (allocated_score > this.maxScore) {
            String msg = "SCORING ERROR: Total Score is set to " + this.maxScore + " but more points are allocated with @Score";
            System.err.println(msg);
            throw new TestScoringException(msg);
        }
        if (allocated_score == this.maxScore && nbr_method_with_no_score > 0) {
            String msg = "SCORING ERROR: There are no points to distribute to tests without a @Score annotation";
            System.err.println(msg);
            throw new TestScoringException(msg);
        }
        if (allocated_score < this.maxScore && nbr_method_with_no_score == 0) {
            String msg = "SCORING ERROR: Some points need to be distributed, but all tests have a @Score annotation";
            System.err.println(msg);
            throw new TestScoringException(msg);
        }

        try{
            checkScoreCorrectnessForEachCategory();
        }catch (TestScoringException e){
            throw new TestScoringException(e.getMessage());
        }

        implicitMethodScores = false;
    }

    // Distribute score to the methods of this class
    //TODO: keep allocation for each category bellow max category allowance
    private void allocateScore(double scoreToAllocate, int nbr_method_with_no_score, HashMap<String, Double> category_tests, double max_score) {
        double scoreAvailableForAllocation = (max_score - scoreToAllocate)/nbr_method_with_no_score;
        if(!Double.isNaN(scoreAvailableForAllocation)){
            for (String test : category_tests.keySet()) {
                double score = category_tests.get(test);
                if(Double.isNaN(score)){
                    category_tests.replace(test, scoreAvailableForAllocation);
                }
            }
        }
    }

    private void checkScoreCorrectnessForEachCategory() {
        // check score correctness for each annotation
        for(String cls : classScores.keySet()){
            for(String category: categoriesTestScores.get(cls).keySet()){
                TupleIntDbl tid = countUnallocatedTest(categoriesTestScores.get(cls).get(category));

                double allocated = tid.d;
                int nbr_method = tid.i;

                if(allocated > categoriesMaxScore.get(category)){
                    String msg = "SCORING ERROR in " + category + " : Total Score is set to " + categoriesMaxScore.get(category) + " but more points are allocated with @Score";
                    System.err.println(msg);
                    throw new TestScoringException(msg);
                }

                if(allocated == categoriesMaxScore.get(category) && nbr_method > 0){
                    String msg = "SCORING ERROR in " + category + ": There are no points to distribute to tests without a @Score annotation";
                    System.err.println(msg);
                    throw new TestScoringException(msg);
                }

                if(allocated < categoriesMaxScore.get(category) && nbr_method == 0){
                    String msg = "SCORING ERROR in " + category + ": Some points need to be distributed, but all tests have a @Score annotation";
                    System.err.println(msg);
                    throw new TestScoringException(msg);
                }
            }
        }
    }


    public void testFinished(Description description) throws Exception {
        formatter.testFinished(description);
    }

    public void testFailure(Failure failure) throws Exception {
        formatter.testFailed(failure, failedOrIgnoredTest);
    }

    public void testAssumptionFailure(Failure failure) {
        Description testMethod = failure.getDescription();
        Class testClass = testMethod.getTestClass();
        failedOrIgnoredTest.get(testClass.getCanonicalName()).put(failure.getDescription().getMethodName(), failure.getDescription());
        formatter.printString("Failed: " + failure.getDescription().getMethodName());
    }

    public void testIgnored(Description description) throws Exception {
        Class testClass = description.getTestClass();
        failedOrIgnoredTest.get(testClass.getCanonicalName()).put(description.getMethodName(), description);
        formatter.printString("Ignored: " + description.getMethodName());
    }


    /**
     * Container class for a tuple of (int, double)
     */
    private static class TupleIntDbl {
        private int i;    // stores a count
        private double d; // stores a sum

        private TupleIntDbl(int i, double d) {
            this.i = i;
            this.d = d;
        }
    }


}

