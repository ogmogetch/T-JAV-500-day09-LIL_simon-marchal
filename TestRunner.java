import java.lang.reflect.Method;

public class TestRunner {
    public static void runTests(Class<?> testClass) {
        try {
            Object testObject = testClass.getDeclaredConstructor().newInstance();

            Method beforeClassMethod = getMethodAnnotatedWith(testClass, BeforeClass.class);
            if (beforeClassMethod != null) {
                beforeClassMethod.invoke(testObject);
            }

            for (Method method : testClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Test.class) && method.getAnnotation(Test.class).enabled()) {
                    executeTestWithAnnotations(testObject, method);
                }
            }

            Method afterClassMethod = getMethodAnnotatedWith(testClass, AfterClass.class);
            if (afterClassMethod != null) {
                afterClassMethod.invoke(testObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeTestWithAnnotations(Object testObject, Method method) throws Exception {
        Method beforeMethod = getMethodAnnotatedWith(method.getDeclaringClass(), Before.class);
        if (beforeMethod != null) {
            beforeMethod.invoke(testObject);
        }

        try {
            method.invoke(testObject);
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getCause());
        }

        Method afterMethod = getMethodAnnotatedWith(method.getDeclaringClass(), After.class);
        if (afterMethod != null) {
            afterMethod.invoke(testObject);
        }
    }

    private static Method getMethodAnnotatedWith(Class<?> testClass, Class<?> annotation) {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                return method;
            }
        }
        return null;
    }
}
