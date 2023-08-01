package org.leruk.spring.SensorMeasurementsApp.utils;

public class SensorErrorResponse {
    private String msg;
    private long timestamp;

    public SensorErrorResponse(String msg, long timestamp)
    {
        this.msg = msg;
        this.timestamp = timestamp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
