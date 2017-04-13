package main;

import measure.MeasurerFactory;
import objects.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start measurement...");

        System.out.printf(
                "String from StringPool: %d bytes\n",
                MeasurerFactory.getMeasurer(String.class).measure()
        );

        System.out.printf(
                "String from new char array: %d bytes\n",
                MeasurerFactory.getMeasurer(new StringFactory()).measure()
        );

        int objectSize = MeasurerFactory.getMeasurer(Object.class).measure();
        System.out.printf(
                "Object class: %d bytes\n",
                objectSize
        );

        int objectWithReferenceSize = MeasurerFactory.getMeasurer(ObjectWithReference.class).measure();
        System.out.printf(
                "Object class with reference: %d bytes\n",
                objectWithReferenceSize
        );

        System.out.printf("Reference size: %d bytes\n", objectWithReferenceSize - objectSize);

        System.out.printf(
                "Object with two int props: %d bytes\n",
                MeasurerFactory.getMeasurer(ObjectWithInt.class).measure()
        );

        System.out.printf(
                "Integer: %d bytes\n",
                MeasurerFactory.getMeasurer(new IntegerFactory()).measure()
        );

        System.out.printf(
                "Integer array with 1 elements: %d bytes\n",
                MeasurerFactory.getMeasurer(new IntegerArrayFactory(1)).measure()
        );

        System.out.printf(
                "Integer array with 3 elements: %d bytes\n",
                MeasurerFactory.getMeasurer(new IntegerArrayFactory(3)).measure()
        );

        System.out.printf(
                "Integer array with 5 elements: %d bytes\n",
                MeasurerFactory.getMeasurer(new IntegerArrayFactory(5)).measure()
        );

        System.out.printf(
                "Object array with 5 elements: %d bytes\n",
                MeasurerFactory.getMeasurer(new ObjectArrayFactory(5)).measure()
        );

        System.out.printf(
                "ArrayList: %d bytes\n",
                MeasurerFactory.getMeasurer(new ArrayListFactory()).measure()
        );
    }
}
