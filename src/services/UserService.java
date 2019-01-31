package services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UserService implements Services{
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	private DatabaseConnectionService dbService = null;

	public UserService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean useApplicationLogins() {
		return true;
	}
	
	public boolean login(String username, String password) {
		//TODO: Complete this method.
//		try {
//			String query = "SELECT PasswordSalt, PasswordHash FROM [User] WHERE Username = ?";
//			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
//			stmt.setString(1, username);
//			stmt.executeQuery();
//			ResultSet rs = stmt.getResultSet();
//			while(rs.next()){
//				String hashedPassword = hashPassword(getBytesFromString(rs.getString("PasswordSalt")), password);
//				if(hashedPassword.equals(rs.getString("PasswordHash"))) return true;
//			}
//			// it already displays a message
////			JOptionPane.showMessageDialog(null, "Login failed. Please try again.");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return false;
	}

	public boolean register(String username, String password) {
		//TODO: Task 6
		byte[] newSalt = getNewSalt();
		String hashedPassword = hashPassword(newSalt, password);
		try {
			CallableStatement cs = this.dbService.getConnection().prepareCall("{ ? = call dbo.Register(?, ?, ?) }");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setString(3, getStringFromBytes(newSalt));
			cs.setString(4, hashedPassword);
			cs.execute();
			int returnVal = cs.getInt(1);
			if(returnVal != 0){
				// it already displays a message
//				JOptionPane.showMessageDialog(null, "Registration failed, please try again");
				return false;
			}
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}
	
	public byte[] getBytesFromString(String salt){
		return dec.decode(salt);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}

	@Override
	public JPanel getJPanel() {
		// TODO Auto-generated method stub
		return null;
	}

}
