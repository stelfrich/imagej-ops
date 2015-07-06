package net.imagej.ops.descriptor3d;
import static org.junit.Assert.assertTrue;
import net.imagej.ops.AbstractOpTest;
import net.imglib2.Cursor;
import net.imglib2.FinalDimensions;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.type.logic.BitType;
import net.imglib2.view.Views;

import org.junit.Before;
import org.junit.Test;

public class DefaultGenerateFacesTest extends AbstractOpTest {

	private Img<BitType> img;

	@Before
	public void init() {
		img = new ArrayImgFactory<BitType>().create(new int[]{9, 9, 9}, new BitType());		
	}
	
	@Test
	public void gnerateTestPoint() {
		resetImg();
		Cursor<BitType> c = Views.interval(img, new long[]{0, 0, 0}, new long[]{1, 1, 1}).cursor();
		while (c.hasNext()) {
			c.next().setOne();
		}
		
		DefaultFaces faces = (DefaultFaces)ops.run(GenerateFaces.class, new DefaultFaces(), img, 1);

		assertTrue(faces.getArea() == 6);
	}
	
	@Test
	public void gnerateTest1x1() {
		resetImg();
		Cursor<BitType> c = Views.interval(img, new long[]{0, 0, 0}, new long[]{1, 1, 1}).cursor();
		while (c.hasNext()) {
			c.next().setOne();
		}
		
		DefaultFaces faces = (DefaultFaces)ops.run(GenerateFaces.class, new DefaultFaces(), img, 1);

		assertTrue(faces.getArea() == 6);
	}
	
	@Test
	public void gnerateTest2x2() {
		resetImg();
		Cursor<BitType> c = Views.interval(img, new long[]{1, 1, 1}, new long[]{3, 3, 3}).cursor();
		while (c.hasNext()) {
			c.next().setOne();
		}
		
		DefaultFaces faces = (DefaultFaces)ops.run(GenerateFaces.class, new DefaultFaces(), img, 1);

		assertTrue(faces.getArea() == 24);
	}
	
	@Test
	public void gnerateTest2x2GridSize() {
		resetImg();
		Cursor<BitType> c = Views.interval(img, new long[]{2, 2, 2}, new long[]{7, 7, 7}).cursor();
		while (c.hasNext()) {
			c.next().setOne();
		}
		
		DefaultFaces faces = (DefaultFaces)ops.run(GenerateFaces.class, new DefaultFaces(), img, 1);
		
		assertTrue(faces.getArea() == 150);
	}
	
	@Test
	public void gnerateTest1x1And1x1() {
		resetImg();
		Cursor<BitType> c = Views.interval(img, new long[]{1, 1, 1}, new long[]{2, 2, 2}).cursor();
		while (c.hasNext()) {
			c.next().setOne();
		}
		c = Views.interval(img, new long[]{3, 3, 3}, new long[]{4, 4, 4}).cursor();
		while (c.hasNext()) {
			c.next().setOne();
		}
		
		DefaultFaces faces = (DefaultFaces)ops.run(GenerateFaces.class, new DefaultFaces(), img, 1);
		
		assertTrue(faces.getArea() == 12);
	}
	
	private void resetImg() {
		Cursor<BitType> c = img.cursor();
		while (c.hasNext()) {
			c.next().setZero();
		}
	}
}
