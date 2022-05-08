package cn.lsr.noveladmin.helpPo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

@Data
@NoArgsConstructor
public class serie {
    private String name;
    @JSONField(ordinal = 1)
    private LinkedList<Integer> data;

    public serie(String name, LinkedList<Integer> data) {
        this.name = name;
        this.data = data;
    }
}
