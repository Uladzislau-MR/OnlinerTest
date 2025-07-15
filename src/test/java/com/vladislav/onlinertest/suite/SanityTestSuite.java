package com.vladislav.onlinertest.suite;

import com.vladislav.onlinertest.test.MainPageTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({MainPageTest.class})
public class SanityTestSuite {
}
