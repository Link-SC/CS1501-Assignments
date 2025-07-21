/******************************************************************************
 *  readme.txt template                                                   
 *  Map
 *****************************************************************************/

ID: 2023141520240
Name: 林思成

/******************************************************************************
 *  Explain your overall approach.
 *****************************************************************************/
For this project, I implemented Dijkstra's algorithm and optimized it using the A* algorithm to find the shortest path between two points on a map. The map is represented as a graph where vertices are locations, edges represent roads, and Euclidean distances are used as weights.

My approach consists of the following steps:
1. Dijkstra's Algorithm: I first implemented the classic Dijkstra's algorithm for finding the shortest path between two vertices. The algorithm starts from the source point and iteratively expands the known shortest path until it reaches the destination point.
2. A* Optimization: I enhanced the performance of Dijkstra's algorithm using the A* heuristic. The algorithm introduces a distance estimate of a vertex to the target, here Euclidean distance is used, thus allowing it to prioritize vertices that are closer to the target, rather than blindly expanding all vertices. This significantly reduces the overall planning time.
3. Path Visualization: I used a turtle diagram to visualize the computed shortest path between the source vertex and the destination vertex, in order to visually verify the correctness of the algorithm.
4. Performance test: To evaluate the effectiveness of the A* optimization, I used large-scale map to compare the execution time of the A* algorithm with the traditional Dijkstra algorithm. When you enter A pair of coordinates, the running times of Dijkstra's algorithm and the A* algorithm are displayed, allowing you to visually compare their efficiency.

I tested both algorithms on the provided dataset and compared their execution times.  The A* algorithm consistently performed faster, especially when the source and destination nodes were farther apart, due to the heuristic’s ability to guide the search.
The program also includes a feature to track the number of vertices examined during the pathfinding process and reports the average number of vertices examined for each query.
******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
1. Graph visualization can get messy for large graphs with a very high number of nodes and edges.
2. If the source and target are far apart in the graph, the path visualization can become difficult to follow as the path can be long.
3. The performance of A program can be affected by very large datasets with too many vertices, despite optimizations like A*.
/******************************************************************************
 *  List whatever help (if any) that you received.
 *****************************************************************************/
I used Deepseek R1 for this task and ChatGPT 4.0 model to assist me in writing the code. This is because I am not much of A framework builder. The AI model helped me to read and understand the A* algorithm and help me to optimize the code. Eventually helped me fully understand the task.
Besides, my friend Ray also helped me a lot. He comforted me when I couldn't write code.
/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
For some unknown reason, my code keeps getting function reference errors and variable declaration errors. In addition, I am not familiar with the operation of eclipse software. Maybe some environment configuration is not properly configured, which makes me unable to properly debug the program through the console in eclipse. I ended up using cmd for debugging and running.
/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/
I think the task is more difficult. It's hard to do all the optimization tasks on your own without outside help (AI assistance, guru guidance).
Of course, I learned a lot, this assignment helped me to better understand graph algorithms, especially Dijkstra's algorithm and A*. It was interesting to implement pathfinding and optimization strategies, and I learned that small changes like introducing heuristics can greatly improve performance.
