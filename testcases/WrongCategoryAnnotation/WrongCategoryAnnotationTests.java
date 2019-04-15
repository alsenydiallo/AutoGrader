import org.junit.Test;
import wsuv.autolab.Category;
import wsuv.autolab.Score;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Case 3: Wrong category annotations. One of the category annotation is miss spelled
 * This test should fail.
 * and throw an @exception("Illegal Annotation, Category not declared in project config file")
 *
 */

public class WrongCategoryAnnotationTests {

    @Test
    @Category("correctness")
    @Score(score = 50)
    public void correctness1_true(){
        WrongCategoryAnnotation c = new WrongCategoryAnnotation();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Correctness")
    @Score(score = 50)
    public void correctness1_false(){
        WrongCategoryAnnotation c = new WrongCategoryAnnotation();
        assertFalse(c.returnFalse());
    }

    @Test
    @Category("Extra Credit 1")
    @Score(score = 25)
    public void extraCredit1_true(){
        WrongCategoryAnnotation c = new WrongCategoryAnnotation();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Extra Credit 1")
    @Score(score = 25)
    public void extraCredit1_false(){
        WrongCategoryAnnotation c = new WrongCategoryAnnotation();
        assertFalse(c.returnFalse());
    }

    @Test
    @Category("Extra Credit 2")
    @Score(score = 25)
    public void extraCredit2(){
        WrongCategoryAnnotation c = new WrongCategoryAnnotation();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Wrong category")
    @Score(score = 25)
    public void extraCredit3(){
        WrongCategoryAnnotation c = new WrongCategoryAnnotation();
        assertTrue(c.returnTrue());
    }
}
