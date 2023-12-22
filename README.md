# Heaps-and-hash-tables
Third assignment for csci241: data structures &amp; algorithms

The assignment's objective is to implement heaps and hash tables

Phase 1: Implementation of a min-heap

1. Implement the add() method, using the bubbleUp helper according to its specification.
2. Implement the swap() helper method, which you’ll use in both bubbling routines.
3. Implement the bubbleUp() helper method.
4. Implement peek().
5. Implement bubbleDown().
6. Implement poll().

Phase 2: Implement a hash table with chaining for collision resolution.

1. Implement get(K key)
2. Implement put() without worrying about load factor and array growth. Replace the value if the key is
   already in the map, and insert a new key-value pair otherwise.
3. Implement containsKey().
4. Implement remove().
5. Modify put() to check the load factor and grow and rehash the array if the load factor exceeds 0.8 after the
   insertion. Use the createBucketArray() helper method to create the new array.

Phase 3: Overlay the hashtable on the heap to make the contains() and changePriority() operations efficient.

1. Update the add() method to keep the map consistent with the state of the heap. Throw an exception if a
   duplicate value is inserted.
2. Update the swap() method to keep the HashTable consistent with the heap.
3. Update poll().
4. Implement contains().
5. Implement changePriority() by finding the value in question, updating its priority, and fixing the Heap property by
   bubbling it up or down.





   
