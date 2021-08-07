//
//  ImageViewController.swift
//  Cassini
//
//  Created by Hai Xiao on 04/12/2017.
//  Copyright © 2017 Hai Xiao. All rights reserved.
//

import UIKit

class ImageViewController: UIViewController {

    var imageUrl: URL? {
        didSet {
            image = nil
            if view.window != nil {
                fetchImage()
            }
        }
    }

    private func fetchImage() {
        print("fetchImage here.")
        if let url = imageUrl {
            let urlContents = try? Data(contentsOf: url)
            if let imageData = urlContents {
                image = UIImage(data: imageData)
            }
        }
    }


    override func viewDidLoad() {
        super.viewDidLoad()
        //view.addSubview(imageView)
        imageUrl = DemoURL.stanford
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if image == nil {
            fetchImage()
        }
    }
    
    @IBOutlet weak var scrollView: UIScrollView!{
        didSet{
            scrollView.delegate = self
            scrollView.minimumZoomScale = 0.03
            scrollView.maximumZoomScale = 1.5
            scrollView.contentSize = imageView.frame.size
            scrollView.addSubview(imageView)
        }
    }
    
    fileprivate var imageView = UIImageView()

    private var image: UIImage? {
        get {
            return imageView.image
        }
        set {
            imageView.image = newValue
            imageView.sizeToFit()
            scrollView?.contentSize = imageView.frame.size
        }
    }

}

extension ImageViewController: UIScrollViewDelegate{
    func viewForZooming(in scrollView: UIScrollView) -> UIView? {
        return imageView
    }
}
