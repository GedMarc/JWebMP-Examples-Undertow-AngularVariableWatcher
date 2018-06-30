package com.jwebmp.examples.undertow.angularvariablewatcher;

import com.jwebmp.Page;
import com.jwebmp.base.angular.AngularVariableWatcher;
import com.jwebmp.base.html.Paragraph;
import com.jwebmp.base.html.inputs.InputTextType;
import com.jwebmp.guicedinjection.GuiceContext;
import com.jwebmp.logger.LogFactory;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

import javax.servlet.ServletException;
import java.util.logging.Level;

public class VariableWatchDemo
		extends Page
{
	public VariableWatchDemo()
	{
		super("Variable Watcher Demo - JWebSwing");
		add(new Paragraph("JWebSwing Variable Watcher Demo"));

		InputTextType textInput = new InputTextType();
		textInput.bind("demo.var");
		textInput.setPlaceholder("On-Demand Update - Value Here");
		add(textInput);

		InputTextType textInputOnBlur = new InputTextType();
		textInputOnBlur.bind("demo.var2");
		textInputOnBlur.setPlaceholder("On-Blur Update - Value Here");
		add(textInputOnBlur);

		getAngular().getAngularWatchers()
		            .add(new AngularVariableWatcher("DemoWatcher", "demo.var", DemoVariableEvent.class));
		getAngular().getAngularWatchers()
		            .add(new AngularVariableWatcher("DemoWatcherOnBlur", "demo.var2", DemoVariableEvent.class));
	}

	/**
	 * This part runs the web site :)
	 *
	 * @param args
	 *
	 * @throws ServletException
	 */
	public static void main(String[] args) throws ServletException
	{
		LogFactory.configureConsoleColourOutput(Level.FINE);

		DeploymentInfo servletBuilder = Servlets.deployment()
		                                        .setClassLoader(VariableWatchDemo.class.getClassLoader())
		                                        .setContextPath("/")
		                                        .setDeploymentName("layoutdemo.war");
		DeploymentManager manager = Servlets.defaultContainer()
		                                    .addDeployment(servletBuilder);
		manager.deploy();
		GuiceContext.inject();
		HttpHandler jwebSwingHandler = manager.start();
		Undertow server = Undertow.builder()
		                          .addHttpListener(6002, "localhost")
		                          .setHandler(jwebSwingHandler)
		                          .build();
		server.start();
	}
}
