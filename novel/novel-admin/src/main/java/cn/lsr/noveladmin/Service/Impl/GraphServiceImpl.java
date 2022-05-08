package cn.lsr.noveladmin.Service.Impl;

import cn.lsr.noveladmin.Service.GraphService;
import cn.lsr.noveladmin.mapping.GraphtimecountMapper;
import cn.lsr.noveladmin.model.Graphtimecount;
import cn.lsr.noveladmin.model.GraphtimecountExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GraphServiceImpl implements GraphService {
    @Autowired
    private GraphtimecountMapper graphtimecountMapper;

    @Override
    public void insertGraph(Graphtimecount graphtimecount) {
        if(graphtimecount == null) return;
        else {
                int date = LocalDate.now().getYear();
                GraphtimecountExample g = new GraphtimecountExample();
                GraphtimecountExample.Criteria c = g.createCriteria();
                c.andYearEqualTo(date);
                List<Graphtimecount> res = graphtimecountMapper.selectByExample(g);
                graphtimecount.setId(res.get(0).getId());
                graphtimecount.setYear(date);
                if(res == null || res.size() == 0){
                    graphtimecountMapper.insert(graphtimecount);
                }else{
                    graphtimecountMapper.updateByExample(graphtimecount, g);
                }
        }
    }

    @Override
    public Graphtimecount selectByYear(Integer year) {
        GraphtimecountExample g = new GraphtimecountExample();
        GraphtimecountExample.Criteria c = g.createCriteria();
        c.andYearEqualTo(year);
        List<Graphtimecount> graphtimecounts = graphtimecountMapper.selectByExample(g);
        if(graphtimecounts == null || graphtimecounts.size() == 0) return null;
        else return graphtimecounts.get(0);
    }
}
