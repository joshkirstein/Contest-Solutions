import java.util.*;
import java.io.*;

class ACPC10D {
	public static void main(String[] args) throws Exception{
		Parser sc = new Parser(System.in);
		int c = 1;
         StringBuilder sb = new StringBuilder();
		while (true) {
			int N = sc.nextInt();
			if (N == 0) break;
			int[][] costs = new int[N][3];
         int[][] dp = new int[N][3];
			for (int i = 0; i < N; i++) {
				costs[i][0] = sc.nextInt();
				costs[i][1] = sc.nextInt();
				costs[i][2] = sc.nextInt();
            dp[i][0] = Integer.MAX_VALUE;
            dp[i][1] = Integer.MAX_VALUE;
            dp[i][2] = Integer.MAX_VALUE;
			}
			//dp[0][0] = costs[0][0];
			dp[0][1] = costs[0][1];
			//dp[0][2] = costs[0][2]+costs[0][1];//Have to have middle...
			for (int i = 0; i < N; i++) {
				//for (int j = (i == 0) ? 1 : 0; j < 3; j++) {
					//if (j == 0) {
						//(0, 1)
						//(1, 0)
						//(1, 1)
               if (i != 0) {
						dp[i][1] = Math.min(dp[i][1], dp[i][0]+costs[i][1]);
						if (i+1 < N) {
							dp[i+1][0] = Math.min(dp[i+1][0], dp[i][0]+costs[i+1][0]);
							dp[i+1][1] = Math.min(dp[i+1][1], dp[i][0]+costs[i+1][1]);
						}
               }
					//} else if (j == 1) {
						//(1, 0)
						//(1, -1)
						//(1, 1)
						//Left:
						dp[i][2] = Math.min(dp[i][2], dp[i][1]+costs[i][2]);
						if (i+1<N) {
							dp[i+1][1] = Math.min(dp[i+1][1], dp[i][1]+costs[i+1][1]);
							dp[i+1][0] = Math.min(dp[i+1][0], dp[i][1]+costs[i+1][0]);
							dp[i+1][2] = Math.min(dp[i+1][2], dp[i][1]+costs[i+1][2]);
						}
					//} else if (j == 2) {
						//(1, -1)
						//(1, 0)
						if (i+1<N) {
							dp[i+1][1] = Math.min(dp[i+1][1], dp[i][2]+costs[i+1][1]);
							dp[i+1][2] = Math.min(dp[i+1][2], dp[i][2]+costs[i+1][2]);
						}
					//}
				//}
				//System.out.println(Arrays.toString(dp[i]));
			}
			sb.append((c++) + ". " + dp[N-1][1] + "\n");
		}
      System.out.print(sb.toString());
	}
}
class Parser
{
   final private int BUFFER_SIZE = 1 << 16;

   private DataInputStream din;
   private byte[] buffer;
   private int bufferPointer, bytesRead;

   public Parser(InputStream in)
   {
      din = new DataInputStream(in);
      buffer = new byte[BUFFER_SIZE];
      bufferPointer = bytesRead = 0;
   }

   public long nextLong() throws Exception
   {
      long ret = 0;
      byte c = read();
      while (c <= ' ') c = read();
      boolean neg = c == '-';
      if (neg) c = read();
      do
      {
         ret = ret * 10 + c - '0';
         c = read();
      } while (c > ' ');
      if (neg) return -ret;
      return ret;
   }
   
   //reads in the next string
   public String next() throws Exception
   {
      StringBuilder ret =  new StringBuilder();
      byte c = read();
      while (c <= ' ') c = read();
      do
      {
         ret = ret.append((char)c);
         c = read();
      } while (c > ' ');
      return ret.toString();
   }

   public int nextInt() throws Exception
   {
      int ret = 0;
      byte c = read();
      while (c <= ' ') c = read();
      boolean neg = c == '-';
      if (neg) c = read();
      do
      {
         ret = ret * 10 + c - '0';
         c = read();
      } while (c > ' ');
      if (neg) return -ret;
      return ret;
   }
   
   private void fillBuffer() throws Exception
   {
      bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
      if (bytesRead == -1) buffer[0] = -1;
   }

   private byte read() throws Exception
   {
      if (bufferPointer == bytesRead) fillBuffer();
      return buffer[bufferPointer++];
   }
}