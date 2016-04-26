import java.util.*;
import java.io.*;
public class E {
	
	public static void main(String[] args) throws Exception{
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		String word = sc.readLine();
		int length = 1;
		long ms = System.currentTimeMillis();
		//2* because we're doing a linear convolution
		while (length <= 2*1000000) {
			length *= 2;
		}
		//System.out.println("LENGTH: " + length);
		cpx[] a = new cpx[length];
		cpx[] b = new cpx[length];
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == 'A') {
				a[i] = cpx.one;
			} else {
				b[word.length()-i] = cpx.one;
			}
		}
		//System.out.println("HI1");
		a = FFT.fft(a, false);

		//System.out.println("HI2");
		b = FFT.fft(b, false);
		//System.out.println("HI3");
		cpx[] c = new cpx[length];
		for (int i = 0; i < length; i++) {
			c[i] = cpx.mult(a[i], b[i]);
		}
		//System.out.println("HI4");
		c = FFT.fft(c, true);
		//System.out.println("HI5");
		int n = word.length();
		StringBuilder bs = new StringBuilder("");
		for (int i = n+1; i < 2*n; i++) {
			bs.append(String.format("%.0f\n", c[i].a));
		}
		System.out.print(bs.toString());
		long fin = System.currentTimeMillis();
		//System.out.println("FFT1 TOOK: " + (fin-ms) + " ms.");
	}
}
class FFT {
	//must be power of two
	static cpx[] fft(cpx[] x, boolean inverse) {
		// check that length is a power of 2
        int N = x.length;

        long ms = System.currentTimeMillis();
        for (int i = 1, j = 0; i < N; i++) {
        	int bit = N >> 1;
        	for (; j >= bit; bit >>= 1)
        		j -= bit;
        	j += bit;
        	if (i < j) {
                cpx temp = x[i];
                x[i] = x[j];
                x[j] = temp;
        	}
        }


        // butterfly updates
        for (int L = 2; L <= N; L <<= 1) {
            double ang = 2 * Math.PI / L * (inverse ? -1 : 1);
            cpx wlen = cpx.exp(ang);
            for (int i = 0; i < N; i += L) {
            	cpx w = new cpx(1, 0);
                for (int j = 0; j < L/2; j++) {
                	cpx u = x[i+j];//x[i+j]
                	cpx v = null;//x[i+j+L/2].test(w)
                	if (x[i+j+L/2] != null) v = x[i+j+L/2].test(w);
                	x[i+j] = cpx.add(u, v);
                	x[i+j+L/2] = cpx.sub(u, v);
                    w = w.test(wlen);
                }
            }
        }

        if (inverse) {
        	for (int i = 0; i < N; i++) {
        		x[i] = cpx.mult_scalar(x[i], 1.0/N);
        	}
        }
        long finish = System.currentTimeMillis();
        //System.out.println("TOOK: " + (finish-ms) + " ms.");

		return x;
	}
}
class cpx {
	static	cpx one = new cpx(1, 0);
	static	cpx zero = new cpx(0, 0);
	cpx(double a, double b) {
		this.a = a;
		this.b = b;
	}
	double a, b;
	double mag() {
		return a*a+b*b;
	}
	cpx conjugate() {
		return new cpx(a, -b);
	}
	static cpx add(cpx fst, cpx snd) {
		if (fst == null) fst = zero;
		if (snd == null) snd = zero;
		return new cpx(fst.a+snd.a, fst.b+snd.b);
	}
	static cpx sub(cpx fst, cpx snd) {
		if (fst == null) fst = zero;
		if (snd == null) snd = zero;
		return new cpx(fst.a-snd.a, fst.b-snd.b);
	}
	cpx test(cpx snd) {
		cpx fst = this;
		double testa = fst.a * snd.a - fst.b * snd.b;
		double testb = fst.a * snd.b + fst.b * snd.a;
		this.a = testa;
		this.b = testb;
		return this;
	}
	static cpx mult(cpx fst, cpx snd) {
		return new cpx(fst.a * snd.a - fst.b * snd.b, fst.a * snd.b + fst.b * snd.a);
	}
	static cpx mult_scalar(cpx fst, double scalar) {
		return new cpx(fst.a * scalar, fst.b * scalar);
	}
	static cpx div(cpx fst, cpx snd) {
		cpx r = mult(fst, snd.conjugate());
		return new cpx(r.a / snd.mag(), r.b / snd.mag());
	}
	static cpx exp(double theta) {
		return new cpx(Math.cos(theta), Math.sin(theta));
	}
	public String toString() {
		return "(" + a + ", " + b + ")";
	}
}