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
}
