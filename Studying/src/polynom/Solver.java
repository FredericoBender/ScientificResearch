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

public class Solver {
    private Shturm shturm;

    public Solver() {
        shturm = new Shturm();
    }

    public Solver(double eps) {
        shturm = new Shturm(eps);
    }

    public double[] solve(Polynom polynom) {

        if (polynom.degree() == 0) {
            return new double[]{};
        } else if (polynom.degree() == 1) {
            double x;
            double[] c = polynom.getCoeffs();

            x = -c[1] / c[0];

            return new double[]{x};
        } else if (polynom.degree() == 2) {
            double d;
            double[] c = polynom.getCoeffs();

            d = Math.pow(c[1], 2) - 4 * c[2] * c[0];

            if (d == 0) {
                double x;

                x = -c[1] / (2 * c[2]);
                return new double[]{x};
            } else if (d > 0) {
                double x1, x2;

                x1 = (-c[1] + Math.sqrt(d)) / (2 * c[2]);
                x2 = (-c[1] - Math.sqrt(d)) / (2 * c[2]);

                return new double[]{x1, x2};
            } else if (d < 0) {
                return new double[]{};
            }
        } else if (polynom.degree() > 2) {
            Polynom q = polynom.gcd(polynom.differentiate());

            if (q.equals(1.0)) {
                return shturm.solve(polynom);
            } else {
                Polynom p = polynom.mod(q)[0];
                return shturm.solve(p);
            }
        }

        return new double[]{};
    }
}
