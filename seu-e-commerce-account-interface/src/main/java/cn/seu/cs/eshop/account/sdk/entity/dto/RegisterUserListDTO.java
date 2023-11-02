package cn.seu.cs.eshop.account.sdk.entity.dto;

import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserListDTO implements PageInterface<RegisterUserInfoDTO> {
    List<RegisterUserInfoDTO> records;
    PageDTO page;
}
