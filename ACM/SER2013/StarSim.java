
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class StarSim {
    
    static class Point implements Comparable<Point> {
        
        static int idSt = 0;
        public int x, y, z, id;
        
        public Point(int xx, int yy, int zz) {
            this.x = xx;
            this.y = yy;
            this.z = zz;
            id = ++idSt;
        }
        
        @Override
        public int compareTo(Point o) {
            if (o.z == z) {
                if (o.y == y) {
                    return x - o.x;
                } else {
                    return y - o.y;
                }
            } else {
                return z - o.z;
            }
        }
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + id;
            result = prime * result + x;
            result = prime * result + y;
            result = prime * result + z;
            return result;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Point other = (Point) obj;
            if (id != other.id) {
                return false;
            }
            if (x != other.x) {
                return false;
            }
            if (y != other.y) {
                return false;
            }
            if (z != other.z) {
                return false;
            }
            return true;
        }
        
        public String toString() {
            return "(" + x + "," + y + "," + z + ")";
        }
    }
    static int K;
    static ArrayList<Point> arr;
    
    public static void main(String[] args) throws Exception {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        //long ms = System.currentTimeMillis();
        while (true) {
            String[] spl = sc.readLine().split(" ");
            int N = Integer.parseInt(spl[0]);
            K = Integer.parseInt(spl[1]);
            if (N == 0 && K == 0) {
                break;
            }
            arr = new ArrayList<Point>(N);
            for (int i = 0; i < N; i++) {
                spl = sc.readLine().split(" ");
                arr.add(new Point(Integer.parseInt(spl[0]), Integer.parseInt(spl[1]), Integer.parseInt(spl[2])));
            }
            ds1=null;ds2=null;
            Collections.sort(arr);
            double[] splt = split(arr, 0, arr.size() - 1);
            //check if spl[2].get(0) == -1...
            if (splt[0] == -1) {//
                System.out.println(do2d(0, arr.size() - 1));
            } else {
                System.out.println(dq((int) splt[0], (int) splt[1], (int) splt[2], (int) splt[3], splt[4], splt[4]));
            }
        }
        //System.out.println((System.currentTimeMillis()-ms)/1000 + " seconds");
    }
    
    public static boolean less(Point p1, Point p2) {
        return Math.sqrt((Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2) + Math.pow(p1.z - p2.z, 2))) < K;
    }
    
    public static int do2d(int lo, int hi) {
        double[] spl = splity(arr, lo, hi);
        if (spl[0] == -1) {
            return do1d(lo, hi);
        } else {
            return dq3((int) spl[0], (int) spl[1], (int) spl[2], (int) spl[3], spl[4]);
        }
    }
    
    public static int do1d(int lo, int hi) {
        int cnt = 0;
        for (int i = lo; i <= hi; i++) {
            Point p = arr.get(i);
            int x = p.x;
            for (int j = i+1; j <= hi; j++) {
                if (less(p, arr.get(j))) {
                    cnt++;
                } else break;
            }
        }
        return cnt;
    }
    
    public static int dq3(int lo1, int hi1, int lo2, int hi2, double y) {
        int one = 0, two = 0, thr = 0;
        if (hi1 - lo1 + 1 == 2) {
            Point p1 = arr.get(lo1);
            Point p2 = arr.get(hi1);
            if (less(p1, p2)) {
                one++;
            }
        } else if (hi1 - lo1 + 1 > 2) {
            double[] splOne = splity(arr, lo1, hi1);
            if (splOne[0] == -1) {//run 1d
                one += do1d(lo1, hi1);
            } else {
                one += dq3((int) splOne[0], (int) splOne[1], (int) splOne[2], (int) splOne[3], splOne[4]);
            }
        }
        if (hi2 - lo2 + 1 == 2) {
            Point p1 = arr.get(lo2);
            Point p2 = arr.get(hi2);
            if (less(p1, p2)) {
                one++;
            }
        } else if (hi2 - lo2 + 1 > 2) {
            double[] splOne = splity(arr, lo2, hi2);
            if (splOne[0] == -1) {//tells us all one a line.run 1d version!
                one += do1d(lo2, hi2);
            } else {
                one += dq3((int) splOne[0], (int) splOne[1], (int) splOne[2], (int) splOne[3], splOne[4]);
            }
        }
        TreeMap<Double, ArrayList<Point>> ds1 = new TreeMap<Double, ArrayList<Point>>();
        TreeMap<Double, ArrayList<Point>> ds2 = new TreeMap<Double, ArrayList<Point>>();
        for (int i = lo1; i <= hi1; i++) {
            Point p = arr.get(i);
            double dd = Math.abs(p.y - y);
            if (y-p.y>K) continue;
            if (ds1.containsKey(dd)) {
                ds1.get(dd).add(p);
            } else {
                ArrayList<Point> withP = new ArrayList<Point>();
                withP.add(p);
                ds1.put(dd, withP);
            }
        }
        for (int i = lo2; i <= hi2; i++) {
            Point p = arr.get(i);
            double dd = Math.abs(p.y - y);
            if (p.y-y > K) continue;
            if (ds2.containsKey(dd)) {
                ds2.get(dd).add(p);
            } else {
                ArrayList<Point> withP = new ArrayList<Point>();
                withP.add(p);
                ds2.put(dd, withP);
            }
        }
        for (Map.Entry<Double, ArrayList<Point>> ent : ds1.entrySet()) {
            for (Point p : ent.getValue()) {
                double max = Math.abs(K - ent.getKey());
                SortedMap<Double, ArrayList<Point>> good = ds2.headMap(max+1);
                for (Map.Entry<Double, ArrayList<Point>> ent2 : good.entrySet()) {
                    for (Point p2 : ent2.getValue()) {
                        if (p.y == p2.y) continue;
                        if (less(p, p2)) {
                            thr++;
                        }
                    }
                }
            }
        }
        return one + two + thr;
    }
    static TreeMap<Double, ArrayList<Point>> ds1;
    static TreeMap<Double, ArrayList<Point>> ds2;
    static double crW;
    public static int dq(int lo1, int hi1, int lo2, int hi2, double z, double oldSplit) {
        int one = 0, two = 0, thr = 0;
        if (hi1 - lo1 + 1 == 2) {
            Point p1 = arr.get(lo1);
            Point p2 = arr.get(hi1);
            if (less(p1, p2)) {
                one++;
            }
        } else if (hi1 - lo1 + 1 > 2) {
            double[] splOne = split(arr, lo1, hi1);
            if (splOne[0] == -1) {//tells us all split on plane..run 2d version!
                one += do2d(lo1, hi1);
            } else {
                one += dq((int) splOne[0], (int) splOne[1], (int) splOne[2], (int) splOne[3], splOne[4], z);
            }
        }
        if (hi2 - lo2 + 1 == 2) {
            Point p1 = arr.get(lo2);
            Point p2 = arr.get(hi2);
            if (less(p1, p2)) {
                one++;
            }
        } else if (hi2 - lo2 + 1 > 2) {
            double[] splOne = split(arr, lo2, hi2);
            if (splOne[0] == -1) {//tells us all on plane..run 2d version!
                one += do2d(lo2, hi2);
            } else {
                one += dq((int) splOne[0], (int) splOne[1], (int) splOne[2], (int) splOne[3], splOne[4], z);
            }
        }
        //if (ds1 == null || ds2 == null) {
        ds1 = new TreeMap<Double, ArrayList<Point>>();
        ds2 = new TreeMap<Double, ArrayList<Point>>();
        crW = z;
        for (int i = lo1; i <= hi1; i++) {
            Point p = arr.get(i);
            double dd = Math.abs(p.z - z);
            if (z-p.z > K) continue;
            if (ds1.containsKey(dd)) {
                ds1.get(dd).add(p);
            } else {
                ArrayList<Point> withP = new ArrayList<Point>();
                withP.add(p);
                ds1.put(dd, withP);
            }
        }
        for (int i = lo2; i <= hi2; i++) {
            Point p = arr.get(i);
            double dd = Math.abs(p.z - z);
            if (p.z-z > K) continue;
            if (ds2.containsKey(dd)) {
                ds2.get(dd).add(p);
            } else {
                ArrayList<Point> withP = new ArrayList<Point>();
                withP.add(p);
                ds2.put(dd, withP);
            }
        }
        for (Map.Entry<Double, ArrayList<Point>> ent : ds1.entrySet()) {
            for (Point p : ent.getValue()) {
                double max = Math.abs(K - ent.getKey());
                SortedMap<Double, ArrayList<Point>> good = ds2.headMap(max);
                for (Map.Entry<Double, ArrayList<Point>> ent2 : good.entrySet()) {
                    for (Point p2 : ent2.getValue()) {
                        if (p.z == p2.z) continue;//handled by 2d...
                        if (less(p, p2)) {
                            thr++;
                        }
                    }
                }
            }
        }
        //}
        return one + two + thr;
    }
    
    public static double[] split(ArrayList<Point> pts, int lo, int hi) {
        if (pts.size() == 0) {
            return new double[]{-5.0};
        }
        double zFirst = pts.get(lo).z;
        double zSec = pts.get(hi).z;
        double av = zFirst + (zSec - zFirst) / 2;
        int lo1 = lo;
        int hi1 = lo;
        int lo2 = lo;
        int hi2 = hi;
        if (av != zSec) {//if points not all cut by the same plane...
            for (int i = lo; i <= hi; i++) {
                if (pts.get(i).z >= av) {
                    hi1 = i - 1;
                    lo2 = i;
                    break;
                }
            }
            return new double[]{lo1, hi1, lo2, hi2, av};
        } else {
            return new double[]{-1};
        }
    }
    
    public static double[] splity(ArrayList<Point> pts, int lo, int hi) {
        if (pts.size() == 0) {
            return new double[]{-5.0};
        }
        double zFirst = pts.get(lo).y;
        double zSec = pts.get(hi).y;
        double av = zFirst + (zSec - zFirst) / 2;
        int lo1 = lo;
        int hi1 = lo;
        int lo2 = lo;
        int hi2 = hi;
        if (av != zSec) {//if points not all cut by the same plane...
            for (int i = lo; i <= hi; i++) {
                if (pts.get(i).y >= av) {
                    hi1 = i - 1;
                    lo2 = i;
                    break;
                }
            }
            return new double[]{lo1, hi1, lo2, hi2, av};
        } else {
            return new double[]{-1};
        }
    }
}