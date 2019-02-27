package com.lucas.brush.main.personal.blogs;

import com.heid.frame.data.bean.IBean;

import java.util.List;

/**
 * @package com.lucas.brush.main.personal.blogs
 * @anthor luan
 * @date 2019/2/22
 * @des
 */
public class BlogsBean extends IBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * blogName : 封装TCP请求框架
         * blogUrl : https://blog.csdn.net/lucas19911226/article/details/80180352
         * id : 1
         * refreshCount : 0
         * taskStatus : 1
         * userId : 3
         */

        public String blogName;
        public String blogUrl;
        public int id;
        public int refreshCount;
        public int taskStatus;
        public int userId;
    }
}
