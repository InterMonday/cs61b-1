import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testIsPalindrome(){
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("ada"));
        assertTrue(palindrome.isPalindrome("aaa"));
        assertTrue(palindrome.isPalindrome("adada"));
        assertTrue(palindrome.isPalindrome("adaada"));
        assertFalse(palindrome.isPalindrome("adaaa"));
        assertFalse(palindrome.isPalindrome("ad"));
    }
    @Test
    public void testEqual(){
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertTrue(palindrome.isPalindrome("a", offByOne));
        assertTrue(palindrome.isPalindrome("ab", offByOne));
        assertTrue(palindrome.isPalindrome("adb", offByOne));
        assertTrue(palindrome.isPalindrome("baa", offByOne));
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertTrue(palindrome.isPalindrome("bdaea", offByOne));
        assertFalse(palindrome.isPalindrome("adaada", offByOne));
        assertFalse(palindrome.isPalindrome("adaaa", offByOne));
        assertFalse(palindrome.isPalindrome("aa", offByOne));
    }
}
