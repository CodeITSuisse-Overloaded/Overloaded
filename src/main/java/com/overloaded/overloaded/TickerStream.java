package com.overloaded.overloaded;

import java.util.*;

/**
 * Assumptions made about input data in general:
 * 1. Unique combinations of `timestamp` and `ticker` <br>
 * 2. `timestamp` refers to "hh:mm" in a particular day <br>
 * 3. No leading and trailing spaces, and no spaces within each `tick` string <br>
 * 4. `ticker`s are spelled correctly with appropriate upper/lower case <br>
 * 5. Value for each of `quantity` is within the range of [1, 2^31-1] i.e. fits in Java's positive int <br>
 */
public final class TickerStream {

    private static class Tick {
        int ts;
        String ticker;
        int quantity;
        double price;

        Tick(String tick) {
            String[] parts = tick.split(",");
            this.ts = Tick.tsStrToInt(parts[0]);
            this.ticker = parts[1];
            this.quantity = Integer.parseInt(parts[2]);
            this.price = Double.parseDouble(parts[3]);
        }

        static int tsStrToInt(String ts) {
            String[] parts = ts.split(":");
            return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
        }

        static String tsIntToStr(int minutes) {
            int h = minutes / 60;
            int m = minutes % 60;
            return String.format("%02d:%02d", h, m);
        }
    }

    interface Record {
        int getTs();
        long getQuantity();
        double getNotional();
        void setTs(int ts);
        void addQuantity(long addedQuantity);
        void addNotional(double addedNotional);
    }

    interface BasicRecord extends Record {}

    private static class BasicRecordImpl implements BasicRecord {
        int ts;
        long cumQuantity;
        double cumNotional;

        BasicRecordImpl() {}

        @Override
        public int getTs() {
            return this.ts;
        }

        @Override
        public long getQuantity() {
            return this.cumQuantity;
        }

        @Override
        public double getNotional() {
            return this.cumNotional;
        }

        @Override
        public void setTs(int ts) {
            this.ts = ts;
        }

        @Override
        public void addQuantity(long addedQuantity) {
            this.cumQuantity += addedQuantity;
        }

        @Override
        public void addNotional(double addedNotional) {
            this.cumNotional += addedNotional;
        }
    }

    interface DelayedRecord extends Record {
        int getRemainderQuantity();
        double getRemainderNotional();
        void setRemainderQuantity(int remainderQuantity);
        void setRemainderNotional(double remainderNotional);
    }

    private static class DelayedRecordImpl extends BasicRecordImpl implements DelayedRecord {
        int remainderQuantity;
        double remainderNotional;

        DelayedRecordImpl() {}

        @Override
        public int getRemainderQuantity() {
            return this.remainderQuantity;
        }

        @Override
        public double getRemainderNotional() {
            return this.remainderNotional;
        }

        @Override
        public void setRemainderQuantity(int remainderQuantity) {
            this.remainderQuantity = remainderQuantity;
        }

        @Override
        public void setRemainderNotional(double remainderNotional) {
            this.remainderNotional = remainderNotional;
        }
    }

    private TickerStream() {
        // empty
    }

    /**
     * Construct an array of sorted `Tick`s from a list of `tick` strings.
     * O(n log n) time complexity, where n is the number of `tick` strings.
     */
    private static Tick[] preprocessTicks(List<String> ticks) {
        Tick[] res = new Tick[ticks.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = new Tick(ticks.get(i));
        }

        Arrays.sort(res, new Comparator<Tick>() {
            @Override
            public int compare(Tick a, Tick b) {
                return a.ts == b.ts ? a.ticker.compareTo(b.ticker) : Integer.compare(a.ts, b.ts);
            }
        });

        return res;
    }

