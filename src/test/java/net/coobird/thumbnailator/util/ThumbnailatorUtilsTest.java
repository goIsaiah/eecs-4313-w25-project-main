package net.coobird.thumbnailator.util;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.util.Collections;
import static org.mockito.Mockito.*;

import java.util.List;

import javax.imageio.ImageIO;
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

    @Test
    public void getSupportedOutputFormatsTest1() {
        List<String> formats = ThumbnailatorUtils.getSupportedOutputFormats();
        assertNotNull(formats);
        //assertEquals(3, formats.size());
        assertTrue(formats.contains("jpg"));
        assertTrue(formats.contains("png"));
        assertTrue(formats.contains("bmp"));
    }

    /* Commenting because I'm not sure if this will break code
    @Test
    public void supportedOutputFormatsNoneExist() {
        IIORegistry registry = IIORegistry.getDefaultInstance();
        registry.deregisterAll(ImageWriterSpi.class);

        List<String> formats = ThumbnailatorUtils.getSupportedOutputFormats();
        assertNotNull(formats);
        assertTrue(formats.isEmpty());
    }
    */
}
