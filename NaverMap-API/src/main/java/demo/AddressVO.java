package demo;

//주소정보를 담기 위한 클래스(VO:정보 저장 기능의 클래스로 많이 활용)
public class AddressVO {

	private String roadAddress;
	private String jibunAddress;
	private String x;
	private String y;

	public AddressVO() {

	}

	public AddressVO(String roadAddress, String jibunAddressm, String x, String y) {
		this.roadAddress = roadAddress;
		this.jibunAddress = jibunAddress;
		this.x = x;
		this.y = y;
	}
	// getter, setter

	@Override
	public String toString() {
		return "AddressVo[roadAddress=" + roadAddress + ",jibunAddress=" + jibunAddress + "]";
	}

	public String getRoadAddress() {
		return roadAddress;
	}

	public void setRoadAddress(String roadAddress) {
		this.roadAddress = roadAddress;
	}

	public String getJibunAddress() {
		return jibunAddress;
	}

	public void setJibunAddress(String jibunAddress) {
		this.jibunAddress = jibunAddress;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

}
