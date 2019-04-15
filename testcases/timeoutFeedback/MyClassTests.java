import org.junit.Test;
import wsuv.autolab.Score;

import wsuv.autolab.Category;
import wsuv.autolab.Score;


import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.Timeout;
/**
 * This test should pass with full scores.
 */
public class MyClassTests {

    @Rule
    public Timeout globalTimeout= new Timeout(20);

    @Category("Extra Credit")
    //@Test(timeout=10)
    @Test
    public void checkExtraCredit(){
	MyClass c = new MyClass();
	try {
	    Thread.sleep(1000);
        } catch (Exception e) {}

	assertTrue(c.extraCredit1());
    }

    @Test
    public void checkNormal() {
	MyClass c = new MyClass();
	assertTrue(c.normal());
    }
}
