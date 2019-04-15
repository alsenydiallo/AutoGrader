import org.junit.Test;
import wsuv.autolab.Category;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Case 2: Explicitly declare category annotation
 * No score annotation declared
 * This test should pass with full score.
 * Auto grader should distribute points to each method test with respect to the
 * category max score in the config file
 *
 */

public class YesCategoryAnnot_NoScoreAnnotTests {

    @Test
    @Category("Correctness")
    public void correctness1_true(){
        YesCategoryAnnot_NoScoreAnnot c = new YesCategoryAnnot_NoScoreAnnot();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Correctness")
    public void correctness1_false(){
        YesCategoryAnnot_NoScoreAnnot c = new YesCategoryAnnot_NoScoreAnnot();
        assertFalse(c.returnFalse());
    }

    @Test
    @Category("Extra Credit 1")
    public void extraCredit1_true(){
        YesCategoryAnnot_NoScoreAnnot c = new YesCategoryAnnot_NoScoreAnnot();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Extra Credit 1")
    public void extraCredit1_false(){
        YesCategoryAnnot_NoScoreAnnot c = new YesCategoryAnnot_NoScoreAnnot();
        assertFalse(c.returnFalse());
    }

    @Test
    @Category("Extra Credit 2")
    public void extraCredit2(){
        YesCategoryAnnot_NoScoreAnnot c = new YesCategoryAnnot_NoScoreAnnot();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Extra Credit 3")
    public void extraCredit3(){
        YesCategoryAnnot_NoScoreAnnot c = new YesCategoryAnnot_NoScoreAnnot();
        assertTrue(c.returnTrue());
    }
}
