package com.overloaded.overloaded;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CryptoCollapz {
    public static int DP_THRESHOLD = 1000000;

    public static List<List<Long>> cryptoCollapz(List<List<Long>> input) {
        long[] dp = new long[DP_THRESHOLD];
        for (int i = 0; i < DP_THRESHOLD; i++) {
            dp[i] = -1;
        }
        List<List<Long>> allRes = new ArrayList<>();

        for (List<Long> list : input) {
            List<Long> currRes = new ArrayList<>();
            for (Long num : list) {
                if (dp[Math.toIntExact(num)] > 0) {
                    currRes.add(dp[Math.toIntExact(num)]);
                } else {
                    Set<Long> sequence = new HashSet<>();
                    long newPrice = num, max = -1;
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

                    for (long seqNum : sequence) {
                        if (seqNum <= num && dp[(int) seqNum] < 0) dp[(int) seqNum] = max;
                    }
                    currRes.add(max);
                }
            }
            allRes.add(currRes);
        }


        return allRes;
    }
}
