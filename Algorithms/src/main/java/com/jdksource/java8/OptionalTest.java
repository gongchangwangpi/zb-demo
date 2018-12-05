package com.jdksource.java8;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

/**
 * @author zhangbo
 */
public class OptionalTest {

    public static void main(String[] args) {

        Optional<Person> person = Optional.ofNullable(new Person(new Car(new Insurance("平安"))));

        Optional<String> name = person.map(Person::getCar).map(Car::getInsurance).map(Insurance::getName);

        name.ifPresent(System.out::print);

    }
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Person {
        private Car car;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Car {
        private Insurance insurance;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Insurance {
        private String name;
    }
}
