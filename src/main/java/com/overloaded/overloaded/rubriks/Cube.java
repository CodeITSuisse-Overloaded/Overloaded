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
        if (!isClockwise) {
            front[rowAffected] = left[rowAffected];
            right[rowAffected] = front[rowAffected];
            back[rowAffected] = right[rowAffected];
            left[rowAffected] = back[rowAffected];
        } else {
            // anticlockwise --> [l->b, b->r, r->f, f->l]
            back[rowAffected] = left[rowAffected];
            right[rowAffected] = back[rowAffected];
            front[rowAffected] = right[rowAffected];
            left[rowAffected] = front[rowAffected];
        }
    }

    public void rightAction(boolean isClockwise) {
        // affected faces for clockwise --> [f->u, u->b, b->d, d->f]
        // rows&cols affected --> x[0][2], x[1][2], x[2][2]
        if (isClockwise) {
            
        }
    }
}
