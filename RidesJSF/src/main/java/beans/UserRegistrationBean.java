package beans;

import businessLogic.BLFacadeImplementation;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class UserRegistrationBean {
	private String email;
	private String nombre;
	private String password;
	private String repeatedPassword;

	private BLFacadeImplementation facade = new BLFacadeImplementation();

	public UserRegistrationBean() {
	}

	public String register() {
		if (!password.equals(repeatedPassword)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las contraseñas deben coincidir", null));
			return null;
		}

		try {
			facade.createDriver(email, nombre, password);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado correctamente", null));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
		return "Login";
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

	public String getRepeatedPassword() {
		return this.repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}