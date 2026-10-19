import org.junit.Test;
import static org.junit.Assert.*;

public class logintest {

    // Create a valid user for testing
    private Login createValidUser() {
        return new Login(
            "Kyle",
            "Smith",
            "KY1_1",
            "Ch&&sec@ke99!",
            "+27838968976"
        );
    }

    // Test 1: Successful login
    @Test
    public void testSuccessfulLogin() {
        Login user = createValidUser();

        assertTrue(user.loginUser(
            "KY1_1",
            "Ch&&sec@ke99!"
        ));
    }

    // Test 2: Unsuccessful login
    @Test
    public void testUnsuccessfulLogin() {
        Login user = createValidUser();

        assertFalse(user.loginUser(
            "WRONG",
            "wrongpassword"
        ));
    }

    // Test 3: Valid username
    @Test
    public void testValidUsername() {
        Login user = createValidUser();

        assertTrue(user.checkUserName());
    }

    // Test 4: Invalid username
    @Test
    public void testInvalidUsername() {
        Login user = new Login(
            "Kyle",
            "Smith",
            "KyleSmith",
            "Ch&&sec@ke99!",
            "+27838968976"
        );

        assertFalse(user.checkUserName());
    }

    // Test 5: Valid password
    @Test
    public void testValidPassword() {
        Login user = createValidUser();

        assertTrue(user.checkPasswordComplexity());
    }

    // Test 6: Invalid password
    @Test
    public void testInvalidPassword() {
        Login user = new Login(
            "Kyle",
            "Smith",
            "KY1_1",
            "password",
            "+27838968976"
        );

        assertFalse(user.checkPasswordComplexity());
    }

    // Test 7: Valid cellphone number
    @Test
    public void testValidCellPhoneNumber() {
        Login user = createValidUser();

        assertTrue(user.checkCellPhoneNumber());
    }

    // Test 8: Invalid cellphone number
    @Test
    public void testInvalidCellPhoneNumber() {
        Login user = new Login(
            "Kyle",
            "Smith",
            "KY1_1",
            "Ch&&sec@ke99!",
            "12345"
        );

        assertFalse(user.checkCellPhoneNumber());
    }
}