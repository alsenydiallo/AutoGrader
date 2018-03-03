import org.junit.Test;
import wsuv.autolab.JUnitTester;


/**
 * NOTE THESE TESTS ARE DESIGNED TO CHECK THE SCORING METHOD
 */
public class PartialPassingTests {


    @Test
    public void testPublicTests() {
        String [] args = {"WarmOrColdTestsBadSpecA"}; //, "WarmOrColdSecretTests"};
        JUnitTester.main(args);

    }


}
