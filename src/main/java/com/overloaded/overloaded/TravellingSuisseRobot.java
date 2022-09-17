package com.overloaded.overloaded;

import java.util.*;

public class TravellingSuisseRobot {
    final static String TARGET = "CODEITSUISSE";
    final static char ROBOT = 'X';
    final static String STRAIGHT = "S";
    final static String TURN_LEFT = "L";
    final static String TURN_RIGHT = "R";
    final static String PICKUP = "P";

    public static String solve(String input) {
        StringBuilder sb = new StringBuilder();
        String[] lines = input.split("\n");
        Map<Character, List<int[]>> positions = parseInput(lines);
        for (char letter : TARGET.toCharArray()) {
            resolveOneLetter(positions, letter, sb);
        }
        return sb.toString();
    }

    static Map<Character, List<int[]>> parseInput(String[] lines) {
        Map<Character, List<int[]>> res = new HashMap<>();
        Set<Character> targetSet = new HashSet<>();
        for (char c : TravellingSuisseRobot.TARGET.toCharArray()) {
            targetSet.add(c);
        }

        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                char c = lines[i].charAt(j);
                if (c == ROBOT) {
                    res.put(c, List.of(new int[]{i, j}));
                } else if (targetSet.contains(c)) {
                    List<int[]> currList = res.getOrDefault(c, new ArrayList<>());
                    currList.add(new int[]{i, j});
                    res.put(c, currList);
//                    System.out.println(res.get(c));
                }
            }
        }

        return res;
    }

    static void resolveOneLetter(Map<Character, List<int[]>> positions, char letter, StringBuilder sb) {
        // implement greedy
        int[] robotPos = positions.get(ROBOT).get(0);  // robot facing upwards
        List<int[]> letterPosList = positions.get(letter);
        int minIndex = getNearest(letterPosList, robotPos);
        int[] letterPos = letterPosList.get(minIndex);

        // move robot to letter
        // resolve y-axis
        int[] diff = new int[] {robotPos[0] - letterPos[0], robotPos[1] - letterPos[1]};
        if (diff[0] > 0) {
            // letter is above robot
            sb.append(STRAIGHT.repeat(diff[0]));
        } else if (diff[0] < 0) {
            sb.append(TURN_LEFT).append(TURN_LEFT)
                    .append(STRAIGHT.repeat(-diff[0]))
                    .append(TURN_RIGHT).append(TURN_RIGHT);  // robot facing upwards
        }
        // resolve x-axis
        if (diff[1] > 0) {
            // letter is to the left of robot
            sb.append(TURN_LEFT).append(STRAIGHT.repeat(diff[1])).append(TURN_RIGHT);
        } else if (diff[1] < 0) {
            sb.append(TURN_RIGHT).append(STRAIGHT.repeat(-diff[1])).append(TURN_LEFT);
        }

        // pick up letter
        sb.append(PICKUP);
        letterPosList.remove(minIndex);
        positions.put(letter, letterPosList);

        // update robot position
        positions.put(ROBOT, List.of(new int[] {letterPos[0], letterPos[1]}));
    }

    static int getNearest(List<int[]> letterPositions, int[] robotPos) {
        int minDist = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < letterPositions.size(); i++) {
            int[] pos = letterPositions.get(i);
            int dist = Math.abs(pos[0] - robotPos[0]) + Math.abs(pos[1] - robotPos[1]);
            if (dist < minDist) {
                minDist = dist;
                minIndex = i;
            }
        }
        return minIndex;
    }
}
