package hexlet.code.schemas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumberSchemaTest {

    private NumberSchema schema;

    @BeforeEach
    void setUp() {
        schema = new NumberSchema();
    }

    @Test
    void testFluent() {
        assertThat(schema).isInstanceOf(NumberSchema.class);
        assertThat(schema.required()).isInstanceOf(NumberSchema.class);
        assertThat(schema.positive()).isInstanceOf(NumberSchema.class);
        assertThat(schema.range(5, 10)).isInstanceOf(NumberSchema.class);
    }

    @Test
    void testRequired() {
        assertThat(schema.isValid(5)).isTrue();
        assertThat(schema.isValid(null)).isTrue();

        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(-10)).isTrue();
    }

    @Test
    void testPositive() {
        schema.positive();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(-10)).isFalse();
        assertThat(schema.isValid(0)).isFalse();
    }

    @Test
    void testRange() {
        schema.range(5, 10);

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(4)).isFalse();
        assertThat(schema.isValid(5)).isTrue();
        assertThat(schema.isValid(7)).isTrue();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(11)).isFalse();
    }

    @Test
    void testComplex() {
        schema.required().positive();
        schema.range(3, 70);

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(-3)).isFalse();
        assertThat(schema.isValid(50)).isTrue();
        assertThat(schema.isValid(100)).isFalse();

        schema.range(-200, -100);
        // валидания не проходит, потому что указано требования positive()
        assertThat(schema.isValid(-150)).isFalse();
    }

}
