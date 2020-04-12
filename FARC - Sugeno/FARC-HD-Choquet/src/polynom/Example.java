/*
  Copyright (c) 2017 Leontev Daniil
  prohormitrich53@gmail.com
  <p>
  Permission is hereby granted, free of charge, to any person obtaining
  a copy of this software and associated documentation files (the
  "Software"), to deal in the Software without restriction, including
  without limitation the rights to use, copy, modify, merge, publish,
  distribute, sublicense, and/or sell copies of the Software, and to
  permit persons to whom the Software is furnished to do so, subject to
  the following conditions:
  <p>
  The above copyright notice and this permission notice shall be included
  in all copies or substantial portions of the Software.
  <p>
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
  SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package polynom;

import java.util.Arrays;

public class Example {
    public static void main(String[] args) {

        /*

          You can create new polynomial using several ways.

         */

        Polynom p1 = new Polynom(); //creates "x^0" polynomial, which is exactly "1.0"
        System.out.println(p1); //Will print "1.0"

        /*

          You can send an array of coefficients to constructor.

         */

        int[] intCoefficients = {1, 15, 4, 0, 3};
        Polynom p2 = new Polynom(intCoefficients);
        System.out.println(p2); //Will print "1.0 + 15.0·x + 4.0·x^2 + 3.0·x^4". double[] arrays of course supported

        /*

          You can create polynomials like 3·x^4 and 10.5·x^7 easily with special constructor.

         */

        int a = 3;
        int aDegree = 4;
        double b = 10.5;
        int bDegree = 7;
        Polynom p3 = new Polynom(a, aDegree);
        Polynom p4 = new Polynom(b, bDegree);
        System.out.println(p3); //Will print "3.0·x^4"
        System.out.println(p4); //Will print "10.5·x^7"

        /*

          You can create polynomials directly from string keeping any rules.
          Correct variants listed below.

         */


        String s5 = " -18*x^2 + 14*x + 5 ";
        String s6 = " +7.5 + 5.0·x + 40·x^2 + 3x^4";
        String s7 = "10x^10";
        Polynom p5 = new Polynom(s5);
        Polynom p6 = new Polynom(s6);
        Polynom p7 = new Polynom(s7);
        System.out.println(p5);
        System.out.println(p6);
        System.out.println(p7); //All three variants will print correctly evaluated polynomial.

        //String incorrect = "(x-3)(x-2)(x-1)";
        //Polynom p = new Polynom(incorrect); // Will throw the IllegalArgumentException.

        /*

          There are some useful methods listed below

         */

        double[] gettedCoeffs = p2.getCoeffs(); // getCoeffs() will return array of double[] with coefficients of polynomial
        System.out.println(Arrays.toString(gettedCoeffs)); //Will print [1.0, 15.0, 4.0, 0.0, 3.0]

        int n2 = p2.degree(); // degree() will return the degree of polynomial
        System.out.println(n2); // 4

        boolean b2 = p2.isConst(); // check is polynomials degree is 0
        System.out.println(b2); // false

        boolean b21 = p2.equals(p1); // will compare two polynomials. Also can compare to int and double number
        System.out.println(b21); // false

        boolean b11 = p1.equals(1);
        System.out.println(b11); // true

        /*

          There are several algebra operations: addition, subtraction, multiplication, modulo operation, exponentiation,
          differentiation, signum operation, solving equations p(x) = 0, greatest common divisor for two polynomials.

         */

        Polynom p8 = p3.add(p4); // p8 = p3 + p4 -- addition
        System.out.println(p8);

        Polynom p9 = p3.subtract(p5); // p9 = p3 - p5 -- subtraction
        System.out.println(p9);


        Polynom p10 = p3.multiply(p6); // p10 = p3 * p6 -- multiplication
        System.out.println(p10);

        Polynom[] p11 = p6.mod(p5); // Will return an array with quotient and residue [quotient, residue]
        System.out.println(Arrays.toString(p11));

        Polynom p12 = p3.power(3); // p12 = (p3)^3
        System.out.println(p12);

        Polynom p13 = p6.differentiate(); // p13 is derivative of p6
        System.out.println(p13);

        Polynom p14 = p6.differentiate(4); // p14 is 4'th derivative of p6
        System.out.println(p14);

        int s3 = p3.sign(15); // Will return the signum of p3 at x = 15;
        System.out.println(s3); // 1

        int s4 = p3.sign(Double.NEGATIVE_INFINITY); // Sign on -Infinity
        System.out.println(s4);

        double y = p3.valueOf(99); // Will calculate the value of p3 at x = 99
        System.out.println(y);

        int[] xs = {1, 2, 3, 4};
        double[] ys = p4.valueOf(xs); // Will return array with values of p4 in each point from xs array
        System.out.println(Arrays.toString(ys));

        /*

          You can easily find all real roots of polynomial of any degree, including multiple roots with method solve()

         */

        Polynom p15 = new Polynom("x^6 - 21x^5 + 175x^4 - 735x^3 + 1624x^2 - 1764x + 720");
        double[] roots = p15.solve(); // Will solve p15 = 0 equation
        System.out.println(Arrays.toString(roots));

        roots = p15.solve(0.001); // You can set accuracy as argument
        System.out.println(Arrays.toString(roots));

        Polynom p16 = p15.gcd(p13); // Will return the greatest common divisor of p15 and p13
        System.out.println(p16);

        Polynom p17 = new Polynom("1-x+x^2-x^3"); // Will return the antiderivative polynomial with given с
        System.out.println(p17.antiderivative(77)); // default c = 0

        double integral = p17.integrate(0, 1); // Integrate from a to b
        System.out.println(integral);

    }
}