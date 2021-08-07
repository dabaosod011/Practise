//
//  ViewController.swift
//  Calculator
//
//  Created by Hai Xiao on 02/11/2017.
//  Copyright © 2017 Hai Xiao. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    @IBOutlet weak var display: UILabel!
    var userIsInTheMiddleOfTyping = false

    override func viewDidLoad() {
        super.viewDidLoad()
        brain.addUnaryOperation(named: "✅") { [unowned self] in
            self.display.textColor = UIColor.green
            return sqrt($0) }
    }

    @IBAction func touchDigit(_ sender: UIButton) {
        let digit = sender.currentTitle!
        if userIsInTheMiddleOfTyping {
            let textCurrentlyInDisplay = display.text!
            display.text = textCurrentlyInDisplay + digit }
        else {
            display.text = digit
            userIsInTheMiddleOfTyping = true
        }

    }

    var displayValue: Double {
        get {
            return Double(display.text!)!
        }
        set {
            display.text = String(newValue)
        }
    }

    private var brain = CalculatorBrain()

    @IBAction func performOperation(_ sender: UIButton) {
        print("\(brain)")
        if userIsInTheMiddleOfTyping {
            brain.setOperand(displayValue)
            userIsInTheMiddleOfTyping = false
        }

        if let mathematicalSymbol = sender.currentTitle {
            brain.performOperation(mathematicalSymbol)
        }

        if let result = brain.result {
            displayValue = result
        }
    }

}

