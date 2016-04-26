import java.util.*;

class ACODE {
    
    //static long[] memo = new long[5001];
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	while (sc.hasNextLine()) {
	    String s = sc.nextLine();
	    if (s.equals("0")) break;
	    System.out.println(auxCount(s));
	}
    }
    public static long auxCount(String pool) {//speed this up
	long[] count = new long[pool.length()];
	//count[i] = # of ways from i...pool.length()-1
	if (pool.charAt(pool.length()-1) != '0') count[count.length-1] = 1;
	for (int i = pool.length() - 2; i >= 0; i--) {
	    if (pool.charAt(i) == '0') continue;
	    if (Integer.parseInt(pool.charAt(i) + "" + pool.charAt(i+1)) <= 26)
		count[i] += (i+2)>=pool.length() ? 1 : count[i+2];
	    count[i] += count[i+1];
	    if (count[i] == 0) count[i] = 1;
	}
	//System.out.println(Arrays.toString(count));
	return count[0];
	/*long count = 0;
	if (idx < pool.length()) {
	    if (memo[idx] != -1) return memo[idx];
	    if (pool.charAt(idx) == '0') return 0;//be careful with solo zero
	    count += auxCount(pool, idx+1, memo);
	    if (idx+1<pool.length()) {
		int val = Integer.parseInt(pool.charAt(idx) + "" + pool.charAt(idx+1));
		if (val <= 26)
		    count += auxCount(pool, idx+2, memo);
	    }
	} else { return 1; }
	return memo[idx] = count;*/
    }
}
