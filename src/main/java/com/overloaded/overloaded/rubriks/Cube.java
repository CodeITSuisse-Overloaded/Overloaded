package com.overloaded.overloaded.rubriks;

import java.util.Arrays;

public class Cube {
    String ops;
    State state;

    public void parseOps() {
        for (int i = 0; i < ops.length(); i++) {
            boolean isClockwise = ((i + 1) < ops.length() && ops.charAt(i + 1) != 'i') || i + 1 == ops.length();
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
            if (!isClockwise) {
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

        int[][] copyArray(int[][] ori) {
            int[][] res = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    res[i][j] = ori[i][j];
                }
            }

            return res;
        }

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

        public void rotateFace(int[][] ori, boolean isClockwise) {
            int[][] res = new int[3][3];
            if (isClockwise) {
                for (int i = 0; i < 3 / 2; i++) {
                    for (int j = i; j < 3 - i - 1; j++)
                    {

                        // Swap elements of each cycle
                        // in clockwise direction
                        int temp = ori[i][j];
                        ori[i][j] = ori[3 - 1 - j][i];
                        ori[3 - 1 - j][i] = ori[3 - 1 - i][3 - 1 - j];
                        ori[3 - 1 - i][3 - 1 - j] = ori[j][3 - 1 - i];
                        ori[j][3 - 1 - i] = temp;
                    }
                }
            } else if (!isClockwise) {
                for (int x = 0; x < 3 / 2; x++) {
                    // Consider elements in group
                    // of 4 in current square
                    for (int y = x; y < 3 - x - 1; y++) {
                        // Store current cell in
                        // temp variable
                        int temp = ori[x][y];

                        // Move values from right to top
                        ori[x][y] = ori[y][3 - 1 - x];

                        // Move values from bottom to right
                        ori[y][3 - 1 - x]
                                = ori[3 - 1 - x][3 - 1 - y];

                        // Move values from left to bottom
                        ori[3 - 1 - x][3 - 1 - y]
                                = ori[3 - 1 - y][x];

                        // Assign temp to left
                        ori[3 - 1 - y][x] = temp;
                    }
                }
            }
        }

        public void upDownAction(char face, boolean isClockwise) {
        // affected faces in seq for anticlockwise --> [l->f, f->r, r->b, b->l]
        // up = all top rows affected --> x[0]
        // down = all bottom rows affected --> x[2]
        int rowAffected = face == 'U' ? 0 : 2;
        // copy all required arrays to ensure immutability
        int[][] cLeft = copyArray(l);
        int[][] cFront = copyArray(f);
        int[][] cRight = copyArray(r);
        int[][] cBack = copyArray(b);
        if ((face == 'U' && !isClockwise) || (face == 'D' && isClockwise)) {
            f[rowAffected] = cLeft[rowAffected];
            r[rowAffected] = cFront[rowAffected];
            b[rowAffected] = cRight[rowAffected];
            l[rowAffected] = cBack[rowAffected];
        } else if ((face == 'U' && isClockwise) || (face == 'D' && !isClockwise)) {
            // clockwise --> [l->b, b->r, r->f, f->l]
            b[rowAffected] = cLeft[rowAffected];
            r[rowAffected] = cBack[rowAffected];
            f[rowAffected] = cRight[rowAffected];
            l[rowAffected] = cFront[rowAffected];
        }
        if (face == 'U') {
            rotateFace(u, isClockwise);
        } else {
            rotateFace(d, isClockwise);
        }
    }

        public void leftRightAction(char face, boolean isClockwise) {
            // affected faces for clockwise --> [f->u, u->b, b->d, d->f]
            // rows&cols affected --> x[0][2], x[1][2], x[2][2]
            int colAffected = face == 'R' ? 2 : 0;
            int[][] cFront = copyArray(f);
            int[][] cUp = copyArray(u);
            int[][] cBack = copyArray(b);
            int[][] cDown = copyArray(d);
            if ((face == 'R' && isClockwise) || (face == 'L' && !isClockwise)) {
                for (int i = 0; i < 3; i++) {
                    u[i][colAffected] = cFront[i][colAffected];
                    b[i][face == 'R' ? 0 : 2] = cUp[i][colAffected];
                    d[i][colAffected] = cBack[i][face == 'R' ? 0 : 2];
                    f[i][colAffected] = cDown[i][colAffected];
                }
            } else if ((face == 'R' && !isClockwise) || (face == 'L' && isClockwise)) {
                // anticlockwise --> [u->f, f->d, d->b, b->u]
                for (int i = 0; i < 3; i++) {
                    f[i][colAffected] = cUp[i][colAffected];
                    d[i][colAffected] = cFront[i][colAffected];
                    b[i][face == 'R' ? 0 : 2] = cDown[i][colAffected];
                    u[i][colAffected] = cBack[i][face == 'R' ? 0 : 2];
                }
            }
            if (face == 'R') {
                rotateFace(r, isClockwise);
            } else {
                rotateFace(l, isClockwise);
            }
        }

        public void frontBackAction(char face, boolean isClockwise) {
            // affected faces for clockwise --> [l->u, u->r, r->d, d->l]
            // rows&col affected --> x[2][0], x[2][1], x[2][2]
            int rowAffected = face == 'F' ? 2 : 0;
            int[][] cLeft = copyArray(l);
            int[][] cUp = copyArray(u);
            int[][] cRight = copyArray(r);
            int[][] cDown = copyArray(d);
            if ((face == 'F' && isClockwise) || (face == 'B' && !isClockwise)) {
                for (int i = 0; i < 3; i++) {
                    u[rowAffected][i] = cLeft[i][rowAffected];
                    r[i][face == 'F' ? 0 : 2] = cUp[rowAffected][i];
                    d[face == 'F' ? 0 : 2][i] = cRight[i][face == 'F' ? 0 : 2];
                    l[i][rowAffected] = cDown[face == 'F' ? 0 : 2][i];
                }
            } else if ((face == 'F' && !isClockwise) || (face == 'B' && isClockwise)) {
                for (int i = 0; i < 3; i++) {
                    d[face == 'F' ? 0 : 2][i] = cLeft[i][rowAffected];
                    r[i][face == 'F' ? 0 : 2] = cDown[face == 'F' ? 0 : 2][i];
                    u[rowAffected][i] = cRight[i][face == 'F' ? 0 : 2];
                    l[i][rowAffected] = cUp[rowAffected][i];
                }
            }
            if (face == 'F') {
                rotateFace(f, isClockwise);
            } else {
                rotateFace(b, isClockwise);
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
