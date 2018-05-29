package y2018.m05.d28;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestMetric {

    private static long MB = 1024 * 1024;

    @Test
    public void testHeap() {
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        final MemoryUsage headMemory = memory.getHeapMemoryUsage();
        final MemoryUsage nonHeadMemory = memory.getNonHeapMemoryUsage();
        Map<String, Object> gauges = new LinkedHashMap<>();
        gauges.put("heap-used", headMemory.getInit() / MB + "MB");
        gauges.put("heap-commited", headMemory.getCommitted() / MB + "MB");
        gauges.put("heap-used-ratio", headMemory.getUsed() * 100 / headMemory.getCommitted() + "%");
        gauges.put("nonheap-used", nonHeadMemory.getInit() / MB + "MB");
        gauges.put("nonheap-commited", nonHeadMemory.getCommitted() / MB + "MB");
        gauges.put("nonheap-used-ratio", nonHeadMemory.getUsed() * 100 / nonHeadMemory.getCommitted() + "%");


        System.out.println(gauges);
    }

    @Test
    public void testMemoryDetail() {
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        Map<String, Object> gauges = new LinkedHashMap<>();
        if(pools != null && !pools.isEmpty()){
            for(final MemoryPoolMXBean pool : pools){
                gauges.put(pool.getName() + "-used", pool.getUsage().getUsed());
                gauges.put(pool.getName() + "-ratio", pool.getUsage().getUsed() * 100 / pool.getUsage().getCommitted());
            }
        }
        System.out.println(gauges);
    }

}
