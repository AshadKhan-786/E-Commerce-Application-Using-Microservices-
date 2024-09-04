package in.ashokit.rest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ashokit.bindings.ApiResponse;
import in.ashokit.bindings.ResetPassword;
import in.ashokit.bindings.UserForgetPassword;
import in.ashokit.bindings.UserRequest;
import in.ashokit.bindings.UserResponse;
import in.ashokit.constants.AppConstants;
import in.ashokit.entity.Users;
import in.ashokit.props.AppProperties;
import in.ashokit.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserRestController {
	
	private static final Logger log = LoggerFactory.getLogger(UserRestController.class);
	
	@Autowired
	private AuthService userService;

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private AppProperties props;
	
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<Users>> registerUser(@RequestParam("user") String userJson, @RequestParam("file")  MultipartFile file) throws Exception {
		log.info("user registration process started");
		
		ObjectMapper mapper = new ObjectMapper();
		Users users = mapper.readValue(userJson, Users.class);
		Users user = userService.addUser(users, file);
		ApiResponse<Users> response = new ApiResponse<>();
		Map<String, String> message = props.getMessage();
		if(user != null) {
			response.setStatus(201);
			response.setMessage(message.get(AppConstants.USER_REG));
			response.setData(user);
		}else {
			log.error("user registration failed");
			response.setStatus(500);
			response.setMessage(message.get(AppConstants.USER_REG_ERR));
			response.setData(user);
		}
		
		log.info("user registration process ended");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request) {
		log.info("user login process started");
		
		UserResponse response = new UserResponse();
		Map<String, String> message = props.getMessage();
		try {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
			Authentication authenticate = authManager.authenticate(authToken);
			
			if(authenticate.isAuthenticated()) {
				String token = userService.generateToken(request.getEmail());
				response.setStatus(200);
				response.setValidLogin(message.get(AppConstants.LOGIN));
				response.setToken(token);
			}
			
		} catch (Exception e) {
			log.error("user login failed");
			response.setStatus(403);
			response.setValidLogin(message.get(AppConstants.LOGIN_ERR));
			response.setToken("");
			
			
		}
		
		log.info("user login process ended");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/forgetPwd")
	public String forgetPassword(@RequestBody UserForgetPassword forget) {
		Users user = userService.findUserByEmail(forget.getEmail());
		if(user != null) {
			boolean sendEmail = userService.sendEmail(forget.getEmail());
			return sendEmail ? "OTP send to your " + forget.getEmail() + " Successfully"  : "Failed to send OTP";
		}
		
		return "Invalid " + forget.getEmail(); 
	}
	
	@GetMapping("/verify-otp")
	public String verfiyOTP(@RequestBody UserForgetPassword otp) {
		boolean verifyOTP = userService.verifyOTP(otp.getEmail(), otp.getOtp());
		
		return verifyOTP ? "OTP verified" : "OTP verification failed";
	}
	
	@PostMapping("/reset-pwd")
	public String resetPassword(@RequestBody ResetPassword reset) {
		Users user = userService.findUserByEmail(reset.getEmail());
		
		if(user != null) {
			if(reset.getNewPassword().equals(reset.getRetypePassword())) {
				user.setPassword(pwdEncoder.encode(reset.getNewPassword()));
				String msg = userService.updateUser(user);
				
				return msg;
			}else {
				return "New password and Retype password is not same";
			}
		}
		
		return "Invalid " + reset.getEmail() + " to reset password"; 
		
	}
}
