package de.codeboje.springbootbook.model.utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.TimestampTypeDescriptor;

/**
 * Hibernate hint to retrieve timestamps as UTC from database instead of default
 * time zone.
 */
@SuppressWarnings("serial")
public class UtcTimestampTypeDescriptor extends TimestampTypeDescriptor {
	public static final UtcTimestampTypeDescriptor INSTANCE = new UtcTimestampTypeDescriptor();

	private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

	@Override
	public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
		return new BasicBinder<X>(javaTypeDescriptor, this) {
			@Override
			protected void doBind(CallableStatement st, X value, String index, WrapperOptions options)
					throws SQLException {
				st.setTimestamp(index, javaTypeDescriptor.unwrap(value, Timestamp.class, options),
						Calendar.getInstance(UTC));
			}

			@Override
			protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
				st.setTimestamp(index, javaTypeDescriptor.unwrap(value, Timestamp.class, options),
						Calendar.getInstance(UTC));

			}
		};
	}

	@Override
	public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
		return new BasicExtractor<X>(javaTypeDescriptor, this) {
			@Override
			protected X doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException {
				return javaTypeDescriptor.wrap(rs.getTimestamp(name, Calendar.getInstance(UTC)), options);
			}

			@Override
			protected X doExtract(CallableStatement arg0, int name, WrapperOptions options) throws SQLException {
				return javaTypeDescriptor.wrap(arg0.getTimestamp(name, Calendar.getInstance(UTC)), options);
			}

			@Override
			protected X doExtract(CallableStatement arg0, String name, WrapperOptions options) throws SQLException {
				return javaTypeDescriptor.wrap(arg0.getTimestamp(name, Calendar.getInstance(UTC)), options);
			}
		};
	}

}
