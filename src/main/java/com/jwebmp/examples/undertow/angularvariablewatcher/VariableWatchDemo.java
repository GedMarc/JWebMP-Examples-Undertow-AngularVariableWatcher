package com.jwebmp.examples.undertow.angularvariablewatcher;

import com.google.inject.servlet.GuiceFilter;
import com.jwebmp.core.Page;
import com.jwebmp.core.base.angular.AngularClientVariableWatcher;
import com.jwebmp.core.base.angular.AngularPageConfigurator;
import com.jwebmp.core.base.html.Paragraph;
import com.jwebmp.core.base.html.inputs.InputTextType;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.logger.LogFactory;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.FilterInfo;

import javax.servlet.DispatcherType;
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


		GuiceContext.get(AngularPageConfigurator.class)
		            .getAngularWatchers()
		            .add(new AngularClientVariableWatcher("DemoWatcher", "demo.var", DemoVariableEvent.class));
		GuiceContext.get(AngularPageConfigurator.class)
		            .getAngularWatchers()
		            .add(new AngularClientVariableWatcher("DemoWatcherOnBlur", "demo.var2", DemoVariableEvent.class));
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
		LogFactory.setLogToConsole(true);
		LogFactory.configureConsoleColourOutput(Level.FINE);

		DeploymentInfo deploymentInfo = Servlets.deployment()
		                                        .setClassLoader(VariableWatchDemo.class.getClassLoader())
		                                        .setContextPath("/")
		                                        .setDeploymentName("VariableWatchDemo.war");

		deploymentInfo.addFilter(new FilterInfo("GuiceFilter", GuiceFilter.class).setAsyncSupported(true));
		deploymentInfo.addFilterUrlMapping("GuiceFilter", "/*", DispatcherType.REQUEST);
		deploymentInfo.setResourceManager(new ClassPathResourceManager(deploymentInfo.getClassLoader(), "META-INF/resources"));

		DeploymentManager manager2 = Servlets.defaultContainer()
		                                     .addDeployment(deploymentInfo);

		GuiceContext.inject();
		manager2.deploy();

		HttpHandler jwebSwingHandler = manager2.start();
		Undertow server = Undertow.builder()
		                          .addHttpListener(6002, "localhost")
		                          .setHandler(jwebSwingHandler)
		                          .build();
		server.start();
	}
}
