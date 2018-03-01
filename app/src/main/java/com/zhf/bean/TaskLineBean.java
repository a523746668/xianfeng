package com.zhf.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhf on 2017/10/11.
 */

public class TaskLineBean {


    /**
     * error_msg : success
     * error_code : 0
     * data : {"0":{"id":1,"system_id":1,"parent_id":0,"library_id":1,"level":0,"row":2,"parent_link":"1","name":"营造浓厚氛围夯实基础工作","child":[{"id":3,"system_id":1,"parent_id":1,"library_id":3,"level":1,"row":1,"parent_link":"1-3","name":"创建氛围","mytask":[{"id":1,"target":"党组（党委）年初专题研究文明创建工作。4 分","score":"4.00"},{"id":2,"target":"工青妇、关工委等群团组织大力开展活动，营造文明创建浓厚氛围。4 分","score":"4.00"}]},{"id":4,"system_id":1,"parent_id":1,"library_id":4,"level":1,"row":1,"parent_link":"1-4","name":"基础工作","mytask":[{"id":3,"target":"年初制定出台文明创建实施方案。3 分","score":"3.00"},{"id":4,"target":"文明创建列入年度（绩效）考核，在人、财、物方面有保障。3 分","score":"3.00"},{"id":5,"target":"开展文明处科室、文明车间班组、文明职工特别是文明家庭建设活动。6 分","score":"6.00"}]}]},"1":{"id":2,"system_id":1,"parent_id":0,"library_id":2,"level":0,"row":2,"parent_link":"2","name":"加强理论武装突出廉政建设","child":[{"id":5,"system_id":1,"parent_id":2,"library_id":5,"level":1,"row":1,"parent_link":"2-5","name":"理论武装","mytask":[{"id":6,"target":"每年开展党组（党委）中心组学习不低于 4 次。10 分","score":"10.00"},{"id":7,"target":"扎实推进\u201c两学一做\u201d学习教育常态化制度化。5 分","score":"5.00"},{"id":8,"target":"在落实意识形态工作责任制、加强意识形态工作方面有实际举措。5 分","score":"3.00"}]},{"id":6,"system_id":1,"parent_id":2,"library_id":6,"level":1,"row":1,"parent_link":"2-6","name":"廉政建设","mytask":[{"id":9,"target":"组织开展党纪党规学习教育，不断增强党员干部廉政意识和规矩意识。5 分","score":"5.00"}]}]},"6":{"id":7,"system_id":1,"parent_id":0,"library_id":7,"level":0,"row":2,"parent_link":"7","name":"打造精神家园培育文明新风","child":[{"id":11,"system_id":1,"parent_id":7,"library_id":11,"level":1,"row":1,"parent_link":"7-11","name":"精神家园","mytask":[{"id":10,"target":"重视文化建设，打造文化品牌，增强文化自信。5 分","score":"5.00"},{"id":11,"target":"文体活动设施条件不断改善。5 分","score":"5.00"}]},{"id":12,"system_id":1,"parent_id":7,"library_id":12,"level":1,"row":1,"parent_link":"7-12","name":"文明风尚","mytask":[{"id":12,"target":"深入开展志愿服务活动，全年省直文明单位集体性志愿服务活动不少于 2 次，省直文明标兵单位不少于 3 次，省级文明单位不少于 4 次，省级文明标兵单位不少于 6次，全国文明单位不少于 8 次。10 分","score":"10.00"},{"id":13,"target":"组织开展\u201c文明餐桌行动\u201d、\u201c文明交通行动\u201d、\u201c文明出游宣传引导\u201d等活动。5 分","score":"5.00"},{"id":14,"target":"结合实际开展网上或手机文明传播活动。 5 分","score":"5.00"}]}]},"7":{"id":8,"system_id":1,"parent_id":0,"library_id":8,"level":0,"row":1,"parent_link":"8","name":"优化内部管理推进综合治理","child":[{"id":13,"system_id":1,"parent_id":8,"library_id":13,"level":1,"row":1,"parent_link":"8-13","name":"综合治理","mytask":[{"id":15,"target":"认真组织开展学法用法考试，考试成绩合格。5 分","score":"5.00"},{"id":16,"target":"结合实际面向干部职工或服务对象开展法治宣传教育。5 分","score":"5.00"}]}]},"8":{"id":9,"system_id":1,"parent_id":0,"library_id":9,"level":0,"row":1,"parent_link":"9","name":"美化内外环境构建生态文明","child":[{"id":14,"system_id":1,"parent_id":9,"library_id":14,"level":1,"row":1,"parent_link":"9-14","name":"环境","mytask":[{"id":17,"target":"贯彻绿色发展理念，积极推进资源节约型、环境友好型机关（单位）建设。 5 分","score":"5.00"}]}]},"9":{"id":10,"system_id":1,"parent_id":0,"library_id":10,"level":0,"row":2,"parent_link":"10","name":"落实工作任务总结工作情况","child":[{"id":15,"system_id":1,"parent_id":10,"library_id":15,"level":1,"row":1,"parent_link":"10-15","name":"落实工作任务","mytask":[{"id":18,"target":"落实省直机关文明办组织开展的工作任务情况。5分。","score":"5.00"}]},{"id":16,"system_id":1,"parent_id":10,"library_id":16,"level":1,"row":1,"parent_link":"10-16","name":"全年工作总结","mytask":[{"id":19,"target":"深入总结本单位本部门全年文明创建工作情况（2000字以内） 5 分。","score":"4.00"}]}]}}
     */

