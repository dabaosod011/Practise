//
//  CalculatorBrain.swift
//  Calculator
//
//  Created by Hai Xiao on 03/11/2017.
//  Copyright © 2017 Hai Xiao. All rights reserved.
//

import Foundation

struct CalculatorBrain: CustomStringConvertible {
    var description: String{
        get{
            return "I am the CalculatorBrain."
        }
    }
    
    mutating func addUnaryOperation(named symbol: String, _ operation: @escaping (Double) -> Double){
        opeartions[symbol] = Operation.unaryOperation(operation)
    }

    private var accumulator: Double?

    private enum Operation {
        case constant(Double)
        case unaryOperation((Double) -> Double)
        case binaryOperation((Double, Double) -> Double)
        case equals
    }

    private var opeartions: Dictionary<String , Operation> = [
        "π": Operation.constant(Double.pi),
        "e": Operation.constant(M_E),
        "√": Operation.unaryOperation(sqrt),
        "cos": Operation.unaryOperation(cos),
        "±": Operation.unaryOperation({ -$0 }),
        "+": Operation.binaryOperation({ $0 + $1 }),
        "−": Operation.binaryOperation({ $0 - $1 }),
        "×": Operation.binaryOperation({ $0 * $1 }),
        "÷": Operation.binaryOperation({ $0 / $1 }),
        "=": Operation.equals
    ]

    mutating func performOperation(_ symbol: String) {
        if let operation = opeartions[symbol] {
            switch operation {
            case .constant(let associatedConstantValue):
                accumulator = associatedConstantValue
            case .unaryOperation(let function):
                if accumulator != nil {
                    accumulator = function(accumulator!)
                }
            case .binaryOperation(let function):
                if accumulator != nil {
                    pendingBinaryOperation = PendingBinaryOperation(function: function, firstOperand: accumulator!)
                    accumulator = nil
                }
            case .equals:
                performPendingBinaryOperation()
            }
        }
    }

    private mutating func performPendingBinaryOperation() {
        if pendingBinaryOperation != nil && accumulator != nil {
            accumulator = pendingBinaryOperation!.perform(with: accumulator!)

            pendingBinaryOperation = nil
        }
    }

    private var pendingBinaryOperation: PendingBinaryOperation?

    private struct PendingBinaryOperation {
        let function: (Double, Double) -> Double
        let firstOperand: Double

        func perform(with secondOperand: Double) -> Double {
            return function(firstOperand, secondOperand)
        }
    }

    mutating func setOperand(_ operand: Double) {
        accumulator = operand
    }

    var result: Double? {
        get {
            return accumulator
        }

        // no set, so this is read-only variable value.
    }
}
