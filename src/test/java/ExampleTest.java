import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExampleTest {
    @Test
    void shouldShowSimpleAssertion () {
        Assertions.assertEquals(ExampleClass.returnOne(), 1);
    }
}
