package heap;

/** A hash table modeled after java.util.Map. It uses chaining for collision
 * resolution and grows its underlying storage by a factor of 2 when the load
 * factor exceeds 0.8. */
public class HashTable<K,V> {

    protected Pair[] buckets; // array of list nodes that store K,V pairs
    protected int size; // how many items currently in the map


    /** class Pair stores a key-value pair and a next pointer for chaining
     * multiple values together in the same bucket, linked-list style*/
    public class Pair {
        protected K key;
        protected V value;
        protected Pair next;

        /** constructor: sets key and value */
        public Pair(K k, V v) {
            key = k;
            value = v;
            next = null;
        }

        /** constructor: sets key, value, and next */
        public Pair(K k, V v, Pair nxt) {
            key = k;
            value = v;
            next = nxt;
        }

        /** returns (k, v) String representation of the pair */
        public String toString() {
            return "(" + key + ", " + value + ")";
        }
    }

    /** constructor: initialize with default capacity 17 */
    public HashTable() {
        this(17);
    }

    /** constructor: initialize the given capacity */
    public HashTable(int capacity) {
        buckets = createBucketArray(capacity);
    }

    /** Return the size of the map (the number of key-value mappings in the
     * table) */
    public int getSize() {
        return size;
    }

    /** Return the current capacity of the table (the size of the buckets
     * array) */
    public int getCapacity() {
        return buckets.length;
    }

    /** Return the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     * Runtime: average case O(1); worst case O(size) */
    public V get(K key) {
        // TODO 2.2 - do this together with put.
        int hash = calcHashKey(key);
        Pair P = buckets[hash];
        while(P != null) {
            if(P.key.equals(key)) {
                return P.value;
            }
            P = P.next;
        }
        return null;
    }

    /** Associate the specified value with the specified key in this map. If
     * the map previously contained a mapping for the key, the old value is
     * replaced. Return the previous value associated with key, or null if
     * there was no mapping for key. If the load factor exceeds 0.8 after this
     * insertion, grow the array by a factor of two and rehash.
     * Precondition: val is not null.
     * Runtime: average case O(1); worst case O(size + a.length)*/
    public V put(K key, V val) {
        // TODO 2.3
        //   do this together with get. For now, don't worry about growing the
        //   array and rehashing.
        //   Tips:
        //     - Use the key's hashCode method to find which bucket it belongs in.
        //     - It's possible for hashCode to return a negative integer.
        //
        // TODO 2.6 - modify this method to grow and rehash if the load factor
        //            exceeds 0.8.
        int hash = calcHashKey(key);
        Pair P = buckets[hash];
        Pair newP = new Pair(key, val);
        V dup = get(key);
        if(dup != null) {
            while(newP.key != P.key) {
                P = P.next;
            }
            P.value = val;
            return dup;
        }
        if(P == null) {
            size++;
            buckets[hash] = newP;
            growIfNeeded();
            return null;
        }
        V temp = P.value;
        size++;
        buckets[hash] = new Pair(key, val, P);
        growIfNeeded();
        return temp;
    }

    /** Return true if this map contains a mapping for the specified key.
     *  Runtime: average case O(1); worst case O(size) */
    public boolean containsKey(K key) {
        // TODO 2.4
        if(get(key) == null) {
            return false;
        }
        return true;
    }

    /** Remove the mapping for the specified key from this map if present.
     *  Return the previous value associated with key, or null if there was no
     *  mapping for key.
     *  Runtime: average case O(1); worst case O(size)*/
    public V remove(K key) {
        // TODO 2.5
        int hash = calcHashKey(key);
        Pair P = buckets[hash];
        Pair prev = P;
        if(P != null) {
            while ((key != P.key) && (P != null)) {
                prev = P;
                P = P.next;
            }
            if (P == null) {
                return null;
            }
            V temp = P.value;
            if(prev == P) {
                buckets[hash] = prev.next;
            } else {
                prev.next = P.next;
            }
            size--;
            return temp;
        }
        return null;
    }

    // helper methods below:

    private int calcHashKey(K key) {
        return Math.abs(key.hashCode()) % getCapacity();
    }


    // suggested helper method:
    /* check the load factor; if it exceeds 0.8, double the capacity 
     * and rehash values from the old array to the new array */
    private void growIfNeeded() {
        float LF = (float) size/getCapacity();
        if(LF > 0.8) {
            Pair P;
            Pair[] temp = createBucketArray(getCapacity() * 2);
            System.arraycopy(buckets, 0, temp, 0, getCapacity());
            buckets = createBucketArray(getCapacity() * 2);

            for (int i = 0; i < getCapacity(); i++) {
                P = temp[i];
                while (P != null) {
                    int hash = calcHashKey(P.key);
                    if(buckets[hash] == null) {
                        buckets[hash] = P;
                    } else {
                        buckets[hash] = new Pair(P.key, P.value, buckets[hash]);
                    }
                    P = P.next;
                }
            }
        }
    }

    /* useful method for debugging - prints a representation of the current
     * state of the hash table by traversing each bucket and printing the
     * key-value pairs in linked-list representation */
    protected void dump() {
        System.out.println("Table size: " + getSize() + " capacity: " +
                getCapacity());
        for (int i = 0; i < buckets.length; i++) {
            System.out.print(i + ": --");
            Pair node = buckets[i];
            while (node != null) {
                System.out.print(">" + node + "--");
                node = node.next;

            }
            System.out.println("|");
        }
    }

    /*  Create and return a bucket array with the specified size, initializing
     *  each element of the bucket array to be an empty LinkedList of Pairs.
     *  The casting and warning suppression is necessary because generics and
     *  arrays don't play well together.*/
    @SuppressWarnings("unchecked")
    protected Pair[] createBucketArray(int size) {
        return (Pair[]) new HashTable<?,?>.Pair[size];
    }
}
