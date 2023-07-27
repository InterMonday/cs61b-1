package synthesizer;
import org.junit.Test;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer b[] = new ArrayRingBuffer[3];
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        arb.enqueue(10);
        arb.enqueue(13);
        ArrayRingBuffer c = new ArrayRingBuffer(10);
        c.enqueue(102);
        c.enqueue(32);
        ArrayRingBuffer d = new ArrayRingBuffer(10);

        d.enqueue(2);
        b[0] = arb;
        b[1] = c;
        b[2] = d;
        for (ArrayRingBuffer o: b) {
            for (Object t: o) {
                System.out.println(t);
            }
        }

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
