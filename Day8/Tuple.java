package Day8;

/**
 * Tuple
 */
public class Tuple<T> {
    // The values
    public T left;
    public T right;
   
    /**
     * Constructor
     * @param l the left value
     * @param r the right value
     */
    public Tuple(T l,T r) {
        left = l;
        right = r;
    }

    /**
     * Constructor
     * @param array the array of values (only the first 2 will be used)
     */
    public Tuple(T[] array) {
        this(array[0], array[1]);            
    }

    /**
     * Constructor
     * @param t the tuple to copy
     */
    public Tuple(Tuple<T> t) {
        this(t.left, t.right);
    }

    /**
     * Constructor
     */
    public Tuple() {
        this(null, null);
    }

    /**
     * Set the values
     * @param l the left value
     * @param r the right value
     */
    public void set(T l, T r) {
        left = l;
        right = r;
    }

    /**
     * Set the values from an array
     * @param array the array of values (only the first 2 will be used)
     */
    public void set(T[] array) {
        set(array[0], array[1]);
    }

    /**
     * Set the values from a tuple
     * @param t the tuple to copy
     */
    public void set(Tuple<T> t) {
        set(t.left, t.right);
    }

    /**
     * Set the left value
     * @param l the value to set
     */
    public void setLeft(T l) {
        left = l;
    }

    /**
     * Set the right value
     * @param r the value to set
     */
    public void setRight(T r) {
        right = r;
    }

    /**
     * Get the left value
     * @return the left value
     */
    public T getLeft() {
        return left;
    }

    /**
     * Get the right value
     * @return the right value
     */
    public T getRight() {
        return right;
    }

    /**
     * Check if the tuple equals another tuple
     * @return true if the tuples are equals
     */
    public boolean equals(Tuple<T> t) {
        return left.equals(t.left) && right.equals(t.right);
    }

    /**
     * toString method
     * @return the tuple as a string
     */
    public String toString() {
        return "(" + left.toString() + ", " + right.toString() + ")";
    }

}