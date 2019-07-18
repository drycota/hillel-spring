package hillel.spring.problem;

import lombok.SneakyThrows;
import lombok.val;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProblemSolver {
    public String solve(Problem problem) {
//        if (problem instanceof ToBeOrNotToBeProblem)
//            return problem.simpleSolution();
//        else
//            return problem.complexSolution();
        val aClass = problem.getClass();
        val methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(CorrectAnswer.class)) {
//                try {
//                    method.invoke(problem);
//                } catch (Exception e) {
//                    throw  new RuntimeException(e);
//                }
                return invoke(method, problem);
            }
        }
        throw new RuntimeException("There is no methods marked id CorrectAnswer");
    }

    @SneakyThrows
    private String invoke(Method m, Object o) {
        return (String) m.invoke(o);
    }
}
