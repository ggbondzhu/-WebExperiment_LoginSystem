package domain;

public class UserInfo extends User {
    boolean gender;
    int age;
    String school;
    String college;
    String major;
    String sno;
    String nativePlace;
    int projectNum;
    int fansNum;
    int assetNum;
    String about;
    String avatarUrl;
    String name;
    String techang;

    public UserInfo() {

    }

    @Override
    public String toString() {
        return "UserInfoVo{" +
                "gender=" + gender +
                ", age=" + age +
                ", school='" + school + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", sno='" + sno + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", projectNum=" + projectNum +
                ", fansNum=" + fansNum +
                ", assetNum=" + assetNum +
                ", about='" + about + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", name='" + name + '\'' +
                ", techang='" + techang + '\'' +
                '}';
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }


    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(int projectNum) {
        this.projectNum = projectNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public int getAssetNum() {
        return assetNum;
    }

    public void setAssetNum(int assetNum) {
        this.assetNum = assetNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTechang() {
        return techang;
    }

    public void setTechang(String techang) {
        this.techang = techang;
    }
}
