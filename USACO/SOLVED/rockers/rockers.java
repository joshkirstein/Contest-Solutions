/*
ID: joshkir1
LANG: JAVA
TASK: rockers
*/
import java.io.*;
import java.util.*;

class rockers
{
	public static int disks;
	public static int songs;
	public static int[] songList;
	public static int diskSize;

	public static byte[][] MEMO;//DP2[i][j] = most that can fit on ONE DISK with of size i with j as used bitmask...
	//This can be done better since if we layed out the disks in order, the songs ***ACROSS ALL DISKS*** must be ordered
	//by the date they were written as follows:
	//DP[i][j][k] = max number of disks where i is current current disk, j is size of current disk, k is idx of last cd used.
	// = for (int l = 0; l < songs; l++) 
	//		if (song[l] <= j) [we can take song l]
	//			get max of DP[i][j-song[l]][l]
	//the parameter k makes the bitmask essentially useless...it's only there because of early attempts...it ended up working
	//Switching to BYTES was necessary because the array was taking up too much space.
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("rockers.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("rockers.out")));
		songs = sc.nextInt();
		diskSize = sc.nextInt();
		disks = sc.nextInt();
		songList = new int[songs];
		for (int i = 0; i < songs; i++) {
			songList[i] = sc.nextInt();
		}
		MEMO = new byte[diskSize+1][(int) (Math.pow(2, songs)+1)];
		for (byte[] arr : MEMO) {
			Arrays.fill(arr, (byte) -1);
		}
		byte max = DP(0, diskSize, 0, 0);
		out.print(max + "\n");
		out.close();
		System.exit(0);
	}

	public static byte DP(int diskNumber, int curSize, int used, int firstIdx) { // used = used songs. used=[0, 2^disks]
		if (diskNumber >= disks) return 0;
		if (curSize <= 0) return DP(diskNumber+1, diskSize, used, firstIdx);
		if (MEMO[curSize][used] != -1) return MEMO[curSize][used];
		byte max = Byte.MIN_VALUE;
		boolean allUsed = true;
		boolean couldTake = false;
		for (int i = firstIdx; i < songs; i++) {
			int mask = used & (1 << i);
			if (mask == 0) { // bit not used.
				allUsed = false;
				int cost = songList[i];
				if (cost <= curSize) {
					couldTake = true;
					int andMask = used | (1 << i);
					max = (byte) Math.max(max, 1 + DP(diskNumber, curSize - cost, andMask, i));
				}
			}
		}
		max = (byte) Math.max(max, DP(diskNumber+1, diskSize, used, firstIdx));
		if (allUsed) return 0;
		return MEMO[curSize][used] = max;
	}
}