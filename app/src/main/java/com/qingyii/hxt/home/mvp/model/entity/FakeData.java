package com.qingyii.hxt.home.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xubo on 2017/6/25.
 */

public class FakeData implements Serializable {
    /**
     * error_msg : success
     * error_code : 0
     * data : {"data":[{"type":"activity","industry_id":1,"name":"省直工委中心组举行2017年第四次集中学习","id":25,"link":"/task/6/getDynamic?actid=25&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vd2ViLnNlZW8uY24vYXV0aC9sb2dpbiIsImlhdCI6MTUxMDIxOTEyNSwiZXhwIjoxNTQ2NTA3MTI1LCJuYmYiOjE1MTAyMTkxMjUsImp0aSI6IjlkZTdlOThjNjBhMjBmYzk0NWRkZjQzYjUyOTg5OGE1Iiwic3ViIjoyOTAwfQ.SmTFZak-HZ0CJ3RTvqM3N08NG3qhGa2sHbSWzbxxbF8"},{"type":"activity","industry_id":1,"name":"省直中心组大课堂全面解读中央纪委七次全会精神","id":26,"link":"/task/6/getDynamic?actid=26&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vd2ViLnNlZW8uY24vYXV0aC9sb2dpbiIsImlhdCI6MTUxMDIxOTEyNSwiZXhwIjoxNTQ2NTA3MTI1LCJuYmYiOjE1MTAyMTkxMjUsImp0aSI6IjlkZTdlOThjNjBhMjBmYzk0NWRkZjQzYjUyOTg5OGE1Iiwic3ViIjoyOTAwfQ.SmTFZak-HZ0CJ3RTvqM3N08NG3qhGa2sHbSWzbxxbF8"},{"type":"activity","industry_id":1,"name":"省直工委中心组举行2017年第二次集中学习","id":27,"link":"/task/6/getDynamic?actid=27&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vd2ViLnNlZW8uY24vYXV0aC9sb2dpbiIsImlhdCI6MTUxMDIxOTEyNSwiZXhwIjoxNTQ2NTA3MTI1LCJuYmYiOjE1MTAyMTkxMjUsImp0aSI6IjlkZTdlOThjNjBhMjBmYzk0NWRkZjQzYjUyOTg5OGE1Iiwic3ViIjoyOTAwfQ.SmTFZak-HZ0CJ3RTvqM3N08NG3qhGa2sHbSWzbxxbF8"},{"type":"activity","industry_id":1,"name":"省直工委宣传部支部召开组织生活会","id":28,"link":"/task/6/getDynamic?actid=28&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vd2ViLnNlZW8uY24vYXV0aC9sb2dpbiIsImlhdCI6MTUxMDIxOTEyNSwiZXhwIjoxNTQ2NTA3MTI1LCJuYmYiOjE1MTAyMTkxMjUsImp0aSI6IjlkZTdlOThjNjBhMjBmYzk0NWRkZjQzYjUyOTg5OGE1Iiwic3ViIjoyOTAwfQ.SmTFZak-HZ0CJ3RTvqM3N08NG3qhGa2sHbSWzbxxbF8"}]}
     */

    private String error_msg;
    private int error_code;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean  implements  Serializable {
            /**
             * type : activity
             * industry_id : 1
             * name : 省直工委中心组举行2017年第四次集中学习
             * id : 25
             * link : /task/6/getDynamic?actid=25&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vd2ViLnNlZW8uY24vYXV0aC9sb2dpbiIsImlhdCI6MTUxMDIxOTEyNSwiZXhwIjoxNTQ2NTA3MTI1LCJuYmYiOjE1MTAyMTkxMjUsImp0aSI6IjlkZTdlOThjNjBhMjBmYzk0NWRkZjQzYjUyOTg5OGE1Iiwic3ViIjoyOTAwfQ.SmTFZak-HZ0CJ3RTvqM3N08NG3qhGa2sHbSWzbxxbF8
             */

            private String type;
            private int industry_id;
            private String name;
            private int id;
            private String link;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getIndustry_id() {
                return industry_id;
            }

            public void setIndustry_id(int industry_id) {
                this.industry_id = industry_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }

    /**
     * name : 平台给你推送了一条系统消息，请查阅。
     * time : 刚刚
     */


}
