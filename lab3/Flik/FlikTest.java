import static org.junit.Assert.*;

import org.junit.Test;

public class FlikTest {
    @Test
    public void sameTest(){
        Integer a = new Integer(2);
        Integer b = new Integer(3);
        Integer c = new Integer(2);
        assertTrue(Flik.isSameNumber(a, c));
        assertTrue(!Flik.isSameNumber(a, b));
    }

    public static void main(String... args) {
        jh61b.junit.TestRunner.runTests("all", FlikTest.class);
    }
}
