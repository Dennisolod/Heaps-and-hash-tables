package heap;

import java.util.NoSuchElementException;

/** An instance is a min-heap of distinct values of type V with
 *  priorities of type P. Since it's a min-heap, the value
 *  with the smallest priority is at the root of the heap. */
public final class Heap<V, P extends Comparable<P>> {

    // TODO 1.1: Read and understand the class invariants given in the
    // following comment:

    /**
     * The contents of c represent a complete binary tree. We use square-bracket
     * shorthand to denote indexing into the AList (which is actually
     * accomplished using its get method. In the complete tree,
     * c[0] is the root; c[2i+1] is the left child of c[i] and c[2i+2] is the
     * right child of i.  If c[i] is not the root, then c[(i-1)/2] (using
     * integer division) is the parent of c[i].
     *
     * Class Invariants:
     *
     *   The tree is complete:
     *     1. `c[0..c.size()-1]` are non-null
     *
     *   The tree satisfies the heap property:
     *     2. if `c[i]` has a parent, then `c[i]`'s parent's priority
     *        is smaller than `c[i]`'s priority
     *
     *   In Phase 3, the following class invariant also must be maintained:
     *     3. The tree cannot contain duplicate *values*; note that dupliate
     *        *priorities* are still allowed.
     *     4. map contains one entry for each element of the heap, so
     *        map.size() == c.size()
     *     5. For each value v in the heap, its map entry contains in the
     *        the index of v in c. Thus: map.get(c[i]) = i.
     */
    protected AList<Entry> c;
    protected HashTable<V, Integer> map;

    /** Constructor: an empty heap with capacity 10. */
    public Heap() {
        c = new AList<Entry>(10);
        map = new HashTable<V, Integer>();
    }

    /** An Entry contains a value and a priority. */
    class Entry {
        public V value;
        public P priority;

        /** An Entry with value v and priority p*/
        Entry(V v, P p) {
            value = v;
            priority = p;
        }

        public String toString() {
            return value.toString();
        }
    }

    /** Add v with priority p to the heap.
     *  The expected time is logarithmic and the worst-case time is linear
     *  in the size of the heap. Precondition: p is not null.
     *  In Phase 3 only:
     *  @throws IllegalArgumentException if v is already in the heap.*/
    public void add(V v, P p) throws IllegalArgumentException {
        // TODO 1.2: Write this whole method. Note that bubbleUp is not implemented,
        // so calling it will have no effect. The first tests of add, using
        // test100Add, ensure that this method maintains the class invariant in
        // cases where no bubbling up is needed.
        // When done, this should pass test100Add.
        if(map.containsKey(v)){
            throw new IllegalArgumentException();
        }

        Entry e = new Entry(v, p);

        c.resize(size() + 1);

        c.put(size() - 1, e);
        map.put(v, size() -1);

        bubbleUp(size() - 1);
        // TODO 3.1: Update this method to maintain class invariants 3-5.
        // (delete the following line after completing TODO 1.2)
    }

    /** Return the number of values in this heap.
     *  This operation takes constant time. */
    public int size() {
        return c.size();
    }

    /** Swap c[h] and c[k].
     *  precondition: h and k are >= 0 and < c.size() */
    protected void swap(int h, int k) {
        //TODO 1.3: When bubbling values up and down (later on), two values,
        // c[h] and c[k], will have to be swapped. In order to always get this right,
        // write this helper method to perform the swap.
        // When done, this should pass test110Swap.
        Entry temp = c.get(k);
        c.put(k, c.get(h));
        c.put(h, temp);

        map.put(c.get(k).value, k);
        map.put(c.get(h).value, h);
        // TODO 3.2 Change this method to additionally maintain class
        // invariants 3-5 by updating the map field.
    }

    /** Bubble c[k] up in heap to its right place.
     *  Precondition: Priority of every c[i] >= its parent's priority
     *                except perhaps for c[k] */
    protected void bubbleUp(int k) {
        // TODO 1.4 As you know, this method should be called within add in order
        // to bubble a value up to its proper place, based on its priority.
        // When done, this should pass test115Add_BubbleUp
        int pI = parentIndex(k);
        if(c.size() <= 1){
            return;
        }
        if(c.get(k).priority.compareTo(parentOf(k).priority) < 0){
            swap(k, pI);
            bubbleUp(pI);
        }
    }

