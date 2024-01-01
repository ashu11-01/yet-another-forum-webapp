package net.yafw.forum.validations;

import net.yafw.forum.model.User;

public class UserValidation extends BasicValidations{

	private boolean uniqueResourceValidation;
	public UserValidation(boolean beanValidation, boolean uniqueResourceValidation) {
		super(beanValidation);
		this.uniqueResourceValidation = uniqueResourceValidation;
	}
	
	public boolean validate(User user) {
		boolean result = false;
		result = super.validate(user);
		if(!result) {
			return result;
		}
		result = result && uniqueUserValidation();
		return result;
	}

	private boolean uniqueUserValidation() {
		if(!uniqueResourceValidation) {
			return true;
		}
		return false;
	}
}
