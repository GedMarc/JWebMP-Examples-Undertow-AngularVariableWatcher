package com.jwebmp.examples.undertow.angularvariablewatcher;

import com.jwebmp.base.ComponentHierarchyBase;
import com.jwebmp.base.ajax.AjaxCall;
import com.jwebmp.base.ajax.AjaxResponse;
import com.jwebmp.base.ajax.AjaxResponseReaction;
import com.jwebmp.base.angular.AngularChangeDto;
import com.jwebmp.base.angular.AngularChangeEvent;

public class DemoVariableEvent
		extends AngularChangeEvent<DemoVariableEvent>
{

	public DemoVariableEvent(ComponentHierarchyBase component)
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