    /**
     * Converts an array of `Tick`s to a list of strings
     * showing cumulative quantities and notionals aggregated by timestamp.
     * O(n t) time complexity, where n is the number of `Tick`s
     * and t is the number of unique tickers.
     */
    private static List<String> aggregateTicks(Tick[] ticks) {
        List<String> result = new ArrayList<>();
        SortedMap<String, BasicRecord> cumRecords = new TreeMap<>();

        int currTs = 0;

        for (Tick tick : ticks) {
            if (tick.ts > currTs) {
                buildStringFromCumRecords(cumRecords, currTs, result);
                currTs = tick.ts;
            }

            BasicRecord record = cumRecords.get(tick.ticker);
            if (record == null) {
                record = new BasicRecordImpl();
                cumRecords.put(tick.ticker, record);
            }
            record.setTs(tick.ts);
            record.addQuantity(tick.quantity);
            record.addNotional(tick.quantity * tick.price);
        }

        buildStringFromCumRecords(cumRecords, currTs, result);

        return result;
    }

    /**
     * Converts an array of `Tick`s to a list of strings
     * showing cumulative quantities and notionals aggregated by timestamp,
     * but a "delay" by `quantityBlock`.
     */
    private static List<String> aggregateTicksDelayed(Tick[] ticks, int quantityBlock) {
        List<String> result = new ArrayList<>();
        SortedMap<String, DelayedRecord> cumRecords = new TreeMap<>();

        int currTs = 0;

        for (Tick tick : ticks) {
            if (tick.ts > currTs) {
                buildStringFromCumRecords(cumRecords, currTs, result);
                currTs = tick.ts;
            }

            DelayedRecord record = cumRecords.get(tick.ticker);
            if (record == null) {
                record = new DelayedRecordImpl();
                record.setTs(-1);  // mark as not applicable for building result string
                cumRecords.put(tick.ticker, record);
            }

            if ((record.getRemainderQuantity() + tick.quantity) / quantityBlock >= 1) {
                record.setTs(tick.ts);

                int n = (record.getRemainderQuantity() + tick.quantity) / quantityBlock;
                int addedQuantity = n * quantityBlock;
                record.addQuantity(addedQuantity);

                int diff = addedQuantity - record.getRemainderQuantity();
                record.addNotional(record.getRemainderNotional() + diff * tick.price);

                int remainderQuantity = (record.getRemainderQuantity() + tick.quantity) % quantityBlock;
                double remainderNotional = remainderQuantity * tick.price;
                record.setRemainderQuantity(remainderQuantity);
                record.setRemainderNotional(remainderNotional);
            } else {
                record.setRemainderQuantity(record.getRemainderQuantity() + tick.quantity);
                record.setRemainderNotional(record.getRemainderNotional() + tick.quantity * tick.price);
            }
        }

        buildStringFromCumRecords(cumRecords, currTs, result);

        return result;
    }

    /**
     * Helper method to build result strings.
     * O(t) time complexity, where t is the number of unique tickers.
     */
    private static <T extends Record> void buildStringFromCumRecords(
            SortedMap<String, T> cumRecords, int currTs, List<String> result
    ) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, T> entry : cumRecords.entrySet()) {
            if (entry.getValue().getTs() == currTs) {
                sb.append(",").append(entry.getKey())
                        .append(",").append(entry.getValue().getQuantity())
                        .append(",").append(String.format("%.1f", entry.getValue().getNotional()));
            }
        }

        if (sb.length() > 0) {
            result.add(Tick.tsIntToStr(currTs) + sb.toString());
        }
    }

    /**
     * O(n log n + n t) time complexity, where n is the number of `tick` strings
     * and t is the number of unique tickers.
     */
    public static List<String> toCumulative(List<String> ticks) {
        return aggregateTicks(preprocessTicks(ticks));
    }

    /**
     * ASSUMPTION MADE:
     * An output is only built for a ticker with cumulative quantity being a multiple of `quantityBlock`.
     * i.e. if a `quantityBlock` is 5, then the output for A will only show for timestamps
     * where the cumulative quantity of A is 5, 10, 20, etc. where some multiples of 5 might be skipped.
     * <p>
     * O(n log n + n t) time complexity, where n is the number of `tick` strings
     * and t is the number of unique tickers.
     */
    public static List<String> toCumulativeDelayed(List<String> ticks, int quantityBlock) {
        return aggregateTicksDelayed(preprocessTicks(ticks), quantityBlock);
    }
}

