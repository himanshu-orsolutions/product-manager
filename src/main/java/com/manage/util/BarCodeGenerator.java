package com.manage.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import org.krysalis.barcode4j.TextAlignment;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

/**
 * The utility BarCodeGenerator. It holds implementation to generate barcodes.
 */
public class BarCodeGenerator {

	private BarCodeGenerator() {
		// Its a utility class. Thus the instantiation is not allowed.
	}

	/**
	 * Generates the bar-code
	 * 
	 * @param referenceNumber The bar-code data
	 * @param searchResult    The search result
	 * @return The bar-code image
	 */
	public static BufferedImage generateBarCode(String referenceNumber, String searchResult) {

		try {
			// Initializing code 39 bean
			Code39Bean code39Bean = new Code39Bean();
			code39Bean.setHeight(10f);
			code39Bean.setModuleWidth(0.1);
			code39Bean.setQuietZone(1);
			code39Bean.doQuietZone(true);

			// Preparing the canvas
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BitmapCanvasProvider bitmapCanvasProvider = new BitmapCanvasProvider(outputStream, "image/x-png", 300,
					BufferedImage.TYPE_BYTE_BINARY, false, 0);
			code39Bean.generateBarcode(bitmapCanvasProvider, referenceNumber);
			bitmapCanvasProvider.deviceText(searchResult, 1, 1, 400, Font.SERIF, 10, TextAlignment.TA_LEFT);
			bitmapCanvasProvider.finish();

			// Barcode buffered image
			BufferedImage barcodeImage = bitmapCanvasProvider.getBufferedImage();

			// Search result image
			BufferedImage resultImage = new BufferedImage(500, 100, BufferedImage.TYPE_INT_ARGB);
			Graphics2D resultGraphics = resultImage.createGraphics();
			resultGraphics.setColor(Color.black);
			resultGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			String[] lines = searchResult.split("\n");
			FontMetrics fontMetrics = resultGraphics.getFontMetrics();
			int y = 20;
			for (String line : lines) {
				resultGraphics.drawString(line, 0, y);
				y += fontMetrics.getHeight();
			}
			resultGraphics.dispose();

			// Combining both images
			BufferedImage combined = new BufferedImage(400, 280, BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = combined.createGraphics();
			graphics.drawImage(resultImage, 10, 0, null);
			graphics.drawImage(barcodeImage, 200 - (barcodeImage.getWidth() / 2), 101, null);
			graphics.dispose();

			// Converting the output stream to byte array
			return combined;
		} catch (Exception exception) {
			System.out.println("Error generating barcode");
		}
		return new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
	}
}
