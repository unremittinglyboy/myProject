package cn.lsr.noveladmin.Service;

import cn.lsr.noveladmin.model.Graphtimecount;

import java.util.Date;

public interface GraphService {
    void insertGraph(Graphtimecount graphtimecount);
    Graphtimecount selectByYear(Integer year);
}
