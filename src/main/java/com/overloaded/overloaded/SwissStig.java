package com.overloaded.overloaded;

import java.util.ArrayList;
import java.util.List;

public class SwissStig {

    public static class Interview {
        public List<Question> questions;
        public int maxRating;
    }
    public static class Question {
        public int lower;
        public int higher;
    }
    public static class Accuracy {
        public int p;
        public int q;
        Accuracy(int p, int q) {
            this.p = p;
            this.q = q;
        }
    }

    // NOTE: incorrect solution
    // TODO: implement this method correctly
    public static List<Accuracy> warmup(List<Interview> interviews) {
        List<Accuracy> res = new ArrayList<>();
        for (Interview interview : interviews) {
            int maxRating = interview.maxRating;
            int p = 0;
            int q = maxRating;
            for (Question question : interview.questions) {
                int lower = question.lower;
                int higher = question.higher;
                for (int i = 1; i <= maxRating; i++) {
                    if (i >= lower && i <= higher) {
                        p++;
                    }
                    q++;
                }
                res.add(new Accuracy(p, q));
            }
        }
        return res;
    }
}
