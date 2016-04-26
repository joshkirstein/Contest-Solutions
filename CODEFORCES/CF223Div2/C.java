import java.util.*;

public class C {

    static class Stage {
	int type, num1, num2;
	long idxLo, idxHi;//long is important
	public Stage(int type, int num1, int num2, long idxLo, long idxHi) {
	    this.type = type;
	    this.num1 = num1;
	    this.num2 = num2;
	    this.idxLo = idxLo;
	    this.idxHi = idxHi;
	}
    }

    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int M = sc.nextInt();//as described in input
	Stage[] stages = new Stage[M];
	int[] precomp = new int[100001];//precompute first 100000 values
	int precompIdx = 0;
	for (int i = 0; i < M; i++) {
	    int type = sc.nextInt();
	    if (type == 1) {
		int x = sc.nextInt();
		if (precompIdx <= 100000) {
		    precomp[precompIdx++] = x;
		}
		if (i == 0) {
		    stages[i] = new Stage(1, x, 123456, 0, 0);
		} else {
		    stages[i] = new Stage(1, x, 666, stages[i-1].idxHi+1, stages[i-1].idxHi+1);//in stage 1, num2 is unimportant
		}
	    } else {
		int length = sc.nextInt();
		int copies = sc.nextInt();
		stages[i] = new Stage(2, length, copies, stages[i-1].idxHi+1, stages[i-1].idxHi+length*copies);
		for (int x = 0; x < copies && precompIdx <= 100000; x++)
		    for (int y = 0; y < length && precompIdx <= 100000; y++)
			precomp[precompIdx++] = precomp[y];
	    }
	}
	int numElements = sc.nextInt();
	StringBuilder br = new StringBuilder("");//faster way to output when numElements is big
	for (int i = 0; i < numElements; i++) {
	    long index = sc.nextLong()-1;//long important
	    int elem = -1;
	    //idea binary search to element range...if range is type 1 output x, otherwise reduce search to 1...l and re-adjust indices
	    int lo = 0, hi = M-1;
	    while (lo <= hi) {
		int mid = lo + ((hi-lo)/2);
		if (stages[mid].idxLo <= index && stages[mid].idxHi >= index) {
		    if (stages[mid].type == 1) {
			elem = stages[mid].num1;
		    } else {//type 2 handle
			long adjusted = index - stages[mid].idxLo;//between [0, c*l-1]
			int realIdx = (int) adjusted % stages[mid].num1;
			elem = precomp[realIdx];
		    }
		    break;
		} else {
		    if (stages[mid].idxLo > index) {
			hi = mid-1;//careful here
		    } else {
			lo = mid+1;
		    }
		}
	    }
	    br.append(elem + " ");
	}
	System.out.println(br.toString());
    }
}
