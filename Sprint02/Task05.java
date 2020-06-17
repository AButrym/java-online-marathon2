import java.util.Arrays;
import java.util.List;

interface Shape {
    double getPerimeter();
}

class Rectang implements Shape {
    private final double width;
    private final double height;

    public Rectang(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getPerimeter() {
        return 2 * (width + height);
    }
}

class Square implements Shape {
    private final Rectang rectang;

    public Square(double size) {
        rectang = new Rectang(size, size);
    }

    public double getPerimeter() {
        return rectang.getPerimeter();
    }
}

public class MyUtils {
    public double sumPerimeter(List<?> figures) {
        return figures.stream()
                .filter(Shape.class::isInstance)
                .map(Shape.class::cast)
                .mapToDouble(Shape::getPerimeter)
                .sum();
    }

    public static void main(String[] args) {
        // smoke test
        List<Object> figures = Arrays.asList(
                new Square(4),
                new Square(5),
                new Rectang(2, 3),
                new Object(),
                null
        );
        System.out.println(new MyUtils().sumPerimeter(figures));
    }
}
