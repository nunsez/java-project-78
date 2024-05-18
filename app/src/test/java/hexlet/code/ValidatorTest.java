package hexlet.code;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorTest {

    @Test
    void testString() {
        var v = new Validator().string();
        v.required().minLength(4);
        v.contains("abc");

        assertThat(v.isValid("abcd")).isTrue();
        assertThat(v.isValid("abc")).isFalse();
        assertThat(v.isValid(null)).isFalse();
    }

    @Test
    void testNumber() {
        var v = new Validator().number();
        v.required().positive();
        v.range(3.5, 70);

        assertThat(v.isValid(null)).isFalse();
        assertThat(v.isValid(-3)).isFalse();
        assertThat(v.isValid(50)).isTrue();
        assertThat(v.isValid(100)).isFalse();

        v.range(-200, -100);
        // валидания не проходит, потому что указано требования positive()
        assertThat(v.isValid(-150)).isFalse();
    }

    @Test
    void testMap() {
        var v = new Validator();
        var schemes = new HashMap<>();
        schemes.put("name", v.string().required().minLength(4));
        schemes.put("age", v.number().range(0, 150));
        var schema = v.map().required().sizeof(2).shape(schemes);

        var human1 = Map.of(
            "name", "Alex",
            "age", 30
        );
        assertThat(schema.isValid(human1)).isTrue();

        // тест sizeof
        var human2 = Map.of("name", "Ivan");
        assertThat(schema.isValid(human2)).isFalse();

        // тест на отсутствующий ключ
        var human3 = Map.of(
            "Andrew", "name",
            "age", 15
        );
        assertThat(schema.isValid(human3)).isFalse();

        // тест на возраст в виде строки
        var human4 = Map.of(
            "Andrew", "name",
            "age", "15"
        );
        assertThat(schema.isValid(human4)).isFalse();

        // тест на диапазон возраста, когда значение null
        var human5 = new HashMap<>();
        human5.put("name", "Robert");
        human5.put("age", null);
        assertThat(schema.isValid(human5)).isFalse();

        // тест на возраст вне диапазона
        var human6 = Map.of(
            "name", "Scott",
            "age", 200
        );
        assertThat(schema.isValid(human6)).isFalse();

        // тест на имя с флагом "required"
        var human7 = new HashMap<>();
        human7.put("name", null);
        human7.put("age", 100);
        assertThat(schema.isValid(human7)).isFalse();

        // тест на имя минимальной длины
        var human8 = Map.of(
            "name", "Bob",
            "age", 25
        );
        assertThat(schema.isValid(human8)).isFalse();
    }

}
