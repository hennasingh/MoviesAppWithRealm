package com.artist.web.popularmovies.model;

import android.support.v7.graphics.Palette;

/**
 * Created by User on 18-Mar-18.
 */

public class ColorsPalette {

    public Palette posterPalette;

    public ColorsPalette(){

    }

    public Palette getPosterPalette() {
        return posterPalette;
    }

    public void setPosterPalette(Palette posterPalette) {
        this.posterPalette = posterPalette;
    }

    public static Palette.Swatch getMostPopulousSwatch(Palette palette) {
        Palette.Swatch mostPopulous = null;
        if (palette != null) {
            for (Palette.Swatch swatch : palette.getSwatches()) {
                if (mostPopulous == null || swatch.getPopulation() > mostPopulous.getPopulation()) {
                    mostPopulous = swatch;
                }
            }
        }
        return mostPopulous;
    }

}
