

package com.waminet.common.tools.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
