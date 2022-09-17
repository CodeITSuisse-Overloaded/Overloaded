package com.overloaded.overloaded;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CryptoCollapz {

    public static List<List<Integer>> cryptoCollapz(List<List<Integer>> input) {
        int[] dp = new int[1000000000];
        for (int i = 0; i < 1000000000; i++) {
            dp[i] = -1;
        }
        List<List<Integer>> allRes = new ArrayList<>();
//        allRes.add(Arrays.asList(4, 4, 16, 4, 16));
//        allRes.add(Arrays.asList(16, 52, 8, 52, 16));

        for (List<Integer> list : input) {
            List<Integer> currRes = new ArrayList<>();
            for (int num : list) {
                if (dp[num] > 0) {
                    currRes.add(dp[num]);
                } else {
                    Set<Integer> sequence = new HashSet<>();
                    int newPrice = num, max = -1;
                    while (sequence.add(newPrice) && dp[newPrice] < 0) {
                        max = Math.max(max, newPrice);
                        if (newPrice % 2 == 0) {
                            // even: divide by half
                            newPrice /= 2;
                        } else {
                            // odd: multiply by 3 and + 1
                            newPrice = newPrice * 3 + 1;
                        }
                    }

                    max = Math.max(max, dp[newPrice]);
                    for (int seqNum : sequence) {
                        if (dp[seqNum] < 0 && seqNum <= num) dp[seqNum] = max;
                    }
                    currRes.add(max);
                }
            }
            allRes.add(currRes);
        }


        return allRes;
    }
}
