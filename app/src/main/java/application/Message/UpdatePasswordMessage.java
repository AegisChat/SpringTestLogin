package application.Message;

public class UpdatePasswordMessage extends Message{

	private String changedPassword;
	
	public UpdatePasswordMessage(String changedPassword) {
		
	}

	public String getChangedPassword() {
		return changedPassword;
	}

	public void setChangedPassword(String changedPassword) {
		this.changedPassword = changedPassword;
	}

}
