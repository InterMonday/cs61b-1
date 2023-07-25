import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static OffByN o5 = new OffByN(5);
    static OffByN o4 = new OffByN(4);
    static OffByN o3 = new OffByN(3);
    static OffByN o2 = new OffByN(2);
    static OffByN o1 = new OffByN(1);
    @Test
    public void testEqualN(){
        assertTrue(o1.equalChars('a', 'b'));
        assertFalse(o2.equalChars('c', 'b'));
        assertFalse(o3.equalChars('a', 'c'));
        assertTrue(o4.equalChars('a', 'e'));
        assertTrue(o5.equalChars('a', 'f'));
        assertTrue(o4.equalChars('b', 'f'));
    }
}
