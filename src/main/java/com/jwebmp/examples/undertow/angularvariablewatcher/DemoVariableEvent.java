package com.jwebmp.examples.undertow.angularvariablewatcher;

import com.jwebmp.core.Component;
import com.jwebmp.core.base.ajax.AjaxCall;
import com.jwebmp.core.base.ajax.AjaxResponse;
import com.jwebmp.core.base.ajax.AjaxResponseReaction;
import com.jwebmp.core.base.angular.AngularChangeDto;
import com.jwebmp.core.base.angular.AngularChangeEvent;

public class DemoVariableEvent
		extends AngularChangeEvent<DemoVariableEvent>
{

	public DemoVariableEvent(Component component)
	{
		super(component);
	}

	public DemoVariableEvent()
	{
	}

	@Override
	public void onChange(AjaxCall call, AjaxResponse response)
	{
		AngularChangeDto change = getChangeDto(call);
		response.addReaction(new AjaxResponseReaction("Event Variable Changed", "New Value : " + change.getNewValue()));
	}
}
