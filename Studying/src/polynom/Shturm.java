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

import java.util.ArrayList;
import java.util.Arrays;

public class Shturm {
    private double eps = 0.00001;

    public Shturm() {

    }

    public Shturm(double eps) {
        this.eps = eps;
    }

    public void setAccuracy(double eps) {
        this.eps = eps;
    }

    private Polynom[] buildShturmRow(Polynom polynom) {
        ArrayList<Polynom> shturmRow = new ArrayList<>();
        shturmRow.add(polynom);
        shturmRow.add(polynom.differentiate());

        Polynom p;
        int i = 1;
        do {
            p = shturmRow.get(i - 1).mod(shturmRow.get(i))[1].multiply(-1);
            shturmRow.add(p);
            i++;
        } while (p.getCoeffs().length > 1);

        Polynom[] row = new Polynom[shturmRow.size()];

        return shturmRow.toArray(row);
    }

    private int rootsCount(Polynom polynom) {
        Polynom[] shturmRow = buildShturmRow(polynom);
        int[] mIsigns = new int[shturmRow.length];
        int[] pIsigns = new int[shturmRow.length];
        int c1 = 0;
        int c2 = 0;

        for (int i = 0; i < shturmRow.length; i++) {
            mIsigns[i] = shturmRow[i].sign(Double.NEGATIVE_INFINITY);
            pIsigns[i] = shturmRow[i].sign(Double.POSITIVE_INFINITY);
        }

        for (int i = 1; i < shturmRow.length; i++) {
            if (mIsigns[i - 1] != mIsigns[i]) {
                c1++;
            }
            if (pIsigns[i - 1] != pIsigns[i]) {
                c2++;
            }
        }

        return c1 - c2;

    }

    private int rootsCount(Polynom polynom, double a, double b) {
        Polynom[] shturmRow = buildShturmRow(polynom);
        int[] aSigns = new int[shturmRow.length];
        int[] bSigns = new int[shturmRow.length];
        int c1 = 0;
        int c2 = 0;

        for (int i = 0; i < shturmRow.length; i++) {
            aSigns[i] = shturmRow[i].sign(a);
            bSigns[i] = shturmRow[i].sign(b);
        }

        for (int i = 1; i < shturmRow.length; i++) {
            if (aSigns[i - 1] != aSigns[i]) {
                if (aSigns[i] != 0) {
                    c1++;
                }
            }
            if (bSigns[i - 1] != bSigns[i]) {
                if (bSigns[i] != 0) {
                    c2++;
                }
            }
        }

        return c1 - c2;
    }

    private double findInterval(Polynom polynom) {
        int n = polynom.degree();
        double[] c = polynom.getCoeffs();
        double[] candidates = new double[n];

        for (int i = 0; i < n; i++) {
            double t = Math.abs(c[n - i - 1] * n / c[n]);
            t = Math.pow(t, 1.0 / (i + 1));
            candidates[i] = t;
        }

        Arrays.sort(candidates);

        return candidates[n - 1];
    }

    private double[] localizeRoots(Polynom polynom) {
        double limit = findInterval(polynom);
        if (rootsCount(polynom) != rootsCount(polynom, -limit, limit)) {
            String error = "Something went wrong with roots finding. Please, write to prohormitrich53@gmail.com";
            throw new ArithmeticException(error);
        }

        ArrayList<Double> dots = new ArrayList<>();
        dots.add(-limit);
        dots.add(limit);

        int roots = rootsCount(polynom);
        int foundRoots = 0;

        int i = 1;
        while (foundRoots != roots) {
            double a = dots.get(i - 1);
            double b = dots.get(i);

            if (rootsCount(polynom, a, b) > 1) {
                if (polynom.valueOf((a + b) / 2) == 0) {
                    dots.add(i, (a + b) / 2 - eps);
                } else {
                    dots.add(i, (a + b) / 2);
                }

                i--;
            }

            if (rootsCount(polynom, a, b) == 1) {
                foundRoots++;
            }

            i++;
        }

        ArrayList<Double> cleared = new ArrayList<>();
        cleared.add(-limit);

        for (int k = 1; k < dots.size(); k++) {
            double a = dots.get(k - 1);
            double b = dots.get(k);
            if (rootsCount(polynom, a, b) != 0) {
                if (b != -limit) {
                    cleared.add(b);
                }
            }
        }

        double[] ans = new double[cleared.size()];
        for (int j = 0; j < ans.length; j++) {
            ans[j] = cleared.get(j);
        }

        return ans;
    }

    private double bisection(Polynom polynom, double a, double b) {
        double root = 0;

        while (Math.abs(b - a) > eps) {
            double x = (a + b) / 2;
            double pb = polynom.valueOf(b);
            double pc = polynom.valueOf(x);

            if (Math.signum(pb) * Math.signum(pc) >= 0) {
                b = x;
            } else {
                a = x;
            }
            root = x;
        }

        return root;
    }

    public double[] solve(Polynom polynom) {
        int m = rootsCount(polynom);
        double[] roots = new double[m];

        if (m == 0) {
            return new double[]{};
        } else if (m == 1) {
            double l = findInterval(polynom);
            double x = bisection(polynom, -l, l);
            return new double[]{x};
        } else if (m > 1) {
            double[] interval = localizeRoots(polynom);

            for (int i = 1; i < interval.length; i++) {
                roots[i - 1] = bisection(polynom, interval[i - 1], interval[i]);
            }

            return roots;
        }

        return new double[]{};
    }
}