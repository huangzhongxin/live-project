package project.echo.lottery.pojo;

import lombok.Data;

@Data
public class Response {
    private Boolean success;
    private Integer code;
    private String errMsg;
    private Object data;

    public Response(Boolean success,Integer code,String errMsg,Object data){
        this.code=code;
        this.success=success;
        this.errMsg=errMsg;
        this.data=data;
    }
}
