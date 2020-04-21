package com.ioc.java.beans;

import java.beans.*;
import java.util.stream.Stream;

/**
 * @author zhangbo
 * @date 2020/3/22
 **/
public class BeanInfoDemo {

    public static void main(String[] args) throws IntrospectionException {

        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        Stream.of(propertyDescriptors).forEach(propertyDescriptor -> {
            System.out.println(propertyDescriptor);
            propertyDescriptor.setPropertyEditorClass(StringToIntegerPropertyEditor.class);
        });

    }

    static class StringToIntegerPropertyEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            setValue(Integer.valueOf(text));
        }
    }
}
