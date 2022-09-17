package com.overloaded.overloaded.rubriks;

public class Cube {
    String[] ops;
    int[][] up;
    int[][] left;
    int[][] front;
    int[][] right;
    int[][] back;
    int[][] down;

    public void upDownAction(char face, boolean isClockwise) {
        // affected faces in seq for anticlockwise --> [l->f, f->r, r->b, b->l]
        // up = all top rows affected --> x[0]
        // down = all bottom rows affected --> x[2]
        int rowAffected = face == 'U' ? 0 : 2;
        // copy all required arrays to ensure immutability
        int[][] cLeft = left.clone();
        int[][] cFront = front.clone();
        int[][] cRight = right.clone();
        int[][] cBack = back.clone();
        if (!isClockwise) {
            front[rowAffected] = cLeft[rowAffected];
            right[rowAffected] = cFront[rowAffected];
            back[rowAffected] = cRight[rowAffected];
            left[rowAffected] = cBack[rowAffected];
        } else {
            // anticlockwise --> [l->b, b->r, r->f, f->l]
            back[rowAffected] = cLeft[rowAffected];
            right[rowAffected] = cBack[rowAffected];
            front[rowAffected] = cRight[rowAffected];
            left[rowAffected] = cFront[rowAffected];
        }
    }

    public void leftRightAction(char face, boolean isClockwise) {
        // affected faces for clockwise --> [f->u, u->b, b->d, d->f]
        // rows&cols affected --> x[0][2], x[1][2], x[2][2]
        int colAffected = face == 'R' ? 2 : 0;
        int[][] cFront = front.clone();
        int[][] cUp = up.clone();
        int[][] cBack = back.clone();
        int[][] cDown = down.clone();
        if (isClockwise) {
            for (int i = 0; i < 2; i++) {
                up[i][colAffected] = cFront[i][colAffected];
                back[i][colAffected] = cUp[i][colAffected];
                down[i][colAffected] = cBack[i][colAffected];
                front[i][colAffected] = cDown[i][colAffected];
            }
        } else {
            // anticlockwise --> [u->f, f->d, d->b, b->u]
            for (int i = 0; i < 2; i++) {
                front[i][colAffected] = cUp[i][colAffected];
                down[i][colAffected] = cFront[i][colAffected];
                back[i][colAffected] = cDown[i][colAffected];
                up[i][colAffected] = cBack[i][colAffected];
            }
        }
    }

    public void frontBackAction(char face, boolean isClockwise) {
        // affected faces for clockwise --> [l->u, u->r, r->d, d->l]
        // rows&col affected --> x[2][0], x[2][1], x[2][2]
        int rowAffected = face == 'F' ? 2 : 0;
        int[][] cLeft = left.clone();
        int[][] cUp = up.clone();
        int[][] cRight = right.clone();
        int[][] cDown = down.clone();
        if (isClockwise) {
            for (int i = 0; i < 2; i++) {
                up[rowAffected][i] = cLeft[rowAffected][i];
                right[rowAffected][i] = cUp[rowAffected][i];
                down[rowAffected][i] = cRight[rowAffected][i];
                left[rowAffected][i] = cDown[rowAffected][i];
            }
        } else {
            // anticlockwise --> [l->d, d->r, r->u, u->l]
            for (int i = 0; i < 2; i++) {
                down[rowAffected][i] = cLeft[rowAffected][i];
                right[rowAffected][i] = cDown[rowAffected][i];
                up[rowAffected][i] = cRight[rowAffected][i];
                left[rowAffected][i] = cUp[rowAffected][i];
            }
        }
    }
}
