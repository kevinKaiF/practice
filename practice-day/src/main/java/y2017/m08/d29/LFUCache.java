package y2017.m08.d29;

import java.util.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/8/29
 */
public class LFUCache<Key, Value> implements Map<Key, Value> {
    private final Map<Key, CacheNode<Key, Value>> cache;
    private final LinkedHashSet[]                 frequencyList;
    private       int                             lowestFrequency;
    private       int                             maxFrequency;
    private final int                             maxCacheSize;
    private final float                           evictionFactor;

    public LFUCache(int maxCacheSize, float evictionFactor) {
        if (evictionFactor > 0.0F && evictionFactor < 1.0F) {
            this.cache = new HashMap(maxCacheSize);
            this.frequencyList = new LinkedHashSet[maxCacheSize];
            this.lowestFrequency = 0;
            this.maxFrequency = maxCacheSize - 1;
            this.maxCacheSize = maxCacheSize;
            this.evictionFactor = evictionFactor;
            this.initFrequencyList();
        } else {
            throw new IllegalArgumentException("Eviction factor must be greater than 0 and lesser than or equal to 1");
        }
    }

    public Value put(Key k, Value v) {
        Object oldValue = null;
        CacheNode currentNode = (CacheNode) this.cache.get(k);
        if (currentNode == null) {
            if (this.cache.size() == this.maxCacheSize) {
                this.doEviction();
            }

            LinkedHashSet nodes = this.frequencyList[0];
            currentNode = new CacheNode(k, v, 0);
            nodes.add(currentNode);
            this.cache.put(k, currentNode);
            this.lowestFrequency = 0;
        } else {
            oldValue = currentNode.v;
            currentNode.v = v;
        }

        return (Value) oldValue;
    }

    public void putAll(Map<? extends Key, ? extends Value> map) {
        Iterator i$ = map.entrySet().iterator();

        while (i$.hasNext()) {
            Entry me = (Entry) i$.next();
            this.put((Key) me.getKey(), (Value) me.getValue());
        }

    }

    public Value get(Object k) {
        CacheNode currentNode = (CacheNode) this.cache.get(k);
        if (currentNode != null) {
            int currentFrequency = currentNode.frequency;
            if (currentFrequency < this.maxFrequency) {
                int nodes = currentFrequency + 1;
                LinkedHashSet currentNodes = this.frequencyList[currentFrequency];
                LinkedHashSet newNodes = this.frequencyList[nodes];
                this.moveToNextFrequency(currentNode, nodes, currentNodes, newNodes);
                this.cache.put((Key) k, currentNode);
                if (this.lowestFrequency == currentFrequency && currentNodes.isEmpty()) {
                    this.lowestFrequency = nodes;
                }
            } else {
                LinkedHashSet nodes1 = this.frequencyList[currentFrequency];
                nodes1.remove(currentNode);
                nodes1.add(currentNode);
            }

            return (Value) currentNode.v;
        } else {
            return null;
        }
    }

    public Value remove(Object k) {
        CacheNode currentNode = (CacheNode) this.cache.remove(k);
        if (currentNode != null) {
            LinkedHashSet nodes = this.frequencyList[currentNode.frequency];
            nodes.remove(currentNode);
            if (this.lowestFrequency == currentNode.frequency) {
                this.findNextLowestFrequency();
            }

            return (Value) currentNode.v;
        } else {
            return null;
        }
    }

    public int frequencyOf(Key k) {
        CacheNode node = (CacheNode) this.cache.get(k);
        return node != null ? node.frequency + 1 : 0;
    }

    public void clear() {
        for (int i = 0; i <= this.maxFrequency; ++i) {
            this.frequencyList[i].clear();
        }

        this.cache.clear();
        this.lowestFrequency = 0;
    }

    public Set<Key> keySet() {
        return this.cache.keySet();
    }

    public Collection<Value> values() {
        return null;
    }

    public Set<Entry<Key, Value>> entrySet() {
        return null;
    }

    public int size() {
        return this.cache.size();
    }

    public boolean isEmpty() {
        return this.cache.isEmpty();
    }

    public boolean containsKey(Object o) {
        return this.cache.containsKey(o);
    }

    public boolean containsValue(Object o) {
        return false;
    }

    private void initFrequencyList() {
        for (int i = 0; i <= this.maxFrequency; ++i) {
            this.frequencyList[i] = new LinkedHashSet();
        }

    }

    private void doEviction() {
        int currentlyDeleted = 0;
        float target = (float) this.maxCacheSize * this.evictionFactor;

        while ((float) currentlyDeleted < target) {
            LinkedHashSet nodes = this.frequencyList[this.lowestFrequency];
            if (nodes.isEmpty()) {
                throw new IllegalStateException("Lowest frequency constraint violated!");
            }

            Iterator it = nodes.iterator();

            while (it.hasNext() && (float) (currentlyDeleted++) < target) {
                CacheNode node = (CacheNode) it.next();
                it.remove();
                this.cache.remove(node.k);
            }

            if (!it.hasNext()) {
                this.findNextLowestFrequency();
            }
        }

    }

    private void moveToNextFrequency(CacheNode<Key, Value> currentNode, int nextFrequency, LinkedHashSet<CacheNode<Key, Value>> currentNodes, LinkedHashSet<CacheNode<Key, Value>> newNodes) {
        currentNodes.remove(currentNode);
        newNodes.add(currentNode);
        currentNode.frequency = nextFrequency;
    }

    private void findNextLowestFrequency() {
        while (this.lowestFrequency <= this.maxFrequency && this.frequencyList[this.lowestFrequency].isEmpty()) {
            ++this.lowestFrequency;
        }

        if (this.lowestFrequency > this.maxFrequency) {
            this.lowestFrequency = 0;
        }

    }

    private static class CacheNode<Key, Value> {
        public final Key   k;
        public       Value v;
        public       int   frequency;

        public CacheNode(Key k, Value v, int frequency) {
            this.k = k;
            this.v = v;
            this.frequency = frequency;
        }
    }
}
