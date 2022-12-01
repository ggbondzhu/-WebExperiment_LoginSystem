import com.alibaba.fastjson.JSONObject;
import domain.UserInfo;

public class test {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        UserInfo userInfo = new UserInfo();
        userInfo = JSONObject.parseObject("{gender:false, age:0, school:'null', college:'123456789',nativePlace:'changsha'}", UserInfo.class);
        ;
        System.out.println(userInfo);
        // userInfoVo.setSchool("test");
//        userInfoVo.setCollege("123456789");
//        userInfoVo.setUserID(11);
////        userInfoVo.setAge(18);
////        userInfoVo.setNativePlace("changsha");
//        //EmailUtils.sendEmail("12", "@qq.com");
//        String sql = "UPDATE `loginsystem`.`personal_info` SET `gender` = IFNULL(?,`personal_info`.gender), " +
//                " `school` = IFNULL(?,`personal_info`.school), `college` = IFNULL(?,`personal_info`.college)," +
//                " `major` = IFNULL(?,`personal_info`.major), `sno` = IFNULL(?,`personal_info`.sno), `native_place` = IFNULL(?,`personal_info`.native_place)," +
//                "`age` = IF(?,?,`personal_info`.age), `project_num` = IF(?,?,`personal_info`.project_num), `fans_num` = IF(?,?,`personal_info`.fans_num), `asset_num` = IF(?,?,`personal_info`.asset_num)," +
//                " `about` = IFNULL(?,`personal_info`.about), `avatar_url` = IFNULL(?,`personal_info`.avatar_url) WHERE `user_id` = ?;";
//        try {
//            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
//            ps.setBoolean(1, userInfoVo.isGender());
//            ps.setString(2, userInfoVo.getSchool());
//            ps.setString(3, userInfoVo.getCollege());
//            ps.setString(4, userInfoVo.getMajor());
//            ps.setString(5, userInfoVo.getSno());
//            ps.setString(6, userInfoVo.getNativePlace());
//            ps.setInt(7, userInfoVo.getAge());
//            ps.setInt(8, userInfoVo.getAge());
//            ps.setInt(9, userInfoVo.getProjectNum());
//            ps.setInt(10, userInfoVo.getProjectNum());
//            ps.setInt(11, userInfoVo.getFansNum());
//            ps.setInt(12, userInfoVo.getFansNum());
//            ps.setInt(13, userInfoVo.getAssetNum());
//            ps.setInt(14, userInfoVo.getAssetNum());
//            ps.setString(15, userInfoVo.getAbout());
//            ps.setString(16, userInfoVo.getAvatarUrl());
//            ps.setInt(17, userInfoVo.getUserID());
//            int result = ps.executeUpdate();
//            System.out.println(userInfoVo.toString());
//            if (result == 1) {
//
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
