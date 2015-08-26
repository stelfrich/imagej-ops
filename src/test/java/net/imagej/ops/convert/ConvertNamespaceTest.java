package net.imagej.ops.convert;

import net.imagej.ops.AbstractNamespaceTest;

import org.junit.Test;

/**
 * Tests {@link ConvertNamespace}.
 *
 * @author Alison Walter
 */
public class ConvertNamespaceTest extends AbstractNamespaceTest {

	/**
	 * Tests that the ops of the {@code convert} namespace have corresponding
	 * type-safe Java method signatures declared in the {@link ConvertNamespace}
	 * class.
	 */
	@Test
	public void testCompleteness() {
		assertComplete("convert", ConvertNamespace.class);
	}
}
