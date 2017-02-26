package y2017.m01.d19;



import java.util.*;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2017-01-19 PM01:27
 */
public class HuffManEncode {
    public void encode(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }

        PriorityQueue<KV> frequencyPair = getFrequencyMap(value);
        buildNode(frequencyPair);

    }

    private Node buildNode(PriorityQueue<KV> frequencyMap) {
        PriorityQueue<KV> entryList = frequencyMap;

        Node root = new Node();
        while (entryList.size() > 0) {
            if (entryList.size() == 1) {

            } else {
                Node node = new Node();
                KV entry0 = entryList.poll();
                KV entry1 = entryList.poll();
                // add left
                node.addLeftNode(entry0.getValue(), entry0.getKey());
                // add right
                node.addRightNode(entry1.getValue(), entry1.getKey());
                // merge
                entryList.offer(new KV(entry0.getKey() + entry1.getKey(), entry0.getValue() + entry1.getValue()));

                if (root.getValue() > 0) {
                    Node temp = new Node();
                    if (root.getValue() > node.getValue()) {
                        temp.addLeftNode(node);
                        temp.addRightNode(root);
                        root = temp;
                    } else {
                        temp.addLeftNode(root);
                        temp.addRightNode(node);
                        root = temp;
                    }
                } else {
                    root = node;
                }
            }
        }


        return null;
    }

    class KV {
        private String key;
        private int value;

        public KV(String key, int value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "KV{" +
                    "key='" + key + '\'' +
                    ", value=" + value +
                    '}';
        }
    }


    public PriorityQueue<KV> getFrequencyMap(String value) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            String item = String.valueOf(chars[i]);
            Integer frequency = frequencyMap.get(item);
            if (frequency == null) {
                frequencyMap.put(item, 1);
            } else {
                frequencyMap.put(item, ++frequency);
            }
        }

        PriorityQueue<KV> entryList = new PriorityQueue<>(16, new Comparator<KV>() {
            @Override
            public int compare(KV o1, KV o2) {
                return o1.getValue() - o2.getValue();
            }
        });
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            entryList.offer(new KV(entry.getKey(), entry.getValue()));
        }
        return entryList;
    }

    static class Node {
        private Node left;
        private Node right;
        private int frequency;
        private boolean isLeaf;
        private String origin;

        public Node() {
            this.isLeaf = true;
            this.frequency = 0;
            this.origin = "";
        }

        public Node(String origin) {
            this.isLeaf = true;
            this.frequency = 0;
            this.origin = origin;
        }

        public void addLeftNode(int leftValue, String origin) {
            left = new Node(origin);
            left.frequency = leftValue;
            this.frequency += leftValue;
            isLeaf();
        }

        public void addLeftNode(Node node) {
            left = node;
            if (node != null) {
                this.frequency += node.getValue();
            }
            isLeaf();
        }

        public void addRightNode(int rightValue, String origin) {
            right = new Node(origin);
            right.frequency = rightValue;
            this.frequency += rightValue;
            isLeaf();
        }

        public void addRightNode(Node node) {
            right = node;
            if (node != null) {
                this.frequency += node.getValue();
            }
            isLeaf();
        }

        private void isLeaf() {
            if (left == null && right == null) {
                this.isLeaf = false;
            } else {
                this.isLeaf = true;
            }
        }

        public int getValue() {
            return frequency;
        }

        public void setValue(int value) {
            this.frequency = value;
        }

    }

    public static void main(String[] args) {
//        HuffManEncode huffManEncode = new HuffManEncode();
//        Map<String, Integer> frequencyMap = huffManEncode.getFrequencyMap("我们们");
//        System.out.println(frequencyMap);
    }


}