    private String error_msg;
    private int error_code;
    private DataBean data;

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * 0 : {"id":1,"system_id":1,"parent_id":0,"library_id":1,"level":0,"row":2,"parent_link":"1","name":"营造浓厚氛围夯实基础工作","child":[{"id":3,"system_id":1,"parent_id":1,"library_id":3,"level":1,"row":1,"parent_link":"1-3","name":"创建氛围","mytask":[{"id":1,"target":"党组（党委）年初专题研究文明创建工作。4 分","score":"4.00"},{"id":2,"target":"工青妇、关工委等群团组织大力开展活动，营造文明创建浓厚氛围。4 分","score":"4.00"}]},{"id":4,"system_id":1,"parent_id":1,"library_id":4,"level":1,"row":1,"parent_link":"1-4","name":"基础工作","mytask":[{"id":3,"target":"年初制定出台文明创建实施方案。3 分","score":"3.00"},{"id":4,"target":"文明创建列入年度（绩效）考核，在人、财、物方面有保障。3 分","score":"3.00"},{"id":5,"target":"开展文明处科室、文明车间班组、文明职工特别是文明家庭建设活动。6 分","score":"6.00"}]}]}
         * 1 : {"id":2,"system_id":1,"parent_id":0,"library_id":2,"level":0,"row":2,"parent_link":"2","name":"加强理论武装突出廉政建设","child":[{"id":5,"system_id":1,"parent_id":2,"library_id":5,"level":1,"row":1,"parent_link":"2-5","name":"理论武装","mytask":[{"id":6,"target":"每年开展党组（党委）中心组学习不低于 4 次。10 分","score":"10.00"},{"id":7,"target":"扎实推进\u201c两学一做\u201d学习教育常态化制度化。5 分","score":"5.00"},{"id":8,"target":"在落实意识形态工作责任制、加强意识形态工作方面有实际举措。5 分","score":"3.00"}]},{"id":6,"system_id":1,"parent_id":2,"library_id":6,"level":1,"row":1,"parent_link":"2-6","name":"廉政建设","mytask":[{"id":9,"target":"组织开展党纪党规学习教育，不断增强党员干部廉政意识和规矩意识。5 分","score":"5.00"}]}]}
         * 6 : {"id":7,"system_id":1,"parent_id":0,"library_id":7,"level":0,"row":2,"parent_link":"7","name":"打造精神家园培育文明新风","child":[{"id":11,"system_id":1,"parent_id":7,"library_id":11,"level":1,"row":1,"parent_link":"7-11","name":"精神家园","mytask":[{"id":10,"target":"重视文化建设，打造文化品牌，增强文化自信。5 分","score":"5.00"},{"id":11,"target":"文体活动设施条件不断改善。5 分","score":"5.00"}]},{"id":12,"system_id":1,"parent_id":7,"library_id":12,"level":1,"row":1,"parent_link":"7-12","name":"文明风尚","mytask":[{"id":12,"target":"深入开展志愿服务活动，全年省直文明单位集体性志愿服务活动不少于 2 次，省直文明标兵单位不少于 3 次，省级文明单位不少于 4 次，省级文明标兵单位不少于 6次，全国文明单位不少于 8 次。10 分","score":"10.00"},{"id":13,"target":"组织开展\u201c文明餐桌行动\u201d、\u201c文明交通行动\u201d、\u201c文明出游宣传引导\u201d等活动。5 分","score":"5.00"},{"id":14,"target":"结合实际开展网上或手机文明传播活动。 5 分","score":"5.00"}]}]}
         * 7 : {"id":8,"system_id":1,"parent_id":0,"library_id":8,"level":0,"row":1,"parent_link":"8","name":"优化内部管理推进综合治理","child":[{"id":13,"system_id":1,"parent_id":8,"library_id":13,"level":1,"row":1,"parent_link":"8-13","name":"综合治理","mytask":[{"id":15,"target":"认真组织开展学法用法考试，考试成绩合格。5 分","score":"5.00"},{"id":16,"target":"结合实际面向干部职工或服务对象开展法治宣传教育。5 分","score":"5.00"}]}]}
         * 8 : {"id":9,"system_id":1,"parent_id":0,"library_id":9,"level":0,"row":1,"parent_link":"9","name":"美化内外环境构建生态文明","child":[{"id":14,"system_id":1,"parent_id":9,"library_id":14,"level":1,"row":1,"parent_link":"9-14","name":"环境","mytask":[{"id":17,"target":"贯彻绿色发展理念，积极推进资源节约型、环境友好型机关（单位）建设。 5 分","score":"5.00"}]}]}
         * 9 : {"id":10,"system_id":1,"parent_id":0,"library_id":10,"level":0,"row":2,"parent_link":"10","name":"落实工作任务总结工作情况","child":[{"id":15,"system_id":1,"parent_id":10,"library_id":15,"level":1,"row":1,"parent_link":"10-15","name":"落实工作任务","mytask":[{"id":18,"target":"落实省直机关文明办组织开展的工作任务情况。5分。","score":"5.00"}]},{"id":16,"system_id":1,"parent_id":10,"library_id":16,"level":1,"row":1,"parent_link":"10-16","name":"全年工作总结","mytask":[{"id":19,"target":"深入总结本单位本部门全年文明创建工作情况（2000字以内） 5 分。","score":"4.00"}]}]}
         */

