import org.junit.Test;
import wsuv.autolab.Score;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class CategoryTest {

    @Test
    public void getMethod(){
        MethodWithCategory m = new MethodWithCategory();
        Method myMethod = m.getCategoryMethod("extraCredit1");
        assertNotNull(myMethod);
    }

    @Test
    public void checkMethodCategoryAnnotationAndScore(){
        MethodWithCategory m = new MethodWithCategory();
        Method myMethod = m.getCategoryMethod("extraCredit1");
        Annotation annotation = myMethod.getAnnotation(Score.class);
        assertEquals(1.0, ((Score) annotation).score(), 0);
    }
}
