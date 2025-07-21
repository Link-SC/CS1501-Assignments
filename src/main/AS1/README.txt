---------------------------------------------------------------------------------------------------
Programming Assignment 1: Percolation
Link Lin
Section 1
2023141520240

---------------------------------------------------------------------------------------------------
1.Quick-Find UF implementation analysis

Time complexity analysis
Total PercolationStats run time measured using Stopwatch:

N = 400 T = 400 :    4.093 s
N = 800 T = 400 :    21.168 s (5.17×)
N = 400 T = 800 :    8.49 s (2.07×)

N = 1600 T = 100 :    46.924 s
N = 3200 T = 100 :    483.782 s (10.31×)
N = 1600 T = 200 :    94.245 s (2.01×)

Time complexity formula: Total Time ∼ T ⋅ N⁴

Memory usage analysis

QuickFindUF ~4N² bytes
boolean[][] isOpened ~N² bytes
Total Memory ∼ 5N² bytes

---------------------------------------------------------------------------------------------------
2.Weighted Quick-Union UF implementation analysis

Time complexity analysis
Total PercolationStats run time measured using Stopwatch:

N = 400 T = 400 :    3.892 s
N = 800 T = 400 :    16.104 s (4.14×)
N = 400 T = 800 :    6.979 s (1.79×)

N = 1600 T = 100 :    58.255 s
N = 3200 T = 100 :    400.154 s (6.87×)
N = 1600 T = 200 :    97.411 s (1.67×)

Time complexity formula: Total Time ∼ T ⋅ N²

Memory usage analysis

WeightedQuickUnionUF Data Structure ~8N² bytes
isOpened Boolean Matrix ~N² bytes
Other variables 8 bytes constant
Total Memory ∼ 9N² bytes

---------------------------------------------------------------------------------------------------
3.Summary

Through the above experiments and calculations, it can be seen that when N≤800, there is little difference between the two algorithms. When N≥1600, the O(N⁴) characteristics of Quick-Find begin to explode. Quick-Find is 20% slower than Weighted QU (483.782s vs 400.154s)


