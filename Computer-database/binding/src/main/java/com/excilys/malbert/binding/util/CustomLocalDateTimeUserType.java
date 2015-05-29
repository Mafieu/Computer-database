package com.excilys.malbert.binding.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.EnhancedUserType;

/**
 * Class used for mapping LocalDateTime to TimeStamp
 * 
 * @author excilys
 * @see Computer
 */
public class CustomLocalDateTimeUserType implements EnhancedUserType,
		Serializable {
	private static final long serialVersionUID = 1106096378628680326L;
	private static final int[] SQL_TYPES = new int[] { Types.TIMESTAMP };

	@SuppressWarnings("rawtypes")
	@Override
	public Class returnedClass() {
		return LocalDateTime.class;
	}

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if (x == null || y == null) {
			return false;
		}
		if (LocalDateTime.class.isInstance(x)
				&& LocalDateTime.class.isInstance(y)) {
			LocalDateTime ldtx = (LocalDateTime) x;
			LocalDateTime ldty = (LocalDateTime) y;
			return ldtx.equals(ldty);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode(Object obj) throws HibernateException {
		return obj.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		Object timestamp = StandardBasicTypes.TIMESTAMP.nullSafeGet(resultSet,
				names, session, owner);
		if (timestamp == null) {
			return null;
		}
		Date date = (Date) timestamp;
		Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object value,
			int index, SessionImplementor session) throws HibernateException,
			SQLException {
		if (value == null) {
			StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, null,
					index, session);
		} else {
			LocalDateTime ldt = ((LocalDateTime) value);
			Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
			Date timestamp = Date.from(instant);
			StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement,
					timestamp, index, session);
		}
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object value)
			throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

	@Override
	public String objectToSQLString(Object object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toXMLString(Object object) {
		return object.toString();
	}

	@Override
	public Object fromXMLString(String string) {
		return LocalDateTime.parse(string);
	}
}
