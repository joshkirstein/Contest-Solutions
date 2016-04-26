import java.util.*;
import java.io.*;

class PONY2 {
    public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	int cases = Integer.parseInt(br.readLine());
	while (cases-->0) {
	    count = null;
	    String[] spl = br.readLine().split(" ");
	    long N = Long.parseLong(spl[1]);
	    String pool = spl[0];
	    int poolIdx = 0;
	    String prefix = "";
	    long sum = 1;
	    for (char ch = 'a'; ch <= 'z'; ch++) {
		//try to add ch to prefix
		long add = count(prefix, pool, ch, poolIdx);
		if (add == -1) continue;//ch not after poolIdx
		if (sum == N && sum+add == N && poolIdx >= pool.length()) break;
		if (N >= sum && N <= sum+add-1) {//N has prefix+ch...
		    //use prefix + ch
		    prefix = prefix + ch;
		    if (((int)(ch-'a'+1))>=10) poolIdx += 2;
		    else poolIdx += 1;
		    ch = (char)('a' - 1);
		    continue;
		} else {//N isn't.
		    sum += add;
		    continue;
		}
	    }
	    System.out.println(prefix);
	}
    }

    public static long count(String prefix, String pool, char ch, int poolIdx) {
	int val = (int) (ch-'a')+1;
	int delt = 0;
	if (val >= 10) {
	    if ((pool.length()-poolIdx) >= 2) {
		if (Integer.parseInt(pool.charAt(poolIdx) + "" + pool.charAt(poolIdx+1)) != val) return -1;
		else delt = 2;
	    } else return -1;
	} else {
	    if ((pool.length()-poolIdx) >= 1) {
		if (Integer.parseInt(pool.charAt(poolIdx) + "") != val) return -1;
		else delt = 1;
	    } else return -1;
	}
	return auxCount(pool, poolIdx+delt);
    }

    static long[] count;
    public static long auxCount(String pool, int idx) {
	if (idx >= pool.length()) return 1;
	if (count == null) {
	    count = new long[pool.length()];
	    //count[i] = # of ways from i...pool.length()-1
	    if (pool.charAt(pool.length()-1) != '0') count[count.length-1] = 1;
	    for (int i = pool.length() - 2; i >= 0; i--) {
		if (pool.charAt(i) == '0') continue;
		if (Integer.parseInt(pool.charAt(i) + "" + pool.charAt(i+1)) <= 26)
		    count[i] += (i+2)>=pool.length() ? 1 : count[i+2];
		count[i] += count[i+1];
		if (count[i] == 0) count[i] = 1;
	    }
	}
	return count[idx];
    }
}
