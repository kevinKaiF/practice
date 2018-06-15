package y2018.m06.d08;

import com.google.common.base.Joiner;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author kevin
 * @since 2018-06-15 17:40
 */
public class ThreadMonitor {
    public static final int MAX_DEPTH = 300;

    public static final Joiner COMMA_JOINER = Joiner.on(",").skipNulls();


    public void monitor() {
        // 获取线程监控bean
        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 获取所有的线程id
        long[] allThreadIds = threadMXBean.getAllThreadIds();
        // 获取所有的线程信息，指定entry的深度
        final ThreadInfo[] allThreadInfo = threadMXBean.getThreadInfo(allThreadIds, MAX_DEPTH);
        List<ThreadMonitorInfo> list = resolveThreadInfo(allThreadInfo, threadMXBean);
        for (ThreadMonitorInfo threadMonitorInfo : list) {
            System.out.println(threadMonitorInfo);
        }
    }

    private List<ThreadMonitorInfo> resolveThreadInfo(ThreadInfo[] originalThreadInfos, ThreadMXBean threadMXBean) {
        List<ThreadMonitorInfo> list = new ArrayList<ThreadMonitorInfo>(originalThreadInfos.length);
        for (ThreadInfo threadInfo : originalThreadInfos) {
            // 获取线程存活的cpu时长
            long threadCpuTime = threadMXBean.getThreadCpuTime(threadInfo.getThreadId());
            long threadAllocatedBytes = 0;
            if (threadMXBean instanceof com.sun.management.ThreadMXBean) {
                // 获取为线程分配的字节数
                threadAllocatedBytes = ((com.sun.management.ThreadMXBean) threadMXBean).getThreadAllocatedBytes(threadInfo.getThreadId());
            }
            list.add(create(threadInfo, threadCpuTime, threadAllocatedBytes));
        }
        return list;
    }

    private ThreadMonitorInfo create(ThreadInfo threadInfo, long time, long memory) {
        ThreadMonitorInfo t = new ThreadMonitorInfo();
        // 线程名称
        t.setName(threadInfo.getThreadName());
        // 线程Id
        t.setId(threadInfo.getThreadId());
        // 获取线程阻塞的时长
        t.setBlockedTime(threadInfo.getBlockedTime());
        // 获取线程进入阻塞状态的次数
        t.setBlockedCount(threadInfo.getBlockedCount());
        // 获取线程等待的时长
        t.setWaitedTime(threadInfo.getWaitedTime());
        // 获取线程进入等待状态的次数
        t.setWaitedCount(threadInfo.getWaitedCount());
        // 获取线程阻塞状态时的名称
        t.setLockName(threadInfo.getLockName());
        // 所属线程的Id  所属线程的名称
        t.setLockOwner(threadInfo.getLockOwnerId() + "-" + threadInfo.getLockOwnerName());
        // 是否是JNI启动执行的线程
        t.setInNative(threadInfo.isInNative());
        // 线程是否被suspended
        t.setSuspended(threadInfo.isSuspended());
        // 获取线程状态
        t.setState(threadInfo.getThreadState().name());
        t.setMemory(memory);
        t.setTime(time);
        t.setStackTrace(buildStackTrace(threadInfo));
        return t;
    }

    private String buildStackTrace(ThreadInfo threadInfo) {
        StackTraceElement[] stackTrace = threadInfo.getStackTrace();
        if (stackTrace == null) {
            return "";
        }
        return COMMA_JOINER.join(stackTrace);
    }

}
