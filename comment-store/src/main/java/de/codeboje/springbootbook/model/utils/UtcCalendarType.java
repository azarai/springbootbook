package de.codeboje.springbootbook.model.utils;

import java.util.Calendar;
import java.util.Comparator;
import java.util.TimeZone;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.CalendarType;
import org.hibernate.type.VersionType;
import org.hibernate.type.descriptor.java.CalendarTypeDescriptor;

/**
 * Hibernate column type for Calendars (using UTC time stamps). 
 */
@SuppressWarnings("serial")
public class UtcCalendarType extends AbstractSingleColumnStandardBasicType<Calendar> implements VersionType<Calendar> {

    public static final UtcCalendarType INSTANCE = new UtcCalendarType();
    
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    /**
     * Default constructor.
     */
    public UtcCalendarType() {
        super(UtcTimestampTypeDescriptor.INSTANCE, CalendarTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return CalendarType.INSTANCE.getName();
    }

    @Override
    public String[] getRegistrationKeys() {
        return CalendarType.INSTANCE.getRegistrationKeys();
    }

    @Override
    public Calendar next(Calendar current, SessionImplementor session) {
        return seed( session );
    }

    @Override
    public Calendar seed(SessionImplementor session) {
        return Calendar.getInstance(UTC);
    }

    @Override
    public Comparator<Calendar> getComparator() {
        return CalendarType.INSTANCE.getComparator();
    }

    @Override
    public Calendar fromStringValue(String xml) {
        return CalendarType.INSTANCE.fromStringValue(xml);
    }
}
