import org.junit.Test;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

public class ApplicationTest extends FunctionalTest {
    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
	    assertStatus(302, response);
	    assertEquals("Expecting redirect to /paste/new","/paste/new",  response.getHeader("Location"));
    }

	@Test
	public void testThatNewPastePageWorks() {
		Response response = GET("/paste/new");
		assertIsOk(response);
		assertContentType("text/html", response);
		assertCharset("utf-8", response);
	}

	@Test
	public void testThatBrowsePageWorks() {
		Response response = GET("/pastes");
		assertIsOk(response);
		assertContentType("text/html", response);
		assertCharset("utf-8", response);
	}
}
