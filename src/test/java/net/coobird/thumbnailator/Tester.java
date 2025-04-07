package net.coobird.thumbnailator;
import net.coobird.thumbnailator.builders.BufferedImageBuilder;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import static junit.framework.TestCase.*;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;

import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.ImageWriteParam;

import net.coobird.thumbnailator.util.ThumbnailatorUtils;
import org.mockito.Mockito;

public class Tester {

    /*
    Tests for BufferedImageBuilder.java
     */

    @Test
    public void TestConstructors() {

        int width = 5;
        int height = 8;
        int widthZero = 0;
        int heightZer0 = 0;

        Dimension dim = new Dimension(width, height);

        BufferedImageBuilder bib1;
        BufferedImageBuilder bib2;
        BufferedImageBuilder bib3;
        BufferedImageBuilder bib4;

        BufferedImageBuilder bib5;
        BufferedImageBuilder bib6;

        try {
            bib1 = new BufferedImageBuilder(dim);
            bib2 = new BufferedImageBuilder(dim, BufferedImage.TYPE_CUSTOM);
            bib3 = new BufferedImageBuilder(width, height);
            bib4 = new BufferedImageBuilder(width, height, BufferedImage.TYPE_INT_ARGB);

            try {
                bib1.build();
                bib2.build();
                bib3.build();
                bib4.build();

            } catch (Exception e) {
                fail(e.getMessage());
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            bib5 = new BufferedImageBuilder(width, heightZer0);
            bib5.build();
        } catch (Exception e) {
            assertEquals("Height must be greater than 0.", e.getMessage());
        }

        try {
            bib6 = new BufferedImageBuilder(widthZero, height);
            bib6.build();
        } catch (Exception e) {
            assertEquals("Width must be greater than 0.", e.getMessage());
        }
    }


    @Test
    public void buildImage_WithValidDimensionsAndImageType_ShouldSucceed() {
        BufferedImageBuilder builder = new BufferedImageBuilder(100, 200, BufferedImage.TYPE_INT_RGB);
        BufferedImage image = builder.build();

        assertEquals(100, image.getWidth());
        assertEquals(200, image.getHeight());
        assertEquals(BufferedImage.TYPE_INT_RGB, image.getType());
    }

    @Test
    public void buildImage_WithDimensionObject_DefaultType_ShouldSucceed() {
        Dimension dim = new Dimension(50, 50);
        BufferedImage image = new BufferedImageBuilder(dim).build();

        assertEquals(50, image.getWidth());
        assertEquals(50, image.getHeight());
        assertEquals(BufferedImage.TYPE_INT_ARGB, image.getType());
    }

    @Test
    public void buildImage_WithTypeCustom_ShouldSubstituteWithDefault() {
        Dimension dim = new Dimension(30, 30);
        BufferedImageBuilder builder = new BufferedImageBuilder(dim, BufferedImage.TYPE_CUSTOM);
        BufferedImage image = builder.build();

        assertEquals(30, image.getWidth());
        assertEquals(30, image.getHeight());
        assertEquals(BufferedImage.TYPE_INT_ARGB, image.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildImage_WithZeroWidth_ShouldThrowException() {
        new BufferedImageBuilder(0, 100).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildImage_WithZeroHeight_ShouldThrowException() {
        new BufferedImageBuilder(100, 0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildImage_WithNegativeWidth_ShouldThrowException() {
        new BufferedImageBuilder(-10, 50).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildImage_WithNegativeHeight_ShouldThrowException() {
        new BufferedImageBuilder(50, -10).build();
    }

    @Test
    public void buildImage_WithChainedMethods_ShouldSucceed() {
        BufferedImageBuilder builder = new BufferedImageBuilder(1, 1);
        builder.width(75).height(60).imageType(BufferedImage.TYPE_INT_RGB);
        BufferedImage image = builder.build();

        assertEquals(75, image.getWidth());
        assertEquals(60, image.getHeight());
        assertEquals(BufferedImage.TYPE_INT_RGB, image.getType());
    }

    @Test
    public void buildImage_MinimalValidSize_ShouldSucceed() {
        BufferedImage image = new BufferedImageBuilder(1, 1).build();

        assertEquals(1, image.getWidth());
        assertEquals(1, image.getHeight());
    }

    /*
    Tests for ThumbnailatorUtils.java
     */

    @Test
    public void testGetWriterFormatNamesIsNull() {
        IIORegistry registry = IIORegistry.getDefaultInstance();
        registry.deregisterAll(ImageWriterSpi.class);
        List<String> formats = ThumbnailatorUtils.getSupportedOutputFormats();
        assertNotNull(formats);
        assertTrue(formats.isEmpty());
    }

    @Test
    public void getSupportedOutputFormatsTest1() {
        List<String> formats = ThumbnailatorUtils.getSupportedOutputFormats();
        assertNotNull(formats);
        //assertEquals(3, formats.size());
//        assertTrue(formats.contains("jpg"));
//        assertTrue(formats.contains("png"));
//        assertTrue(formats.contains("bmp"));
    }

    @Test
    public void supportedOutputFormatsNoneExist() {
        IIORegistry registry = IIORegistry.getDefaultInstance();
        registry.deregisterAll(ImageWriterSpi.class);

        List<String> formats = ThumbnailatorUtils.getSupportedOutputFormats();
        assertNotNull(formats);
        assertTrue(formats.isEmpty());
    }

    @Test
    public void getSupportedOutputFormatTypesTest() {

        assertEquals(Collections.emptyList(), ThumbnailatorUtils.getSupportedOutputFormatTypes(ThumbnailParameter.ORIGINAL_FORMAT));

    }

    @Test
    public void isSupportedOutputFormatTypeTest() {
        assertEquals(false, ThumbnailatorUtils.isSupportedOutputFormatType("asm", ThumbnailParameter.DEFAULT_FORMAT_TYPE));
        assertEquals(true, ThumbnailatorUtils.isSupportedOutputFormatType(ThumbnailParameter.ORIGINAL_FORMAT, ThumbnailParameter.DEFAULT_FORMAT_TYPE));
        assertEquals(false, ThumbnailatorUtils.isSupportedOutputFormatType(ThumbnailParameter.ORIGINAL_FORMAT, "JPEG"));
        assertEquals(true, ThumbnailatorUtils.isSupportedOutputFormatType("TIFF", ThumbnailParameter.DEFAULT_FORMAT_TYPE));
        assertEquals(true, ThumbnailatorUtils.isSupportedOutputFormatType("jpeg", "JPEG"));
        assertEquals(false, ThumbnailatorUtils.isSupportedOutputFormatType("jpg", ((Integer) (ThumbnailParameter.DEFAULT_IMAGE_TYPE)).toString()));
    }

    @Test
    public void formatIsNotNull () {
        List <String> formats = ThumbnailatorUtils.getSupportedOutputFormats();
        assertNotNull(formats);
        assertTrue(formats.isEmpty());
    }

    @Test
    public void testUnsupportedFormat() {
        assertFalse(ThumbnailatorUtils.isSupportedOutputFormat("unsupported_format"));
    }

    private String getWritableFormatWithCompression() {
        for (String format : ImageIO.getWriterFormatNames()) {
            Iterator<javax.imageio.ImageWriter> writers = ImageIO.getImageWritersByFormatName(format);
            if (writers.hasNext()) {
                try {
                    String[] types = writers.next().getDefaultWriteParam().getCompressionTypes();
                    if (types != null && types.length > 0) {
                        return format;
                    }
                } catch (UnsupportedOperationException ignored) {}
            }
        }
        return null;
    }

    @Test
    public void testGetSupportedOutputFormatTypes() {
        String format = getWritableFormatWithCompression();
        if (format != null) {
            List<String> types  = ThumbnailatorUtils.getSupportedOutputFormatTypes(format);
            assertNotNull(types);
        }
    }

    @Test
    public void testGetSupportedOutputFormatTypes_NoCompression() {
        // This is similar to the previous test, except it uses a format without compression
        String format = "bmp";
        List<String> types = ThumbnailatorUtils.getSupportedOutputFormatTypes(format);
        assertNotNull(types);
    }

    @Test
    public void privateConstructorTest() throws Exception {
        java.lang.reflect.Constructor<ThumbnailatorUtils> constructor =
                ThumbnailatorUtils.class.getDeclaredConstructor();

        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testGetSupportedOutputFormatTypes_WhenNoWriters() {
        List<String> types = ThumbnailatorUtils.getSupportedOutputFormatTypes("nonexistent_format");
        assertTrue(types.isEmpty());
    }

    @Test
    public void SupportedOutputFormat_UnsupportedOperation_WithMock() {
        ImageWriter writer = mock(ImageWriter.class);
        when(writer.getDefaultWriteParam()).thenThrow(new UnsupportedOperationException());
        List<String> result = ThumbnailatorUtils.getSupportedOutputFormatTypes("jpeg");
        assertNotNull(result);
    }

    @Test
    public void getSupportedOutputFormat_NullCompression_WithMock() {
        ImageWriter writer = mock(ImageWriter.class);
        javax.imageio.ImageWriteParam param = mock(javax.imageio.ImageWriteParam.class);
        when(writer.getDefaultWriteParam()).thenReturn(param);
        when(param.getCompressionTypes()).thenReturn(null);

        List<String> types = ThumbnailatorUtils.getSupportedOutputFormatTypes("jpeg");
        assertNotNull(types);
    }

    @Test
    public void testDuplicateCompressionTypes() {
        String[] duplicates = {"JPEG", "jpeg"};
        ImageWriter writer = mock(ImageWriter.class);
        javax.imageio.ImageWriteParam param = mock(javax.imageio.ImageWriteParam.class);
        when(writer.getDefaultWriteParam()).thenReturn(param);
        when(param.getCompressionTypes()).thenReturn(duplicates);

        List<String> types = ThumbnailatorUtils.getSupportedOutputFormatTypes("jpeg");
        assertTrue(types.contains("JPEG"));
        assertEquals(1, types.size());
    }

    @Test
    public void supportedOutputFormats_NullArray() {
        try (MockedStatic<ImageIO> mockedImageIO = Mockito.mockStatic(ImageIO.class)) {
            mockedImageIO.when(ImageIO::getWriterFormatNames).thenReturn(null);
            List<String> formats = ThumbnailatorUtils.getSupportedOutputFormats();
            assertEquals(0, formats.size());
        }
    }

    @Test
    public void supportedOutputFormats_ExceptionReached() throws IOException {
        ImageWriter mockWriter = mock(ImageWriter.class);
        ImageWriteParam mockWriteParam = mock(ImageWriteParam.class);
        when(mockWriteParam.getCompressionTypes()).thenThrow(new UnsupportedOperationException(""));
        when(mockWriter.getDefaultWriteParam()).thenReturn(mockWriteParam);

        Iterator<ImageWriter> mockIterator = Collections.singletonList(mockWriter).iterator();
        try (MockedStatic<ImageIO> mockedImageIO = mockStatic(ImageIO.class)) {
            mockedImageIO.when(() -> ImageIO.getImageWritersByFormatName("placeholderFormat")).thenReturn(mockIterator);
            List<String> formats = ThumbnailatorUtils.getSupportedOutputFormatTypes("placeholderFormat");
            assertTrue(formats.isEmpty());
        }
    }

    @Test
    public void supportedOutputFormats_NullTypes() {
        ImageWriter mockWriter = mock(ImageWriter.class);
        ImageWriteParam mockWriteParam = mock(ImageWriteParam.class);
        when(mockWriteParam.getCompressionTypes()).thenReturn(null);
        when(mockWriter.getDefaultWriteParam()).thenReturn(mockWriteParam);

        Iterator<ImageWriter> mockIterator = Collections.singletonList(mockWriter).iterator();
        try (MockedStatic<ImageIO> mockedImageIO = mockStatic(ImageIO.class)) {
            mockedImageIO.when(() -> ImageIO.getImageWritersByFormatName("placeholderFormat")).thenReturn(mockIterator);
            List<String> formats = ThumbnailatorUtils.getSupportedOutputFormatTypes("placeholderFormat");
            assertTrue(formats.isEmpty());
        }
    }

    @Test
    public void originalFormat_AND_notDefaultFormatType() {
        boolean result = ThumbnailatorUtils.isSupportedOutputFormatType(
                ThumbnailParameter.ORIGINAL_FORMAT, "notDefault"
        );
        assertFalse(result);
    }

}
