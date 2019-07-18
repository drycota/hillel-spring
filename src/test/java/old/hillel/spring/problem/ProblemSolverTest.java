package hillel.spring.problem;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

public class ProblemSolverTest {
    @Test
    public void souldBeComplexSolution() {
        val solution = new ProblemSolver().solve(new FermatTheoremProblem());
        assertThat(solution).contains("Correct");
    }

    @Test
    public void souldBeSimpleSolution() {
        val solution = new ProblemSolver().solve(new ToBeOrNotToBeProblem());
        assertThat(solution).contains("Correct");
    }
}