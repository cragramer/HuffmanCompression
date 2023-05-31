import java.util.*;

public class HuffmanCompression {

    private static class HuffmanNode implements Comparable<HuffmanNode> {
        char data;
        int frequency;
        HuffmanNode left;
        HuffmanNode right;

        public HuffmanNode(char data, int frequency) {
            this.data = data;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(HuffmanNode other) {
            return this.frequency - other.frequency;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //System.out.print();
        String input = scanner.nextLine();
        if (input.length() == 0) {
            System.out.println();
            return;
        }
        Map<Character, Integer> frequencies = getFrequencies(input);
        HuffmanNode root = buildHuffmanTree(frequencies);
        Map<Character, String> codes = getCodes(root);
        String compressed = compress(input, codes);
        System.out.println(compressed);
        //System.out.println();
        List<Character> keys = new ArrayList<>(codes.keySet());
        Collections.sort(keys);
        for (char key : keys) {
            System.out.println((int) key + ":" + codes.get(key));
        }
    }

    private static Map<Character, Integer> getFrequencies(String input) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }

    private static HuffmanNode buildHuffmanTree(Map<Character, Integer> frequencies) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (char c : frequencies.keySet()) {
            pq.offer(new HuffmanNode(c, frequencies.get(c)));
        }
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parent = new HuffmanNode('\0', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            pq.offer(parent);
        }
        return pq.poll();
    }

    private static void getCodesHelper(HuffmanNode node, String code, Map<Character, String> codes) {
        if (node == null) {
            return;
        }
        if (node.data != '\0') {
            codes.put(node.data, code);
        }
        if (node.left != null)
            getCodesHelper(node.left, code + "0", codes);
        if (node.right != null) {
            getCodesHelper(node.right, code + "1", codes);
        }
    }

    private static String compress(String input, Map<Character, String> codes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            s.append(codes.get(c));
        }
        return s.toString();
    }


    private static Map<Character, String> getCodes(HuffmanNode root) {
        Map<Character, String> codes = new HashMap<>();
        if (root.left == null && root.right == null) { // special case for one character input
            codes.put(root.data, "0");
        } else {
            getCodesHelper(root, "", codes);
        }
        return codes;
    }
}
