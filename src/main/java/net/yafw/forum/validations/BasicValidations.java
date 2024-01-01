package net.yafw.forum.validations;

import net.yafw.forum.model.BaseModel;

public class BasicValidations{

	private boolean beanValidation;
	public BasicValidations(boolean beanValidation) {
		this.beanValidation = beanValidation;
	}

	public boolean validate(BaseModel baseModel) {
		if(!beanValidation) {
			return true;
		}
		boolean result = false;
		
		return result;
	}
}
