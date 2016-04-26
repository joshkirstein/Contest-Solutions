import java.util.*;

public class C {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int ghosts = sc.nextInt();
		int candleBurn = sc.nextInt();
		int numBurning = sc.nextInt();
		int last = 0;
		int cnt = 0;
		TreeSet<Integer> total = new TreeSet<Integer>();
		TreeSet<Integer> candles = new TreeSet<Integer>();
		for (int z = 0; z < ghosts; z++) {
			int ghost = sc.nextInt();
			int move = ghost-last;
			
			//filter out burned out candles
			TreeSet<Integer> candlesNow = new TreeSet<Integer>();
			for (Integer i : candles) {
				//System.out.println("FILTER: " + i + " DELT: " + (ghost-i));
				if ((ghost-i) <= candleBurn) candlesNow.add(i);
			}
			
			int need = numBurning-candlesNow.size();
			//System.out.println("NEED: " + need);
			for (int i = ghost-1; need > 0; need--, i--) {
				if ((ghost-i) > candleBurn) {
					System.out.println("-1");
					return;
				} else {
					if (total.contains(i)) continue;
					candlesNow.add(i);
					total.add(i);
					cnt++;
					//System.out.println("ADDING CANDLE: @ " + i + " FOR GHOST: " + ghost);
				}
			}
			candles = candlesNow;
			last = ghost;
		}
		System.out.println("" + cnt);
	}
}
