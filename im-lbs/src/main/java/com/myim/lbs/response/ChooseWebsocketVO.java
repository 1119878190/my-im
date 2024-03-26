package com.myim.lbs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lx
 * @date 2024/03/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChooseWebsocketVO {

    /**
     *ip
     */
    private String ip;

    /**
     *port
     */
    private Integer port;
}
