package com.overloaded.overloaded;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CryptoCollapz {
    public static int DP_THRESHOLD = 1000000;

    public static List<List<Integer>> cryptoCollapz(List<List<Integer>> input) {
        int[] dp = new int[DP_THRESHOLD];
        for (int i = 0; i < DP_THRESHOLD; i++) {
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
                    int newPrice = num, max = -1;
                    while (sequence.add(newPrice)) {
                        max = Math.max(max, newPrice);
                        if (newPrice % 2 == 0) {
                            // even: divide by half
                            newPrice /= 2;
                        } else {
                            // odd: multiply by 3 and + 1
                            newPrice = newPrice * 3 + 1;
                        }
                    }

                    for (int seqNum : sequence) {
                        if (seqNum <= num && dp[seqNum] < 0) dp[seqNum] = max;
                    }
                    currRes.add(max);
                }
            }
            allRes.add(currRes);
        }


        return allRes;
    }
}
