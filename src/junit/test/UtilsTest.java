package junit.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.itcast.utils.ImageSizer;

public class UtilsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void ImageSize() {
		try {
			ImageSizer.resize(new File("e:\\abc.jpg"), new File("e:\\def.jpg"), 140, "jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
