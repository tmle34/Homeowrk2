package sample;

import java.util.UUID;

public class Student implements Students1 {
    public String Name1;
    public UUID Id;
    public int Age;
    public String Major;
    public double GPA;
    @Override
    public String toString()
    {
        return(this.Name1+" "+this.Id+" "+this.Age+" "+this.Major+" "+this.GPA);
    }
}
