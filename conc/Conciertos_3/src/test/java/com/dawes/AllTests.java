package com.dawes;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ TestGrupo.class, TestSala.class, TestConcierto.class, TestVenta.class })
public class AllTests {

}
