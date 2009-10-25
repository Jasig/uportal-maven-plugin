/**
 * 
 */
package org.jasig.portal.web.skin;

import org.junit.Assert;
import org.junit.Test;

/**
 * Simple test cases for {@link Css}, specifically the 
 * {@link Css#willAggregateWith(Css)}, {@link Css#isAbsolute()}, 
 * and {@link Css#isConditional()} methods.
 * 
 * @author Nicholas Blair, npblair@wisc.edu
 *
 */
public class CssTest {

	@Test
	public void testWillAggregateWithControl() throws Exception {
		Css c1 = new Css();
		Css c2 = new Css();
		
		c1.setValue("1.css");
		c2.setValue("2.css");
		Assert.assertTrue(c1.willAggregateWith(c2));
		Assert.assertTrue(c2.willAggregateWith(c1));
	}
	
	@Test
	public void testWillAggregateWithSubdirectory() throws Exception {
		Css c1 = new Css();
		Css c2 = new Css();
	
		c1.setValue("subdirectory/1.css");
		c2.setValue("subdirectory/2.css");
		Assert.assertTrue(c1.willAggregateWith(c2));
		Assert.assertTrue(c2.willAggregateWith(c1));
	}
	
	@Test
	public void testWillAggregateWithMultipleSubs() throws Exception {
		Css c1 = new Css();
		Css c2 = new Css();
		c1.setValue("sub/sub/sub/sub/1.css");
		c2.setValue("sub/sub/sub/sub/2.css");
		Assert.assertTrue(c1.willAggregateWith(c2));
		Assert.assertTrue(c2.willAggregateWith(c1));
	}
	
	@Test
	public void testWillAggregateWithOtherDir() throws Exception {
		Css c1 = new Css();
		Css c2 = new Css();
		c1.setValue("1.css");
		c2.setValue("otherdirectory/2.css");
		Assert.assertFalse(c1.willAggregateWith(c2));
		Assert.assertFalse(c2.willAggregateWith(c1));
		
		c1.setValue("otherdirectory/1.css");
		c2.setValue("2.css");
		Assert.assertFalse(c1.willAggregateWith(c2));
		Assert.assertFalse(c2.willAggregateWith(c1));
	}
	
	@Test
	public void testWillAggregateWithMedia() throws Exception {
		Css c1 = new Css();
		Css c2 = new Css();
		c1.setValue("1.css");
		c1.setMedia("screen");
		c2.setValue("2.css");
		Assert.assertFalse(c1.willAggregateWith(c2));
		Assert.assertFalse(c2.willAggregateWith(c1));
		
		c1.setMedia(null);
		c2.setMedia("screen");
		Assert.assertFalse(c1.willAggregateWith(c2));
		Assert.assertFalse(c2.willAggregateWith(c1));
		
		c1.setMedia("screen");
		Assert.assertTrue(c1.willAggregateWith(c2));
		Assert.assertTrue(c2.willAggregateWith(c1));
		
		c2.setValue("subdir/2.css");
		Assert.assertFalse(c1.willAggregateWith(c2));
		Assert.assertFalse(c2.willAggregateWith(c1));
	}
	
	@Test
	public void testWillAggregateWithConditional() throws Exception {
		Css c1 = new Css();
		Css c2 = new Css();
		
		c1.setValue("subdirectory/1.css");
		c1.setConditional("condition1");
		c2.setValue("subdirectory/2.css");
		Assert.assertFalse(c1.willAggregateWith(c2));
		Assert.assertFalse(c2.willAggregateWith(c1));
		
		c2.setConditional("condition2");
		Assert.assertFalse(c1.willAggregateWith(c2));
		Assert.assertFalse(c2.willAggregateWith(c1));
		
		// set both to same condition, should aggregate
		c1.setConditional("condition2");
		Assert.assertTrue(c1.willAggregateWith(c2));
		Assert.assertTrue(c2.willAggregateWith(c1));
	}
	
	
	@Test
	public void testIsConditional() throws Exception {
		Css c = new Css();
		c.setValue("a.css");
		
		Assert.assertFalse(c.isConditional());
		c.setConditional("some condition");
		Assert.assertTrue(c.isConditional());
	}
	
	@Test
	public void testIsAbsolute() throws Exception {
		Css c = new Css();
		c.setValue("subdirectory/1.css");
		Assert.assertFalse(c.isAbsolute());
		c.setValue("/subdirectory/1.css");
		Assert.assertTrue(c.isAbsolute());
	}
}
