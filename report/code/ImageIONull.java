public static List<String> getSupportedOutputFormats() {
    String[] formats = ImageIO.getWriterFormatNames();
    
    if (formats == null) {
        return Collections.emptyList();
    } else {
        return Arrays.asList(formats);
    }
}