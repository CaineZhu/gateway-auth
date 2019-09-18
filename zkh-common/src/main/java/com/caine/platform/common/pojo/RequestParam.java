package com.caine.platform.common.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 16:49 2019/9/16
 * @Modified By:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RequestParam  implements Serializable {
    private long uid;
    private long companyId;
    private long roleId;
}
