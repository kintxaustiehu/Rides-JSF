package validators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("customRegexValidator")
public class CustomRegexValidator implements Validator<String> {

	@Override
	public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
		String pattern = (String) component.getAttributes().get("pattern");
		String message = (String) component.getAttributes().get("validatorMessage");

		if (pattern != null && !value.matches(pattern)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
		}
	}
}