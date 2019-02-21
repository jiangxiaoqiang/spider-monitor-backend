package model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApiResult<T> {
    @ApiModelProperty(value = "response code")
    private int code = 20000;

    @ApiModelProperty(value = "message")
    private String message = "ok";

    @ApiModelProperty(value = "data wrap")
    private Object data;

    public ApiResult(T data)
    {
        this.data = data;
    }
}
