package com.myim.lbs.controller;

import com.myim.common.responseprotocal.ResultResponse;
import com.myim.common.util.ResultResponseUtil;
import com.myim.lbs.response.ChooseWebsocketVO;
import com.myim.lbs.service.RandomWebsocketLBSImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lx
 * @date 2024/03/26
 */
@RestController
@RequestMapping("/lbs/choose")
public class ChooseWebsocketController {


    @Autowired
    private RandomWebsocketLBSImpl  randomWebsocketLBS;


    @GetMapping("/websocket")
    public ResultResponse<ChooseWebsocketVO> choose(){
        return ResultResponseUtil.success(randomWebsocketLBS.choose());
    }


}
