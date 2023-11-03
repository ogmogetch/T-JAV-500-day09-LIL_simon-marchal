import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Inspector<T> {
    private final Class<T> inspectedClass;

    public Inspector(Class<T> inspectedClass) {
        this.inspectedClass = inspectedClass;
    }

    public void displayInformations() {
        System.out.println("Information of the \"" + inspectedClass.getName() + "\" class:");
        System.out.println("Superclass: " + inspectedClass.getSuperclass().getName());

        Method[] declaredMethods = inspectedClass.getDeclaredMethods();
        Field[] declaredFields = inspectedClass.getDeclaredFields();

        System.out.println(declaredMethods.length + " methods:");
        for (Method method : declaredMethods) {
            if (Modifier.isPublic(method.getModifiers())) {
                System.out.println("- " + method.getName());
            }
        }

        System.out.println(declaredFields.length + " fields:");
        for (Field field : declaredFields) {
            System.out.println("- " + field.getName());
        }
    }

    public T createInstance() throws Exception {
        try {
            return inspectedClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void main(String[] args) {
        Inspector<String> inspector = new Inspector<>(String.class);
        inspector.displayInformations();
    }
}
