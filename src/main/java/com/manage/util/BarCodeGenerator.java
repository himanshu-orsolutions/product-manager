package com.manage.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

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
	 * @param data The bar-code data
	 * @return The bar-code input stream
	 */
	public static byte[] generateBarCode(String data) {

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
			code39Bean.generateBarcode(bitmapCanvasProvider, data);
			bitmapCanvasProvider.finish();

			// Converting the output stream to byte array
			return outputStream.toByteArray();
		} catch (Exception exception) {
			System.out.println("Error generating barcode");
		}
		return new byte[0];
	}
}
