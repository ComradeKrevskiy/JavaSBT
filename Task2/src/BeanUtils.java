import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanUtils{
    public static void assign(Object to, Object from){
        Method[] methodsF = from.getClass().getDeclaredMethods();

        for (Method mf : methodsF){
            if(mf.getName().startsWith("get")) {
                try {
                    String setter = "set" + mf.getName().substring(3);
                    try {
                        to.getClass().getDeclaredMethod(setter).invoke(to, mf.invoke(from));
                    } catch (IllegalAccessException | InvocationTargetException e) {}
                } catch (NoSuchMethodException ignore){}
            }
        }
    }
}