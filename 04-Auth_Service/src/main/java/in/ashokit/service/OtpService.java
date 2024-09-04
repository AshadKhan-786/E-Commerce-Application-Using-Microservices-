package in.ashokit.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class OtpService {

	private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
	
	public void storeOtp(String email, String otp) {
		map.put(email, otp);
		// Schedule OTP removal after 5 minutes
		scheduleOtpRemoval(email, otp);
	}
	
	public boolean otpVerification(String email, String otp) {
		String storedOtp = map.get(email);
		
		return otp != null && otp.equals(storedOtp);
	}
	
	private void scheduleOtpRemoval(String email, String otp) {
		
		// Schedule the removal of the OTP after 5 minutes
        new Thread(() -> {
            try {
                TimeUnit.MINUTES.sleep(5);
                map.remove(email, otp);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
	}
}
