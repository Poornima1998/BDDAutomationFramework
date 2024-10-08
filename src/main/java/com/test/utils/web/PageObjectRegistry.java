package com.test.utils.web;

import java.util.ArrayList;
import java.util.List;

public class PageObjectRegistry {

	private static List<Object> pageObjects = new ArrayList<>();

    public static void registerPageObject(Object pageObject) {
        pageObjects.add(pageObject);
    }

    public static List<Object> getPageObjects() {
        return pageObjects;
    }
	
}
