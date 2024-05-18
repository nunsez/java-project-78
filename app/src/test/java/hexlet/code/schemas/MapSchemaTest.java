package hexlet.code.schemas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MapSchemaTest {

    private MapSchema schema;

    @BeforeEach
    void setUp() {
        schema = new MapSchema();
    }

    @Test
    void testFluent() {
        assertThat(schema).isInstanceOf(MapSchema.class);
        assertThat(schema.required()).isInstanceOf(MapSchema.class);
        assertThat(schema.sizeof(2)).isInstanceOf(MapSchema.class);
    }

    @Test
    void testRequired() {
        assertThat(schema.isValid(Map.of())).isTrue();
        assertThat(schema.isValid(null)).isTrue();

        schema.required();

        assertThat(schema.isValid(Map.of())).isTrue();
        assertThat(schema.isValid(null)).isFalse();

        var map1 = Map.of("k1", "v1");
        assertThat(schema.isValid(map1)).isTrue();
    }

    @Test
    void testSizeof() {
        assertThat(schema.isValid(Map.of())).isTrue();
        assertThat(schema.isValid(null)).isTrue();

        schema.sizeof(2);

        assertThat(schema.isValid(Map.of())).isFalse();
        assertThat(schema.isValid(null)).isFalse();

        var map1 = Map.of("k1", "v2");
        assertThat(schema.isValid(map1)).isFalse();

        var map2 = new HashMap<String, String>();
        map2.put("k1", "v1");
        map2.put("k2", null);
        assertThat(schema.isValid(map2)).isTrue();

        var map3 = new HashMap<>();
        map3.put("k1", "v1");
        map3.put("k2", null);
        map3.put("k3", 3);
        assertThat(schema.isValid(map3)).isFalse();
    }

    @Test
    void testShape() {
        var stringSchema = new StringSchema();
        var numberSchema = new NumberSchema();
        var schemas = new HashMap<String, Schema<?>>();
        schemas.put("firstName", stringSchema.required());
        schemas.put("lastName", stringSchema.required().minLength(2));
        schemas.put("age", numberSchema.required().positive().range(1, 150));
        schema.shape(schemas);

        var human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        human1.put("age", 30);
        assertThat(schema.isValid(human1)).isTrue();

        var human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        human2.put("age", 30);
        assertThat(schema.isValid(human2)).isFalse();

        var human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        human2.put("age", 30);
        assertThat(schema.isValid(human3)).isFalse();

        var human4 = new HashMap<>();
        human4.put("firstName", "John");
        human4.put("lastName", "Smith");
        human4.put("age", null);
        assertThat(schema.isValid(human4)).isFalse();

        var human5 = new HashMap<>();
        human5.put("firstName", "John");
        human5.put("lastName", "Smith");
        human5.put("age", 200);
        assertThat(schema.isValid(human5)).isFalse();

        var human6 = new HashMap<>();
        human6.put("firstName", "John");
        human6.put("lastName", "Smith");
        human6.put("age", "200");
        assertThat(schema.isValid(human6)).isFalse();
    }

}
