package com.overloaded.overloaded.rubriks;

import java.util.Arrays;

public class Cube {
    String ops;
    State state;

    public void parseOps() {
        for (int i = 0; i < ops.length(); i++) {
            boolean isClockwise = (i + 1) < ops.length() && ops.charAt(i + 1) != 'i';
//            System.out.println("Step: " + ops.charAt(i) + ", clockwise: " + isClockwise);
            switch (ops.charAt(i)) {
                case 'U':
                case 'D':
                    state.upDownAction(ops.charAt(i), isClockwise);
                    break;
                case 'L':
                case 'R':
                    state.leftRightAction(ops.charAt(i), isClockwise);
                    break;
                case 'F':
                case 'B':
                    state.frontBackAction(ops.charAt(i), isClockwise);
                    break;
            }
            if (isClockwise) {
                i++;
            }
//            System.out.println(state.toString());
        }
    }

    public void setOps(String ops) {
        this.ops = ops;
    }

    public String getOps() {
        return ops;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }


    public class State {
        int[][] u;
        int[][] l;
        int[][] f;
        int[][] r;
        int[][] b;
        int[][] d;

        public void setU(int[][] u) {
            this.u = u;
        }

        public int[][] getU() {
            return u;
        }

        public void setR(int[][] r) {
            this.r = r;
        }

        public int[][] getR() {
            return r;
        }

        public void setL(int[][] l) {
            this.l = l;
        }

        public int[][] getL() {
            return l;
        }

        public void setF(int[][] f) {
            this.f = f;
        }

        public int[][] getF() {
            return f;
        }

        public void setD(int[][] d) {
            this.d = d;
        }

        public int[][] getD() {
            return d;
        }

        public void setB(int[][] b) {
            this.b = b;
        }

        public int[][] getB() {
            return b;
        }

        public void upDownAction(char face, boolean isClockwise) {
        // affected faces in seq for anticlockwise --> [l->f, f->r, r->b, b->l]
        // up = all top rows affected --> x[0]
        // down = all bottom rows affected --> x[2]
        int rowAffected = face == 'U' ? 0 : 2;
        // copy all required arrays to ensure immutability
        int[][] cLeft = l.clone();
        int[][] cFront = f.clone();
        int[][] cRight = r.clone();
        int[][] cBack = b.clone();
        if (!isClockwise) {
            f[rowAffected] = cLeft[rowAffected];
            r[rowAffected] = cFront[rowAffected];
            b[rowAffected] = cRight[rowAffected];
            l[rowAffected] = cBack[rowAffected];
        } else {
            // anticlockwise --> [l->b, b->r, r->f, f->l]
            b[rowAffected] = cLeft[rowAffected];
            r[rowAffected] = cBack[rowAffected];
            f[rowAffected] = cRight[rowAffected];
            l[rowAffected] = cFront[rowAffected];
        }
    }

        public void leftRightAction(char face, boolean isClockwise) {
            // affected faces for clockwise --> [f->u, u->b, b->d, d->f]
            // rows&cols affected --> x[0][2], x[1][2], x[2][2]
            int colAffected = face == 'R' ? 2 : 0;
            int[][] cFront = f.clone();
            int[][] cUp = u.clone();
            int[][] cBack = b.clone();
            int[][] cDown = d.clone();
            if (isClockwise) {
                for (int i = 0; i < 2; i++) {
                    u[i][colAffected] = cFront[i][colAffected];
                    b[i][colAffected] = cUp[i][colAffected];
                    d[i][colAffected] = cBack[i][colAffected];
                    f[i][colAffected] = cDown[i][colAffected];
                }
            } else {
                // anticlockwise --> [u->f, f->d, d->b, b->u]
                for (int i = 0; i < 2; i++) {
                    f[i][colAffected] = cUp[i][colAffected];
                    d[i][colAffected] = cFront[i][colAffected];
                    b[i][colAffected] = cDown[i][colAffected];
                    u[i][colAffected] = cBack[i][colAffected];
                }
            }
        }

        public void frontBackAction(char face, boolean isClockwise) {
            // affected faces for clockwise --> [l->u, u->r, r->d, d->l]
            // rows&col affected --> x[2][0], x[2][1], x[2][2]
            int rowAffected = face == 'F' ? 2 : 0;
            int[][] cLeft = l.clone();
            int[][] cUp = u.clone();
            int[][] cRight = r.clone();
            int[][] cDown = d.clone();
            if (isClockwise) {
                for (int i = 0; i < 2; i++) {
                    u[rowAffected][i] = cLeft[rowAffected][i];
                    r[rowAffected][i] = cUp[rowAffected][i];
                    d[rowAffected][i] = cRight[rowAffected][i];
                    l[rowAffected][i] = cDown[rowAffected][i];
                }
            } else {
                // anticlockwise --> [l->d, d->r, r->u, u->l]
                for (int i = 0; i < 2; i++) {
                    d[rowAffected][i] = cLeft[rowAffected][i];
                    r[rowAffected][i] = cDown[rowAffected][i];
                    u[rowAffected][i] = cRight[rowAffected][i];
                    l[rowAffected][i] = cUp[rowAffected][i];
                }
            }
        }

        @Override
        public String toString() {
            return "u: " + Arrays.deepToString(u) + "\n"
                    + "l: " + Arrays.deepToString(l) + "\n"
                    + "f: " + Arrays.deepToString(f) + "\n"
                    + "r: " + Arrays.deepToString(r) + "\n"
                    + "b: " + Arrays.deepToString(b) + "\n"
                    + "d: " + Arrays.deepToString(d) + "\n";
        }
    }

    @Override
    public String toString() {
        return "ops: " + ops + "\n"
                + "state: " + state;
    }
}
