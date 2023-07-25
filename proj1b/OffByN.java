public class OffByN implements CharacterComparator{
    private int m_n;
    public OffByN(int n){
        m_n = n;
    }

    @Override
    public boolean equalChars(char x, char y){
        if(Math.abs(x - y) == m_n)
            return true;
        else return false;
    }
}
