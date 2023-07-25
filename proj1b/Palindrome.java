public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> c = new LinkedListDeque<>();
        int i = 0;
        while(i < word.length()){
            char a = word.charAt(i);
            c.addLast(a);
            i++;
        }
        return c;
    }

    public boolean isPalindrome(String word){
        Deque<Character> c = wordToDeque(word);
        if (c.isEmpty() || c.size() == 1) return true;
        while (!c.isEmpty() && c.size() > 1){
            if (c.removeFirst() != c.removeLast())
                return false;
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> c = wordToDeque(word);
        if (c.isEmpty() || c.size() == 1) return true;
        while (!c.isEmpty() && c.size() > 1){
            if (!cc.equalChars(c.removeFirst(), c.removeLast()))
                return false;
        }
        return true;
    }
}
