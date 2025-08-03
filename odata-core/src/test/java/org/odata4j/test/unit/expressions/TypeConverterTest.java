package org.odata4j.test.unit.expressions;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import junit.framework.Assert;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Test;
import org.odata4j.internal.TypeConverter;

public class TypeConverterTest {

  @Test
  public void testTypeConverter() {
    Assert.assertNull(TypeConverter.convert(null, Object.class));
    Assert.assertEquals((byte) 16, (Object) TypeConverter.convert(16, Byte.class));
    Assert.assertEquals(16, (Object) TypeConverter.convert(16, Integer.class));
  }

  @Test
  public void testTemporalTypes() throws ParseException {

    // Create expected dates directly to avoid locale-dependent parsing
    Calendar cal = Calendar.getInstance();

    // Create expected date: March 28, 2011, 7:20:21 PM
    cal.set(2011, Calendar.MARCH, 28, 19, 20, 21);
    cal.set(Calendar.MILLISECOND, 0);
    Date expectedDateTime = cal.getTime();

    // Create expected time: 7:20:21 PM (with epoch date 1970-01-01)
    cal.set(1970, Calendar.JANUARY, 1, 19, 20, 21);
    cal.set(Calendar.MILLISECOND, 0);
    Date expectedTime = cal.getTime();

    // Create expected date for 0:00:00 AM
    cal.set(2011, Calendar.MARCH, 28, 0, 0, 0);
    cal.set(Calendar.MILLISECOND, 0);
    Date expectedDateOnly = cal.getTime();

    Assert.assertEquals(expectedDateTime,
        TypeConverter.convert(new LocalDateTime(2011, 03, 28, 19, 20, 21), Date.class));

    Assert.assertEquals(expectedTime,
        TypeConverter.convert(new LocalTime(19, 20, 21), Date.class));

    cal.setTime(expectedDateTime);
    Assert.assertEquals(cal,
        TypeConverter.convert(new LocalDateTime(2011, 03, 28, 19, 20, 21), Calendar.class));

    cal.setTime(expectedTime);
    Assert.assertEquals(cal,
        TypeConverter.convert(new LocalTime(19, 20, 21), Calendar.class));

    Assert.assertEquals(new java.sql.Time(expectedTime.getTime()),
        TypeConverter.convert(new LocalDateTime(1970, 1, 1, 19, 20, 21), java.sql.Time.class));

    Assert.assertEquals(new java.sql.Time(expectedTime.getTime()),
        TypeConverter.convert(new LocalTime(19, 20, 21), java.sql.Time.class));

    Assert.assertEquals(new java.sql.Date(expectedDateOnly.getTime()),
        TypeConverter.convert(new LocalDateTime(2011, 03, 28, 0, 0), java.sql.Date.class));

    Assert.assertEquals(new java.sql.Timestamp(expectedDateTime.getTime()),
        TypeConverter.convert(new LocalDateTime(2011, 03, 28, 19, 20, 21), java.sql.Timestamp.class));

    Assert.assertEquals(new java.sql.Timestamp(expectedTime.getTime()),
        TypeConverter.convert(new LocalTime(19, 20, 21), java.sql.Timestamp.class));
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertLocalDateTimeWithDateComponentsToSqlTimeFails() throws Exception {
    TypeConverter.convert(new LocalDateTime(2011, 03, 28, 19, 20, 21), java.sql.Time.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertLocalDateTimeWithTimeComponentsToSqlDateFails() throws Exception {
    TypeConverter.convert(new LocalDateTime(2011, 03, 28, 19, 20, 21), java.sql.Date.class);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void convertLocalTimeToSqlDateFails() throws Exception {
    TypeConverter.convert(new LocalTime(19, 20, 21), java.sql.Date.class);
  }
}
