package net.imagej.ops.descriptor3d;

import javassist.expr.NewArray;
import ij.ImagePlus;
import ij.ImageStack;
import ij.io.FileInfo;
import ij.io.FileOpener;
import ij.io.FileSaver;
import ij.io.Opener;
import net.imagej.ImgPlus;
import net.imagej.ops.AbstractOpTest;
import net.imglib2.Cursor;
import net.imglib2.FinalDimensions;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.NumericType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.view.Views;

import org.junit.Test;

public class ShowMesh extends AbstractOpTest {

	@Test
	public void test() {
//		
//		Opener o = new Opener();
//		ImagePlus imp = o.openImage("/home/tibuch/mri.tif");
		
		Img<BitType> img = new ArrayImgFactory<BitType>().create(new FinalDimensions(81, 81, 81), new BitType());		
		
		Cursor<BitType> c = Views.interval(img, new long[]{0, 0, 0}, new long[]{80, 80, 80}).cursor();
		while (c.hasNext()) {
			c.next().setOne();
		}

		
		long t = System.currentTimeMillis();
		DefaultFaces faces = (DefaultFaces)ops.run(GenerateFaces.class, new DefaultFaces(), img, 1, 1);
		long tt = System.currentTimeMillis() - t;
		System.out.println(tt);

		
		Img<BitType> img2 = new ArrayImgFactory().create(new int[]{100,100,100}, new BitType());
		RandomAccess<BitType> ra = img2.randomAccess();
		
		for (DefaultFace f : faces.getFaces()) {
			int[] p1 = new int[]{(int) f.getVertices()[0].getX()+10, 
					(int) f.getVertices()[0].getY()+10,
					(int) f.getVertices()[0].getZ()+10};
			int[] p2 = new int[]{(int) f.getVertices()[1].getX()+10, 
					(int) f.getVertices()[1].getY()+10,
					(int) f.getVertices()[1].getZ()+10};
			int[] p3 = new int[]{(int) f.getVertices()[2].getX()+10, 
					(int) f.getVertices()[2].getY()+10,
					(int) f.getVertices()[2].getZ()+10};
			int startx = Math.min(p1[0], Math.min(p2[0], p3[0]));
			int endx = Math.max(p1[0], Math.max(p2[0], p3[0]));
			int starty = Math.min(p1[1], Math.min(p2[1], p3[1]));
			int endy = Math.max(p1[1], Math.max(p2[1], p3[1]));
			int startz = Math.min(p1[2], Math.min(p2[2], p3[2]));
			int endz = Math.max(p1[2], Math.max(p2[2], p3[2]));
			
			for (int k = startx; k <= endx; k++) {
				for (int j = starty; j <= endy; j++) {
					for (int i = startz; i <= endz; i++) {
						ra.setPosition(new int[]{k, j, i});
//						imp.setSlice((int)f.getCentroid().getZ());
//						int[] px = imp.getPixel((int)f.getCentroid().getX(), (int)f.getCentroid().getY());
						
						ra.get().setOne();
					}
				}
			}

//			ra.setPosition(p1);
//			ra.get().setOne();
//			ra.setPosition(p2);
//			ra.get().setOne();
//			ra.setPosition(p3);
//			ra.get().setOne();
			
		}
		
		ImagePlus out = ImageJFunctions.wrap(img2, "test");
		out.setFileInfo(new FileInfo());
		FileSaver fs = new FileSaver(out);
		fs.saveAsTiffStack("/home/tibuch/test.tif");
		System.out.println(faces.getArea());
	}
}
