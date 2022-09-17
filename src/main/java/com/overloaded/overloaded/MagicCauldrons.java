package com.overloaded.overloaded;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MagicCauldrons {
    public static class Input {
        @JsonProperty("part1") PartOneInput part1;
        @JsonProperty("part2") PartTwoInput part2;
        @JsonProperty("part3") PartThreeInput part3;
        @JsonProperty("part4") PartFourInput part4;

        @Override
        public String toString() {
            return "Input{" +
                    "part1=" + part1 +
                    ", part2=" + part2 +
                    ", part3=" + part3 +
                    ", part4=" + part4 +
                    '}';
        }
    }
    public static class Output {
        @JsonProperty("part1") PartOneOutput part1;
        @JsonProperty("part2") PartTwoOutput part2;
        @JsonProperty("part3") PartThreeOutput part3;
        @JsonProperty("part4") PartFourOutput part4;
        Output (PartOneOutput part1, PartTwoOutput part2, PartThreeOutput part3, PartFourOutput part4) {
            this.part1 = part1;
            this.part2 = part2;
            this.part3 = part3;
            this.part4 = part4;
        }
    }

    public static class PartOneInput {
        public int flow_rate;
        public int time;
        public int row_number;
        public int col_number;

        @Override
        public String toString() {
            return "PartOneInput{" +
                    "flow_rate=" + flow_rate +
                    ", time=" + time +
                    ", row_number=" + row_number +
                    ", column_number=" + col_number +
                    '}';
        }
    }
    public static class PartOneOutput {
        public String amount_of_soup;
        PartOneOutput(String amountOfSoup) {
            this.amount_of_soup = amountOfSoup;
        }
    }
    public static class PartTwoInput {
        public int flow_rate;
        public double amount_of_soup;
        public int row_number;
        public int col_number;
    }
    public static class PartTwoOutput {
        public int time;
        PartTwoOutput(int time) {
            this.time = time;
        }
    }

    public static class PartThreeInput extends PartOneInput {}
    public static class PartThreeOutput extends PartOneOutput {
          PartThreeOutput(String amountOfSoup) {
                super(amountOfSoup);
          }
    }

    public static class PartFourInput extends PartTwoInput {}
    public static class PartFourOutput extends PartTwoOutput {
        PartFourOutput(int time) {
            super(time);
        }
    }

    public static final int CAULDRON_CAP = 100;

    public static PartOneOutput partOne(PartOneInput input) {
        return new PartOneOutput(
                calculatePartOne(input.flow_rate, input.time, input.row_number, input.col_number)
        );
    }

    static String calculatePartOne(int flowRate, int time, int rowNumber, int columnNumber) {
        int totalSoup = flowRate * time;
        double amt = 0d;
        int i = 0;
        while (totalSoup > 0 || i <= rowNumber) {
            int levelCount = i + 1;
            int levelCap = levelCount * CAULDRON_CAP;
            if (totalSoup > levelCap) {
                totalSoup -= levelCap;
            } else {
                amt  = (double) totalSoup / levelCount;
                break;
            }
        }
        return String.format("%.2f", amt);
    }

    public static PartTwoOutput partTwo(PartTwoInput input) {
        return new PartTwoOutput(
                calculatePartTwo(input.flow_rate, input.amount_of_soup, input.row_number, input.col_number)
        );
    }

    static int calculatePartTwo(int flowRate, double amountOfSoup, int rowNumber, int columnNumber) {
        double currLevelAmt = amountOfSoup * (rowNumber + 1);

        int levelsPrior = rowNumber;
        double amtPrior = 0d;
        for (int i = 0; i < levelsPrior; i++) {
            amtPrior += (i + 1) * CAULDRON_CAP;
        }

        double totalSoup = amtPrior + currLevelAmt;

        return (int) (totalSoup / flowRate);
    }

    // TODO: implement partThree
    public static PartThreeOutput partThree(PartThreeInput input) {
        return new PartThreeOutput(
                calculatePartOne(input.flow_rate, input.time, input.row_number, input.col_number)
        );
    }

    // TODO: implement partFour
    public static PartFourOutput partFour(PartFourInput input) {
        return new PartFourOutput(
                calculatePartTwo(input.flow_rate, input.amount_of_soup, input.row_number, input.col_number)
        );
    }

}
