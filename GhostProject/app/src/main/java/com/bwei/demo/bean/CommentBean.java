package com.bwei.demo.bean;

import java.util.List;

/**
 * Created by ${李晨阳} on 2017/12/15.
 */

public class CommentBean {


    /**
     * msg : 成功
     * ret : {"pnum":1,"totalRecords":7,"records":20,"list":[{"msg":"小鲸鱼真的死了吗","phoneNumber":"第一次做 鸡蛋放肠和葱很好吃","dataId":"ff8080815f287394015f37f2df667fd1","userPic":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqglgWicQJ1ic3BiaQDCdFv1KUo3gibhbAjxoXDnoQAWntZgibv0uviaLueWtj8mYoKibiaJuK9USWpNRyA4A/0","time":"2017-10-20 12:03:04","likeNum":2},{"msg":"很不错的电影 他们感动了我","phoneNumber":"131****2143","dataId":"ff8080815e37cc09015e4b3c895f79c9","userPic":"http://phonemovie.ks3-cn-beijing.ksyun.com/%2Fupload/memberPic/2017/02/09/1486612500512154963.jpg","time":"2017-09-04 12:53:32","likeNum":0},{"msg":"用手机投到电视上看完的，效果比手机强多了。","phoneNumber":"自欺欺人","dataId":"ff8080815cb03cdc015cc46d3dba2d26","userPic":"","time":"2017-06-25 15:35:12","likeNum":3},{"msg":"这部片子不错看了很感动'。","phoneNumber":"豆子","dataId":"ff8080815cb03cdc015ccd52198948e1","userPic":"http://q.qlogo.cn/qqapp/1101034181/0F9530A11E7544E2E11664D0DC738BB7/40","time":"2017-06-22 09:02:08","likeNum":0},{"msg":"朋友推荐的这部片子，看完之后觉得真的很棒！","phoneNumber":"伪装坚强","dataId":"ff8080815cb03cdc015cc46d3dc22d29","userPic":"","time":"2017-06-13 13:35:12","likeNum":1},{"msg":"这片子早就应该看了，幸好没有错过这么好的片子。","phoneNumber":"你不懂我","dataId":"ff8080815cb03cdc015cc46d3dc02d28","userPic":"","time":"2017-06-04 14:35:12","likeNum":1},{"msg":"演员的表演很卖力嘛！","phoneNumber":"盗不走的爱人","dataId":"ff8080815cb03cdc015cc46d3dbe2d27","userPic":"","time":"2017-05-21 19:52:12","likeNum":1}],"totalPnum":1}
     * code : 200
     */

    private String msg;
    private RetBean ret;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RetBean getRet() {
        return ret;
    }

    public void setRet(RetBean ret) {
        this.ret = ret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class RetBean {
        /**
         * pnum : 1
         * totalRecords : 7
         * records : 20
         * list : [{"msg":"小鲸鱼真的死了吗","phoneNumber":"第一次做 鸡蛋放肠和葱很好吃","dataId":"ff8080815f287394015f37f2df667fd1","userPic":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqglgWicQJ1ic3BiaQDCdFv1KUo3gibhbAjxoXDnoQAWntZgibv0uviaLueWtj8mYoKibiaJuK9USWpNRyA4A/0","time":"2017-10-20 12:03:04","likeNum":2},{"msg":"很不错的电影 他们感动了我","phoneNumber":"131****2143","dataId":"ff8080815e37cc09015e4b3c895f79c9","userPic":"http://phonemovie.ks3-cn-beijing.ksyun.com/%2Fupload/memberPic/2017/02/09/1486612500512154963.jpg","time":"2017-09-04 12:53:32","likeNum":0},{"msg":"用手机投到电视上看完的，效果比手机强多了。","phoneNumber":"自欺欺人","dataId":"ff8080815cb03cdc015cc46d3dba2d26","userPic":"","time":"2017-06-25 15:35:12","likeNum":3},{"msg":"这部片子不错看了很感动'。","phoneNumber":"豆子","dataId":"ff8080815cb03cdc015ccd52198948e1","userPic":"http://q.qlogo.cn/qqapp/1101034181/0F9530A11E7544E2E11664D0DC738BB7/40","time":"2017-06-22 09:02:08","likeNum":0},{"msg":"朋友推荐的这部片子，看完之后觉得真的很棒！","phoneNumber":"伪装坚强","dataId":"ff8080815cb03cdc015cc46d3dc22d29","userPic":"","time":"2017-06-13 13:35:12","likeNum":1},{"msg":"这片子早就应该看了，幸好没有错过这么好的片子。","phoneNumber":"你不懂我","dataId":"ff8080815cb03cdc015cc46d3dc02d28","userPic":"","time":"2017-06-04 14:35:12","likeNum":1},{"msg":"演员的表演很卖力嘛！","phoneNumber":"盗不走的爱人","dataId":"ff8080815cb03cdc015cc46d3dbe2d27","userPic":"","time":"2017-05-21 19:52:12","likeNum":1}]
         * totalPnum : 1
         */

        private int pnum;
        private int totalRecords;
        private int records;
        private int totalPnum;
        private List<ListBean> list;

        public int getPnum() {
            return pnum;
        }

        public void setPnum(int pnum) {
            this.pnum = pnum;
        }

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public int getRecords() {
            return records;
        }

        public void setRecords(int records) {
            this.records = records;
        }

        public int getTotalPnum() {
            return totalPnum;
        }

        public void setTotalPnum(int totalPnum) {
            this.totalPnum = totalPnum;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * msg : 小鲸鱼真的死了吗
             * phoneNumber : 第一次做 鸡蛋放肠和葱很好吃
             * dataId : ff8080815f287394015f37f2df667fd1
             * userPic : http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqglgWicQJ1ic3BiaQDCdFv1KUo3gibhbAjxoXDnoQAWntZgibv0uviaLueWtj8mYoKibiaJuK9USWpNRyA4A/0
             * time : 2017-10-20 12:03:04
             * likeNum : 2
             */

            private String msg;
            private String phoneNumber;
            private String dataId;
            private String userPic;
            private String time;
            private int likeNum;

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getDataId() {
                return dataId;
            }

            public void setDataId(String dataId) {
                this.dataId = dataId;
            }

            public String getUserPic() {
                return userPic;
            }

            public void setUserPic(String userPic) {
                this.userPic = userPic;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getLikeNum() {
                return likeNum;
            }

            public void setLikeNum(int likeNum) {
                this.likeNum = likeNum;
            }
        }
    }
}
