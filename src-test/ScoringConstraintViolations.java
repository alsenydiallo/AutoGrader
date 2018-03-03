import org.junit.Test;
import wsuv.autolab.JUnitTester;
import wsuv.autolab.TestScoringException;


public class ScoringConstraintViolations {

    @Test(expected = TestScoringException.class)
    public void testOverAllocated() {
        String [] args = {"WarmOrColdBrokenTestsA"};
        JUnitTester.main(args);

    }

    @Test(expected = TestScoringException.class)
    public void testOverAllocatedB() {
        String [] args = {"WarmOrColdBrokenTestsB"};
        JUnitTester.main(args);

    }

    @Test(expected = TestScoringException.class)
    public void testOverAllocatedC() {
        String [] args = {"-cten.config", "WarmOrColdTestsScoringA"};
        JUnitTester.main(args);

    }
    @Test
    public void testUnderAllocated() {
        String [] args = {"-csixty.config", "WarmOrColdTestsScoringA"};
        JUnitTester.main(args);

    }
    @Test
    public void testFullySpecifiedScoreAtMethodLevel() {
        String [] args = {"-cnonexistant.config", "WarmOrColdTestsScoringB"};
        JUnitTester.main(args);

    }

}
