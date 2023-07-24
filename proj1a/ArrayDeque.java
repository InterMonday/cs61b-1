public class ArrayDeque<T> {
    private int size, begin, end;
    private T[] arr;
    public ArrayDeque(){
        arr = (T[])new Object[8];
        begin = end = size = 0;
    }
    public ArrayDeque(T t){
        arr = (T[])new Object[8];
        arr[0] = t;
        begin = 0;
        end = 1;
        size = 1;
    }
    public void adjSize(int s){
        T[] temp = (T[]) new Object[s];
        System.arraycopy(arr, begin, temp, s + begin - arr.length, arr.length - begin);
        System.arraycopy(arr, 0, temp, 0, end);
        begin = s + begin - arr.length;
        arr = temp;
    }
    public void adjSizeDec(int s){
        T[] temp = (T[]) new Object[s];
        System.arraycopy(arr, begin, temp, 0, arr.length - begin);
        System.arraycopy(arr, 0, temp, arr.length - begin, end);
        begin = s + begin - arr.length;
        arr = temp;
    }
    public void addFirst(T t){
        if(begin == 0) begin = arr.length - 1;
        else begin--;
        if (begin < end) adjSize(arr.length * 2);
        arr[begin] = t;
        size++;
    }
    public void addLast(T t){
        if (end == arr.length) end = 0;
        if(end == begin) adjSize(arr.length * 2);
        arr[end++] = t;
        size++;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        if(size != 0) System.out.print(arr[begin] + " ");
        else return;
        for(int i = begin + 1; i != end; i++){
            if(i == arr.length) i = 0;
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    public T removeLast(){
        T t = arr[end];
        if(begin != end) arr[end] = null;
        if (end == 0) end = arr.length - 1;
        else end--;
        size--;
        if(size < arr.length / 4) adjSizeDec(arr.length / 2);
        return t;
    }
    public T removeFirst(){
        T t = arr[begin];
        if(begin != end) arr[begin] = null;
        if(begin == arr.length - 1) begin = 0;
        else begin++;
        size--;
        if(size < arr.length / 4) adjSizeDec(arr.length / 2);
        return t;
    }
    public T get(int index){
//        if(index > arr.length - 1) return null;
//        int i = begin;
//        for(; index > 0; i++){
//            if(i == arr.length) i = 0;
//            index--;
//        }
//        return arr[i];
        return arr[index];
    }
    public ArrayDeque(ArrayDeque other){
        other.arr = (T[]) new Object[arr.length];
        other.begin = begin;
        other.end = end;
        other.size = size;
        for (int i = 0; i < arr.length; i++){
            other.arr[i] = arr[i];
        }
    }


}
