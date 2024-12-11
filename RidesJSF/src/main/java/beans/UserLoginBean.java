package beans;

import businessLogic.BLFacadeImplementation;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class UserLoginBean {
	private String email;
	private String password;
	
    private BLFacadeImplementation facade = new BLFacadeImplementation();

	public UserLoginBean() {	
	}
	
	public String login() {
        try {
            boolean success = facade.login(email, password);
            if (success) {
                return "ok"; 
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales incorrectas", null));
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}