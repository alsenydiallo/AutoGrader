import org.junit.Test;
import wsuv.autolab.Score;

import wsuv.autolab.Category;
import wsuv.autolab.Score;

import static org.junit.Assert.*;

/**
 * This test should fail with an exception since we're referencing a 
 * category that is not defined in the config.
 *
 * */
public class MyClassBrokenATests {

    @Category("Extra Roodit")
    @Test
    public void checkExtraCredit(){
	MyClassBrokenA c = new MyClassBrokenA();
	assertTrue(c.extraCredit1());
	    
    }

    @Category("Extra Credit")
    @Test
    public void checkExtraCreditFails(){
        MyClassBrokenA c = new MyClassBrokenA();
        assertTrue(!c.extraCredit1());
    }

    @Test
    public void checkNormal() {
	MyClassBrokenA c = new MyClassBrokenA();
	assertTrue(c.normal());
    }
}
