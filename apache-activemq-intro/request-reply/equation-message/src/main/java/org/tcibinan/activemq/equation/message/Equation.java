package org.tcibinan.activemq.equation.message;

public record Equation(Long left,
                       Long right,
                       EquationOperator operator) {
}
