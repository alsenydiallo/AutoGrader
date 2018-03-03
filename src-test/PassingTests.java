import org.junit.Test;
import wsuv.autolab.JUnitTester;


public class PassingTests {


    @Test
    public void testPublicTests() {
        String [] args = {"WarmOrColdTests"}; //, "WarmOrColdSecretTests"};
        JUnitTester.main(args);

    }


    @Test
    public void testPublicAndSecretTests() {
        String [] args = {"WarmOrColdTests", "WarmOrColdSecretTests"}; //, "WarmOrColdSecretTests"};
        JUnitTester.main(args);

    }

}
