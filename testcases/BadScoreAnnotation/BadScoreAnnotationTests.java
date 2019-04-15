import org.junit.Test;
import wsuv.autolab.Category;
import wsuv.autolab.Score;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Case 8: Bad score annotation. negative score value for category
 * This test should fail.
 * Auto grader should throw an exception @exception("Illegal score annotation, Negative score value for method")
 *
 *
 * current behavior: Behavior is silent for higher score than config file. Total grad is auto balanced
 *                  (due to auto grader only been aware of the method score after it fails)
 */

public class BadScoreAnnotationTests {
    @Test
    @Category("Correctness")
    @Score(score = 50)
    public void correctness1_true(){
        BadScoreAnnotation c = new BadScoreAnnotation();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Correctness")
    @Score(score = 50)
    public void correctness1_false(){
        BadScoreAnnotation c = new BadScoreAnnotation();
        assertFalse(c.returnFalse());
    }

    @Test
    @Category("Extra Credit 1")
    @Score(score = 25)
    public void extraCredit1_true(){
        BadScoreAnnotation c = new BadScoreAnnotation();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Extra Credit 1")
    @Score(score = 25)
    public void extraCredit1_false(){
        BadScoreAnnotation c = new BadScoreAnnotation();
        assertFalse(c.returnFalse());
    }

    @Test
    @Category("Extra Credit 2")
    @Score(score = 25)
    public void extraCredit2(){
        BadScoreAnnotation c = new BadScoreAnnotation();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Extra Credit 3")
    @Score(score = -25)
    public void extraCredit3(){
        BadScoreAnnotation c = new BadScoreAnnotation();
        assertTrue(c.returnTrue());
    }
}
