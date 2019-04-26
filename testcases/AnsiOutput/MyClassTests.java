import org.junit.Test;
import wsuv.autolab.Score;

import wsuv.autolab.Category;
import wsuv.autolab.Score;

import static org.junit.Assert.*;

/**
 * This test should pass with full scores.
 */
public class MyClassTests {

    @Category("Extra Credit")
    @Test
    public void checkExtraCredit(){
	MyClass c = new MyClass();
	assertTrue(c.extraCredit1());
    }

    @Test
    public void checkNormal() {
	MyClass c = new MyClass();
	assertTrue(c.normal());
    }
}
