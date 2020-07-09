import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;


class Scratch {
    public static void main(String[] args) throws IOException {
        Path filename = Files.createTempFile(null, null);
        writeFile(filename.toString(), "Hello!");
        String res = Files.readString(filename);
        System.out.println(res);
        Files.deleteIfExists(filename);
    }

    public static void writeFile(String filename, String text) {
        try {
            Files.writeString(
                    Path.of(filename),
                    text.chars()
                            .mapToObj(Integer::toBinaryString)
                            .map(s -> String.format("%7s", s))
                            .collect(Collectors.joining())
                            .replaceAll(" ", "0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
