package command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseCommandResult {
    /**
     * 是否成功
     */
    Boolean success;
    /**
     * 远程服务器返回内容 中性
     */
    String returnNormalContent;
    /**
     * 远程服务器返回内容 失败
     */
    String returnErrorContent;
    /**
     * 内部执行报错
     */
    String executeExceptionContent;
}
