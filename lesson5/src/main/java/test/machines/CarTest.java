package test.machines;

import examples.Car;
import xunit.annotations.Test;
import xunit.helpers.ReflectionHelper;

import static xunit.assertion.Assert.*;

public class CarTest {

    @Test
    public void testGetFullName() {
        Car car = new Car();
        ReflectionHelper.setFieldValue(car, "brand", "Mercedes");
        ReflectionHelper.setFieldValue(car, "model", "c300");

        assertEquals("Mercedes c300", ReflectionHelper.callMethod(car, "getFullName"));
    }
}
