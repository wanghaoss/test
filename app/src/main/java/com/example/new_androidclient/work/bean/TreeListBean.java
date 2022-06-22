package com.example.new_androidclient.work.bean;

import java.io.Serializable;
import java.util.List;

public class TreeListBean implements Serializable {

    /**
     * children : [{"children":[{"id":9,"label":"将动火设备、管道内的物料清晰、置换、分析确认合格"},{"id":10,"label":"储罐动火清理易燃物，管内盛满清水或惰性气体保护"},{"id":11,"label":"设备内通氮气或蒸汽保护"},{"id":12,"label":"进入受限空间作业必须办理受限空间作业票，强制通风"},{"id":13,"label":"涉爆粉尘作业场所，将积粉彻底清理干净，储罐动火要空罐罐内无积粉"}],"id":1,"label":"易燃易爆有害物质"},{"children":[{"id":15,"label":"电缆沟内动火必须清除沟内易燃气体液体，并将沟两端隔断"},{"id":14,"label":"清除动火点周围的易燃物，动火附近的电缆沟、库房易燃物清理后予以封闭"}],"id":2,"label":"动火点周围有易燃物"},{"children":[{"id":16,"label":"高处作业必须办理高处作业票"},{"id":17,"label":"注意火星飞溅方向，用水或者渍泥浇灭火星落点"}],"id":3,"label":"火星飞溅"},{"children":[{"id":18,"label":"气瓶间安全距离不小于5米，与动火点不小于10米"},{"id":19,"label":"气瓶不准在烈日下曝晒，乙炔瓶不得卧防"}],"id":4,"label":"气瓶间距不足"},{"children":[{"id":20,"label":"动火作业前，要检查焊把、焊帽、氧气管等电气焊作业工具，确保安全可靠，不带病使用\t\t\t\r\n动火作业前，要检查焊把、焊帽、氧气管等电气焊作业工具，确保安全可靠，不带病使用"}],"id":5,"label":"电气焊工具有缺陷"},{"children":[{"id":21,"label":"煤气管网内等受限空间作业，要30分钟内进行采样分析，检测达标准许作业，同时进行强制通风，不免气体积聚，引发窒息或中毒"}],"id":6,"label":"未定时检测、通风不良"},{"children":[{"id":22,"label":"岗位监火人必须熟悉现场环境和检查确认安全措施是否到位，具备相关安全知识和应急常识，做好岗位把关工作"},{"id":23,"label":"岗位监火人要随时采取防火措施，消灭飞溅的火花，发现异常立即停止作业"}],"id":7,"label":"监护不当"},{"children":[{"id":24,"label":"动火现场要针对性地备好灭火器材（蒸汽管、灭火器、沙子、铁锨）"}],"id":8,"label":"应急措施不足"}]
     * id : 1
     * label : 动火作业
     */

    private int id;
    private String label;
    private List<ChildrenBeanX> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ChildrenBeanX> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBeanX> children) {
        this.children = children;
    }

    public static class ChildrenBeanX implements Serializable{
        /**
         * children : [{"id":9,"label":"将动火设备、管道内的物料清晰、置换、分析确认合格"},{"id":10,"label":"储罐动火清理易燃物，管内盛满清水或惰性气体保护"},{"id":11,"label":"设备内通氮气或蒸汽保护"},{"id":12,"label":"进入受限空间作业必须办理受限空间作业票，强制通风"},{"id":13,"label":"涉爆粉尘作业场所，将积粉彻底清理干净，储罐动火要空罐罐内无积粉"}]
         * id : 1
         * label : 易燃易爆有害物质
         */

        private int id;
        private String label;
        private List<ChildrenBean> children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean implements Serializable{
            /**
             * id : 9
             * label : 将动火设备、管道内的物料清晰、置换、分析确认合格
             */

            private int id;
            private String label;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }
    }
}
