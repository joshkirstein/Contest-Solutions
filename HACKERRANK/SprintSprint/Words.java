import java.util.*;

public class Words {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int H = sc.nextInt();
		int M = sc.nextInt();

		if (M == 0) {
			System.out.println(convert(H).trim() + " o' clock");
		} else if (M == 1) {
			System.out.println("one minute past " + convert(H).trim());
		} else if (M == 15) { 
			System.out.println("quarter past " + convert(H).trim());
		} else if (M >= 2 && M < 30) {
			System.out.println(convert(M).trim() + " minutes past " + convert(H).trim());
		} else if (M == 30) {
			System.out.println("half past " + convert(H).trim());
		} else if (M == 45) {
			int plus = H+1;
			if (plus == 13) plus = 1;
			System.out.println("quarter to " + convert(plus).trim());
		} else if (M == 59) {

			int res = 60-M;
			int plus = H+1;
			if (plus == 13) plus = 1;
			System.out.println(convert(res).trim() + " minute to " + convert(plus).trim());
		} else {
			int res = 60-M;
			int plus = H+1;
			if (plus == 13) plus = 1;
			System.out.println(convert(res).trim() + " minutes to " + convert(plus).trim());
		}
	}


	private static final String[] tensNames = {
    "",
    " ten",
    " twenty",
    " thirty",
    " forty",
    " fifty",
    " sixty",
    " seventy",
    " eighty",
    " ninety"
  };

  private static final String[] numNames = {
    "",
    " one",
    " two",
    " three",
    " four",
    " five",
    " six",
    " seven",
    " eight",
    " nine",
    " ten",
    " eleven",
    " twelve",
    " thirteen",
    " fourteen",
    " fifteen",
    " sixteen",
    " seventeen",
    " eighteen",
    " nineteen"
  };

  private static String convert(int number) {
    String soFar;

    if (number % 100 < 20){
      soFar = numNames[number % 100];
      number /= 100;
    }
    else {
      soFar = numNames[number % 10];
      number /= 10;

      soFar = tensNames[number % 10] + soFar;
      number /= 10;
    }
    if (number == 0) return soFar;
    String res = numNames[number] + " hundred" + soFar;
    return res.trim();
  }
}