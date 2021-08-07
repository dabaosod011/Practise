//
//  ViewController.swift
//  FaceIt
//
//  Created by Hai Xiao on 09/11/2017.
//  Copyright © 2017 Hai Xiao. All rights reserved.
//

import UIKit

class FaceViewController: VCLLoggingViewController {

    @IBOutlet weak var faceView: FaceView! {
        didSet {
            let handler = #selector(FaceView.changeScale(byReactingTo:))

            let pinchRecognizer = UIPinchGestureRecognizer(target: faceView, action: handler)
            faceView.addGestureRecognizer(pinchRecognizer)

            let tapRecognizer = UITapGestureRecognizer(target: self, action: #selector(toggleEyes(byReactingTo:)))
            tapRecognizer.numberOfTapsRequired = 1
            faceView.addGestureRecognizer(tapRecognizer)

            let swipeUpRecognizer = UISwipeGestureRecognizer(target: self, action: #selector(increaseHappiness))
            swipeUpRecognizer.direction = .up
            faceView.addGestureRecognizer(swipeUpRecognizer)

            let swipeDownRecognizer = UISwipeGestureRecognizer(target: self, action: #selector(decreaseHappiness))
            swipeDownRecognizer.direction = .down
            faceView.addGestureRecognizer(swipeDownRecognizer)

            updateUI()
        }
    }

    @objc func increaseHappiness() {
        expression = expression.happier
    }

    @objc func decreaseHappiness() {
        expression = expression.sadder
    }

    @objc func toggleEyes(byReactingTo tapRecognizer: UITapGestureRecognizer) {
        if tapRecognizer.state == .ended {
            let eyes: FacialExpression.Eyes = (expression.eyes == .closed) ? .open : .closed
            expression = FacialExpression(eyes: eyes, mouth: expression.mouth)
        }
    }

    var expression = FacialExpression(eyes: .closed, mouth: .frown) {
        didSet {
            updateUI()
        }
    }

    private func updateUI() {
        switch expression.eyes {
        case .open:
            faceView?.eyesOpen = true
        case .closed:
            faceView?.eyesOpen = false
        case .squinting:
            faceView?.eyesOpen = false
        }

        faceView?.mouthCurvature = moithCurvatures[expression.mouth] ?? 0.0
    }

    private let moithCurvatures = [
        FacialExpression.Mouth.grin: 0.5,
            .frown: -1.0,
            .smile: 1.0,
            .smirk: -0.5,
            .neutral: 0
    ]

}

