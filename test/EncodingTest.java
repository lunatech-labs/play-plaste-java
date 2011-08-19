import backend.Pygments;
import org.junit.Test;
import play.test.UnitTest;

public class EncodingTest extends UnitTest {
	@Test
	public void testPygmentsEncoding() {
		assertTrue("Highlighting of UTF-8 encoded text", Pygments.highlight("Éñçødìng", "html").contains("Éñçødìng"));
	}
}
