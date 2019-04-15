import org.junit.Test;
import wsuv.autolab.Category;
import wsuv.autolab.Score;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Case 1: Explicitly declare category annotation and score annotation
 * This test should pass with full score.
 *
 */

public class YesCategoryAnnot_YesScoreAnnotTests {

    @Test
    @Category("Correctness")
    @Score(score = 50)
    public void correctness1_true(){
        YesCategoryAnnot_YesScoreAnnot c = new YesCategoryAnnot_YesScoreAnnot();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Correctness")
    @Score(score = 50)
    public void correctness1_false(){
        YesCategoryAnnot_YesScoreAnnot c = new YesCategoryAnnot_YesScoreAnnot();
        assertFalse(c.returnFalse());
    }

    @Test
    @Category("Extra Credit 1")
    @Score(score = 25)
    public void extraCredit1_true(){
        YesCategoryAnnot_YesScoreAnnot c = new YesCategoryAnnot_YesScoreAnnot();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Extra Credit 1")
    @Score(score = 25)
    public void extraCredit1_false(){
        YesCategoryAnnot_YesScoreAnnot c = new YesCategoryAnnot_YesScoreAnnot();
        assertFalse(c.returnFalse());
    }

    @Test
    @Category("Extra Credit 2")
    @Score(score = 25)
    public void extraCredit2(){
        YesCategoryAnnot_YesScoreAnnot c = new YesCategoryAnnot_YesScoreAnnot();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Extra Credit 3")
    @Score(score = 25)
    public void extraCredit3(){
        YesCategoryAnnot_YesScoreAnnot c = new YesCategoryAnnot_YesScoreAnnot();
        assertTrue(c.returnTrue());
    }
}
