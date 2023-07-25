public class LinkedListDeque<T> implements Deque<T>{
    private class Node<T>{
        Node<T> pre, next;
        T item;

        public Node(T i, Node p, Node n){
            item = i;
            pre = p;
            next = n;
        }
    }

    int size;
    Node<T> head;

    public LinkedListDeque(){
        head = new Node<>(null,null,null);
        head.next = head;
        head.pre = head;
        size = 0;;
    }
    public LinkedListDeque(T i){
        head = new Node<T>(null, null, null);
        Node<T> node = new Node<>(i, head, head);
        head.next = node;
        head.pre = node;
        size = 1;
    }
    public LinkedListDeque(LinkedListDeque other){
        head = new Node<>(null,null,null);
        head.next = head;
        head.pre = head;
        Node<T> p = other.head.next;
        while (p !=other.head){
            addLast(p.item);
            p = p.next;
        }
    }
    @Override
    public void addFirst(T i){
        Node<T> node = new Node<>(i, head, head.next);
        head.next.pre = node;
        head.next = node;
        size++;
    }
    @Override
    public void addLast(T i){
        Node<T> node = new Node<>(i, head.pre, head);
        head.pre.next = node;
        head.pre = node;
        size++;
    }
    @Override
    public boolean isEmpty(){
        if(head.next == head) return true;
        return false;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque(){
        if(head == null) return;
        Node p = head.next;
        while (p !=head){
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }
    @Override
    public T removeFirst(){
        if(head.next == head) return null;
        T t = head.next.item;
        head.next.next.pre = head;
        head.next = head.next.next;
        size--;
        return t;
    }
    @Override
    public T removeLast(){
        if (head.next == head) return null;
        T t = head.pre.item;
        head.pre.pre.next = head;
        head.pre = head.pre.pre;
        size--;
        return t;
    }
    @Override
    public T get(int index){
        Node<T> p = head.next;
        while(index > 0){
            if (p == head) return null;
            p = p.next;
            index--;
        }
        return p.item;
    }

    private T gr(int index, Node<T> p){
        if(p == head) return null;
        if (index == 0) return p.item;
        return gr(--index, p.next);
    }
    public T getRecursive(int index){
        return gr(index, head.next);
    }
}
