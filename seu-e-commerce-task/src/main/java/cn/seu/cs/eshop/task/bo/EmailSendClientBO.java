package cn.seu.cs.eshop.task.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailSendClientBO implements Serializable {
    String host;
    String username;
    String password;
    String defaultEncoding;
    String from;
}
