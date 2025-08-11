package ticket.booking.util;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil1
{
    public static String hashPassword(String plainpassword) {
        return BCrypt.hashpw(plainpassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
