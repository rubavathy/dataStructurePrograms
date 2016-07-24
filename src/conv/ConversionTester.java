package conv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConversionTester {
	public static void main(String[] args) {

		Number N1 = new Number();
		N1.Base = 15;
		N1.Int = new short[2];
		N1.NonRep = new short[3];
		N1.Int[1] = 1;
		N1.Int[0] = 10;
		N1.NonRep[2] = 6;
		N1.NonRep[1] = 2;
		N1.NonRep[0] = 5;
		N1.Rep = new short[0];
		N1.printNumber(N1);
		Number N2 = new Number();
		short R = 2;
		N2 = N1.convert(N1, R);
		N2.printNumber(N2);
	}
}



  class Number {
	short Base;
	short[] Int, NonRep, Rep;
	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z' };

	final HashMap<String, Integer> numberMap = new HashMap<String, Integer>();

	public Number() {
		numberMap.put("a", 10);
		numberMap.put("b", 11);
		numberMap.put("c", 12);
		numberMap.put("d", 13);
		numberMap.put("e", 14);
		numberMap.put("f", 15);
		numberMap.put("g", 16);
		numberMap.put("h", 17);
		numberMap.put("i", 18);
		numberMap.put("j", 19);
		numberMap.put("k", 20);

		numberMap.put("l", 21);
		numberMap.put("m", 22);
		numberMap.put("n", 23);
		numberMap.put("o", 24);
		numberMap.put("p", 25);
		numberMap.put("q", 26);
		numberMap.put("r", 27);
		numberMap.put("s", 28);
		numberMap.put("t", 29);
		numberMap.put("u", 30);
		numberMap.put("v", 31);
		numberMap.put("w", 32);
		numberMap.put("x", 33);
		numberMap.put("y", 34);
		numberMap.put("z", 35);

	}

	// =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
	// your method for converting belongs here...
	// =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
	public Number convert(Number A, short Base) {
		Number B = new Number();
		B.Base = Base;
		B.Int = A.NonRep;
		B.NonRep = A.Int;
		B.Rep = A.NonRep;

		int inputBase = A.Base;
		short[] intPart = A.Int;
		int decimal = 0;
		int powIndex = 0;

		// convert integer part Decimal

		for (int i = 0; i < intPart.length; i++) {
			double multiply = intPart[i] * Math.pow(inputBase, powIndex);

			powIndex++;
			decimal += multiply;
		}

		// convert Fraction part to Decimal
		double fraction = convertFractionToDecimal(A.NonRep, A.Base);
		System.out.println("Fraction part in decimal::" + fraction);

		// now convert it to the required base.
		short[] intPartConverted = convertIntToBase(decimal, Base);
		B.NonRep = convertFractionToBase(fraction, Base);
		B.Int = intPartConverted;

		return B;
	}

	public short[] convertFractionToBase(double fraction, short base) {
		List<Integer> remainder = new ArrayList<>();

		int pos = 0;
		do {
			fraction = fraction * base; // Shift radix point to right by 1.
			if (fraction == 0.0)
				break;

			if (fraction > 0) // Case 1: num is greater than or equal to 1
			{
				remainder.add(pos, (int) fraction); // keep only integer
													// part
				fraction -= (int) fraction;// get rid of value left of radix
											// point
				pos++;

			} else
				// num is less than 1
				remainder.add(pos, 0);

		} while (fraction > 0);

		System.out.println("Fraction Part in " + base + " :" + remainder);

		short[] nonRepeatingPart = new short[remainder.size()];
		int i = 0;
		for (Integer intValue : remainder) {
			nonRepeatingPart[i] = intValue.shortValue();
			i++;
		}
		return nonRepeatingPart;
	}

	public short[] convertIntToBase(int decimal, short base) {
		List<Character> remainder = new ArrayList<>();
		String result = new String();
		int index = 0;
		while (decimal != 0) {
			int rem = decimal % base != 0 ? decimal % base : 0;
			remainder.add(index, digits[rem]);

			decimal = decimal / base; // integer division
			try {
				result += remainder.get(index);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			index++;

		}
		short[] integerPart = new short[remainder.size()];
		for (int i = 0; i < remainder.size(); i++) {
			try {
				integerPart[i] = new Short((remainder.get(i)).toString());
			} catch (NumberFormatException e) {
				String num = (remainder.get(i)).toString();
				short shortNum = numberMap.get(num).shortValue();
				integerPart[i] = shortNum;
			}
		}

		return integerPart;
	}

	public static double convertFractionToDecimal(short[] nonRepeating,
			short base) {
		double sum = 0;
		int length = nonRepeating.length;
		int i = 0;
		int roundOff = 1;
		int count = 0;
		while (count < length) {
			roundOff = roundOff * 10;
			count++;
		}

		for (int index = length - 1; index >= 0; index--) {
			int num = nonRepeating[index];
			i--;
			double num1 = num * Math.pow(base, i);

			num1 = Math.round(num1 * roundOff) / (double) roundOff;
			sum = sum + num1;

		}
		return sum;
	}

 

	// =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
	public void printShortArray(short[] S) {
		for (int i = S.length - 1; i >= 0; i--) {
			if (S[i] > 9) {
				System.out.print(digits[S[i] ]);
			} else {
				System.out.print(S[i]);
			}
		}
	}

	public void printNumber(Number N) {
		System.out.print("(");
		N.printShortArray(N.Int);
		System.out.print(".");
		N.printShortArray(N.NonRep);
		System.out.print("{");
		N.printShortArray(N.Rep);
		System.out.print("})_");
		System.out.println(N.Base);
	}

};