        @SerializedName("0")
        private _$0Bean _$0;
        @SerializedName("1")
        private _$1Bean _$1;
        @SerializedName("6")
        private _$6Bean _$6;
        @SerializedName("7")
        private _$7Bean _$7;
        @SerializedName("8")
        private _$8Bean _$8;
        @SerializedName("9")
        private _$9Bean _$9;

        public _$0Bean get_$0() {

            return _$0;
        }


        public void set_$0(_$0Bean _$0) {
            this._$0 = _$0;
        }

        public _$1Bean get_$1() {
            return _$1;
        }

        public void set_$1(_$1Bean _$1) {
            this._$1 = _$1;
        }

        public _$6Bean get_$6() {
            return _$6;
        }

        public void set_$6(_$6Bean _$6) {
            this._$6 = _$6;
        }

        public _$7Bean get_$7() {
            return _$7;
        }

        public void set_$7(_$7Bean _$7) {
            this._$7 = _$7;
        }

        public _$8Bean get_$8() {
            return _$8;
        }

        public void set_$8(_$8Bean _$8) {
            this._$8 = _$8;
        }

        public _$9Bean get_$9() {
            return _$9;
        }

        public void set_$9(_$9Bean _$9) {
            this._$9 = _$9;
        }

        public static class _$0Bean {
            /**
             * id : 1
             * system_id : 1
             * parent_id : 0
             * library_id : 1
             * level : 0
             * row : 2
             * parent_link : 1
             * name : 营造浓厚氛围夯实基础工作
             * child : [{"id":3,"system_id":1,"parent_id":1,"library_id":3,"level":1,"row":1,"parent_link":"1-3","name":"创建氛围","mytask":[{"id":1,"target":"党组（党委）年初专题研究文明创建工作。4 分","score":"4.00"},{"id":2,"target":"工青妇、关工委等群团组织大力开展活动，营造文明创建浓厚氛围。4 分","score":"4.00"}]},{"id":4,"system_id":1,"parent_id":1,"library_id":4,"level":1,"row":1,"parent_link":"1-4","name":"基础工作","mytask":[{"id":3,"target":"年初制定出台文明创建实施方案。3 分","score":"3.00"},{"id":4,"target":"文明创建列入年度（绩效）考核，在人、财、物方面有保障。3 分","score":"3.00"},{"id":5,"target":"开展文明处科室、文明车间班组、文明职工特别是文明家庭建设活动。6 分","score":"6.00"}]}]
             */

