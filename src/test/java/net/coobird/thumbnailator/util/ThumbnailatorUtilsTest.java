package net.coobird.thumbnailator.util;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.Collections;
import static org.mockito.Mockito.*;

import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageWriterSpi;

import org.junit.Test;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.resizers.Resizer;
import net.coobird.thumbnailator.resizers.ResizerFactory;
import net.coobird.thumbnailator.resizers.Resizers;

import net.coobird.thumbnailator.util.ThumbnailatorUtils;
import org.mockito.Mockito;

public class ThumbnailatorUtilsTest {

    // EQUIVALENCE CLASS TESTING used to check valid output and null output
    @Test
    public void getSupportedOutputFormatsTest1() {
        List<String> formats = ThumbnailatorUtils.getSupportedOutputFormats();
        assertNotNull(formats);
        //assertEquals(3, formats.size());
        assertTrue(formats.contains("jpg"));
        assertTrue(formats.contains("png"));
        assertTrue(formats.contains("bmp"));
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

//        System.out.println("Showing formats: ");
//        String[] formats = ImageIO.getWriterFormatNames();
//        for (String supportedFormat : Arrays.asList(formats)) {
//            System.out.println(supportedFormat);
//        }
//
//        System.out.println("Showing types: ");
//        for (String supportedFormat : ThumbnailatorUtils.getSupportedOutputFormatTypes(ThumbnailParameter.DEFAULT_FORMAT_TYPE)) {
//            System.out.println(supportedFormat);
//        }

        assertEquals(false, ThumbnailatorUtils.isSupportedOutputFormatType("asm", ThumbnailParameter.DEFAULT_FORMAT_TYPE));
        assertEquals(true, ThumbnailatorUtils.isSupportedOutputFormatType(ThumbnailParameter.ORIGINAL_FORMAT, ThumbnailParameter.DEFAULT_FORMAT_TYPE));
        assertEquals(false, ThumbnailatorUtils.isSupportedOutputFormatType(ThumbnailParameter.ORIGINAL_FORMAT, "JPEG"));
        assertEquals(true, ThumbnailatorUtils.isSupportedOutputFormatType("TIFF", ThumbnailParameter.DEFAULT_FORMAT_TYPE));
        assertEquals(true, ThumbnailatorUtils.isSupportedOutputFormatType("jpeg", "JPEG"));
        assertEquals(false, ThumbnailatorUtils.isSupportedOutputFormatType("jpg", ((Integer) (ThumbnailParameter.DEFAULT_IMAGE_TYPE)).toString()));




    }
}
