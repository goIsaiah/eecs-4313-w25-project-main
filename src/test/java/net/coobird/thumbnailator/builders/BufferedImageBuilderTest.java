package net.coobird.thumbnailator.builders;

import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static junit.framework.TestCase.*;

public class BufferedImageBuilderTest {

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

}
