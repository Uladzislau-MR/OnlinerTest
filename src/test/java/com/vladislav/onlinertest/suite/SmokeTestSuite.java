package com.vladislav.onlinertest.suite;

import com.vladislav.onlinertest.test.MainPageTest;
import com.vladislav.onlinertest.test.NegativeTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({NegativeTest.class})
public class SmokeTestSuite {
}
