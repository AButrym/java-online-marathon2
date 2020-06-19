enum LineType {
    SOLID, DOTTED, DASHED, DOUBLE
}

public static String drawLine(LineType lineType) {
    return String.format("The line is %s type",
            lineType.name().toLowerCase());
}
