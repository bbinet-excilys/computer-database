package com.excilys.ui;

import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.validator.routines.DateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityToStringStyle extends ToStringStyle {

  private Logger LOGGER        = LoggerFactory.getLogger(EntityToStringStyle.class);
  DateValidator  dateValidator = DateValidator.getInstance();

  public EntityToStringStyle() {
    setUseClassName(false);
    setUseFieldNames(false);
    setUseIdentityHashCode(false);
    setContentStart(null);
    setContentEnd(null);
    setNullText("-");
  }

  @Override
  public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
    Optional.ofNullable(value).ifPresentOrElse(object -> {
      if (object instanceof Long) {
        buffer.append(String.format("%5s", object));
      }
      else {
        buffer.append(String.format("%30.30s", object));
      }
    }, () -> {
      buffer.append(String.format("%30s", getNullText()));
    });
  }

}
