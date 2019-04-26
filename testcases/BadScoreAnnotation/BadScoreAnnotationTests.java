import org.junit.Test;
import wsuv.autolab.Category;
import wsuv.autolab.Score;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Case 8: Bad score annotation. negative score value for category should not be allowed
 * This test should fail.
 * Auto grader should throw an exception @exception("Illegal Score annotation value. Method cannot have negative score value")
 *
 *
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
