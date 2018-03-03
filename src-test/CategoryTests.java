import org.junit.Test;
import wsuv.autolab.JUnitTester;

public class CategoryTests {

    @Test
    public void testPublicTests() {
        String [] args = {"WarmOrColdCategoryTests"}; //, "WarmOrColdSecretTests"};
        JUnitTester.main(args);
    }
}
