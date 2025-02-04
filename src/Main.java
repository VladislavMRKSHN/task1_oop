import java.io.*;
import java.util.ArrayList;
import java.util.List;
// Класс Student
class Student implements Serializable {
    private String name;
    private int age;
    private String gender;
    // Обычный конструктор
    public Student() {
    }
    // Конструктор с параметрами
    public Student(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    // Реализация паттерна "Билдер"
    public static class Builder {
        private String name;
        private int age;
        private String gender;
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setAge(int age) {
            this.age = age;
            return this;
        }
        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }
        public Student build() {
            return new Student(name, age, gender);
        }
    }
    // Геттеры
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }
    @Override
    public String toString() {
        return "Студент " +
                "Имя-'" + name + '\'' +
                ", Возраст-" + age +
                ", Пол-'" + gender + '\'';
    }
}
class StudentApp {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        // Добавление полей через разные конструкторы
        students.add(new Student("Анна", 20, "Женский"));
        students.add(new Student("Сергей", 22, "Мужской"));
        students.add(new
                Student.Builder().setName("Антон").setAge(21).setGender("Мужской").build());
        students.add(new Student("Арина", 19, "Женский"));
        String fileName = "students.ser";
        // Запись в файл
        writeStudentsToFile(students, fileName);
        // Чтение из файла
        List<Student> readStudents = readStudentsFromFile(fileName);
        // Сортировка и вывод студентов только одного пола - мужской
        System.out.println("Студенты мужского пола:");
        for (Student student : readStudents) {
            if ("Мужской".equalsIgnoreCase(student.getGender())) {
                System.out.println(student);
            }
        }
    }
    // Метод для записи списка студентов в файл
    private static void writeStudentsToFile(List<Student> students, String
            fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new
                FileOutputStream(fileName))) {
            oos.writeObject(students);
            System.out.println("Список студентов был записан в файл.");
        } catch (IOException e) {
            System.err.println("Ошибка при записи студентов в файл: " +
                    e.getMessage());
        }
    }
    // Метод для чтения списка студентов из файла
    private static List<Student> readStudentsFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new
                FileInputStream(fileName))) {
            return (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при чтении студентов из файла: " +
                    e.getMessage());
            return new ArrayList<>();

        }

    }
}