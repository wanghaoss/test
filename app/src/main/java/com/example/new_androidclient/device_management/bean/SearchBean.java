package com.example.new_androidclient.device_management.bean;

import java.util.List;

public class SearchBean {

    /**
     * id : 2
     * name : 丹东明珠
     * parentId : 1
     * treeId : 2
     * children : [{"id":16,"name":"三车间","parentId":2,"treeId":16,"children":[{"id":2,"name":"氯气仓库","parentId":16,
     * "treeId":null,"children":null}]},{"id":17,"name":"四车间","parentId":2,"treeId":17,"children":[{"id":1,"name":"聚合装置",
     * "parentId":17,"treeId":null,"children":null},{"id":3,"name":"锅炉","parentId":17,"treeId":null,"children":null},{"id":60,
     * "name":"q","parentId":17,"treeId":null,"children":null},{"id":61,"name":"qwe","parentId":17,"treeId":null,"children":null},
     * {"id":63,"name":"22","parentId":17,"treeId":null,"children":null},{"id":62,"name":"1","parentId":17,"treeId":null,"children":null},
     * {"id":64,"name":"1","parentId":17,"treeId":null,"children":null},{"id":66,"name":"1","parentId":17,"treeId":null,"children":null},
     * {"id":67,"name":"1","parentId":17,"treeId":null,"children":null},{"id":68,"name":"1","parentId":17,"treeId":null,"children":null},
     * {"id":69,"name":"1","parentId":17,"treeId":null,"children":null},{"id":70,"name":"1","parentId":17,"treeId":null,"children":null},
     * {"id":71,"name":"1","parentId":17,"treeId":null,"children":null},{"id":72,"name":"7","parentId":17,"treeId":null,"children":null},
     * {"id":73,"name":"789","parentId":17,"treeId":null,"children":null},{"id":74,"name":"聚合装置111","parentId":17,"treeId":null,"children":null},
     * {"id":75,"name":"mojito","parentId":17,"treeId":null,"children":null}]},
     * {"id":23,"name":"七车间","parentId":2,"treeId":23,"children":[{"id":59,"name":"111","parentId":23,"treeId":null,"children":null},
     * {"id":65,"name":"11","parentId":23,"treeId":null,"children":null},
     * {"id":4,"name":"硫酸再生","parentId":23,"treeId":null,"children":null}]}]
     */

    private int id;
    private String name;
    private int parentId;
    private int treeId;
    private List<SearchBean> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getTreeId() {
        return treeId;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }

    public List<SearchBean> getChildren() {
        return children;
    }

    public void setChildren(List<SearchBean> children) {
        this.children = children;
    }

    public static class ChildrenBeanX {
        /**
         * id : 16
         * name : 三车间
         * parentId : 2
         * treeId : 16
         * children : [{"id":2,"name":"氯气仓库","parentId":16,"treeId":null,"children":null}]
         */

        private int id;
        private String name;
        private int parentId;
        private int treeId;
        private List<SearchBean> children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getTreeId() {
            return treeId;
        }

        public void setTreeId(int treeId) {
            this.treeId = treeId;
        }

        public List<SearchBean> getChildren() {
            return children;
        }

        public void setChildren(List<SearchBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 2
             * name : 氯气仓库
             * parentId : 16
             * treeId : null
             * children : null
             */

            private int id;
            private String name;
            private int parentId;
            private Object treeId;
            private Object children;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public Object getTreeId() {
                return treeId;
            }

            public void setTreeId(Object treeId) {
                this.treeId = treeId;
            }

            public Object getChildren() {
                return children;
            }

            public void setChildren(Object children) {
                this.children = children;
            }
        }
    }
}
