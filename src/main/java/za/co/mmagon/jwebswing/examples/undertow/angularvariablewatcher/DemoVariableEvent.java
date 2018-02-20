package za.co.mmagon.jwebswing.examples.undertow.angularvariablewatcher;

import za.co.mmagon.jwebswing.base.ComponentHierarchyBase;
import za.co.mmagon.jwebswing.base.ajax.AjaxCall;
import za.co.mmagon.jwebswing.base.ajax.AjaxResponse;
import za.co.mmagon.jwebswing.base.ajax.AjaxResponseReaction;
import za.co.mmagon.jwebswing.base.angular.AngularChangeDto;
import za.co.mmagon.jwebswing.base.angular.AngularChangeEvent;

public class DemoVariableEvent extends AngularChangeEvent<DemoVariableEvent>
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
