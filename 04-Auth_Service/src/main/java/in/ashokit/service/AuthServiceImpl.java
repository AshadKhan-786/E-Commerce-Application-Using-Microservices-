package in.ashokit.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.ashokit.entity.Users;
import in.ashokit.repo.UserRepo;
import in.ashokit.utils.FileUtils;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private EmailService mailService;
	
	@Autowired
	private OtpService otpService;
	
	@Override
	public Users addUser(Users users , MultipartFile file) throws Exception{
		 Optional<Users> byEmail = userRepo.findByEmail(users.getEmail());
		 
		 if(byEmail.isPresent()) {
			return byEmail.get();
		 }else {
			 String fileName = FileUtils.saveFile(file.getName(), file);
			 users.setPassword(pwdEncoder.encode(users.getPassword()));
			 users.setUserPIC(fileName);
			 Users user = userRepo.save(users);
			 if(user.getUserID() != null) {
				 return user;
			 }else {
				 return null;
			 }
		 }
		
	}
	
	public String updateUser(Users users) {
		 
		 users.setPassword(pwdEncoder.encode(users.getPassword()));
		 Users save = userRepo.save(users);
		 
		 return save != null ? "User " + users.getEmail() + " Updated Successfully" : "Failed to Update";
		
	}
	
	
	
	@Override
	public String generateToken(String email) {
		return jwtService.generateToken(email);
	}

	@Override
	public Users findUserByEmail(String email) {
		Optional<Users> byEmail = userRepo.findByEmail(email);
		
		if(byEmail.isPresent()){
			Users user = byEmail.get();
			return user;
		}else {
			return null;
		}
		
	}

	@Override
	public boolean sendEmail(String email) {
		String otp = mailService.generateOTP();
		
		String to = email;
		String sub = "Otp Verification";
		String body = "Your OTP code is : " + otp;
		otpService.storeOtp(email, otp);
		boolean send = mailService.sendEmail(to, sub, body);
		
		return send;
	}

	@Override
	public boolean verifyOTP(String email, String otp) {
		
		return otpService.otpVerification(email, otp);
	}

	@Override
	public Users getUserById(Integer userId) {
		return userRepo.findById(userId).orElseThrow();
	}

	@Override
	public List<Users> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public Users deleteUserById(Integer userId) {
		Users user = userRepo.findById(userId).orElseThrow();
		
		if(user != null) {
			userRepo.deleteById(userId);
			return user;
		}else {
			return null;
		}
	}
	
	
	
}
