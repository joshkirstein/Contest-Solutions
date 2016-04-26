/*
ID: joshkir1
LANG: JAVA
TASK: castle
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class castle {
    
    static class WallData {
        public boolean N, S, E, W;
        public WallData(int encode) {
            if (encode >= 8) {
                encode -= 8;
                S = true;
            }
            if (encode >= 4) {
                encode -= 4;
                E = true;
            }
            if (encode >= 2) {
                encode -= 2;
                N = true;
            }
            if (encode >= 1) {
                encode -= 1;
                W = true;
            }
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader sc = new BufferedReader(new FileReader("castle.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("castle.out"));
        String[] spl = sc.readLine().split(" ");
        int M = Integer.parseInt(spl[0]);
        int N = Integer.parseInt(spl[1]);
        WallData[][] data = new WallData[N][M];//almost like our implicit adjacency matrix.
        int[][] vis = new int[N][M];
        for (int i = 0; i < N; i++) {
            String[] splTwo = sc.readLine().split(" ");
            for (int j = 0; j < M; j++) {
                data[i][j] = new WallData(Integer.parseInt(splTwo[j]));
            }
        }
        int rCount = 1;//Different per room. (1 because 0 is every room at initialization)
        int[] sizeMat = new int[N*M + 10];
        int max = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (vis[i][j] == 0) {//unvisited.
                    sizeMat[rCount] = dfs(i, j, data, vis, rCount);
                    max = Math.max(max, sizeMat[rCount]);
                    rCount++;
                }
            }
        }
        int totalMax = -1;
        int maxI = 0, maxJ = 0, maxWall = 0;
        for (int j = 0; j < M; j++) {
            for (int i = N - 1; i >= 0; i--) {
                int maxComb = sizeMat[vis[i][j]];
                int maxDelta = -1;
                int wall = 0;
                if (data[i][j].N) {
                    int dX = i - 1;
                    int dY = j;
                    try {
                        int othIdx = vis[dX][dY];
                        if (othIdx != vis[i][j]) {
                            if (sizeMat[othIdx] > maxDelta) {
                                wall = 1;
                                maxDelta = sizeMat[othIdx];
                            }
                        }
                    } catch (Exception ex0) { }
                }
                if (data[i][j].S) {
                    int dX = i + 1;
                    int dY = j;
                    try {
                        int othIdx = vis[dX][dY];
                        if (othIdx != vis[i][j]) {
                            if (sizeMat[othIdx] > maxDelta) {
                                wall = 2;
                                maxDelta = sizeMat[othIdx];
                            }
                        }
                    } catch (Exception ex0) { }
                }
                if (data[i][j].E) {
                    int dX = i;
                    int dY = j + 1;
                    try {
                        int othIdx = vis[dX][dY];
                        if (othIdx != vis[i][j]) {
                            if (sizeMat[othIdx] > maxDelta) {
                                wall = 3;
                                maxDelta = sizeMat[othIdx];
                            }
                        }
                    } catch (Exception ex0) { }
                }
                if (data[i][j].W) {
                    int dX = i;
                    int dY = j - 1;
                    try {
                        int othIdx = vis[dX][dY];
                        if (othIdx != vis[i][j]) {
                            if (sizeMat[othIdx] > maxDelta) {
                                wall = 4;
                                maxDelta = sizeMat[othIdx];
                            }
                        }
                        
                    } catch (Exception ex0) { }
                }
                maxComb += maxDelta;
                if (maxComb > totalMax) {
                    maxI = i;
                    maxJ = j;
                    maxWall = wall;
                    totalMax = maxComb;
                }
            }
        }
        out.append(rCount - 1 + "\n");
        out.append(max + "\n");
        out.append(totalMax + "\n");
        out.append((maxI + 1) + " " + (maxJ + 1) + " ");
        if (maxWall == 1) {
            out.append("N" + "\n");
        } else if (maxWall == 2) {
            out.append("S" + "\n");
        } else if (maxWall == 3) {
            out.append("E" + "\n");
        } else {
            out.append("W" + "\n");
        }
        out.close();
    }
    
    static int[][] DELTA = {
        { 0, -1 },
        { -1, 0 },
        { 0, 1 },
        { 1, 0 }
    };
    
    public static int dfs(int i, int j, WallData[][] adjData, int[][] vis, final int curCount) {
        int count = 1;
        vis[i][j] = curCount;
        /* try and expand */
        //NORTH:
        if (!adjData[i][j].N) {
            int dX = i - 1;
            int dY = j;
            try {
                if (vis[dX][dY] == 0) {
                    count += dfs(dX, dY, adjData, vis, curCount);
                }
            } catch (Exception ex0) { }
        }
        if (!adjData[i][j].S) {
            int dX = i + 1;
            int dY = j;
            try {
                if (vis[dX][dY] == 0) {
                    count += dfs(dX, dY, adjData, vis, curCount);
                }
            } catch (Exception ex0) { }
        }
        if (!adjData[i][j].E) {
            int dX = i;
            int dY = j + 1;
            try {
                if (vis[dX][dY] == 0) {
                    count += dfs(dX, dY, adjData, vis, curCount);
                }
            } catch (Exception ex0) { }
        }
        if (!adjData[i][j].W) {
            int dX = i;
            int dY = j - 1;
            try {
                if (vis[dX][dY] == 0) {
                    count += dfs(dX, dY, adjData, vis, curCount);
                }
            } catch (Exception ex0) { }
        }
        /*    */
        return count;
    }
}