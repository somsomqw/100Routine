package jp.co.a100routine.model

import java.io.Serializable

class Routine(id: Int, title: String, memo: String, createTime: Long) : Serializable {

    val id = id;
    val title = title;
    val memo = memo;
    val createTime = createTime;

}