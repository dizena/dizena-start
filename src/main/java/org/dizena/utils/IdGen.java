package org.dizena.utils;

public class IdGen
{

    /**
     * 节点数0~31，32*32个
     */
    private final long workerId;
    private final long datacenterId;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private static class IdGenHolder
    {
        static long w = Math.abs(IpUtils.SERVER_HOST_NAME.hashCode()) % 32;
        static long d = Math.abs(IpUtils.SERVER_IP.hashCode()) % 32;
        private static final IdGen instance = new IdGen(w, d);
    }

    public static IdGen get()
    {
        return IdGenHolder.instance;
    }

    public IdGen(long workerId, long datacenterId)
    {
        long maxWorkerId = 31L;
        if (workerId > maxWorkerId || workerId < 0)
        {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        long maxDatacenterId = 31L;
        if (datacenterId > maxDatacenterId || datacenterId < 0)
        {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized String nextSid()
    {
        return nextId() + "";
    }

    public synchronized long nextId()
    {
        //获取当前毫秒数
        long timestamp = timeGen();
        // 如果服务器时间有问题(时钟后退) 报错。
        if (timestamp < lastTimestamp)
        {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        // 如果上次生成时间和当前时间相同,在同一毫秒内
        if (lastTimestamp == timestamp)
        {
            // sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
            long sequenceMask = 4095L;
            sequence = (sequence + 1) & sequenceMask;
            // 判断是否溢出,也就是每毫秒内超过4095，当为4096时，与sequenceMask相与，sequence就等于0
            if (sequence == 0)
            {
                // 自旋等待到下一毫秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else
        {
            // 如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // 最后按照规则拼出ID。
        // time datacenterId workerId sequence
        long epoch = 1288834974657L;
        long workerIdShift = 12L;
        long datacenterIdShift = 17L;
        long timestampLeftShift = 22L;
        return ((timestamp - epoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp)
    {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp)
        {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen()
    {
        return System.currentTimeMillis();
    }

}
