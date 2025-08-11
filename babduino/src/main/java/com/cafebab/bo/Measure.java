package com.cafebab.bo;

import java.util.Date;

public class Measure {

  private int sensor;
  private int value;
  private int clock;
  private Date date;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + sensor;
    result = prime * result + value;
    result = prime * result + clock;
    result = prime * result + (date == null ? 0 : date.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Measure other = (Measure) obj;
    if (sensor != other.sensor) {
      return false;
    }
    if (value != other.value) {
      return false;
    }
    if (clock != other.clock) {
      return false;
    }
    if (date == null) {
      if (other.date != null) {
        return false;
      }
    } else if (!date.equals(other.date)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Measure [sensor="
        + sensor
        + ", value="
        + value
        + ", clock="
        + clock
        + ", date="
        + date
        + "]";
  }

  public int getSensor() {
    return sensor;
  }

  public void setSensor(int sensor) {
    this.sensor = sensor;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getClock() {
    return clock;
  }

  public void setClock(int clock) {
    this.clock = clock;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