    /** Return the value of this heap with lowest priority. Do not
     *  change the heap. This operation takes constant time.
     *  @throws NoSuchElementException if the heap is empty. */
    public V peek() throws NoSuchElementException {
        // TODO 1.5: Do peek. This is an easy one.
        //         test120Peek will not find errors if this is correct.
        if(size() == 0) {
            throw new NoSuchElementException();
        } else {
            return c.get(0).value;
        }
    }

    /** Remove and return the element of this heap with lowest priority.
     *  The expected time is logarithmic and the worst-case time is linear
     *  in the size of the heap.
     *  @throws NoSuchElementException if the heap is empty. */
    public V poll() throws NoSuchElementException {
        // TODO 1.6: Do poll (1.6) and bubbleDown (1.7) together. When they
        // are written correctly, testing procedures
        // test30Poll_BubbleDown_NoDups and test140testDuplicatePriorities
        // should pass. The second of these checks that entries with equal
        // priority are not swapped.
        if (size() < 1) {
            throw new NoSuchElementException();
        } else if (size() == 1) {
            map.remove(peek());
            return c.pop().value;
        } else {
            V temp = peek();
            map.remove(c.get(0).value);
            c.put(0, c.pop());

            map.put(c.get(0).value,0);
            bubbleDown(0);
            return temp;
        }
        // TODO 3.3: Update poll() to maintain class invariants 3-5.
    }

    /** Bubble c[k] down in heap until it finds the right place.
     *  If there is a choice to bubble down to both the left and
     *  right children (because their priorities are equal), choose
     *  the right child.
     *  Precondition: Each c[i]'s priority <= its childrens' priorities
     *                except perhaps for c[k] */
    protected void bubbleDown(int k) {
        // TODO 1.7: Do poll (1.6) and bubbleDown together.  We also suggest
        //         implementing and using smallerChild, though you don't
        //         have to.
        if(c.size() <= 1){
            return;
        }
        int compLeft = c.get(k).priority.compareTo(leftChildOf(k).priority);
        int compRight = c.get(k).priority.compareTo(rightChildOf(k).priority);
        if(compLeft > 0 || compRight > 0) {
            if (leftChildOf(k).priority.compareTo(rightChildOf(k).priority) < 0) {
                int left = leftChildIndex(k);
                swap(left, k);
                bubbleDown(left);
            } else {
                int right = rightChildIndex(k);
                swap(right, k);
                bubbleDown(right);
            }
        }
    }

    /** Return true if the value v is in the heap, false otherwise.
     *  The average case runtime is O(1).  */
    public boolean contains(V v) {
        if(map.containsKey(v)) {
            return true;
        }
        return false;
        //throw new UnsupportedOperationException();
    }

    /** Change the priority of value v to p.
     *  The expected time is logarithmic and the worst-case time is linear
     *  in the size of the heap.
     *  @throws IllegalArgumentException if v is not in the heap. */
    public void changePriority(V v, P p) throws IllegalArgumentException {
        if(contains(v)) {
            int i = map.get(v);
            c.get(i).priority = p;
            bubbleDown(i);
            bubbleUp(i);
            return;
        }
        // the heap.
        throw new IllegalArgumentException();
    }

    // Recommended helper method spec:
    /* Return the index of the child of k with smaller priority.
     * if only one child exists, return that child's index
     * Precondition: at least one child exists.*/
    private int smallerChild(int k) {
      throw new UnsupportedOperationException();
    }

    // helper methods below:

    // Specification: returns Entry parent of index
    private Entry parentOf(int index){
        return c.get((index - 1)/2);
    }
    // Specification: returns index parent of index
    private int parentIndex(int index){
        return (index - 1)/2;
    }

    // Specification: returns Entry left child of index
    private Entry leftChildOf(int index) {
        if(size() <= (2 * index + 1)) {
            return c.get(index);
        }
        return c.get(2 * index + 1);
    }
    // Specification: returns indexleft child of index
    private int leftChildIndex(int index) {
        return 2 * index + 1;
    }

    // Specification: returns Entry right child of index
    private Entry rightChildOf(int index) {
        if(size() <= (2 * index + 2)) {
            return c.get(index);
        }
        return c.get(2 * index + 2);
    }
    // Specification: returns index right child of index
    private int rightChildIndex(int index) {
        return 2 * index + 2;
    }


}
