import com.google.gson.Gson;
import test.Test2;
import test.TestObject;
import test.TestObjectBuilder;
import xjson.ISerializer;
import xjson.JsonSerializer;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        ISerializer serializer = new JsonSerializer();
        Gson gson = new Gson();

        Map<List<String>, Map<Integer, Double>> map = new HashMap<>();
        map.put(Arrays.asList("Bill", "Murray"), Collections.singletonMap(1, 1.2));
        map.put(Arrays.asList("Tom", "Hanks"), Collections.singletonMap(2, 11.6));
        map.put(null, Collections.singletonMap(2, 11.6));
        map.put(Collections.singletonList("Chuck"), null);

        TestObject testObject = (new TestObjectBuilder())
                .setLongValue(2)
                .setStringValue("John")
                .setCharValue('s')
                .setIntArray(new int[]{1, 2, 3})
                .setList(Arrays.asList(Arrays.asList("Leo", "Messi"), Arrays.asList("Cristiano", "Ronaldo")))
                .setMap(map)
                .createTestObject();

        System.out.println(gson.toJson(testObject));
        System.out.println(gson.toJson(new Test2()));

        System.out.println(serializer.serialize(testObject));
        System.out.println(serializer.serialize(new Test2()));
    }
}
