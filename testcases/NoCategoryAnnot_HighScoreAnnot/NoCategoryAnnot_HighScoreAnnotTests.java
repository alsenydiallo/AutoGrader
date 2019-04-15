import org.junit.Test;
import wsuv.autolab.Score;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Case 6: No category annotations. Yes score annotations with higher value than config file
 * This test should fail.
 * Auto grader should throw an @exception("")
 *
 *
 * Current behavior, Auto grader Behavior is silent for higher score than config file. Total grad is auto balanced
 * (due to auto grader only been aware of the method score after it fails)
 */

public class NoCategoryAnnot_HighScoreAnnotTests {

    @Test
    @Score(score = 60)
    public void correctness1_true(){
        NoCategoryAnnot_HighScoreAnnot c = new NoCategoryAnnot_HighScoreAnnot();
        assertTrue(c.returnTrue());
    }

    @Test
    @Score(score = 70)
    public void correctness1_false(){
        NoCategoryAnnot_HighScoreAnnot c = new NoCategoryAnnot_HighScoreAnnot();
        assertFalse(c.returnFalse());
    }

}
