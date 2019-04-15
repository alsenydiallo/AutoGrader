import org.junit.Test;
import wsuv.autolab.Score;

import wsuv.autolab.Category;
import wsuv.autolab.Score;

import static org.junit.Assert.*;

/**
 * This test should pass with 50% extra credit, 100% correctness
 *
 * */
public class MyClassBTests {

    @Category("Extra Credit")
    @Test
    public void checkExtraCredit(){
	MyClassB c = new MyClassB();
	assertTrue(c.extraCredit1());
	    
    }

    @Category("Extra Credit")
    @Test
    public void checkExtraCreditFails(){
        MyClassB c = new MyClassB();
        assertTrue(!c.extraCredit1());
    }

    @Test
    public void checkNormal() {
	MyClassB c = new MyClassB();
	assertTrue(c.normal());
    }
}
