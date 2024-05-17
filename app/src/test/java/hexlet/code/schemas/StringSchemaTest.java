package hexlet.code.schemas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringSchemaTest {

    private StringSchema schema;

    @BeforeEach
    void setUp() {
        schema = new StringSchema();
    }

    @Test
    void testFluent() {
        assertThat(schema).isInstanceOf(StringSchema.class);
        assertThat(schema.required()).isInstanceOf(StringSchema.class);
        assertThat(schema.contains("abc")).isInstanceOf(StringSchema.class);
        assertThat(schema.minLength(3)).isInstanceOf(StringSchema.class);
    }

    @Test
    void testRequired() {
        assertThat(schema.isValid("")).isTrue();
        assertThat(schema.isValid(null)).isTrue();

        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid("what does the fox say")).isTrue();
        assertThat(schema.isValid("hexlet")).isTrue();
    }

    @Test
    void testContains() {
        schema.contains(null);
        assertThat(schema.isValid("not null")).isTrue();

        schema.contains("");
        assertThat(schema.isValid("not null")).isTrue();
        assertThat(schema.isValid(null)).isFalse();

        schema.contains("wh");
        assertThat(schema.isValid("what does the fox say")).isTrue();

        schema.contains("what");
        assertThat(schema.isValid("what does the fox say")).isTrue();

        schema.contains("whatthe");
        assertThat(schema.isValid("what does the fox say")).isFalse();

        // Здесь уже false, так как добавлена еще одна проверка contains("whatthe")
        assertThat(schema.isValid("what does the fox say")).isFalse();
    }

    @Test
    void testMinLength() {
        // Если один валидатор вызывался несколько раз
        // то последний имеет приоритет (перетирает предыдущий)
        schema.minLength(10).minLength(4);
        assertThat(schema.isValid("Hexlet")).isTrue();
        assertThat(schema.isValid("Hex")).isFalse();

        assertThat(schema.isValid(null)).isFalse();
    }

    @Test
    void testComplex() {
        schema.required().minLength(4);
        schema.contains("abc");

        assertThat(schema.isValid("abcd")).isTrue();
        assertThat(schema.isValid("abc")).isFalse();
        assertThat(schema.isValid(null)).isFalse();
    }

}
