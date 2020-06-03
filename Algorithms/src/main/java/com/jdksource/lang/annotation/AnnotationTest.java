package com.jdksource.lang.annotation;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @author zhangbo
 * @date 2020/5/7
 */
@MyAnnotation(value = 1)
public class AnnotationTest {

    public static void main(String[] args) {

        Annotation[] declaredAnnotations = AnnotationTest.class.getDeclaredAnnotations();

        System.out.println(Arrays.toString(declaredAnnotations));

        Annotation annotation = declaredAnnotations[0];

        System.out.println(annotation.annotationType().equals(MyAnnotation.class));

        System.out.println(MyAnnotation.class.isAnnotation());

        MyAnnotation myAnnotation = AnnotationTest.class.getDeclaredAnnotation(MyAnnotation.class);
        System.out.println(myAnnotation.value());

    }

}
