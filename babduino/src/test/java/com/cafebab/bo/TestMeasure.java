package com.cafebab.bo;

import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** Test the Measure value object. */
class TestMeasure {

  private static Measure measure(
      final int sensor, final int value, final int clock, final Date date) {
    final Measure m = new Measure();
    m.setSensor(sensor);
    m.setValue(value);
    m.setClock(clock);
    m.setDate(date);
    return m;
  }

  @Test
  void gettersReturnSetValues() {
    final Date date = new Date(1000L);
    final Measure m = measure(1, 2, 3, date);
    Assertions.assertEquals(1, m.getSensor());
    Assertions.assertEquals(2, m.getValue());
    Assertions.assertEquals(3, m.getClock());
    Assertions.assertEquals(date, m.getDate());
  }

  @Test
  void equalObjectsAreEqualAndShareHashCode() {
    final Measure a = measure(1, 2, 3, new Date(1000L));
    final Measure b = measure(1, 2, 3, new Date(1000L));
    Assertions.assertEquals(a, b);
    Assertions.assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  void differingFieldsAreNotEqual() {
    final Date date = new Date(1000L);
    final Measure base = measure(1, 2, 3, date);
    Assertions.assertNotEquals(base, measure(9, 2, 3, date));
    Assertions.assertNotEquals(base, measure(1, 9, 3, date));
    Assertions.assertNotEquals(base, measure(1, 2, 9, date));
    Assertions.assertNotEquals(base, measure(1, 2, 3, new Date(2000L)));
  }

  @Test
  void notEqualToNullOrOtherType() {
    final Measure m = measure(1, 2, 3, new Date(0L));
    Assertions.assertNotEquals(m, null);
    Assertions.assertNotEquals(m, "not a measure");
  }

  @Test
  void toStringContainsFields() {
    final String s = measure(4, 5, 6, new Date(0L)).toString();
    Assertions.assertTrue(s.contains("sensor=4"));
    Assertions.assertTrue(s.contains("value=5"));
    Assertions.assertTrue(s.contains("clock=6"));
  }
}
