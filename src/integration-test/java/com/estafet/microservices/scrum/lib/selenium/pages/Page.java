package com.estafet.microservices.scrum.lib.selenium.pages;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

public abstract class Page {

	private final WebDriver driver;
	private final URL url;

	public Page(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		try {
			this.url = new URL(System.getenv("BASIC_UI_URI") + uri());
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public Page() {
		this("");
	}

	public Page(String... params) {
		try {
			this.url = new URL(System.getenv("BASIC_UI_URI") + resolveUri(params));
			Capabilities capabilities = DesiredCapabilities.htmlUnitWithJs();
			driver = new HtmlUnitDriver(capabilities);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			PageFactory.initElements(driver, this);
			driver.get(url.toString());
			if (!isLoaded()) {
				throw new RuntimeException("cannot load url " + driver.getCurrentUrl());
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public abstract String title();

	public boolean isLoaded(String... params) {
		String compare = System.getenv("BASIC_UI_URI") + resolveUri(params);
		return compare.equals(driver.getCurrentUrl()) && driver.getTitle().equals(title());
	}

	public boolean isLoaded() {
		return url.toString().equals(driver.getCurrentUrl())
				&& driver.getTitle().equals(title());
	}
	
	public void close() {
		driver.close();
	}

	protected <T extends Page> T click(WebElement element, Class<T> clazz) {
		try {
			element.click();
			Constructor<T> constructor = clazz.getConstructor(WebDriver.class);
			return constructor.newInstance(driver);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	protected <T extends Page> T click(String text, List<WebElement> items, Class<T> clazz) {
		for (WebElement item : items) {
			if (item.getText().equals(text)) {
				return click(item, clazz);
			}
		}
		throw new RuntimeException("cannot find link for " + text);
	}

	protected List<String> getTextList(List<WebElement> items) {
		List<String> names = new ArrayList<String>();
		for (WebElement item : items) {
			names.add(item.getText());
		}
		return names;
	}

	protected WebDriver getDriver() {
		return driver;
	}

	protected URL getUrl() {
		return url;
	}

	private String resolveUri(String... params) {
		String uri = uri();
		int index = 1;
		for (String param : params) {
			uri = uri.replaceAll("\\{" + index + "\\}", param);
			index++;
		}
		return uri;
	}

	public abstract String uri();

}
