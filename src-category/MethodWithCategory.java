import wsuv.autolab.Category;
import wsuv.autolab.Score;

import java.lang.reflect.Method;

public class MethodWithCategory {

    @Category(name ="ExtraCredit1")
    @Score(score = 1)
    public void extraCredit1(){
    }

    @Category(name = "ExtraCredit2")
    @Score(score = 2)
    public void extraCredit2(){
    }

    Method getCategoryMethod(String name){
        try {
            MethodWithCategory m = new MethodWithCategory();
            Class c = MethodWithCategory.class;
            Method method = c.getMethod(name);
            return method;

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
