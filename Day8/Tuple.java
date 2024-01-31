package Day8;

public class Tuple<T> {
    public T left;
    public T right;
    
    public Tuple(T l,T r) {
        left = l;
        right = r;
    }

    public Tuple(T[] array) {
        this(array[0], array[1]);            
    }

}