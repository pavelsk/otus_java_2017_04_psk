package xjson;

import com.google.gson.Gson;
import org.junit.Test;
import test.TestObject;
import test.TestObjectBuilder;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class JsonSerializerTest {

    @Test
    public void testBigObject() {
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

        String json = serializer.serialize(testObject);

        assertEquals(json, gson.toJson(testObject));
    }

    @Test
    public void testObject() {
        ISerializer serializer = new JsonSerializer();
        Gson gson = new Gson();

        assertEquals(serializer.serialize(new Object()), gson.toJson(new Object()));
    }

    @Test
    public void testString() {
        ISerializer serializer = new JsonSerializer();
        Gson gson = new Gson();

        assertEquals(serializer.serialize("Toy"), gson.toJson("Toy"));
    }

    @Test
    public void testInteger() {
        ISerializer serializer = new JsonSerializer();
        Gson gson = new Gson();

        assertEquals(serializer.serialize(125), gson.toJson(125));
    }

    @Test
    public void testList() {
        ISerializer serializer = new JsonSerializer();
        Gson gson = new Gson();

        List<String> list = new ArrayList<>();
        list.add("One");
        list.add("Two");
        list.add("Three");

        assertEquals(serializer.serialize(list), gson.toJson(list));
    }

    @Test
    public void testArray() {
        ISerializer serializer = new JsonSerializer();
        Gson gson = new Gson();

        int[] array = new int[5];
        Arrays.setAll(array, i -> i + 1);

        assertEquals(serializer.serialize(array), gson.toJson(array));
    }

    @Test
    public void testMap() {
        ISerializer serializer = new JsonSerializer();
        Gson gson = new Gson();

        Map<String, Integer> map = new HashMap<>();
        map.put("Barcelona", 150);
        map.put("Real", 130);

        assertEquals(serializer.serialize(map), gson.toJson(map));
    }
}
