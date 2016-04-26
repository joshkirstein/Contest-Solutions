import java.util.*;
import java.io.*;

class DSUBSEQ {
	static long MOD = 1000000007;
	public static void main(String[] args) throws Exception{
		Parser sc = new Parser(System.in); // Needs parser
		int cases = Integer.parseInt(sc.next());
		while (cases-->0) {
			String line = sc.next();
			long[] dp = new long[line.length()];
			int[] lastIdx = new int[26];
			Arrays.fill(lastIdx, -1);
			dp[0] = 2;
			lastIdx[line.charAt(0)-'A'] = 0;
			for (int i = 1; i < dp.length; i++) {
				long val;
				if (lastIdx[line.charAt(i)-'A'] >= 0 && lastIdx[line.charAt(i)-'A'] != i) {
					if (lastIdx[line.charAt(i)-'A'] == 0) val = 1;
					else val=dp[lastIdx[line.charAt(i)-'A']-1]; 
				}
				else val = 0;
				dp[i] = ((dp[i-1]%MOD*(2))%MOD - val + MOD) % MOD;
				lastIdx[line.charAt(i)-'A'] = i;
			}
			System.out.println(dp[dp.length-1]%MOD);
		}
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