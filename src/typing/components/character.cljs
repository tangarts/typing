(ns typing.components.character
  (:require 
    [typing.components.style :refer [word-css]]))

(defn character
  "
  A character can be:
    - waiting '' (or (nil? actual) (<= (count actual) i))
    - done -> {correct,incorrect}
    - current (= i (count @input-ref!))
    
    update class with atoms ['' 'done' 'current']
  "
  [expected actual variant]
  (let [is-match (= expected actual)]
    ; :class form is a vector? should it be?
    [:span {:style word-css
            :class [(case variant
                      "done" (if is-match "correct" "incorrect")
                      "current" "cursor"
                      "awaiting")]} expected]))

(defn get-character-state [a b]
  (cond 
    (< a b) "done"
    (= a b) "current"
    :else "awaiting"))


