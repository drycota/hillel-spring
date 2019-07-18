package hillel.spring.problem;

public class ToBeOrNotToBeProblem implements Problem {
    @CorrectAnswer
    @Override
    public String simpleSolution() {
        return "Correct";
    }

    @Override
    public String complexSolution() {
        return "Wrong";
    }
}
