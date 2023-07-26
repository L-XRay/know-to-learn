package com.cqupt.knowtolearn.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ray
 * @date 2023/7/26 17:05
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应状态
     */
    private Integer code;

    private String status;
    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应中的数据
     */
    private Object data;

    public static Result build(Integer code, String status, String message, Object data) {
        return new Result(code, status, message, data);
    }

    /**
     * 响应成功
     *
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static Result success(Integer code, String message, Object data) {
        return new Result(code, message, data);
    }

    public static Result success(String message, Object data) {
        return new Result(message, data);
    }

    public static Result success(Integer code, String message) {
        return new Result(code, message, null);
    }

    public static Result success(Integer code) {
        return new Result(code, null, null);
    }

    /**
     * 响应失败
     *
     * @param code
     * @param message
     * @return
     */
    public static Result fail(Integer code, String message) {
        return new Result(code, message);
    }

    public static Result fail(String message) {
        return new Result(message);
    }

    public static Result build(Integer code, String status, String msg) {
        return new Result(code, status, msg, null);
    }

    public Result(String message, Object data) {
        this.code = 200;
        this.status = "success";
        this.message = message;
        this.data = data;
    }

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.status = "success";
        this.message = message;
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.status = "fail";
        this.message = message;
    }

    public Result(String message) {
        this.code = 501;
        this.status = "fail";
        this.message = message;
    }

    public static Result formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, Result.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("status").asText(), jsonNode.get("message").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static Result format(String json) {
        try {
            return MAPPER.readValue(json, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Result formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("status").asText(), jsonNode.get("message").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }
}