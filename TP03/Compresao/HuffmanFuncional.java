package Compresao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class HuffmanNode {
    int frequency;
    byte data;
    HuffmanNode left;
    HuffmanNode right;

    HuffmanNode(byte data, int frequency) {
        this.data = data;
        this.frequency = frequency;
    }

    HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "frequency=" + frequency +
                ", data=" + data +
                '}';
    }
}

public class HuffmanFuncional {

    private RandomAccessFile raf = null;

    private byte[] readArchiveBytes() {
        try {
            raf = new RandomAccessFile("./TP03/BD/games.db", "rw");
            long tam = raf.length();
            byte[] vet = new byte[(int) tam];
            raf.read(vet);
            return vet;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Map<Byte, Integer> countFrequencies() {
        byte[] bytes = readArchiveBytes();
        if (bytes == null)
            return null;

        Map<Byte, Integer> frequencies = new HashMap<>();
        for (byte b : bytes) {
            frequencies.put(b, frequencies.getOrDefault(b, 0) + 1);
        }

        return frequencies;
    }

    private HuffmanNode buildHuffmanTree() {
        Map<Byte, Integer> frequencies = countFrequencies();
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));

        // Cria um nó para cada byte e adiciona à fila de prioridade
        for (Map.Entry<Byte, Integer> entry : frequencies.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        // Combina os nós até restar apenas um
        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode parent = new HuffmanNode(left.frequency + right.frequency, left, right);
            System.out.println("Combinando nós: " + left + " e " + right + " para formar: " + parent);
            priorityQueue.add(parent);
        }

        return priorityQueue.poll();
    }

    private void printCodes(HuffmanNode root, String code, Map<Byte, String> huffmanCodes) {
        if (root == null)
            return;

        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.data, code);
        }

        printCodes(root.left, code + "0", huffmanCodes);
        printCodes(root.right, code + "1", huffmanCodes);
    }

    private void writeCompressedFile(String outputFilePath, Map<Byte, String> huffmanCodes, byte[] originalBytes) {
        StringBuilder encodedData = new StringBuilder();
        for (byte b : originalBytes) {
            encodedData.append(huffmanCodes.get(b));
        }

        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            for (int i = 0; i < encodedData.length(); i += 8) {
                String byteString = encodedData.substring(i, Math.min(i + 8, encodedData.length()));
                byte byteValue = (byte) Integer.parseInt(byteString, 2);
                fos.write(byteValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void decompressFile(String freqFilePath, String compressedFilePath, String outputFilePath) {

        HuffmanNode root = buildHuffmanTree();
        byte[] compressedBytes = readCompressedFile(compressedFilePath);
        if (compressedBytes == null)
            return;

        StringBuilder bits = new StringBuilder();
        for (byte b : compressedBytes) {
            bits.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }

        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            HuffmanNode current = root;
            for (int i = 0; i < bits.length(); i++) {
                current = bits.charAt(i) == '0' ? current.left : current.right;

                if (current.left == null && current.right == null) {
                    fos.write(current.data);
                    current = root;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] readCompressedFile(String compressedFilePath) {
        try (FileInputStream fis = new FileInputStream(compressedFilePath)) {
            byte[] compressedBytes = fis.readAllBytes();
            return compressedBytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        HuffmanFuncional huff = new HuffmanFuncional();
        HuffmanNode root = huff.buildHuffmanTree();
        Map<Byte, String> huffmanCodes = new HashMap<>();
        huff.printCodes(root, "", huffmanCodes);

        byte[] originalBytes = huff.readArchiveBytes();

        if (originalBytes != null) {
            huff.writeCompressedFile("./TP03/BD/compressed_games_huffman.db", huffmanCodes, originalBytes);
            System.out.println("Arquivo comprimido com sucesso!");
        }

        if (originalBytes != null) {
            huff.writeCompressedFile("./TP03/BD/compressed_games_huffman.db", huffmanCodes, originalBytes);
            System.out.println("Arquivo comprimido com sucesso!");

            huff.decompressFile("./TP03/BD/frequencies.dat", "./TP03/BD/compressed_games_huffman.db",
                    "./TP03/BD/decompressed_games_huffman.db");
            System.out.println("Arquivo descompactado com sucesso!");
        }

    }
}