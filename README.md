The goal of this work was to implement and analyze four classical divide-and-conquer algorithms:

MergeSort – efficient stable sorting with reusable buffer.
QuickSort – robust version with randomized pivot and safe recursion.
Deterministic Select (Median-of-Medians) – linear-time selection algorithm.
Closest Pair of Points (2D) – geometric divide-and-conquer algorithm for finding the closest two points.
For each algorithm I measured runtime, recursion depth, number of comparisons, and memory allocations. The goal was to compare theoretical expectations with actual experimental results and to see how constant factors (cache effects, recursion overhead, memory) influence performance.

2. Algorithm Notes

MergeSort
Splits the array into halves, sorts each recursively, and merges.
Optimizations: reusable buffer for merging and cutoff to insertion sort for very small subarrays.
Expected complexity: runs in O(n log n) time, recursion depth about log₂ n.
Advantages: stable performance, no risk of quadratic time.

QuickSort
Uses a randomized pivot to avoid worst-case input.
Always recurses into the smaller partition first, iterating on the bigger one to keep recursion depth safe.
Expected complexity: average O(n log n) time, depth also about log n. Worst case O(n²), but unlikely due to pivot randomization.
Advantages: usually faster in practice than MergeSort because of cache locality.

Deterministic Select (Median-of-Medians)
Finds the k-th smallest element in an array.
Groups elements into blocks of 5, finds medians, and recursively chooses a pivot.
Expected complexity: guaranteed O(n) time, recursion depth is low because only one side is explored.
Practical note: slower than randomized QuickSelect due to extra work with medians, but avoids worst cases.

Closest Pair of Points
Input: n points in 2D space.
Algorithm:
Sort points by x, split recursively.
Check closest pairs inside each half.
Merge step: check only points in the strip near the dividing line, at most 7–8 neighbors per point.
Expected complexity: O(n log n) time, recursion depth log n.
Advantages: much faster than the naive O(n²) solution.

3. Experimental Results
Metrics collected
Execution time (milliseconds)
Recursion depth (maximum)
Comparisons (element-to-element operations)
Allocations (extra memory used)
<img width="543" height="304" alt="image" src="https://github.com/user-attachments/assets/8acebf5b-ea20-467b-8528-2e8c2492e521" />
<img width="627" height="326" alt="image" src="https://github.com/user-attachments/assets/1f815edb-229e-41f5-a137-7d5175fbaeae" />
<img width="590" height="301" alt="image" src="https://github.com/user-attachments/assets/baa3333f-ab12-4951-80c3-54d64a5b949c" />
<img width="569" height="300" alt="image" src="https://github.com/user-attachments/assets/2c040fa3-cbfb-40f9-abcf-7ff6bbc02261" />

Expected trends:
MergeSort and QuickSort: curves close to n log n. QuickSort slightly faster but less predictable.
Select: nearly linear, with higher constants.
Closest Pair: slower than sorts but scales much better than brute force.

4. Discussion
MergeSort: very stable, recursion depth matched exactly log₂ n, performance consistent.

QuickSort: in most cases the fastest algorithm. Randomized pivot avoided bad splits, depth stayed well within theoretical bounds.

Deterministic Select: results confirmed linear scaling, but constant factors were visible (extra grouping slowed it compared to sorts).

Closest Pair: noticeably slower on small arrays (because of overhead) but clearly better than quadratic brute force for large inputs.

5. Summary
Theoretical predictions matched the experimental results.
MergeSort and QuickSort both showed O(n log n) scaling, with QuickSort being faster in practice.
Deterministic Select confirmed linear complexity but with visible overhead.
Closest Pair algorithm was efficient on large datasets and much better than brute force.

Small mismatches were explained by constant-factor effects: memory allocations, cache efficiency, JVM garbage collection, and recursion overhead.

Final conclusion:
Divide-and-conquer algorithms not only work well in theory but also show strong practical performance when implemented carefully (with reusable buffers, safe recursion, and randomized pivots).

