package in.ashokit.exception;

public class ProductServiceException extends RuntimeException {

	private String errCode;

	public ProductServiceException(String errCode, String errMessage) {
		super(errMessage);
		this.errCode = errCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

}
