package y2018.m06.d08;

/**
 * TODO
 *
 * @author kevin
 * @since 2018-06-15 17:41
 */
public class ThreadMonitorInfo {
    private String name;
    private long id;
    private long blockedTime;
    private long blockedCount;
    private long waitedTime;
    private long waitedCount;
    private String lockName;
    private String lockOwner;
    private boolean inNative;
    private boolean suspended;
    private String state;
    private long memory;
    private long time;
    private String stackTrace;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBlockedTime() {
        return blockedTime;
    }

    public void setBlockedTime(long blockedTime) {
        this.blockedTime = blockedTime;
    }

    public long getBlockedCount() {
        return blockedCount;
    }

    public void setBlockedCount(long blockedCount) {
        this.blockedCount = blockedCount;
    }

    public long getWaitedTime() {
        return waitedTime;
    }

    public void setWaitedTime(long waitedTime) {
        this.waitedTime = waitedTime;
    }

    public long getWaitedCount() {
        return waitedCount;
    }

    public void setWaitedCount(long waitedCount) {
        this.waitedCount = waitedCount;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public boolean isInNative() {
        return inNative;
    }

    public void setInNative(boolean inNative) {
        this.inNative = inNative;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getMemory() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getLockOwner() {
        return lockOwner;
    }

    public void setLockOwner(String lockOwner) {
        this.lockOwner = lockOwner;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ThreadMonitorInfo{");
        sb.append("name='").append(name).append('\'');
        sb.append(", id=").append(id);
        sb.append(", blockedTime=").append(blockedTime);
        sb.append(", blockedCount=").append(blockedCount);
        sb.append(", waitedTime=").append(waitedTime);
        sb.append(", waitedCount=").append(waitedCount);
        sb.append(", lockName='").append(lockName).append('\'');
        sb.append(", lockOwner='").append(lockOwner).append('\'');
        sb.append(", inNative=").append(inNative);
        sb.append(", suspended=").append(suspended);
        sb.append(", state='").append(state).append('\'');
        sb.append(", memory=").append(memory);
        sb.append(", time=").append(time);
        sb.append(", stackTrace='").append(stackTrace).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
