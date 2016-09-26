package es.uvigo.ei.sing.s2p.gui.util;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public final class ColorUtils {
	private ColorUtils() {}
	
	public final static Color[] SOFT_COLORS = new Color[] {
		
		/*
		 * MSE like colors
		 */

		new Color(169,208,142),
		new Color(142,169,219),
		new Color(255,230,153),
		new Color(201,201,201),
		
		new Color(244,176,132),
		new Color(155,194,230),
		new Color(132,151,176),
		new Color(117,113,113),
		
		new Color(74,114,47),
		new Color(48,84,160),
		new Color(191,153,0),
		new Color(123,123,123),
		
		new Color(198,89,17),
		new Color(47,117,181),
		new Color(51,63,79),
		new Color(58,56,56),
		
		/*
		 * Other soft colors
		 */
		
		new Color(129,148,167),
		new Color(193,188,0),
		new Color(253,182,182),
		
		new Color(129,255,207),
		new Color(145,244,129),
		new Color(227,242,146),
		
		new Color(188,154,199),
		new Color(142,170,148),
		new Color(216,213,255),
		
		new Color(102,134,170),
		new Color(218,232,133),
		new Color(187,197,158),
		
		new Color(192,192,192),
		new Color(238,169,169),
		new Color(148,184,157),
		
		new Color(158,226,135),
		new Color(184,147,147),
		new Color(134,159,167),
		
		new Color(164,154,110),
		new Color(153,207,193),
		new Color(125,121,110),
		
		new Color(215,197,110),
		new Color(176,114,1),
		new Color (240,221,224),
		
		new Color(148,197,91),
		new Color(216,177,209),
		new Color(241,191,0),
		
		new Color(144,120,48),
		new Color(103,146,125),
		new Color (171,92,92)
	};

	public final static Color[] THIRTY_COLORS = new Color[] {

		new Color(1.00f, 0.41f, 0.38f),
		new Color(0.38f, 1.00f, 0.41f),
		new Color(0.41f, 0.38f, 1.00f),

		new Color(0.97f, 1.00f, 0.38f),
		new Color(0.38f, 0.97f, 1.00f),
		new Color(1.00f, 0.38f, 0.97f),

		new Color(1.00f, 0.53f, 0.38f),
		new Color(0.38f, 1.00f, 0.53f),
		new Color(0.53f, 0.38f, 1.00f),

		new Color(0.84f, 1.00f, 0.38f),
		new Color(0.38f, 0.85f, 1.00f),
		new Color(1.00f, 0.38f, 0.85f),

		new Color(1.00f, 0.66f, 0.38f),
		new Color(0.38f, 1.00f, 0.66f),
		new Color(0.65f, 0.38f, 1.00f),

		new Color(0.72f, 1.00f, 0.38f),
		new Color(0.38f, 0.85f, 1.00f),
		new Color(1.00f, 0.38f, 0.85f),
		
		new Color(1.00f, 0.78f, 0.38f),
		new Color(0.38f, 1.00f, 0.78f),
		new Color(0.78f, 0.38f, 1.00f),

		new Color(0.60f, 1.00f, 0.38f),
		new Color(0.38f, 0.60f, 1.00f),
		new Color(1.00f, 0.38f, 0.60f),

		new Color(1.00f, 0.91f, 0.38f),
		new Color(0.38f, 1.00f, 0.91f),
		new Color(0.90f, 0.38f, 1.00f),

		new Color(0.47f, 1.00f, 0.38f),
		new Color(0.38f, 0.47f, 1.00f),
		new Color(1.00f, 0.38f, 0.47f)
	};

	public final static Color[] FOURTY_FOUR_COLORS;
	
	static {
		final List<Color> colors = new LinkedList<>();
		
		final float hIncrement = 1f/6f;
		for (float b : new float[] { 1f, 0.75f} ) {
			for (float s : new float[] { 1f, 0.75f} ) {
				for (float h = 0f; h <= 1f - hIncrement; h += hIncrement) {
					colors.add(Color.getHSBColor(h, s, b));
				}
				for (float h = hIncrement/2; h <= 1f; h += hIncrement) {
					colors.add(Color.getHSBColor(h, s, b));
				}
			}
		}
		
		FOURTY_FOUR_COLORS = colors.toArray(new Color[colors.size()]);
	}
	
	/**
	 * Returns a Color from the SOFT_COLORS palette.
	 * 
	 * @param offset the color index.
	 * @return a Color from the SOFT_COLORS palette.
	 */
	public static Color getSoftColor(int offset) {
		return ColorUtils.SOFT_COLORS[offset % ColorUtils.SOFT_COLORS.length];
	}

	/**
	 * Returns a Color from the THIRTY_COLORS palette.
	 * 
	 * @param offset the color index.
	 * @return a Color from the THIRTY_COLORS palette.
	 */
	public static Color getThirtyColor(int offset) {
		return ColorUtils.THIRTY_COLORS[offset
				% ColorUtils.THIRTY_COLORS.length];
	}

	/**
	 * Returns a Color from the FOURTY_FOUR_COLORS palette.
	 * 
	 * @param offset the color index.
	 * @return a Color from the FOURTY_FOUR_COLORS palette.
	 */
	public static Color getFourtyForColor(int offset) {
		return ColorUtils.FOURTY_FOUR_COLORS[offset
				% ColorUtils.FOURTY_FOUR_COLORS.length];
	}
	
	/**
	 * Calculates the best foreground color for the given Color color.
	 * 
	 * @param color the background color.
	 * @return the best foreground color for the given Color color.
	 */
	public static Color calculateBestForeground(Color color) {
		final double r = Math.pow((double) color.getRed() / 255d, 2.2d);
		final double g = Math.pow((double) color.getGreen() / 255d, 2.2d);
		final double b = Math.pow((double) color.getBlue() / 255d, 2.2d);

		final double y = 0.2126 * r + 0.7151 * g + 0.0721 * b;

		return y < 0.5 ? Color.WHITE : Color.BLACK;
	}
}