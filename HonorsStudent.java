package studentmanagementsystem;

public class HonorsStudent extends Student {

    public HonorsStudent(String id, String name) {
        super(id, name);
    }

    // Polymorphic override: Honors students receive a 5% weighted bonus (capped at 100)
    @Override
    public double calculateAverage() {
        double baseAvg = super.calculateAverage();
        if (baseAvg == 0.0) {
            return 0.0;
        }
        return Math.min(100.0, baseAvg * 1.05);
    }

    @Override
    public String getStudentType() {
        return "Honors";
    }
}
