package beans;

import java.io.Serializable;
import java.util.Date;

import businessLogic.BLFacadeImplementation;
import domain.Driver;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("login")
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private Date date;

	private BLFacadeImplementation facade = new BLFacadeImplementation();

	public LoginBean() {
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String validate() {
		this.date = new Date();
		FacesContext context = FacesContext.getCurrentInstance();

		if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please provide both username and password", null));
			return null;
		}

		Driver user = facade.validateUser(username, password);

		if (user == null) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password", null));
			return null;
		}

		// Store username in session
		context.getExternalContext().getSessionMap().put("username", username);

		// User successfully authenticated
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login successful", null));
		return "ok"; // Navigate to the next page
	}

	public String register() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "Register";
	}
}