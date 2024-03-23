package com.myim.model;


import lombok.Data;

import java.util.Date;

@Data
public class SendMessageVO {


    private String content;

    private Date sendDate;

}
