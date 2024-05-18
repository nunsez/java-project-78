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

}
