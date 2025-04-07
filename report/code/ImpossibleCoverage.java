if (format == ThumbnailParameter.ORIGINAL_FORMAT
        && type == ThumbnailParameter.DEFAULT_FORMAT_TYPE) {
    return true;
} else if (format == ThumbnailParameter.ORIGINAL_FORMAT && type != ThumbnailParameter.DEFAULT_FORMAT_TYPE) {
    return false;
} else if (type == ThumbnailParameter.DEFAULT_FORMAT_TYPE) {
    return true;
}