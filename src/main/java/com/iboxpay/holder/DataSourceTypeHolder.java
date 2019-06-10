package com.iboxpay.holder;

import com.iboxpay.constants.DataSourceType;

public abstract class DataSourceTypeHolder {

  private static final ThreadLocal<DataSourceType> DATA_SOURCE_TYPE_HOLDER = new ThreadLocal<>();

  /**
   * 根据数据源类型选择数据源
   * @param dataSourceType
   */
  public static void setDataSourceType(DataSourceType dataSourceType) {
    DATA_SOURCE_TYPE_HOLDER.set(dataSourceType);
  }

  public static DataSourceType getDataSourceType() {
    return DATA_SOURCE_TYPE_HOLDER.get();
  }
}
