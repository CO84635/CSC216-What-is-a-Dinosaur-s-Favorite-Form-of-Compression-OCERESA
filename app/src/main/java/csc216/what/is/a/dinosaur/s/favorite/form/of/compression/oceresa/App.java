package csc216.what.is.a.dinosaur.s.favorite.form.of.compression.oceresa;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class App {
    public static class Node {
        int frequency;
        char character;
        Node left;
        Node right;

        Node(char c, int f) {
            this.frequency = f;
            this.character = c;
            this.left = null;
            this.right = null;
        }

        Node (int f, Node left, Node right) {
            this.character = '\0';
            this.frequency = f;
            this.left = left;
            this.right = right;
        }
    };

    private static Node buildHuffmanTree(String text, HashMap<Character, Integer> frequencyHashMap) {
        for (char c : text.toCharArray()) {
            frequencyHashMap.put(c, frequencyHashMap.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Node> greedyQueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.frequency));

        for (Map.Entry<Character, Integer> entry : frequencyHashMap.entrySet()) {
            greedyQueue.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (greedyQueue.size() > 1) {
            Node left = greedyQueue.poll();
            Node right = greedyQueue.poll();

            Node parent = new Node(left.frequency + right.frequency, left, right);

            greedyQueue.add(parent);
        }

        return greedyQueue.poll();
    }

    static void createCodes(Node root, String code, HashMap<Character, String> huffmanCodes) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.character, code);
            return;
        }

        createCodes(root.left, code + "0", huffmanCodes);
        createCodes(root.right, code + "1", huffmanCodes);
    }

    public static Map<String, Object> compression(String text) {
        HashMap<Character, Integer> frequencyHashMap = new HashMap<>();
        HashMap<Character, String> huffManCodes = new HashMap<>();

        Node root = buildHuffmanTree(text, frequencyHashMap);

        createCodes(root, "", huffManCodes);

        StringBuilder compressedString = new StringBuilder();
        for (char c : text.toCharArray()) {
            compressedString.append(huffManCodes.get(c));
        }
        
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("frequencyTable", frequencyHashMap);
        resultMap.put("huffmanCodes", huffManCodes);
        resultMap.put("compressedText", compressedString.toString());
        
        return resultMap;
    }

    public static String decompression(HashMap<Character, String> huffManCodes, HashMap<Character, Integer> frequencyTable, String compressedString) {
        StringBuilder decompressedString = new StringBuilder();
        StringBuilder tempCode = new StringBuilder();

        for (char c : compressedString.toCharArray()) {
            tempCode.append(c);
            
            for (Map.Entry<Character, String> entry : huffManCodes.entrySet()) {
                if (entry.getValue().equals(tempCode.toString())) {
                    decompressedString.append(entry.getKey());
                    tempCode.setLength(0);
                    break;
                }
            }
        }
        return decompressedString.toString();
    }

    public static void main(String[] args) {
        {
            String input = "Hello World!";

            Map<String, Object> result = compression(input);
            HashMap<Character, Integer> frequencyHashMap = (HashMap<Character, Integer>) result.get("frequencyTable");
            HashMap<Character, String> huffHashMap = (HashMap<Character, String>) result.get("huffmanCodes");
            String compressedString = (String) result.get("compressedText");

            System.out.println("Frequency Table: " + result.get("frequencyTable"));
            System.out.println("Huffman Codes: " + result.get("huffmanCodes"));
            System.out.println("Compressed Text: " + result.get("compressedText"));

            System.out.println(decompression(huffHashMap, frequencyHashMap, compressedString));
        }

        {
            String input = "Tony_TonyChopper!#$";

            Map<String, Object> result = compression(input);
            HashMap<Character, Integer> frequencyHashMap = (HashMap<Character, Integer>) result.get("frequencyTable");
            HashMap<Character, String> huffHashMap = (HashMap<Character, String>) result.get("huffmanCodes");
            String compressedString = (String) result.get("compressedText");

            System.out.println("Frequency Table: " + result.get("frequencyTable"));
            System.out.println("Huffman Codes: " + result.get("huffmanCodes"));
            System.out.println("Compressed Text: " + result.get("compressedText"));

            System.out.println(decompression(huffHashMap, frequencyHashMap, compressedString));
        }

        {
            String input = "";

            Map<String, Object> result = compression(input);
            HashMap<Character, Integer> frequencyHashMap = (HashMap<Character, Integer>) result.get("frequencyTable");
            HashMap<Character, String> huffHashMap = (HashMap<Character, String>) result.get("huffmanCodes");
            String compressedString = (String) result.get("compressedText");

            System.out.println("Frequency Table: " + result.get("frequencyTable"));
            System.out.println("Huffman Codes: " + result.get("huffmanCodes"));
            System.out.println("Compressed Text: " + result.get("compressedText"));

            System.out.println(decompression(huffHashMap, frequencyHashMap, compressedString));
        }

        {
            String input = "AaAaAAAaAAAAaAAaAAaAA";

            Map<String, Object> result = compression(input);
            HashMap<Character, Integer> frequencyHashMap = (HashMap<Character, Integer>) result.get("frequencyTable");
            HashMap<Character, String> huffHashMap = (HashMap<Character, String>) result.get("huffmanCodes");
            String compressedString = (String) result.get("compressedText");

            System.out.println("Frequency Table: " + result.get("frequencyTable"));
            System.out.println("Huffman Codes: " + result.get("huffmanCodes"));
            System.out.println("Compressed Text: " + result.get("compressedText"));

            System.out.println(decompression(huffHashMap, frequencyHashMap, compressedString));
        }
    }
}