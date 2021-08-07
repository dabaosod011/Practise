//: Playground - noun: a place where people can play

import UIKit

var str = "Hello, playground"

1 < 2

(1, "A") == (1, "A")
(1, "A") < (1, "B")

let a = 1, b = 2
let c = a + (b < 3 ? 0:1)


let defaultColorName = "red"
var currentColorName: String?

var colorName = currentColorName ?? defaultColorName

let softWrappedQuotation = """
The White Rabbit put on his spectacles.  "Where shall I begin, \
please your Majesty?" he asked.

"Begin at the beginning," the King said gravely, "and go on \
till you come to the end; then stop."
"""

let dollarSign = "\u{24}"
let blackHeart = "\u{2665}"
let sparklingHeart = "\u{1F496}"

let eAcute: Character = "\u{E9}"                         // é
let combinedEAcute: Character = "\u{65}\u{301}"

let unusualMenagerie = "Koala 🐨, Snail 🐌, Penguin 🐧, Dromedary 🐪"
print("unusualMenagerie has \(unusualMenagerie.count) characters")
for char in unusualMenagerie{
    print(char)
}


let romeoAndJuliet = [
    "Act 1 Scene 1: Verona, A public place",
    "Act 1 Scene 2: Capulet's mansion",
    "Act 1 Scene 3: A room in Capulet's mansion",
    "Act 1 Scene 4: A street outside Capulet's mansion",
    "Act 1 Scene 5: The Great Hall in Capulet's mansion",
    "Act 2 Scene 1: Outside Capulet's mansion",
    "Act 2 Scene 2: Capulet's orchard",
    "Act 2 Scene 3: Outside Friar Lawrence's cell",
    "Act 2 Scene 4: A street in Verona",
    "Act 2 Scene 5: Capulet's mansion",
    "Act 2 Scene 6: Friar Lawrence's cell"
]

var act1SceneCount = 0
for scene in romeoAndJuliet {
    if scene.hasPrefix("Act 1 ") {
        act1SceneCount += 1
    }
}
print("There are \(act1SceneCount) scenes in Act 1")

var array1 = [Int]()
array1 += Array(repeating: 10, count: 5)
print(array1)
var array2 = array1 + Array(repeating:8, count: 10)
print(array2)


// array
var shoppingList = ["egg","milk","apple","orange"]
shoppingList.count
shoppingList.append("banana")

for (index, value) in shoppingList.enumerated(){
    print("\(String(index)): \(value)")
}

//set
//var letters = Set<Character>()
//letters.insert("a")
//letters.insert("b")
//letters.insert("c")
//print("there ars \(letters.count) characters.")

var letters:Set = ["a","b","c","d","e","f","g","h","i"]
var numbers: Set = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"]

print(letters.intersection(numbers))
print(letters.union(numbers))
print(letters.subtract(numbers))
print(letters.symmetricDifference(numbers))


//dictionary
var namesOfIntegers = [Int: String]()
namesOfIntegers[1]="one"
namesOfIntegers[2]="two"

if let name = namesOfIntegers[1]{
    print(name)
}
namesOfIntegers.updateValue("TWO", forKey: 2)


//game:

let finalSquare = 25
var board = [Int](repeating: 0, count: finalSquare + 1)
board[03] = +08; board[06] = +11; board[09] = +09; board[10] = +02
board[14] = -10; board[19] = -11; board[22] = -02; board[24] = -08

var square = 0
var diceRoll = 0
while square < finalSquare {
    // 掷骰子
    diceRoll += 1
    if diceRoll == 7 { diceRoll = 1 }
    // 根据点数移动
    square += diceRoll
    if square < board.count {
        // 如果玩家还在棋盘上，顺着梯子爬上去或者顺着蛇滑下去
        square += board[square]
    }
}
print("Game over!")


let yetAnotherPoint = (1, -1)
switch yetAnotherPoint {
case let (x, y) where x == y:
    print("(\(x), \(y)) is on the line y=x")
case let (x, y) where x == -y:
    print("(\(x), \(y)) is on the line y=-x")
case let (x, y):
    print("(\(x), \(y)) is just some arbitrary point")
}

