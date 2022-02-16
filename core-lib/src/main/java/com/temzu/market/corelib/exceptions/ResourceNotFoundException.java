package com.temzu.market.corelib.exceptions;

import com.temzu.market.corelib.enums.ExceptionTypes;

public class ResourceNotFoundException extends RuntimeException {

  private static final String NOT_FOUND_BY = "%s not found by [%s]: %s";

  private ResourceNotFoundException(Class<?> entityClass, ExceptionTypes type, String reason) {
    super(String.format(NOT_FOUND_BY, entityClass.getSimpleName(), type.getType(), reason));
  }

  public static ResourceNotFoundException byId(Long id, Class<?> entityClass) {
    return new ResourceNotFoundException(entityClass, ExceptionTypes.ID, id.toString());
  }

  public static ResourceNotFoundException byName(String name, Class<?> entityClass) {
    return new ResourceNotFoundException(entityClass, ExceptionTypes.NAME, name);
  }

  public static ResourceNotFoundException byTitle(String title, Class<?> entityClass) {
    return new ResourceNotFoundException(entityClass, ExceptionTypes.TITLE, title);
  }

  public static ResourceNotFoundException byLogin(String login, Class<?> entityClass) {
    return new ResourceNotFoundException(entityClass, ExceptionTypes.LOGIN, login);
  }

}
