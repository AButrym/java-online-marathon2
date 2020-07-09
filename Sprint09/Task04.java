import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class Scratch {
    public static void main(String[] args) throws IOException {
        Path file = Files.createTempFile(null, null);
        String filename = file.toString();

        String message = "Hello!";
        System.out.println(message);

        writeFile(filename, message);
        String res = Files.readString(file);
        System.out.println(res);

        String recoveredMessage = readFile(filename);
        System.out.println(recoveredMessage);

        System.out.println(recoveredMessage.equals(message) ? "OK" : "FAIL");

        Files.deleteIfExists(file);
    }

    public static void writeFile(String filename, String text) {
        try {
            Files.writeString(
                    Path.of(filename),
                    text.chars()
                            .mapToObj(Integer::toBinaryString)
                            .map(s -> String.format("%7s", s))
                            .collect(Collectors.joining())
                            .replace(" ", "0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String filename) {
        try {
            String data = Files.readString(Path.of(filename));
            char[] converted = new char[data.length() / 7];
            for (int i = 7, j = 0; i <= data.length(); i += 7, j++) {
                converted[j] = (char) Integer.parseInt(data.substring(i - 7, i), 2);
            }
            return new String(converted);
//            return IntStream.iterate(7, i -> i <= data.length(), i -> i + 7)
//                    .mapToObj(i -> data.substring(i - 7, i))
//                    .mapToInt(s -> Integer.parseInt(s, 2))
//                    .mapToObj(i -> String.format("%c", i))
//                    .collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
