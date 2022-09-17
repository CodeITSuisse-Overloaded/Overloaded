package com.overloaded.overloaded;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CryptoCollapz {

    public static List<List<Integer>> cryptoCollapz(List<List<Integer>> input) {
        int[] dp = new int[1000000];
        for (int i = 0; i < 1000000; i++) {
            dp[i] = -1;
        }
        List<List<Integer>> allRes = new ArrayList<>();
        for (List<Integer> list : input) {
            List<Integer> currRes = new ArrayList<>();
            for (int num : list) {
                if (dp[num] > 0) {
                    currRes.add(dp[num]);
                } else {
                    Set<Integer> sequence = new HashSet<>();
                    int newPrice = num;
                    while (sequence.add(newPrice)) {
                        if (newPrice % 2 == 0) {
                            // even: divide by half
                            newPrice /= 2;
                        } else {
                            // odd: multiply by 3 and + 1
                            newPrice = newPrice * 3 + 1;
                        }
                    }

                    int max = -1;
                    for (int n : sequence) {
                        max = Math.max(max, n);
                    }
                    for (int seqNum : sequence) {
                        if (dp[seqNum] < 0 && seqNum < num) dp[seqNum] = max;
                    }
                    currRes.add(max);
                }
            }
            allRes.add(currRes);
        }
        return allRes;
    }
}
