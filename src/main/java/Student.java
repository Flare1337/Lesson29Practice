public class Student {
    private int age;
    private String name;
    private int averageMark;
    private String faculty;
    private int ID;

    public Student(int ID, String name, int averageMark, String faculty, int age) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.faculty = faculty;
        this.averageMark = averageMark;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(int averageMark) {
        this.averageMark = averageMark;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
