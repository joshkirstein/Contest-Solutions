/*
ID: joshkir1
LANG: JAVA
TASK: job
*/
import java.io.*;
import java.util.*;

class job
{
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(new FileReader("job.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("job.out")));

		int N = sc.nextInt();
		int M1 = sc.nextInt();
		int M2 = sc.nextInt();
		int[] A = new int[M1];
		int[] B = new int[M2];
		for (int i = 0; i < M1; i++) A[i] = sc.nextInt();
		for (int i = 0; i < M2; i++) B[i] = sc.nextInt();

		int[] timeA = new int[M1];
		int[] timeB = new int[M2];

		int[] finA = new int[N];
		int[] finB = new int[N];
		for (int i = 0; i < N; i++) {
			int min = Integer.MAX_VALUE, idx = -1;
			for (int j = 0; j < M1; j++) {
				int timeToFinish = A[j]+timeA[j];
				if (timeToFinish < min) {
					min = timeToFinish;
					idx = j;
				}
			}

			finA[i] = min;
			timeA[idx] += A[idx];
		}
		for (int i = 0; i < N; i++) {
			int min = Integer.MAX_VALUE, idx = -1;
			for (int j = 0; j < M2; j++) {
				int timeToFinish = B[j]+timeB[j];
				if (timeToFinish < min) {
					min = timeToFinish;
					idx = j;
				}
			}
			finB[i] = min;
			timeB[idx] += B[idx];
		}
		//System.out.println(Arrays.toString(finA) + " " + Arrays.toString(finB));
		int bestB = 0;
		for (int i = 0; i < N; i++) {
			bestB = Math.max(bestB, finA[i]+finB[N-i-1]);
		}
		System.out.println(finA[N-1] + " " + bestB);
		out.println(finA[N-1] + " " + bestB);
		out.close();
		System.exit(0);
	}
}