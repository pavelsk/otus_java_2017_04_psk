package test;

import examples.Man;
import xunit.annotations.After;
import xunit.annotations.Before;
import xunit.annotations.Test;
import xunit.helpers.ReflectionHelper;

import static xunit.assertion.Assert.*;

public class ManTest {

    private int someValue;

    @Before
    public void before() {
        assertEquals(0, someValue);

        someValue = 5;
    }

    @Test
    public void checkSomeValue() {
        assertEquals(5 ,this.someValue);
    }

    @After
    public void after() {
        assertEquals(5 ,this.someValue);
    }

    @Test
    public void testSetName() {
        Man man = new Man();

        man.setName("Billy");

        assertEquals(
                "Check setName",
                ReflectionHelper.getFieldValue(man, "name"), "Billy"
        );
    }

    @Test
    public void testSetAge() {
        Man man = new Man();

        man.setAge(15);

        assertEquals(
                "Check setAge",
                ReflectionHelper.getFieldValue(man, "age"), 15
        );
    }
}
