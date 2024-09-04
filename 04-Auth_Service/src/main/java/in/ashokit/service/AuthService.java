package in.ashokit.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.ashokit.entity.Users;

public interface AuthService {

	public Users addUser(Users user, MultipartFile file) throws Exception;
	
	public String updateUser(Users user);
	
	public Users getUserById(Integer userId);
	
	public List<Users> getAllUsers();
	
	public Users deleteUserById(Integer userID);
	
	public String generateToken(String email);
	
	public Users findUserByEmail(String email);
	
	public boolean sendEmail(String email);
	
	public boolean verifyOTP(String email, String otp);
	
}
