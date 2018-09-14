package org.demon.utils.db;

/**
 * 参考 twitter/snowflake 的分布式 ID 生成器
 */
public class IdWorker {
    private final long workerId;
    private final static long TWEPOCH = 1361753741828L;
    private long sequence = 0L;
    private final static long WORKER_ID_BITS = 4L;
    private final static long MAX_WORKER_ID = -1L ^ -1L << WORKER_ID_BITS;
    private final static long SEQUENCE_BITS = 10L;

    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private final static long SEQUENCE_MASK = -1L ^ -1L << SEQUENCE_BITS;

    private long lastTimestamp = -1L;

    public IdWorker(final long workerId) {
        super();
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format(
                    "worker Id can't be greater than %d or less than 0",
                    MAX_WORKER_ID));
        }
        this.workerId = workerId;
    }

    /**
     * 生成ID
     *
     * @return
     */
    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & SEQUENCE_MASK;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            try {
                throw new Exception(
                        String.format(
                                "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                                this.lastTimestamp - timestamp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.lastTimestamp = timestamp;
        //		System.out.println("timestamp:" + timestamp + ",TIMESTAMP_LEFT_SHIFT:"
//				+ TIMESTAMP_LEFT_SHIFT + ",nextId:" + nextId + ",workerId:"
//				+ workerId + ",sequence:" + sequence);
        return ((timestamp - TWEPOCH << TIMESTAMP_LEFT_SHIFT))
                | (this.workerId << IdWorker.WORKER_ID_SHIFT) | (this.sequence);
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    static class Test {
        public static void main(String[] args) {
            IdWorker worker2 = new IdWorker(2);

            for (int i = 0; i < 100; i++) {
                System.err.println(worker2.nextId());
            }
        }
    }

}