            private int id;
            private int system_id;
            private int parent_id;
            private int library_id;
            private int level;
            private int row;
            private String parent_link;
            private String name;
            private List<ChildBean> child;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSystem_id() {
                return system_id;
            }

            public void setSystem_id(int system_id) {
                this.system_id = system_id;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public int getLibrary_id() {
                return library_id;
            }

            public void setLibrary_id(int library_id) {
                this.library_id = library_id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getRow() {
                return row;
            }

            public void setRow(int row) {
                this.row = row;
            }

            public String getParent_link() {
                return parent_link;
            }

            public void setParent_link(String parent_link) {
                this.parent_link = parent_link;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            public static class ChildBean {
                /**
                 * id : 3
                 * system_id : 1
                 * parent_id : 1
                 * library_id : 3
                 * level : 1
                 * row : 1
                 * parent_link : 1-3
                 * name : 创建氛围
                 * mytask : [{"id":1,"target":"党组（党委）年初专题研究文明创建工作。4 分","score":"4.00"},{"id":2,"target":"工青妇、关工委等群团组织大力开展活动，营造文明创建浓厚氛围。4 分","score":"4.00"}]
                 */

                private int id;
                private int system_id;
                private int parent_id;
                private int library_id;
                private int level;
                private int row;
                private String parent_link;
                private String name;
                private List<MytaskBean> mytask;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSystem_id() {
                    return system_id;
                }

                public void setSystem_id(int system_id) {
                    this.system_id = system_id;
                }

                public int getParent_id() {
                    return parent_id;
                }

                public void setParent_id(int parent_id) {
                    this.parent_id = parent_id;
                }

                public int getLibrary_id() {
                    return library_id;
                }

                public void setLibrary_id(int library_id) {
                    this.library_id = library_id;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public int getRow() {
                    return row;
                }

                public void setRow(int row) {
                    this.row = row;
                }

                public String getParent_link() {
                    return parent_link;
                }

                public void setParent_link(String parent_link) {
                    this.parent_link = parent_link;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public List<MytaskBean> getMytask() {
                    return mytask;
                }

                public void setMytask(List<MytaskBean> mytask) {
                    this.mytask = mytask;
                }

                public static class MytaskBean {
                    /**
                     * id : 1
                     * target : 党组（党委）年初专题研究文明创建工作。4 分
                     * score : 4.00
                     */

                    private int id;
                    private String target;
                    private String score;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTarget() {
                        return target;
                    }

                    public void setTarget(String target) {
                        this.target = target;
                    }

                    public String getScore() {
                        return score;
                    }

                    public void setScore(String score) {
                        this.score = score;
                    }
                }
            }
        }

        public static class _$1Bean {
            /**
             * id : 2
             * system_id : 1
             * parent_id : 0
             * library_id : 2
             * level : 0
             * row : 2
             * parent_link : 2
             * name : 加强理论武装突出廉政建设
             * child : [{"id":5,"system_id":1,"parent_id":2,"library_id":5,"level":1,"row":1,"parent_link":"2-5","name":"理论武装","mytask":[{"id":6,"target":"每年开展党组（党委）中心组学习不低于 4 次。10 分","score":"10.00"},{"id":7,"target":"扎实推进\u201c两学一做\u201d学习教育常态化制度化。5 分","score":"5.00"},{"id":8,"target":"在落实意识形态工作责任制、加强意识形态工作方面有实际举措。5 分","score":"3.00"}]},{"id":6,"system_id":1,"parent_id":2,"library_id":6,"level":1,"row":1,"parent_link":"2-6","name":"廉政建设","mytask":[{"id":9,"target":"组织开展党纪党规学习教育，不断增强党员干部廉政意识和规矩意识。5 分","score":"5.00"}]}]
             */

            private int id;
            private int system_id;
            private int parent_id;
            private int library_id;
            private int level;
            private int row;
            private String parent_link;
            private String name;
            private List<ChildBeanX> child;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSystem_id() {
                return system_id;
            }

            public void setSystem_id(int system_id) {
                this.system_id = system_id;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public int getLibrary_id() {
                return library_id;
            }

            public void setLibrary_id(int library_id) {
                this.library_id = library_id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getRow() {
                return row;
            }

            public void setRow(int row) {
                this.row = row;
            }

            public String getParent_link() {
                return parent_link;
            }

            public void setParent_link(String parent_link) {
                this.parent_link = parent_link;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ChildBeanX> getChild() {
                return child;
            }

            public void setChild(List<ChildBeanX> child) {
                this.child = child;
            }

            public static class ChildBeanX {
                /**
                 * id : 5
                 * system_id : 1
                 * parent_id : 2
                 * library_id : 5
                 * level : 1
                 * row : 1
                 * parent_link : 2-5
                 * name : 理论武装
                 * mytask : [{"id":6,"target":"每年开展党组（党委）中心组学习不低于 4 次。10 分","score":"10.00"},{"id":7,"target":"扎实推进\u201c两学一做\u201d学习教育常态化制度化。5 分","score":"5.00"},{"id":8,"target":"在落实意识形态工作责任制、加强意识形态工作方面有实际举措。5 分","score":"3.00"}]
                 */

                private int id;
                private int system_id;
                private int parent_id;
                private int library_id;
                private int level;
                private int row;
                private String parent_link;
                private String name;
                private List<MytaskBeanX> mytask;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSystem_id() {
                    return system_id;
                }

                public void setSystem_id(int system_id) {
                    this.system_id = system_id;
                }

                public int getParent_id() {
                    return parent_id;
                }

                public void setParent_id(int parent_id) {
                    this.parent_id = parent_id;
                }

                public int getLibrary_id() {
                    return library_id;
                }

                public void setLibrary_id(int library_id) {
                    this.library_id = library_id;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public int getRow() {
                    return row;
                }

                public void setRow(int row) {
                    this.row = row;
                }

                public String getParent_link() {
                    return parent_link;
                }

                public void setParent_link(String parent_link) {
                    this.parent_link = parent_link;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public List<MytaskBeanX> getMytask() {
                    return mytask;
                }

                public void setMytask(List<MytaskBeanX> mytask) {
                    this.mytask = mytask;
                }

                public static class MytaskBeanX {
                    /**
                     * id : 6
                     * target : 每年开展党组（党委）中心组学习不低于 4 次。10 分
                     * score : 10.00
                     */

                    private int id;
                    private String target;
                    private String score;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTarget() {
                        return target;
                    }

                    public void setTarget(String target) {
                        this.target = target;
                    }

                    public String getScore() {
                        return score;
                    }

                    public void setScore(String score) {
                        this.score = score;
                    }
                }
            }
        }

        public static class _$6Bean {
            /**
             * id : 7
             * system_id : 1
             * parent_id : 0
             * library_id : 7
             * level : 0
             * row : 2
             * parent_link : 7
             * name : 打造精神家园培育文明新风
             * child : [{"id":11,"system_id":1,"parent_id":7,"library_id":11,"level":1,"row":1,"parent_link":"7-11","name":"精神家园","mytask":[{"id":10,"target":"重视文化建设，打造文化品牌，增强文化自信。5 分","score":"5.00"},{"id":11,"target":"文体活动设施条件不断改善。5 分","score":"5.00"}]},{"id":12,"system_id":1,"parent_id":7,"library_id":12,"level":1,"row":1,"parent_link":"7-12","name":"文明风尚","mytask":[{"id":12,"target":"深入开展志愿服务活动，全年省直文明单位集体性志愿服务活动不少于 2 次，省直文明标兵单位不少于 3 次，省级文明单位不少于 4 次，省级文明标兵单位不少于 6次，全国文明单位不少于 8 次。10 分","score":"10.00"},{"id":13,"target":"组织开展\u201c文明餐桌行动\u201d、\u201c文明交通行动\u201d、\u201c文明出游宣传引导\u201d等活动。5 分","score":"5.00"},{"id":14,"target":"结合实际开展网上或手机文明传播活动。 5 分","score":"5.00"}]}]
             */

            private int id;
            private int system_id;
            private int parent_id;
            private int library_id;
            private int level;
            private int row;
            private String parent_link;
            private String name;
            private List<ChildBeanXX> child;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSystem_id() {
                return system_id;
            }

            public void setSystem_id(int system_id) {
                this.system_id = system_id;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public int getLibrary_id() {
                return library_id;
            }

            public void setLibrary_id(int library_id) {
                this.library_id = library_id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getRow() {
                return row;
            }

            public void setRow(int row) {
                this.row = row;
            }

            public String getParent_link() {
                return parent_link;
            }

            public void setParent_link(String parent_link) {
                this.parent_link = parent_link;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ChildBeanXX> getChild() {
                return child;
            }

            public void setChild(List<ChildBeanXX> child) {
                this.child = child;
            }

            public static class ChildBeanXX {
                /**
                 * id : 11
                 * system_id : 1
                 * parent_id : 7
                 * library_id : 11
                 * level : 1
                 * row : 1
                 * parent_link : 7-11
                 * name : 精神家园
                 * mytask : [{"id":10,"target":"重视文化建设，打造文化品牌，增强文化自信。5 分","score":"5.00"},{"id":11,"target":"文体活动设施条件不断改善。5 分","score":"5.00"}]
                 */

                private int id;
                private int system_id;
                private int parent_id;
                private int library_id;
                private int level;
                private int row;
                private String parent_link;
                private String name;
                private List<MytaskBeanXX> mytask;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSystem_id() {
                    return system_id;
                }

                public void setSystem_id(int system_id) {
                    this.system_id = system_id;
                }

                public int getParent_id() {
                    return parent_id;
                }

                public void setParent_id(int parent_id) {
                    this.parent_id = parent_id;
                }

                public int getLibrary_id() {
                    return library_id;
                }

                public void setLibrary_id(int library_id) {
                    this.library_id = library_id;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public int getRow() {
                    return row;
                }

                public void setRow(int row) {
                    this.row = row;
                }

                public String getParent_link() {
                    return parent_link;
                }

                public void setParent_link(String parent_link) {
                    this.parent_link = parent_link;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public List<MytaskBeanXX> getMytask() {
                    return mytask;
                }

                public void setMytask(List<MytaskBeanXX> mytask) {
                    this.mytask = mytask;
                }

                public static class MytaskBeanXX {
                    /**
                     * id : 10
                     * target : 重视文化建设，打造文化品牌，增强文化自信。5 分
                     * score : 5.00
                     */

                    private int id;
                    private String target;
                    private String score;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTarget() {
                        return target;
                    }

                    public void setTarget(String target) {
                        this.target = target;
                    }

                    public String getScore() {
                        return score;
                    }

                    public void setScore(String score) {
                        this.score = score;
                    }
                }
            }
        }

        public static class _$7Bean {
            /**
             * id : 8
             * system_id : 1
             * parent_id : 0
             * library_id : 8
             * level : 0
             * row : 1
             * parent_link : 8
             * name : 优化内部管理推进综合治理
             * child : [{"id":13,"system_id":1,"parent_id":8,"library_id":13,"level":1,"row":1,"parent_link":"8-13","name":"综合治理","mytask":[{"id":15,"target":"认真组织开展学法用法考试，考试成绩合格。5 分","score":"5.00"},{"id":16,"target":"结合实际面向干部职工或服务对象开展法治宣传教育。5 分","score":"5.00"}]}]
             */

            private int id;
            private int system_id;
            private int parent_id;
            private int library_id;
            private int level;
            private int row;
            private String parent_link;
            private String name;
            private List<ChildBeanXXX> child;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSystem_id() {
                return system_id;
            }

            public void setSystem_id(int system_id) {
                this.system_id = system_id;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public int getLibrary_id() {
                return library_id;
            }

            public void setLibrary_id(int library_id) {
                this.library_id = library_id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getRow() {
                return row;
            }

            public void setRow(int row) {
                this.row = row;
            }

            public String getParent_link() {
                return parent_link;
            }

            public void setParent_link(String parent_link) {
                this.parent_link = parent_link;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ChildBeanXXX> getChild() {
                return child;
            }

            public void setChild(List<ChildBeanXXX> child) {
                this.child = child;
            }

            public static class ChildBeanXXX {
                /**
                 * id : 13
                 * system_id : 1
                 * parent_id : 8
                 * library_id : 13
                 * level : 1
                 * row : 1
                 * parent_link : 8-13
                 * name : 综合治理
                 * mytask : [{"id":15,"target":"认真组织开展学法用法考试，考试成绩合格。5 分","score":"5.00"},{"id":16,"target":"结合实际面向干部职工或服务对象开展法治宣传教育。5 分","score":"5.00"}]
                 */

                private int id;
                private int system_id;
                private int parent_id;
                private int library_id;
                private int level;
                private int row;
                private String parent_link;
                private String name;
                private List<MytaskBeanXXX> mytask;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSystem_id() {
                    return system_id;
                }

                public void setSystem_id(int system_id) {
                    this.system_id = system_id;
                }

                public int getParent_id() {
                    return parent_id;
                }

                public void setParent_id(int parent_id) {
                    this.parent_id = parent_id;
                }

                public int getLibrary_id() {
                    return library_id;
                }

                public void setLibrary_id(int library_id) {
                    this.library_id = library_id;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public int getRow() {
                    return row;
                }

                public void setRow(int row) {
                    this.row = row;
                }

                public String getParent_link() {
                    return parent_link;
                }

                public void setParent_link(String parent_link) {
                    this.parent_link = parent_link;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public List<MytaskBeanXXX> getMytask() {
                    return mytask;
                }

                public void setMytask(List<MytaskBeanXXX> mytask) {
                    this.mytask = mytask;
                }

                public static class MytaskBeanXXX {
                    /**
                     * id : 15
                     * target : 认真组织开展学法用法考试，考试成绩合格。5 分
                     * score : 5.00
                     */

                    private int id;
                    private String target;
                    private String score;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTarget() {
                        return target;
                    }

                    public void setTarget(String target) {
                        this.target = target;
                    }

                    public String getScore() {
                        return score;
                    }

                    public void setScore(String score) {
                        this.score = score;
                    }
                }
            }
        }

        public static class _$8Bean {
            /**
             * id : 9
             * system_id : 1
             * parent_id : 0
             * library_id : 9
             * level : 0
             * row : 1
             * parent_link : 9
             * name : 美化内外环境构建生态文明
             * child : [{"id":14,"system_id":1,"parent_id":9,"library_id":14,"level":1,"row":1,"parent_link":"9-14","name":"环境","mytask":[{"id":17,"target":"贯彻绿色发展理念，积极推进资源节约型、环境友好型机关（单位）建设。 5 分","score":"5.00"}]}]
             */

            private int id;
            private int system_id;
            private int parent_id;
            private int library_id;
            private int level;
            private int row;
            private String parent_link;
            private String name;
            private List<ChildBeanXXXX> child;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSystem_id() {
                return system_id;
            }

            public void setSystem_id(int system_id) {
                this.system_id = system_id;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public int getLibrary_id() {
                return library_id;
            }

            public void setLibrary_id(int library_id) {
                this.library_id = library_id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getRow() {
                return row;
            }

            public void setRow(int row) {
                this.row = row;
            }

            public String getParent_link() {
                return parent_link;
            }

            public void setParent_link(String parent_link) {
                this.parent_link = parent_link;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ChildBeanXXXX> getChild() {
                return child;
            }

            public void setChild(List<ChildBeanXXXX> child) {
                this.child = child;
            }

            public static class ChildBeanXXXX {
                /**
                 * id : 14
                 * system_id : 1
                 * parent_id : 9
                 * library_id : 14
                 * level : 1
                 * row : 1
                 * parent_link : 9-14
                 * name : 环境
                 * mytask : [{"id":17,"target":"贯彻绿色发展理念，积极推进资源节约型、环境友好型机关（单位）建设。 5 分","score":"5.00"}]
                 */

                private int id;
                private int system_id;
                private int parent_id;
                private int library_id;
                private int level;
                private int row;
                private String parent_link;
                private String name;
                private List<MytaskBeanXXXX> mytask;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSystem_id() {
                    return system_id;
                }

                public void setSystem_id(int system_id) {
                    this.system_id = system_id;
                }

                public int getParent_id() {
                    return parent_id;
                }

                public void setParent_id(int parent_id) {
                    this.parent_id = parent_id;
                }

                public int getLibrary_id() {
                    return library_id;
                }

                public void setLibrary_id(int library_id) {
                    this.library_id = library_id;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public int getRow() {
                    return row;
                }

                public void setRow(int row) {
                    this.row = row;
                }

                public String getParent_link() {
                    return parent_link;
                }

                public void setParent_link(String parent_link) {
                    this.parent_link = parent_link;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public List<MytaskBeanXXXX> getMytask() {
                    return mytask;
                }

                public void setMytask(List<MytaskBeanXXXX> mytask) {
                    this.mytask = mytask;
                }

                public static class MytaskBeanXXXX {
                    /**
                     * id : 17
                     * target : 贯彻绿色发展理念，积极推进资源节约型、环境友好型机关（单位）建设。 5 分
                     * score : 5.00
                     */

                    private int id;
                    private String target;
                    private String score;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTarget() {
                        return target;
                    }

                    public void setTarget(String target) {
                        this.target = target;
                    }

                    public String getScore() {
                        return score;
                    }

                    public void setScore(String score) {
                        this.score = score;
                    }
                }
            }
        }

        public static class _$9Bean {
            /**
             * id : 10
             * system_id : 1
             * parent_id : 0
             * library_id : 10
             * level : 0
             * row : 2
             * parent_link : 10
             * name : 落实工作任务总结工作情况
             * child : [{"id":15,"system_id":1,"parent_id":10,"library_id":15,"level":1,"row":1,"parent_link":"10-15","name":"落实工作任务","mytask":[{"id":18,"target":"落实省直机关文明办组织开展的工作任务情况。5分。","score":"5.00"}]},{"id":16,"system_id":1,"parent_id":10,"library_id":16,"level":1,"row":1,"parent_link":"10-16","name":"全年工作总结","mytask":[{"id":19,"target":"深入总结本单位本部门全年文明创建工作情况（2000字以内） 5 分。","score":"4.00"}]}]
             */

            private int id;
            private int system_id;
            private int parent_id;
            private int library_id;
            private int level;
            private int row;
            private String parent_link;
            private String name;
            private List<ChildBeanXXXXX> child;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSystem_id() {
                return system_id;
            }

            public void setSystem_id(int system_id) {
                this.system_id = system_id;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public int getLibrary_id() {
                return library_id;
            }

            public void setLibrary_id(int library_id) {
                this.library_id = library_id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getRow() {
                return row;
            }

            public void setRow(int row) {
                this.row = row;
            }

            public String getParent_link() {
                return parent_link;
            }

            public void setParent_link(String parent_link) {
                this.parent_link = parent_link;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ChildBeanXXXXX> getChild() {
                return child;
            }

            public void setChild(List<ChildBeanXXXXX> child) {
                this.child = child;
            }

            public static class ChildBeanXXXXX {
                /**
                 * id : 15
                 * system_id : 1
                 * parent_id : 10
                 * library_id : 15
                 * level : 1
                 * row : 1
                 * parent_link : 10-15
                 * name : 落实工作任务
                 * mytask : [{"id":18,"target":"落实省直机关文明办组织开展的工作任务情况。5分。","score":"5.00"}]
                 */

                private int id;
                private int system_id;
                private int parent_id;
                private int library_id;
                private int level;
                private int row;
                private String parent_link;
                private String name;
                private List<MytaskBeanXXXXX> mytask;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSystem_id() {
                    return system_id;
                }

                public void setSystem_id(int system_id) {
                    this.system_id = system_id;
                }

                public int getParent_id() {
                    return parent_id;
                }

                public void setParent_id(int parent_id) {
                    this.parent_id = parent_id;
                }

                public int getLibrary_id() {
                    return library_id;
                }

                public void setLibrary_id(int library_id) {
                    this.library_id = library_id;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public int getRow() {
                    return row;
                }

                public void setRow(int row) {
                    this.row = row;
                }

                public String getParent_link() {
                    return parent_link;
                }

                public void setParent_link(String parent_link) {
                    this.parent_link = parent_link;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public List<MytaskBeanXXXXX> getMytask() {
                    return mytask;
                }

                public void setMytask(List<MytaskBeanXXXXX> mytask) {
                    this.mytask = mytask;
                }

                public static class MytaskBeanXXXXX {
                    /**
                     * id : 18
                     * target : 落实省直机关文明办组织开展的工作任务情况。5分。
                     * score : 5.00
                     */

                    private int id;
                    private String target;
                    private String score;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTarget() {
                        return target;
                    }

                    public void setTarget(String target) {
                        this.target = target;
                    }

                    public String getScore() {
                        return score;
                    }

                    public void setScore(String score) {
                        this.score = score;
                    }
                }
            }
        }
    }
}
