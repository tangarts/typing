(ns typing.utils
  (:require
    [reagent.core :as r]
    [clojure.string :as str]))

; WPM means Words Per Minute. It is a measure of the output speed on a
; keyboard. Here's how it works:
; - start a timer and start typing for exactly 1 minute
; - count how many characters you just typed including spaces and
; punctuation
; - divide that value by 5 (the WPM system considers a word is 5 keystrokes)
; - the result is your Words Per Minute speed


(defn strip-text [text] 
  (vec (rest (str/split text #""))))


