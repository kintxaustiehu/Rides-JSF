package beans;

import java.io.Serializable;
import java.util.Date;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("login")
@ApplicationScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private Date date;

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
		FacesContext context = FacesContext.getCurrentInstance();
		UIComponent usernameComponent = context.getViewRoot().findComponent("myForm:username");
		UIComponent passwordComponent = context.getViewRoot().findComponent("myForm:password");

		boolean usernameValid = ((UIInput) usernameComponent).isValid();
		boolean passwordValid = ((UIInput) passwordComponent).isValid();

		if (!usernameValid || !passwordValid) {
			context.addMessage("myForm:password",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong credentials", null));
			return null;
		} else {
			return "ok";
		}
	}

}