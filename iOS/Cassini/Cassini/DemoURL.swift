//
//  DemoURL.swift
//  Cassini
//
//  Created by Hai Xiao on 04/12/2017.
//  Copyright © 2017 Hai Xiao. All rights reserved.
//

import Foundation

struct DemoURL
{
    static let stanford = URL(string: "http://museum.stanford.edu/view/images/Thinker_000.jpg")
    static var NASA: Dictionary<String,URL> = {
        let NASAURLStrings = [
            "Cassini" : "http://www.jpl.nasa.gov/images/cassini/20090202/pia03883-full.jpg",
            "Earth" : "http://www.nasa.gov/sites/default/files/wave_earth_mosaic_3.jpg",
            "Saturn" : "http://www.nasa.gov/sites/default/files/saturn_collage.jpg"
        ]
        var urls = Dictionary<String,URL>()
        for (key, value) in NASAURLStrings {
            urls[key] = URL(string: value)
        }
        return urls
    }()
    
    static let baidu = URL(string: "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png")
}
