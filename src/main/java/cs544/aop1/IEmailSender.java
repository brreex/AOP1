package cs544.aop1;

public interface IEmailSender {
	public void sendEmail(String email, String message);
	public String getOutgoingMailServer();
}