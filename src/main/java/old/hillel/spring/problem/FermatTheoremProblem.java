package hillel.spring.problem;

public class FermatTheoremProblem implements  Problem {

    @Override
    public String simpleSolution() {
        return "Wrong";
    }

    @CorrectAnswer
    @Override
    public String complexSolution() {
        return "Correct";
    }
}
