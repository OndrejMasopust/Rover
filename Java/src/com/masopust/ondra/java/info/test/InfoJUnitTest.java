package com.masopust.ondra.java.info.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.masopust.ondra.java.info.Info;

/**
 * The {@code InfoJUnitTest} class wraps JUnit testing methods assuming that the
 * <b>Info.txt</b> file consists of one line containing "Info is available.".
 * 
 * @author Ondrej Masopust
 *
 */
public class InfoJUnitTest {

	@Test
	public void test() {
		assertEquals("Info is available.", Info.readInfo());
	}

}
